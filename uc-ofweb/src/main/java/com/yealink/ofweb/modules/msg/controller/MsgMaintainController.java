package com.yealink.ofweb.modules.msg.controller;

import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import com.yealink.ofweb.modules.msg.service.MsgManagerService;
import com.yealink.ofweb.modules.msg.vo.MsgProperty;
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
 * 消息 属性设置
 * author:pengzhiyuan
 * Created on:2016/8/8.
 */
@Controller
@RequestMapping("/msg/maintain")
public class MsgMaintainController {

    @Autowired
    private MsgManagerService msgManagerService;

    /**
     * 进入维护页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @LoginRequired
    public String init(Model model) {
        MsgProperty msgProperty = msgManagerService.getMsgPropertyFromDb();
        model.addAttribute("revertMsgValidTimes", PropertiesUtils.getString(msgProperty.getRevertMsgValidTimes()));
        model.addAttribute("msgSaveDays", PropertiesUtils.getString(msgProperty.getMsgSaveDays()));
        model.addAttribute("recentMaxNumbers", PropertiesUtils.getString(msgProperty.getRecentMaxNumbers()));
        return "msg/msgMaintain";
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
        String revertMsgValidTimes = PropertiesUtils.getString(request.getParameter("revertMsgValidTimes"));
        String msgSaveDays = PropertiesUtils.getString(request.getParameter("msgSaveDays"));
        String recentMaxNumbers = PropertiesUtils.getString(request.getParameter("recentMaxNumbers"));

        // 更新数据
        MsgProperty property = new MsgProperty();
        property.setRevertMsgValidTimes(revertMsgValidTimes);
        property.setMsgSaveDays(msgSaveDays);
        property.setRecentMaxNumbers(recentMaxNumbers);
        try {
            Map<String, String> resultMap = msgManagerService.updateMsgProperty(property);
            model.addAllAttributes(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("ret", -2);
            model.addAttribute("msg", e.getMessage());
        }
        model.addAttribute("revertMsgValidTimes", revertMsgValidTimes);
        model.addAttribute("msgSaveDays", msgSaveDays);
        model.addAttribute("recentMaxNumbers", recentMaxNumbers);
        return "msg/msgMaintain";
    }

}
