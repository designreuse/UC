package com.yealink.ofweb.modules.login.controller;

import com.yealink.dataservice.client.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Sean on 2016/7/26.
 */
@Controller
public class MainController {
    @RequestMapping(value="/menu",method = RequestMethod.GET)
    @ResponseBody
    public Result listMenu(HttpServletRequest request){

        return new Result();
    }
}
