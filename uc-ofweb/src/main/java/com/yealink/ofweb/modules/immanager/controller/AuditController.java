package com.yealink.ofweb.modules.immanager.controller;

import com.yealink.dataservice.client.util.Pager;
import com.yealink.dataservice.client.util.Result;
import com.yealink.ofweb.modules.base.BaseController;
import com.yealink.ofweb.modules.immanager.request.AuditSearchBean;
import com.yealink.ofweb.modules.immanager.service.AuditService;
import com.yealink.ofweb.modules.util.DateUtil;
import com.yealink.uc.platform.annotations.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenkl on 2016/8/12.
 */
@Controller
@RequestMapping("/audit")
public class AuditController extends BaseController{
    @Autowired
    private AuditService auditService;

    @LoginRequired
    @RequestMapping(value = "/list")
    public String list(){
        return "/immanager/audit/audit_list";
    }

    @LoginRequired
    @RequestMapping(value = "/get_list",method = RequestMethod.GET)
    @ResponseBody
    public Result getAuditList(HttpServletRequest request, AuditSearchBean searchBean){
        Result result = new Result();
        Pager<Map<String,Object>> pager = auditService.getAuditList(searchBean);
        if(pager==null){
            result.setRet(-2);
            result.setMsg(getMessage("audit.no.data",request));
            return result;
        }
        List<Map<String,Object>> data=pager.getData();
        for(Map<String,Object> map:data){
            Long timeTemp = Long.parseLong(map.get("operationTime").toString());
            map.put("operationTime", DateUtil.formatDate(new Date(timeTemp),"yyyy-MM-dd HH:mm:ss"));
            map.put("operation",getMessage(map.get("operation").toString(),request));
        }
        result.setData(pager.getData());
        result.setRet(200);
        Map<String,Object> attrs = new HashMap<>();
        long total = auditService.getCount(searchBean);
        long totalPage = total/searchBean.getPageSize()+(total%searchBean.getPageSize()>0?1:0);
        attrs.put("total",total);
        attrs.put("searchKey",searchBean.getSearchKey());
        attrs.put("pageNo",searchBean.getPageNo());
        attrs.put("pageSize",searchBean.getPageSize());
        attrs.put("startTime",searchBean.getStartTime());
        attrs.put("endTime",searchBean.getEndTime());
        attrs.put("totalPage",totalPage);
        result.setAttrs(attrs);
        return result;
    }

    @LoginRequired
    @RequestMapping(value = "/delete_audit",method = RequestMethod.POST)
    @ResponseBody
    public Result deleteAuditList(HttpServletRequest request,String ids){
        Result result = new Result();
        long count = auditService.delete(ids);
        if(count==0l){
            result.setRet(-1);
            result.setMsg(getMessage("audit.delete.failed",request));
        }else {
            result.setRet(200);
            result.setMsg(getMessage("audit.delete.succeed",new String[]{count+""},request));
        }
        return result;
    }
}
