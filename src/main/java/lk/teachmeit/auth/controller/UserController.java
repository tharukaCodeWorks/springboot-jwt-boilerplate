package lk.teachmeit.auth.controller;

import lk.teachmeit.auth.dto.ConfirmEmailDto;
import lk.teachmeit.auth.dto.UserDto;
import lk.teachmeit.auth.enums.MailMode;
import lk.teachmeit.auth.model.IModel;
import lk.teachmeit.auth.model.ResponseWrapper;
import lk.teachmeit.auth.model.User;
import lk.teachmeit.auth.service.HtmlProcessService;
import lk.teachmeit.auth.service.interfaces.UserService;
import lk.teachmeit.auth.util.EmailService;
import lk.teachmeit.auth.util.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private HtmlProcessService htmlProcessService;

    //@Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public List<User> listUser(){
        return userService.findAll();
    }

    //@Secured("ROLE_USER")
    //@PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id){
        return userService.findById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/api/token/ping", method = RequestMethod.GET)
    public ResponseWrapper ping(){
        return new ResponseWrapper(null, "Success", "Success");
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/me/details")
    public ResponseWrapper getMeDetails(HttpServletRequest req){

        List<IModel> user = new ArrayList<>();
        user.add(jwtTokenUtil.getAuthUser(req));
        return new ResponseWrapper(user,"success", "User Details");
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public Object saveUser(@RequestBody UserDto user) throws IOException, MessagingException {
        if( userService.findOne(user.getEmail())==null){
            User userObject = new User();
            String resetCode = getRandomNumberString();
            userObject.setFirstName(user.getFirstName());
            userObject.setLastName(user.getLastName());
            userObject.setEmail(user.getEmail());
            userObject.setPassword(user.getPassword());
            userObject.setEmailVerifyCode(resetCode);
            userObject.setIsEmailVerified("FALSE");
            userObject.setPassword(bcryptEncoder.encode(user.getPassword()));

            Context emailContext = new Context();
            emailContext.setVariable("user", userObject);
            emailContext.setVariable("code", resetCode);

            emailService.sendHtml("Email Verification ", this.htmlProcessService.processHtml(emailContext, "email/email-verification"), MailMode.SMPT,userObject.getEmail());

            User savedUser = userService.save(userObject);
            ArrayList responseList = new ArrayList();
            responseList.add(savedUser);
            ResponseWrapper response = new ResponseWrapper(responseList, "success", "You successfully singed up!");
            return response;
        } else {
            return new ResponseWrapper(null, "already", "Email address already using");
        }
    }

    @RequestMapping(value="guest/email/verify", method = RequestMethod.POST)
    public Object verifyEmail(@RequestBody ConfirmEmailDto confirm) throws IOException, MessagingException {
        User authUser = userService.findOne(confirm.getEmail());
        if( authUser != null){
            if(confirm.getVerifyCode().equals(authUser.getEmailVerifyCode())) {
                authUser.setIsEmailVerified("TRUE");
                authUser.setEmailVerifyCode(null);
                userService.save(authUser);
                emailService.sendHtml("Email Verification", "Welcome to the Spring Email", MailMode.SMPT, authUser.getEmail());
                return new ResponseWrapper(null, "success", "Email address verified successfully");
            } else {
                return new ResponseWrapper(null, "wrong", "You provided verify code is wrong");
            }
        } else {
            return new ResponseWrapper(null, "wrong", "You provided email address is wrong");
        }
    }

    @RequestMapping(value="guest/password/forgot", method = RequestMethod.POST)
    public Object forgotPassword(@RequestParam("email") String email, @RequestParam("mode") String mode) throws IOException, MessagingException {
        User authUser = userService.findOne(email);
        String resetCode = getRandomNumberString();
        authUser.setPasswordResetCode(resetCode);
        userService.save(authUser);

        Context emailContext = new Context();
        emailContext.setVariable("user", authUser);
        emailContext.setVariable("code", resetCode);

        emailService.sendHtml("Email Verification", this.htmlProcessService.processHtml(emailContext, "email/email-verification"), MailMode.SMPT, authUser.getEmail());

        return new ResponseWrapper(null, "success", "Sent the password reset link!");
    }

    @RequestMapping(value="guest/password/verify-code", method = RequestMethod.POST)
    public Object verifyResetCode(@RequestParam("email") String email, @RequestParam("resetCode") String resetCode) throws IOException, MessagingException {
        User authUser = userService.findOne(email);
        userService.save(authUser);
        if(resetCode.equals(authUser.getPasswordResetCode())) {
            return new ResponseWrapper(null, "success", "Correct reset code");
        } else {
            return new ResponseWrapper(null, "wrong", "Wrong reset code");
        }
    }

    @RequestMapping(value="guest/password/reset", method = RequestMethod.POST)
    public Object verifyResetCode(@RequestParam("email") String email, @RequestParam("resetCode") String resetCode, @RequestParam("newPassword") String newPassword) throws IOException, MessagingException {
        User authUser = userService.findOne(email);
        if(resetCode.equals(authUser.getPasswordResetCode())) {
            authUser.setPassword(bcryptEncoder.encode(newPassword));
            authUser.setPasswordResetCode(null);
            userService.save(authUser);
            emailService.sendHtml("Password reset successfully", "Your password reset successfully!", MailMode.SMPT, email);
            return new ResponseWrapper(null, "success", "Password reset successful");
        } else {
            return new ResponseWrapper(null, "wrong", "Wrong reset code!");
        }
    }

    private static String getRandomNumberString() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
