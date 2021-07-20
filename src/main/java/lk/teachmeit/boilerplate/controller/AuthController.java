package lk.teachmeit.boilerplate.controller;

import lk.teachmeit.boilerplate.dto.ConfirmEmailDto;
import lk.teachmeit.boilerplate.dto.LoginUserDto;
import lk.teachmeit.boilerplate.dto.ResponseWrapper;
import lk.teachmeit.boilerplate.dto.UserDto;
import lk.teachmeit.boilerplate.exceptions.EmailAlreadyException;
import lk.teachmeit.boilerplate.exceptions.EmailNotVerifiedException;
import lk.teachmeit.boilerplate.exceptions.InvalidCodeException;
import lk.teachmeit.boilerplate.exceptions.InvalidCredentialsException;
import lk.teachmeit.boilerplate.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<ResponseWrapper> signIn(@RequestBody LoginUserDto loginUserDto) throws AuthenticationException {
        try{
            return ResponseEntity.ok(new ResponseWrapper(authService.signIn(loginUserDto), "success", "User authentication successful"));
        } catch (EmailNotVerifiedException emailNotVerifiedException){
            return ResponseEntity.ok(new ResponseWrapper(null, "un-verified", emailNotVerifiedException.getMessage()));
        } catch (InvalidCredentialsException invalidCredentialsException) {
            return ResponseEntity.ok(new ResponseWrapper(null, "wrong", invalidCredentialsException.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseWrapper(null, "failed", "Something went wrong! Please contact developer"));
        }
    }

    @PostMapping(value = "/signup")
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

    @PostMapping(value = "/email/verify")
    public ResponseWrapper verifyEmail(@RequestBody ConfirmEmailDto confirm) throws IOException {
        try {
            return new ResponseWrapper(authService.verifyEmail(confirm), "success", "Email verification Success");
        } catch (InvalidCredentialsException invalidCredentialsException) {
            return new ResponseWrapper(null, "wrong-email", invalidCredentialsException.getMessage());
        } catch (InvalidCodeException invalidCodeException) {
            return new ResponseWrapper(null, "invalid-code", invalidCodeException.getMessage());
        } catch (IOException ioException) {
            return new ResponseWrapper(null, "email-sending-failed", "Email sending failed");
        } catch (Exception e) {
            return new ResponseWrapper(null, "failed", "Something went wrong! Please contact developer");
        }
    }

    @PostMapping(value = "/password/forgot")
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

    @PostMapping(value = "/password/verify-code")
    public Object verifyResetCode(@RequestParam("email") String email, @RequestParam("resetCode") String resetCode) {
        try{
            return new ResponseWrapper(authService.checkPasswordVerifyCode(email, resetCode), "success", "Forgot password request success");
        } catch (InvalidCodeException invalidCodeException) {
            return new ResponseWrapper(null, "invalid-code", invalidCodeException.getMessage());
        } catch (Exception e) {
            return new ResponseWrapper(null, "failed", "Something went wrong! Please contact developer");
        }
    }

    @PostMapping(value = "/password/reset")
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
