package com.sageburner.im.server.controller;

import com.sageburner.im.server.jpbc.IBEParamsWrapper;
import com.sageburner.im.server.service.IBEParamsService;
import com.sageburner.im.server.util.CryptoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UtilsController {

    @RequestMapping(value = "/getHashedPassword", method = RequestMethod.GET)
    public @ResponseBody
    String getHashedPassword(@RequestParam("password") String password){

        if (password == null || password.length() == 0) {
            return "Please enter a valid password";
        } else {
            return CryptoUtils.hashPassword(password);
        }
    }
}