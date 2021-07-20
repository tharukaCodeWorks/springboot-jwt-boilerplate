package lk.teachmeit.boilerplate.service.interfaces;

import lk.teachmeit.boilerplate.dto.ConfirmEmailDto;
import lk.teachmeit.boilerplate.dto.LoginUserDto;
import lk.teachmeit.boilerplate.dto.UserDto;
import lk.teachmeit.boilerplate.model.User;

import java.io.IOException;
import java.util.List;

public interface AuthService {
    List<Object> signIn(LoginUserDto loginUserDto);
    User signUp(UserDto userDto) throws IOException;
    Object verifyEmail(ConfirmEmailDto confirmEmailDto) throws IOException;
    Object forgotPassword(String email) throws IOException;
    Object checkPasswordVerifyCode(String email, String code);
    Object resetPassword(String email, String code, String newPassword) throws IOException;
}
