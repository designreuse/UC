package com.yealink.ofweb.modules.fileshare.controller;

import com.yealink.ofweb.modules.fileshare.request.FileSharePropertyRequest;
import com.yealink.ofweb.modules.fileshare.service.FileServerManagerService;
import com.yealink.ofweb.modules.fileshare.util.Constants;
import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import com.yealink.uc.platform.annotations.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 文件共享维护
 * Created by pzy on 2016/7/31.
 */
@Controller
@RequestMapping("/fileshare/maintain")
public class FileShareMaintainController {
    @Autowired
    private FileServerManagerService fileServerManagerService;

    /**
     * 进入维护页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @LoginRequired
    public String init(Model model) {
        FileSharePropertyRequest request = fileServerManagerService.queryFileShareProperty();
        model.addAttribute("offlineSaveDays", PropertiesUtils.getString(request.getOfflineSaveDays()));
        model.addAttribute("fileMaxSize", PropertiesUtils.getString(request.getFileMaxSize()));
        model.addAttribute("avatarFileTypes", PropertiesUtils.getString(request.getAvatarFileTypes()));
        return "fileshare/maintain";
    }

    /**
     * 保存
     * @param model
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save(HttpServletRequest request, Model model) {
        // 获取数据
        String offlineDays = PropertiesUtils.getString(request.getParameter("offlineSaveDays"));
        String fileMaxSize = PropertiesUtils.getString(request.getParameter("fileMaxSize"));
        String[] avatarFileTypes = request.getParameterValues("avatarFileTypes");
        String avatarFileTypeStr = "";
        if (avatarFileTypes != null) {
            for (int i=0; i<avatarFileTypes.length; i++) {
                if (i == avatarFileTypes.length-1) {
                    avatarFileTypeStr += avatarFileTypes[i];
                } else {
                    avatarFileTypeStr += avatarFileTypes[i] + ",";
                }
            }
        }
        // 更新数据
        FileSharePropertyRequest fileSharePropertyRequest = new FileSharePropertyRequest();
        fileSharePropertyRequest.setAvatarFileTypes(avatarFileTypeStr);
        fileSharePropertyRequest.setFileMaxSize(fileMaxSize);
        fileSharePropertyRequest.setOfflineSaveDays(offlineDays);
        try {
            Map<String, String> resultMap = fileServerManagerService.updateFileShareProperty(fileSharePropertyRequest);
            model.addAllAttributes(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("code", Constants.FAIL_CODE);
            model.addAttribute("errorMsg", e.getMessage());
        }
        model.addAttribute("offlineSaveDays", offlineDays);
        model.addAttribute("fileMaxSize", fileMaxSize);
        model.addAttribute("avatarFileTypes", avatarFileTypeStr);
        return "fileshare/maintain";
    }
}
