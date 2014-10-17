package com.sageburner.im.server.server.controller;

import com.sageburner.im.server.server.model.User;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@RestController
@RequestMapping("/service")
public class LoginController {

//    @Autowired
//    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody
    User getUser(@RequestParam("username")  String username, @RequestParam("password") String password){
        User user = new User();

        user.setId("1");
        user.setUsername(username);
        user.setPassword("pass");
        user.setFirstName("Android");
        user.setLastName("Bootstrap");
        user.setPhone("555-555-5555");
        user.setSessionToken("xkygcf502aav0ay54m7xkphvd");
        user.setPublicKey("");
        Calendar cal = Calendar.getInstance();
        cal.set(2012, 7, 10, 21, 38, 51);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        user.setCreatedAt(sdf.format(cal.getTime()));
        cal.set(2012, 8, 3, 3, 32, 56);
        user.setUpdatedAt(sdf.format(cal.getTime()));

        if (password.equalsIgnoreCase("password")) {
//            return loginService.getUserByUsername(username);
            return user;
        } else {
            return null;
        }
    }
}