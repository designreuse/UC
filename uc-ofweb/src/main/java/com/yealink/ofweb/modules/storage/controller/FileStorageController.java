package com.yealink.ofweb.modules.storage.controller;

import com.yealink.ofweb.modules.common.Constants;
import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import com.yealink.ofweb.modules.storage.entity.FileStorageSetting;
import com.yealink.ofweb.modules.storage.service.FileStorageService;
import com.yealink.uc.platform.annotations.LoginRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件存储设置
 * author:pengzhiyuan
 * Created on:2016/8/25.
 */
@Controller
@RequestMapping("/storage/file")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 进入设置页面
     * @param model
     * @return
     */
    @LoginRequired
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public String init(Model model) {
        initData(model);
        return "storage/fileStorage";
    }

    /**
     * 初始化页面数据
     * @param model
     */
    private void initData(Model model) {
        FileStorageSetting fileStorageSetting = fileStorageService.getFileStorageSettingFromDb();
        if (PropertiesUtils.getString(fileStorageSetting.getMsgSaveDays()).equals("")) {
            fileStorageSetting.setMsgSaveDays(Constants.DEFAULT_MSG_SAVE_DAYS);
        }
        if (PropertiesUtils.getString(fileStorageSetting.getOfflineSaveDays()).equals("")) {
            fileStorageSetting.setOfflineSaveDays(Constants.DEFAULT_OFFLINE_SAVE_DAYS);
        }
        if (PropertiesUtils.getString(fileStorageSetting.getServerSavePath()).equals("")) {
            fileStorageSetting.setServerSavePath(Constants.DEFAULT_SERVER_SAVE_PATH);
        }
        model.addAttribute("fileStorageSetting", fileStorageSetting);
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
        String serverSavePath = PropertiesUtils.getString(request.getParameter("serverSavePath"));
        String isDelOnlineFile = PropertiesUtils.getString(request.getParameter("isDelOnlineFile"));
        String offlineSaveDays = PropertiesUtils.getString(request.getParameter("offlineSaveDays"));
        String isDelMsg = PropertiesUtils.getString(request.getParameter("isDelMsg"));
        String msgSaveDays = PropertiesUtils.getString(request.getParameter("msgSaveDays"));

        FileStorageSetting fileStorageSetting = new FileStorageSetting();
        fileStorageSetting.setServerSavePath(serverSavePath);
        fileStorageSetting.setMsgSaveDays(msgSaveDays);
        fileStorageSetting.setOfflineSaveDays(offlineSaveDays);
        fileStorageSetting.setDelOnlineFile(Boolean.valueOf(isDelOnlineFile));
        fileStorageSetting.setDelMsg(Boolean.valueOf(isDelMsg));

        // 保存文件存储设置
        String code = "";
        try {
            fileStorageService.saveFileStorageSetting(fileStorageSetting);
            model.addAttribute("code", "0");
            initData(model);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("code", "-1");
            model.addAttribute("errorMsg", e.getMessage());
            model.addAttribute("fileStorageSetting", fileStorageSetting);
        }
        return "storage/fileStorage";
    }
}
