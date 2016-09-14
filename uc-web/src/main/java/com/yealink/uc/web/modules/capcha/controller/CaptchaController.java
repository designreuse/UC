package com.yealink.uc.web.modules.capcha.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.IOUtil;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.web.modules.capcha.service.CaptchaService;
import com.yealink.uc.web.modules.common.constant.SessionConstant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CaptchaController {
    public static final String JPEG = "JPEG";
    @Autowired
    CaptchaService captchaService;
    @Autowired
    MessageSource messageSource;
    Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @RequestMapping("/createCaptcha")
    public void createCaptcha(HttpServletRequest request,
                              HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 获得创建好的验证码图片
        BufferedImage image = captchaService.getImage();
        ServletOutputStream outputStream = null;
        try {
            String sRand = captchaService.getCaptcha();
            SessionUtil.set(request, SessionConstant.CURRENT_CAPTCHA, sRand);
            outputStream = response.getOutputStream();
            ImageIO.write(image, JPEG, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            SessionUtil.remove(request, SessionConstant.CURRENT_CAPTCHA);
            logger.error(e.getMessage());
        } finally {
            IOUtil.close(outputStream);
        }
    }

    @RequestMapping("/checkCaptcha")
    @ResponseBody
    public Response checkCaptcha(String inputCaptcha, HttpServletRequest request) {
        String randCaptcha = SessionUtil.getMayNull(request, SessionConstant.CURRENT_CAPTCHA);
        if (randCaptcha == null) {
            return Response.buildResponse(false, MessageUtil.getMessage("login.tips.captcha.out.date"));
        } else {
            boolean isCaptchaEquals = randCaptcha.equalsIgnoreCase(inputCaptcha);
            if (isCaptchaEquals) {
                return Response.buildResponse(true, "");
            } else {
                return Response.buildResponse(false, MessageUtil.getMessage("login.tips.auth.error.captcha"));
            }
        }
    }
}
