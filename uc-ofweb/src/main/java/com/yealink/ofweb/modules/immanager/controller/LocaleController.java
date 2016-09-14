package com.yealink.ofweb.modules.immanager.controller;



import com.yealink.dataservice.client.util.Result;
import com.yealink.ofweb.modules.base.BaseController;
import com.yealink.ofweb.modules.immanager.request.LocaleBean;
import com.yealink.ofweb.modules.util.BeanUtil;
import com.yealink.ofweb.modules.util.DateUtil;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.platform.utils.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by chenkl on 2016/8/8.
 */
@Controller
@RequestMapping("/locale")
public class LocaleController extends BaseController{
    @Value("${muc_request_url_prefix}")
    private String localeUriPrefix;
    @RequestMapping("/locale")
    public String locale(){
        return "/immanager/locale/locale";
    }
    @RequestMapping(value = "/get_timezones",method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public Result getTimezones(HttpServletRequest request){
        StringBuilder uri=new StringBuilder(localeUriPrefix);
        uri.append("locale/");
        uri.append("get_timeZones");
        RestTemplate template=new RestTemplate();
        Result ret=new Result();
        try {
            ret = template.getForObject(uri.toString(), Result.class);
            return ret;
        }catch(Exception e){
            ret.setRet(-1);
            ret.setMsg(e.getMessage());
            return ret;
        }
    }

    @RequestMapping(value = "/get_locale",method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public Result getCurrentlocale(HttpServletRequest request){
        StringBuilder uri=new StringBuilder(localeUriPrefix);
        uri.append("locale/");
        uri.append("get_locale");
        RestTemplate template=new RestTemplate();
        Result ret=new Result();
        try {
            ret = template.getForObject(uri.toString(), Result.class);
            Map<String,Object> data=(Map<String, Object>) ret.getData();
            long time = Long.parseLong(data.get("systemTime").toString());
            TimeZone tz = TimeZone.getTimeZone(data.get("timeZoneId").toString());
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeZone(tz);
            calendar.setTime(new Date(time));
            data.put("systemTime",calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+
                    calendar.get(Calendar.DAY_OF_MONTH)+" "+calendar.get(Calendar.HOUR_OF_DAY)+":"+
            calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND));
            setSessionLocaleAndCopyright(request, getSupportLocale(data.get("local").toString()));
            return ret;
        }catch(Exception e){
            ret.setRet(-1);
            ret.setMsg(e.getMessage());
            return ret;
        }
    }

    @RequestMapping(value = "/save_locale",method = RequestMethod.POST)
    @ResponseBody
    @LoginRequired
    public Result savelocale(HttpServletRequest request, LocaleBean localeBean){
        StringBuilder uri=new StringBuilder(localeUriPrefix);
        Result ret=new Result();
        uri.append("locale/");
        uri.append("save_locale");
        if(StringUtil.isStrEmpty(localeBean.getTimezonesId())){
            ret.setMsg("timezonesId can't null");
            return ret;
        }

        Map<String,Object> params = BeanUtil.beanToMap(localeBean);

        RestTemplate template=new RestTemplate();
        try {
            HttpEntity<Map> paramMap = new HttpEntity<Map>(params,null);
            ResponseEntity<Result> responseEntity = template.exchange(uri.toString(),HttpMethod.PUT,paramMap,Result.class);
            ret = responseEntity.getBody();
            if(!StringUtil.isStrEmpty(localeBean.getLocaleCode())){
                setSessionLocaleAndCopyright(request, getSupportLocale(localeBean.getLocaleCode()));
            }
            if(ret.getRet()>0){
                ret.setMsg(getMessage("locale.set.success",request));
            }
            return ret;
        }catch(Exception e){
            ret.setRet(-1);
            ret.setMsg(getMessage("locale.set.failed",request));
            return ret;
        }
    }
    @RequestMapping(value = "/save_linux_properties",method = RequestMethod.PUT)
    @ResponseBody
    @LoginRequired
    public Result saveLinuxProperties(HttpServletRequest request,String root,String password){
        StringBuilder uri=new StringBuilder(localeUriPrefix);
        Result ret=new Result();
        uri.append("locale/");
        uri.append("save_linux_properties");

        if(StringUtil.isStrEmpty(root)){
            ret.setMsg("username can't null");
            return ret;
        }
        if(StringUtil.isStrEmpty(password)){
            ret.setMsg("password can't null");
            return ret;
        }
        uri.append("?linuxUserName="+root);
        uri.append("&linuxPassword="+password);
        RestTemplate template=new RestTemplate();
        try {
            HttpMessageConverterExtractor<Result> responseExtractor = new HttpMessageConverterExtractor(Result.class, template.getMessageConverters());
            ret = template.execute(uri.toString(), HttpMethod.PUT,null,responseExtractor);
            return ret;
        }catch(Exception e){
            ret.setRet(-1);
            return ret;
        }
    }

    public static void main(String[] args){
        LocaleController controller = new LocaleController();
      //  controller.saveLinuxProperties(null,"root","ucsoftware");
        LocaleBean localeBean = new LocaleBean();
        localeBean.setIsSync(true);
        localeBean.setTimeDomain("time.windows.com");
        localeBean.setTimezonesId("Asia/Hong_Kong");
        localeBean.setDateTime(DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:MM:ss"));
       // controller.savelocale(null,localeBean);
    }

}
