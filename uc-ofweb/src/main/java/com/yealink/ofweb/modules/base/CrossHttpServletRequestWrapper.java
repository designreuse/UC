package com.yealink.ofweb.modules.base;

import com.yealink.ofweb.modules.common.Constants;
import com.yealink.uc.platform.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * tomcat下 跨session request包装类
 * Created by pzy on 2016/9/1.
 */
public class CrossHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final Logger logger = LoggerFactory.getLogger(CrossHttpServletRequestWrapper.class);
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public CrossHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public CrossHttpServletRequestWrapper(HttpServletRequest request,
                                          HttpServletResponse response) {
        super(request);
        this.request = request;
        this.response = response;
    }

    @Override
    public HttpSession getSession(boolean create) {
        HttpSession session = null;
        // 获取UC servlet context
        ServletContext servletContext = request.getServletContext().getContext("/");

        // 获取 cookieId
        String cookieId = getCookieValueByCookieName(Constants.CROSS_CONTEXT_COOKIE_NAME);

        // 从uc-web获取session信息
        if (servletContext != null) {
            session = (HttpSession) servletContext.getAttribute(Constants.CROSS_CONTEXT_SESSION_NAME+":"+cookieId);
        }
        if (session == null) {
            session = request.getSession();
        } else if (session!=null) {
            try {
                if (session.getAttribute(Constant.CURRENT_SESSION_ACCOUNT) == null) {
                    servletContext.removeAttribute(Constants.CROSS_CONTEXT_SESSION_NAME+":"+cookieId);
                    session = request.getSession();
                } else {
                    // 续期 维持session
                    keepAliveSessionForCrossContext(request, session);
                }
            } catch (Exception e) {
                // tomcat session invalided下 getAttribute方法会抛异常
                e.printStackTrace();
                servletContext.removeAttribute(Constants.CROSS_CONTEXT_SESSION_NAME+":"+cookieId);
                session = request.getSession();
            }
        }
        return session;
    }

    @Override
    public HttpSession getSession() {
        return getSession(true);
    }

    /**
     * 根据cookiename获取对应的值
     * @param cookieName
     */
    private String getCookieValueByCookieName(String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 保持session 续期
     * @param request
     */
    private void keepAliveSessionForCrossContext(HttpServletRequest request, HttpSession session) {
        try {
            String localAddr = request.getLocalAddr();
            int port = request.getLocalPort();
            String testPath = request.getScheme() + "://" + localAddr + ":" + port
                    + "/checkCaptcha";
            logger.debug("test testPath="+testPath);
            URL url = new URL(testPath);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Cookie","JSESSIONID="+session.getId());
            connection.setRequestProperty("Connection","keep-alive");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = br.readLine();
            logger.debug("test response="+response);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
