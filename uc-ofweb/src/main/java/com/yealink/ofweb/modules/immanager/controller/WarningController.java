package com.yealink.ofweb.modules.immanager.controller;

import com.yealink.dataservice.client.util.Result;
import com.yealink.ofweb.modules.base.BaseController;
import com.yealink.ofweb.modules.common.Constants;
import com.yealink.ofweb.modules.immanager.request.MonitorProperties;
import com.yealink.ofweb.modules.immanager.request.UserInfo;
import com.yealink.ofweb.modules.util.BeanUtil;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.platform.constant.Constant;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.JSONConverter;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.service.modules.org.api.OrgRESTService;
import com.yealink.uc.service.modules.org.response.ListOrgTreesRESTResponse;
import com.yealink.uc.service.modules.org.vo.OrgTreeNodeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by chenkl on 2016/8/19.
 */
@Controller
@RequestMapping("/warning")
public class WarningController extends BaseController{
    @Value("${muc_request_url_prefix}")
    private String warningUriPrefix;
    @Value("${enterpriseId}")
    private String enterpriseId;
    @Autowired
    private OrgRESTService orgRESTService;

    @LoginRequired
    @RequestMapping(value = "/warningSetting",method = RequestMethod.GET)
    public String getWarningIndex(){
        return "/immanager/warning/warning";
    }

    @LoginRequired
    @RequestMapping(value = "/save_properties",method = RequestMethod.POST)
    @ResponseBody
    public Result saveProperties( MonitorProperties properties){
        Result ret=new Result();
        StringBuilder uri=new StringBuilder(warningUriPrefix);
        uri.append("monitor_properties/");
        uri.append("save_properties");
        Map<String,Object> params = BeanUtil.beanToMap(properties);
        RestTemplate template=new RestTemplate();
        try {
            HttpEntity<Map> paramMap = new HttpEntity<Map>(params,null);
            ResponseEntity<Result> responseEntity = template.exchange(uri.toString(),HttpMethod.PUT,paramMap,Result.class);
            return responseEntity.getBody();
        }catch(Exception e){
            ret.setRet(-1);
            return ret;
        }
    }

    @LoginRequired
    @RequestMapping(value = "/showOrgStaffTreeNodes", method = RequestMethod.GET)
    @ResponseBody
    public Response showOrgStaffTreeNodes(HttpSession httpSession) {
        //此处将httpSession.getAttribute(Constant.CURRENT_SESSION_ACCOUNT)强制转成UCAccountView对象时报错
        UCAccountView user = JSONConverter.fromJSON(UCAccountView.class,
                JSONConverter.toJSON(httpSession.getAttribute(Constant.CURRENT_SESSION_ACCOUNT)));
        ListOrgTreesRESTResponse restResponse = orgRESTService.listOrgTrees(user.getEnterpriseId());
        List<OrgTreeNodeView> notesList =restResponse.getOrgTreeNodeList();
        //用户一定要在某个组织架构中
        if (notesList==null || notesList.size() == 0) {
            return Response.buildResponse(false, "Ora No Exist!");
        } else {
            return new Response(notesList,true);
        }
    }

    @LoginRequired
    @RequestMapping(value = "/get_properties", method = RequestMethod.GET)
    @ResponseBody
    public Result getWarningProperty(){
        Result ret=new Result();
        StringBuilder uri=new StringBuilder(warningUriPrefix);
        uri.append("monitor_properties/");
        uri.append("get_properties");
        RestTemplate template=new RestTemplate();
        try {
            ret = template.getForObject(uri.toString(), Result.class);
            return ret;
        }catch (Exception e){
            ret.setRet(-1);
            ret.setMsg(e.getMessage());
            return ret;
        }
    }

}
