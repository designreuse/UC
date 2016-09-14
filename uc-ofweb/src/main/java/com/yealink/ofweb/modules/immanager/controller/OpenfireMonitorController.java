package com.yealink.ofweb.modules.immanager.controller;

import com.yealink.dataservice.client.util.Result;
import com.yealink.uc.platform.annotations.LoginRequired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenkl on 2016/8/2.
 */
@Controller
@RequestMapping("/of-monitor")
public class OpenfireMonitorController {
    @Value("${muc_request_url_prefix}")
    private String openfireMonitorUriPrefix;

    @LoginRequired
    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String main(){
        return "/immanager/openfire/main";
    }

    @LoginRequired
    @RequestMapping(value = "/monitor",method = RequestMethod.GET)
    @ResponseBody
    public Result infos(HttpServletRequest request, long startTime, long endTime){
        StringBuilder uri=new StringBuilder(openfireMonitorUriPrefix);
        uri.append("openfire_monitor/");
        uri.append("openfire_monitor");
        uri.append("?");
        uri.append("startTime="+startTime);
        uri.append("&endTime="+endTime);
        RestTemplate template=new RestTemplate();
        try {
            Result ret = template.getForObject(uri.toString(), Result.class);
            return ret;
        }catch(Exception e){
            Result ret=new Result();
            ret.setRet(-1);
            ret.setMsg(e.getMessage());
            return ret;
        }
    }
}
