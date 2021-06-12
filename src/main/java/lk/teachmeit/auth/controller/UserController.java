package lk.teachmeit.auth.controller;

import lk.teachmeit.auth.dto.ConfirmEmailDto;
import lk.teachmeit.auth.dto.LoginUserDto;
import lk.teachmeit.auth.dto.UserDto;
import lk.teachmeit.auth.enums.MailMode;
import lk.teachmeit.auth.dto.ResponseWrapper;
import lk.teachmeit.auth.exceptions.EmailAlreadyException;
import lk.teachmeit.auth.exceptions.EmailNotVerifiedException;
import lk.teachmeit.auth.exceptions.InvalidCodeException;
import lk.teachmeit.auth.exceptions.InvalidCredentialsException;
import lk.teachmeit.auth.model.User;
import lk.teachmeit.auth.service.HtmlProcessService;
import lk.teachmeit.auth.service.interfaces.AuthService;
import lk.teachmeit.auth.service.interfaces.UserService;
import lk.teachmeit.auth.util.EmailService;
import lk.teachmeit.auth.util.RandomValueUtil;
import lk.teachmeit.auth.util.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/users", method = RequestMethod.GET)
    public List<User> listUser(){
        return userService.findAll();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getOne(@PathVariable(value = "id") Long id){
        return userService.findById(id);
    }

}
