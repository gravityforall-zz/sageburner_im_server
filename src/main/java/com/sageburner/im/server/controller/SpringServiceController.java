package com.sageburner.im.server.controller;

import com.sageburner.im.server.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service/greeting")
public class SpringServiceController {
//    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
//    public String getGreeting(@PathVariable String name) {
//        String result="Hello "+name;
//        return result;
//    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public @ResponseBody User getUser(@PathVariable String name){
        User user = new User();
        user.setUsername(name);
        return user;
    }
}