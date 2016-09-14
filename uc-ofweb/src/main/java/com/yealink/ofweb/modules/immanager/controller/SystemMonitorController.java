package com.yealink.ofweb.modules.immanager.controller;

import com.yealink.dataservice.client.util.Result;
import com.yealink.ofweb.modules.immanager.request.SystemSearchBean;
import com.yealink.uc.platform.annotations.LoginRequired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenkl on 2016/7/28.
 */
@Controller
@RequestMapping("/system")
public class SystemMonitorController {
    @Value("${muc_request_url_prefix}")
    private String SystemMonitorUriPrefix;

    @LoginRequired
    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String main(){
        return "/immanager/system/main";
    }

    @LoginRequired
    @RequestMapping(value = "/statistics",method = RequestMethod.GET)
    public String statistics(){
        return "/immanager/system/statistics";
    }

    @LoginRequired
    @RequestMapping(value = "/infos",method = RequestMethod.GET)
    @ResponseBody
    public Result infos(HttpServletRequest request,SystemSearchBean systemSearchBean){
        StringBuilder uri=new StringBuilder(SystemMonitorUriPrefix);
        uri.append("server_info/");
        uri.append("system_infos");
        uri.append("?");
        uri.append("startTime="+systemSearchBean.getStartTime().getTime());
        uri.append("&endTime="+systemSearchBean.getEndTime().getTime());
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

    @LoginRequired
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public Result info(HttpServletRequest request){
        StringBuilder uri=new StringBuilder(SystemMonitorUriPrefix);
        uri.append("server_info/");
        uri.append("system_info");
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
