package com.yealink.ofweb.modules.mail.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import com.yealink.ofweb.modules.mail.entity.MailSendInfo;
import com.yealink.ofweb.modules.mail.service.SendMailUtil;
import com.yealink.ofweb.modules.mail.util.MailConstant;
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
import java.util.Map;

/**
 * 邮箱设置
 * author:pengzhiyuan
 * Created on:2016/8/23.
 */
@Controller
@RequestMapping("/mailSetting")
public class MailSettingController {

    @Autowired
    private SendMailUtil sendMailUtil;

    /**
     * 进入设置页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @LoginRequired
    public String init(Model model) {
        initData(model);
        return "mail/mailSetting";
    }

    /**
     * 页面数据初始化
     * @param model
     */
    private void initData(Model model) {
        Map<String, Object> mailMap = sendMailUtil.queryMailSetting();

        if (mailMap != null) {
            model.addAllAttributes(mailMap);
            String secure = PropertiesUtils.getString(mailMap.get("secure"));
            if (secure.equals(MailConstant.MAIL_SECURE_TLS) ||
                    secure.equals(MailConstant.MAIL_SECURE_SSL)) {
                model.addAttribute("isSecure","true");
            }
        }
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
        String host = PropertiesUtils.getString(request.getParameter("mailServerHost"));
        String fromAddress = PropertiesUtils.getString(request.getParameter("fromAddress"));
        String userName = PropertiesUtils.getString(request.getParameter("userName"));
        String passWord = PropertiesUtils.getString(request.getParameter("passWord"));
        String port = PropertiesUtils.getString(request.getParameter("mailServerPort"));
        String secure = PropertiesUtils.getString(request.getParameter("secure"));
        String isSecure = PropertiesUtils.getString(request.getParameter("isSecure"));
        String id = PropertiesUtils.getString(request.getParameter("_id"));

        MailSendInfo mailSendInfo = new MailSendInfo();
        mailSendInfo.setUserName(userName);
        mailSendInfo.setPassWord(passWord);
        mailSendInfo.setMailServerHost(host);
        mailSendInfo.setMailServerPort(port);
        mailSendInfo.setFromAddress(fromAddress);
        mailSendInfo.setId(id);
        // 安全连接配置
        if (!isSecure.equals("") && Boolean.valueOf(isSecure)) {
            if (secure.equals(MailConstant.MAIL_SECURE_TLS)) {
                mailSendInfo.setTlsSend(true);
            } else if (secure.equals(MailConstant.MAIL_SECURE_SSL)) {
                mailSendInfo.setSSlSend(true);
            }
        }
        // 保存邮箱设置
        String code = "";
        try {
            sendMailUtil.saveMailSetting(mailSendInfo);
            model.addAttribute("code", "0");
            initData(model);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("code", "-1");
            model.addAttribute("errorMsg", e.getMessage());
        }
        return "mail/mailSetting";
    }

    /**
     * 测试邮件发送
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST, consumes= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @LoginRequired
    public String testSendMail(HttpServletRequest request, @RequestBody String jsonStr) {
        // 获取数据
        ObjectMapper objectMapper = new ObjectMapper();

        // 邮件测试结果 1成功 0失败
        String code = "1";
        try {
            Map<String, String> mailMap = objectMapper.readValue(jsonStr, Map.class);
            MailSendInfo mailSendInfo = new MailSendInfo();
            mailSendInfo.setUserName(mailMap.get("userName"));
            mailSendInfo.setPassWord(mailMap.get("passWord"));
            mailSendInfo.setMailServerHost(mailMap.get("mailServerHost"));
            mailSendInfo.setMailServerPort(mailMap.get("mailServerPort"));
            mailSendInfo.setFromAddress(mailMap.get("fromAddress"));
            mailSendInfo.setToAddress(mailMap.get("toAddress"));
            String isSecure = PropertiesUtils.getString(mailMap.get("isSecure"));
            String secure = PropertiesUtils.getString(mailMap.get("secure"));
            // 安全连接配置
            if (!isSecure.equals("") && Boolean.valueOf(isSecure)) {
                if (secure.equals(MailConstant.MAIL_SECURE_TLS)) {
                    mailSendInfo.setTlsSend(true);
                } else if (secure.equals(MailConstant.MAIL_SECURE_SSL)) {
                    mailSendInfo.setSSlSend(true);
                }
            }
            sendMailUtil.testMailSend(mailSendInfo);
        } catch (Exception e) {
            e.printStackTrace();
            code="0";
        }
        return code;
    }
}
