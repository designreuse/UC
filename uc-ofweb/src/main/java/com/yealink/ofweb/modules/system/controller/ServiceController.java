package com.yealink.ofweb.modules.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.yealink.ofweb.modules.common.Constants;
import com.yealink.ofweb.modules.fileshare.request.PageRequest;
import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import com.yealink.ofweb.modules.system.entity.SystemEntity;
import com.yealink.ofweb.modules.system.service.SystemService;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.platform.utils.JSONConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.cglib.core.Predicate;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 系统服务
 * Created by pzy on 2016/8/27.
 */
@Controller
@RequestMapping("/system/service")
public class ServiceController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private MessageSource messageSource;

    /**
     * 进入系统服务页面
     * @param model
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(HttpServletRequest request, Model model) {
        return "system/service";
    }

    /**
     * 获取服务列表
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public String getServiceList(HttpServletRequest request, PageRequest<Map<String, String>> pageRequest) {
        ObjectMapper objMapper = new ObjectMapper();
        String result = "";
        File jsonFile = new File(request.getServletContext().getRealPath("/")+"/js/service.json");
        try {
            //读取文件
            List<String> listStr = Files.readLines(jsonFile, Charsets.UTF_8);
            String jsonStr = "";
            for (String str : listStr) {
                jsonStr = jsonStr + str;
            }

            Map<String, List<Map<String, String>>> jsonServiceMap = JSONConverter.fromJSON(Map.class, jsonStr);

            // 获取服务列表
            List<Map<String, String>> serviceList = jsonServiceMap.get("service");

            int pageSize = pageRequest.getPageSize();
            int pageNumber = pageRequest.getPageNumber();
            String serviceName = PropertiesUtils.getString(pageRequest.getDefaultName());

            if (!serviceName.equals("")) {
                final String deServiceName = PropertiesUtils.getString(PropertiesUtils.getStringFromUtf8(serviceName));
                // 根据服务名称筛选过滤
                CollectionUtils.filter(serviceList, new Predicate() {
                    @Override
                    public boolean evaluate(Object o) {
                        Map<String, String> map = (Map<String, String>)o;
                        String serviceValue =  PropertiesUtils.getString(map.get("serviceName"));
                        if (!serviceValue.equals("") &&
                                serviceValue.contains(deServiceName)) {
                            return true;
                        }
                        return false;
                    }
                });
            }

            // 总记录数
            long totals = serviceList.size();
            List<Map<String, String>> resultList = null;

            // 获取当前分页数据
            int startIndex = (pageNumber-1)*pageSize;
            int endIndex = pageNumber*pageSize;
            if (pageNumber*pageSize > totals) {
                endIndex = (int)totals;
            }
            resultList = serviceList.subList(startIndex, endIndex);

            // 获取服务状态
            String host = null;
            int port=22;
            Map<String, String> statusMap = null;
            for (int i=0; i<resultList.size(); i++) {
                Map<String, String> serviceMap = resultList.get(i);
                String serviceDir = null;

                try {
                    // 如果当前主机地址和端口和前一个服务一样，就不再连接获取服务状态
                    if (host != null && host.equals(serviceMap.get("host"))
                            && port==Integer.parseInt(serviceMap.get("port"))) {
                    } else {
                        host = serviceMap.get("host");
                        port = Integer.parseInt(serviceMap.get("port"));
                        statusMap = systemService.execGetSystemServerInfo(host, port);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (statusMap != null) {
                    if (Constants.SERVICE_NAME_FS.equals(serviceMap.get("flag"))) {
                        serviceDir = statusMap.get(Constants.SERVICE_STATUS_FS);
                    } else if (Constants.SERVICE_NAME_IM.equals(serviceMap.get("flag"))) {
                        serviceDir = statusMap.get(Constants.SERVICE_STATUS_IM);
                    }
                    serviceMap.put("status", serviceDir==null?"0":"1");
                } else {
                    serviceMap.put("status", "0");
                }
            }

            pageRequest.setTotal(totals);
            pageRequest.setRows(resultList);
            // 转化为json
            result = objMapper.writeValueAsString(pageRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 操作服务 启动 重启和停止
     * @return
     */
    @RequestMapping(value = "/operate", method = RequestMethod.GET, produces={"text/plain;charset=UTF-8"})
    @ResponseBody
    @LoginRequired
    public String operateService(HttpServletRequest request) {
        String result = messageSource.getMessage("system.service.optsuccess",null, Locale.getDefault()); // 操作成功
        String code = "";

        String flag = request.getParameter("flag");
        String dir = request.getParameter("dir");
        String opt = request.getParameter("opt");
        String host = request.getParameter("host");
        int port = Integer.parseInt(request.getParameter("port"));

        try {
            SystemEntity systemEntity = new SystemEntity();
            systemEntity.setDir(dir);
            systemEntity.setFlag(flag);
            systemEntity.setHost(host);
            systemEntity.setPort(port);
            systemService.operateService(systemEntity, opt);
            code = "1";
        } catch (Exception e) {
            e.printStackTrace();
            code = "0";
            result = messageSource.getMessage("system.service.optfail",null, Locale.getDefault())+e.getMessage();
        }
        return result;
    }
}
