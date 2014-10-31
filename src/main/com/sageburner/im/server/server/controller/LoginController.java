package com.sageburner.im.server.server.controller;

import com.sageburner.im.server.server.model.User;
import com.sageburner.im.server.server.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@RestController
@RequestMapping("/service")
public class LoginController {

    //TODO implement stronger auth
    //TODO implement password hashing

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody
    User getUser(@RequestParam("username")  String username, @RequestParam("password") String password){
        User user = loginService.getUserByUsername(username);
        String userPass;

        if (user != null) {
            userPass = user.getPassword();
            if (password.equalsIgnoreCase(userPass)) {
                return user;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}