package lk.teachmeit.auth.controller;

import lk.teachmeit.auth.model.AuthToken;
import lk.teachmeit.auth.model.LoginUser;
import lk.teachmeit.auth.model.ResponseWrapper;
import lk.teachmeit.auth.model.User;
import lk.teachmeit.auth.service.interfaces.UserService;
import lk.teachmeit.auth.util.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
 * Author: Tharuka Lakshan Dissanayake
 * Date: 2020/12/04
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity signIn(@RequestBody LoginUser loginUser) throws AuthenticationException {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getEmail(),
                            loginUser.getPassword()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseWrapper(null, "wrong", "User email or password incorrect"));
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        User user =  userService.findOne(loginUser.getEmail());
        if(user!=null) {
            if(user.getIsEmailVerified().equals("FALSE")) {
                return ResponseEntity.ok(new ResponseWrapper(null, "un-verified", "Your email is not verified yet"));
            }
        }
        List results = new ArrayList<>();
        results.add(new AuthToken(token));
        results.add(user);
        return ResponseEntity.ok(new ResponseWrapper(results, "success", "User Logged In success"));
    }

}
