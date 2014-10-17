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

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody
    User getUser(@RequestParam("id")  String id, @RequestParam("password") String password){
        User user;

        if (password.equalsIgnoreCase("password")) {
            user = loginService.getUser(id);
            return user;
        } else {
            return null;
        }
    }
}