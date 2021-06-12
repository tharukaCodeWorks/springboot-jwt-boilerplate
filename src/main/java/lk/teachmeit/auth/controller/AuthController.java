package lk.teachmeit.auth.controller;

import lk.teachmeit.auth.dto.ConfirmEmailDto;
import lk.teachmeit.auth.dto.LoginUserDto;
import lk.teachmeit.auth.dto.ResponseWrapper;
import lk.teachmeit.auth.dto.UserDto;
import lk.teachmeit.auth.exceptions.EmailAlreadyException;
import lk.teachmeit.auth.exceptions.EmailNotVerifiedException;
import lk.teachmeit.auth.exceptions.InvalidCodeException;
import lk.teachmeit.auth.exceptions.InvalidCredentialsException;
import lk.teachmeit.auth.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper> signIn(@RequestBody LoginUserDto loginUserDto) throws AuthenticationException {
        try{
            return ResponseEntity.ok(new ResponseWrapper(authService.signIn(loginUserDto), "success", "User authentication successful"));
        } catch (EmailNotVerifiedException emailNotVerifiedException){
            return ResponseEntity.ok(new ResponseWrapper(null, "un-verified", emailNotVerifiedException.getMessage()));
        } catch (IncompatibleClassChangeError incompatibleClassChangeError) {
            return ResponseEntity.ok(new ResponseWrapper(null, "wrong", incompatibleClassChangeError.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseWrapper(null, "failed", "Something went wrong! Please contact developer"));
        }
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public Object saveUser(@RequestBody UserDto user) {
        try {
            return new ResponseWrapper(authService.signUp(user), "success", "User successfully registered");
        } catch (EmailAlreadyException emailAlreadyException) {
            return new ResponseWrapper(null, "already", emailAlreadyException.getMessage());
        } catch (IOException ioException) {
            return new ResponseWrapper(null, "email-sending-failed", "Email sending failed");
        } catch (Exception exception) {
            return new ResponseWrapper(null, "failed", "Something went wrong! Please contact developer");
        }
    }

    @RequestMapping(value="/email/verify", method = RequestMethod.POST)
    public ResponseWrapper verifyEmail(@RequestBody ConfirmEmailDto confirm) throws IOException {
        try {
            return new ResponseWrapper(authService.verifyEmail(confirm), "success", "Email verification Success");
        } catch (InvalidCredentialsException invalidCredentialsException) {
            return new ResponseWrapper(authService.verifyEmail(confirm), "wrong-email", invalidCredentialsException.getMessage());
        } catch (InvalidCodeException invalidCodeException) {
            return new ResponseWrapper(authService.verifyEmail(confirm), "invalid-code", invalidCodeException.getMessage());
        } catch (IOException ioException) {
            return new ResponseWrapper(null, "email-sending-failed", "Email sending failed");
        } catch (Exception e) {
            return new ResponseWrapper(null, "failed", "Something went wrong! Please contact developer");
        }
    }

    @RequestMapping(value="/password/forgot", method = RequestMethod.POST)
    public Object forgotPassword(@RequestParam("email") String email) {
        try{
            return new ResponseWrapper(authService.forgotPassword(email), "success", "Forgot password request success");
        } catch(IOException ioException){
            return new ResponseWrapper(null, "email-sending-failed", "Email sending failed");
        } catch (InvalidCredentialsException invalidCredentialsException) {
            return new ResponseWrapper(null, "wrong-email", invalidCredentialsException.getMessage());
        } catch(Exception e){
            return new ResponseWrapper(null, "failed", "Something went wrong! Please contact developer");
        }
    }

    @RequestMapping(value="/password/verify-code", method = RequestMethod.POST)
    public Object verifyResetCode(@RequestParam("email") String email, @RequestParam("resetCode") String resetCode) {
        try{
            return new ResponseWrapper(authService.checkPasswordVerifyCode(email, resetCode), "success", "Forgot password request success");
        } catch (InvalidCodeException invalidCodeException) {
            return new ResponseWrapper(null, "invalid-code", invalidCodeException.getMessage());
        } catch (Exception e) {
            return new ResponseWrapper(null, "failed", "Something went wrong! Please contact developer");
        }
    }

    @RequestMapping(value="/password/reset", method = RequestMethod.POST)
    public Object verifyResetCode(@RequestParam("email") String email, @RequestParam("resetCode") String resetCode, @RequestParam("newPassword") String newPassword) {
        try{
            return new ResponseWrapper(authService.resetPassword(email, resetCode, newPassword), "success", "Password resettled successfully");
        }catch (IOException ioException) {
            return new ResponseWrapper(null, "email-sending-failed", "Email sending failed");
        } catch (InvalidCodeException invalidCodeException) {
            return new ResponseWrapper(null, "invalid-code", invalidCodeException.getMessage());
        } catch (InvalidCredentialsException invalidCredentialsException) {
            return new ResponseWrapper(null, "wrong-email", invalidCredentialsException.getMessage());
        } catch (Exception exception){
            return new ResponseWrapper(null, "failed", "Something went wrong! Please contact developer");
        }

    }
}
