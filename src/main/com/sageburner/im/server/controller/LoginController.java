package com.sageburner.im.server.controller;

import com.sageburner.im.server.model.User;
import com.sageburner.im.server.service.LoginService;
import com.sageburner.im.server.util.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
//@RequestMapping("/service")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody
    User getUser(@RequestParam("username")  String username, @RequestParam("password") String password){
        User user = loginService.getUserByUsername(username);

        if (user != null) {
            String userPass = user.getAuthPassword();
            if (CryptoUtils.validatePassword(password, userPass)) {
                return user;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}