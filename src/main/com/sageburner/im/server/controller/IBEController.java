package com.sageburner.im.server.controller;

import com.sageburner.im.server.service.IBEParamsService;
import com.sageburner.im.server.jpbc.IBEParamsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IBEController {

    @Autowired
    private IBEParamsService ibeParamsService;

    @RequestMapping(value = "/getIBEParams", method = RequestMethod.GET)
    public @ResponseBody
    IBEParamsWrapper getIBEWrapper(@RequestParam("key")  int key){
        IBEParamsWrapper ibeParamsWrapper;

        if (key == 0) {
            ibeParamsWrapper = ibeParamsService.requestIBEParams();
        } else {
            ibeParamsWrapper = ibeParamsService.requestIBEParams(key);
        }

        if (ibeParamsWrapper != null) {
            return ibeParamsWrapper;
        } else {
            return null;
        }
    }
}