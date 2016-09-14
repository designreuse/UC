package com.yealink.ofweb.modules.fileshare.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.ofweb.modules.fileshare.request.FileShareQueryRequest;
import com.yealink.ofweb.modules.fileshare.service.FileServerManagerService;
import com.yealink.uc.platform.annotations.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 文件共享查询
 * Created by pzy on 2016/7/31.
 */
@Controller
@RequestMapping("/fileshare/query")
public class FileShareQueryController {
    @Autowired
    private FileServerManagerService fileServerManagerService;

    /**
     * 进入查询页面 ==========不用
     * @param model
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(Model model) {
        return "fileshare/query";
    }

    /**
     * 进入头像查询页面
     * @param model
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "/avatarQuery", method = RequestMethod.GET)
    public String avatarQuery(Model model) {
        return "fileshare/avatarQuery";
    }

    /**
     * 获取头像列表
     * @return
     */
    @RequestMapping(value = "/getAvatars", method = RequestMethod.GET)
    @ResponseBody
    @LoginRequired
    public String getAvatars(HttpServletRequest request, FileShareQueryRequest<Map<String, Object>> pageRequest) {
        String result = "";
        // 查询
        Pager<Map<String,Object>> pager = fileServerManagerService.queryAvatarFileList(pageRequest);
        ObjectMapper objMapper = new ObjectMapper();
        try {
            if (pager != null) {
                // 设置总记录数
                long total = pager.getTotal();
                pageRequest.setTotal(total);

                List<Map<String,Object>> rows = pager.getData();
                pageRequest.setRows(rows);

                // 转化为json
                result = objMapper.writeValueAsString(pageRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/deleteAvatar", method=RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8")
    @ResponseBody
    @LoginRequired
    public String deleteAvatar(HttpServletRequest request,@RequestBody String _ids) {
        ObjectMapper objMapper = new ObjectMapper();
        String result = "0"; //1-成功 0-失败
        try {
            fileServerManagerService.deleteAvatarFile(_ids);
            result = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
