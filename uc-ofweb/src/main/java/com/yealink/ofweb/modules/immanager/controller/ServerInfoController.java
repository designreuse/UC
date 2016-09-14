package com.yealink.ofweb.modules.immanager.controller;

import com.yealink.dataservice.client.util.Result;
import com.yealink.uc.platform.annotations.LoginRequired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenkl on 2016/8/2.
 */
@Controller
@RequestMapping("/server")
public class ServerInfoController {
    @Value("${muc_request_url_prefix}")
    private String ServerInfoUriPrefix;
    @RequestMapping("/main")
    public String main(){
        return "/immanager/server/serverInfo";
    }

    @LoginRequired
    @RequestMapping("/properties")
    @ResponseBody
    public Result getProperties(HttpServletRequest request){
        StringBuilder uri=new StringBuilder(ServerInfoUriPrefix);
        uri.append("openfire_info/");
        uri.append("get_properties");
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
    @RequestMapping("/ports")
    @ResponseBody
    public Result getPorts(HttpServletRequest request){
        StringBuilder uri=new StringBuilder(ServerInfoUriPrefix);
        uri.append("openfire_info/");
        uri.append("get_ports");
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
