package com.yealink.ofweb.modules.base;

import com.yealink.ofweb.modules.common.Constants;
import com.yealink.uc.platform.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * tomcat 下session通过servletContext共享 清除无效session
 * author:pengzhiyuan
 * Created on:2016/9/2.
 */
@Component
public class SessionCleanJob {
    private final Logger logger = LoggerFactory.getLogger(SessionCleanJob.class);

    /**
     * 每5分钟执行清除任务
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void cleanContextSession() {
        logger.info("start clean cross context session.....");
        ServletContext servletContext = ServletContextUtil.getServletContext();

        ServletContext ucContext = servletContext.getContext("/");
        if (ucContext != null) {
            Enumeration<String> attributeNames =  ucContext.getAttributeNames();
            if (attributeNames != null) {
                HttpSession session = null;
                // 获取servletContext的属性 查找是存session的属性
                while (attributeNames.hasMoreElements()) {
                    String key = attributeNames.nextElement();
                    if (key != null && key.startsWith(Constants.CROSS_CONTEXT_SESSION_NAME+":")) {
                        session = (HttpSession) ucContext.getAttribute(key);
                        if (session!=null) {
                            try {
                                if (session.getAttribute(Constant.CURRENT_SESSION_ACCOUNT) == null) {
                                    ucContext.removeAttribute(key);
                                }
                            } catch (Exception e) {
                                // tomcat session invalided下 getAttribute方法会抛异常
                                ucContext.removeAttribute(key);
                            }
                        }
                    }
                }
            }
        }
    }
}
