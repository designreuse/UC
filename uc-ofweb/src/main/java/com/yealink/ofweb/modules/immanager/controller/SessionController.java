package com.yealink.ofweb.modules.immanager.controller;

import com.yealink.dataservice.client.util.Result;
import com.yealink.ofweb.modules.base.BaseController;
import com.yealink.ofweb.modules.immanager.request.SessionSearchBean;
import com.yealink.ofweb.modules.util.DateUtil;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.platform.utils.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 负责会话列表展示，会话详情展示，关闭会话，发送系统消息功能
 * Created by chenkl on 2016/8/3.
 */
@Controller
@RequestMapping("/session")
public class SessionController extends BaseController{
    @Value("${muc_request_url_prefix}")
    private String SessionUriPrefix;

    @LoginRequired
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        return "/immanager/session/session_list";
    }

    @LoginRequired
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public String detail(){
        return "/immanager/session/session_detail";
    }

    @LoginRequired
    @RequestMapping(value = "/get_list",method = RequestMethod.GET)
    @ResponseBody
    public Result getList(HttpServletRequest request, SessionSearchBean sessionSearchBean){
        Result ret=new Result();
        int pageNum=sessionSearchBean.getPageNo();
        int pageSize=sessionSearchBean.getPageSize();

        StringBuilder uri=new StringBuilder(SessionUriPrefix);
        uri.append("session_manager/");
        uri.append("session_list");
        uri.append("?");
        uri.append("pageSize="+pageSize);
        uri.append("&pageNo="+pageNum);
        uri.append("&order=1");
        uri.append("&staffIds="+sessionSearchBean.getStaffIds());
        uri.append("&status="+sessionSearchBean.getStatus());
        RestTemplate template=new RestTemplate();
        try {
            ret = template.getForObject(uri.toString(), Result.class);
            return ret;
        }catch(Exception e){
            ret.setRet(-1);
            ret.setMsg(e.getMessage());
            return ret;
        }
    }

    @LoginRequired
    @RequestMapping(value = "/get_detail",method = RequestMethod.GET)
    @ResponseBody
    public Result getDetail(HttpServletRequest request,String jid,String preUrl){
        Result ret=new Result();
        if(StringUtil.isStrEmpty(jid)){
            return ret;
        }
        StringBuilder uri=new StringBuilder(SessionUriPrefix);
        uri.append("session_manager/");
        uri.append("session_detail");
        uri.append("?");
        uri.append("jid="+jid);
        RestTemplate template=new RestTemplate();
        try {
            ret = template.getForObject(uri.toString(), Result.class);
            Map<String,Object> map= new HashMap<>();
            map.put("preUrl",preUrl);
            Map<String,Object> data = (Map)ret.getData();
            data.put("created", DateUtil.formatDate(new Date(Long.parseLong(data.get("created").toString())),"yyyy-MM-dd HH:mm:ss"));
            data.put("lastActive", DateUtil.formatDate(new Date(Long.parseLong(data.get("lastActive").toString())),"yyyy-MM-dd HH:mm:ss"));
            ret.setAttrs(map);
            return ret;
        }catch(Exception e){
            ret.setRet(-1);
            ret.setMsg(e.getMessage());
            return ret;
        }
    }

    @LoginRequired
    @RequestMapping(value = "/close_session",method = RequestMethod.DELETE)
    @ResponseBody
    public Result closeSession(HttpServletRequest request,String ids){
        Result ret=new Result();
        if(StringUtil.isStrEmpty(ids)){
            return ret;
        }
        StringBuilder uri=new StringBuilder(SessionUriPrefix);
        uri.append("session_manager/");
        uri.append("close_session");
        uri.append("?");
        uri.append("jid="+ids);
        RestTemplate template=new RestTemplate();
        try {
            HttpMessageConverterExtractor<Result> responseExtractor = new HttpMessageConverterExtractor(Result.class, template.getMessageConverters());
            ret = template.execute(uri.toString(), HttpMethod.DELETE,null,responseExtractor);
            ret.setMsg(getMessage("session.close.succeed",request));
            return ret;
        }catch(Exception e){
            ret.setRet(-1);
            ret.setMsg(getMessage("session.close.failed",request));
            return ret;
        }
    }

    @LoginRequired
    @RequestMapping(value = "/send_to_all",method = RequestMethod.POST)
    @ResponseBody
    public Result sendMessageToAll(HttpServletRequest request,String message){
        Result ret=new Result();
        if(StringUtil.isStrEmpty(message)){
            return ret;
        }
        StringBuilder uri=new StringBuilder(SessionUriPrefix);
        uri.append("session_manager/");
        uri.append("send_to_all");
        uri.append("?");
        uri.append("message="+message);
        RestTemplate template=new RestTemplate();
        try {
            HttpMessageConverterExtractor<Result> responseExtractor = new HttpMessageConverterExtractor(Result.class, template.getMessageConverters());
            ret = template.execute(uri.toString(), HttpMethod.POST,null,responseExtractor);
            ret.setMsg(getMessage("session.send.message.succeed",request));
            return ret;
        }catch(Exception e){
            ret.setRet(-1);
            ret.setMsg(getMessage("session.send.message.failed",request));
            return ret;
        }
    }


}
