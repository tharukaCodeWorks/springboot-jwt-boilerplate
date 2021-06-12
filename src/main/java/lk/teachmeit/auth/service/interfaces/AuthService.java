package lk.teachmeit.auth.service.interfaces;

import lk.teachmeit.auth.dto.ConfirmEmailDto;
import lk.teachmeit.auth.dto.LoginUserDto;
import lk.teachmeit.auth.dto.UserDto;
import lk.teachmeit.auth.model.User;

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
