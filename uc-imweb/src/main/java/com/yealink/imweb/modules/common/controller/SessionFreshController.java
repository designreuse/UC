package com.yealink.imweb.modules.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by yl1240 on 2016/8/17.
 */
@Controller
@RequestMapping("/session")
public class SessionFreshController {
    @ResponseBody
    @RequestMapping(value = "/freshSession", method = RequestMethod.GET)
    public void freshSession()  {
         fresh();
    }
    //什么也不做
    private void fresh() {
        return;
    }
}
