package com.yealink.ofweb.modules.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Sean on 2016/7/26.
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(){
        return "index";
    }
}
