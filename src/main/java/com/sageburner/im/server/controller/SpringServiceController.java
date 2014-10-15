package com.sageburner.im.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sageburner.im.server.model.AuthUser;
import com.sageburner.im.server.model.JSONError;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@RestController
@RequestMapping("/service/greeting")
public class SpringServiceController {
//    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
//    public String getGreeting(@PathVariable String name) {
//        String result="Hello "+name;
//        return result;
//    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public @ResponseBody
    AuthUser getAuthUser(@PathVariable String username, @PathVariable String password){
        AuthUser authUser = new AuthUser();

        Calendar cal = Calendar.getInstance();
        cal.set(2012, 7, 10, 21, 38, 51);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        authUser.setCreatedAt(sdf.format(cal.getTime()));
        authUser.setFirstName("Android");
        authUser.setLastName("Bootstrap");
        authUser.setObjectId("iccnB7enaw");
        authUser.setPhone("555-555-1212");
        authUser.setSessionToken("xkygcf502aav0ay54m7xkphvd");
        cal.set(2012, 8, 3, 3, 32, 56);
        authUser.setUpdatedAt(sdf.format(cal.getTime()));
        authUser.setUsername(username);

        if (password.equalsIgnoreCase("android")) {
            return authUser;
        } else {
            return null;
        }
    }
}