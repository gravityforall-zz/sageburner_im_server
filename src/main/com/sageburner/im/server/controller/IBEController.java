package com.sageburner.im.server.controller;

import com.sageburner.im.server.model.User;
import com.sageburner.im.server.service.IBEService;
import com.sageburner.im.server.service.LoginService;
import com.sageburner.im.server.util.CryptoUtils;
import com.sageburner.im.server.util.IBEWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IBEController {

    @Autowired
    private IBEService ibeService;

    @RequestMapping(value = "/getIBE", method = RequestMethod.GET)
    public @ResponseBody
    IBEWrapper getIBEWrapper(@RequestParam("key")  int key){
        IBEWrapper ibeWrapper;

        if (key == 0) {
            ibeWrapper = ibeService.requestIBE();
        } else {
            ibeWrapper = ibeService.requestIBE(key);
        }

        if (ibeWrapper != null) {
            return ibeWrapper;
        } else {
            return null;
        }
    }
}