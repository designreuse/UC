package com.yealink.imweb.modules.common.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yealink.imweb.modules.common.constant.Constant;
import com.yealink.imweb.modules.common.interceptor.vo.AuthResult;
import com.yealink.imweb.modules.common.interceptor.vo.UserInfo;
import com.yealink.imweb.modules.common.util.HttpClientUtil;
import com.yealink.imweb.modules.common.util.StringUtil;
import com.yealink.uc.platform.utils.JSONConverter;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Created by yl1240 on 2016/8/17.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    //认证服务器
    //public static final String AUTH_URL = "http://10.3.17.148:9090/plugins/ofmanageservice/rest/ews_auth";
    public static final String AUTH_URL = ResourceBundle.getBundle("config.service").getString("of.service.ews_auth");

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        //用户的区域
        Locale local = LocaleContextHolder.getLocale();
        //设置编码
        response.setContentType("text/plain; charset=utf-8");
        //用户已经认证通过
        HttpSession session = request.getSession();
        //设置定期刷新时间
        int freshInterval = session.getMaxInactiveInterval();
        session.setAttribute(Constant.FRESH_INTERVAL, freshInterval);//单位：秒
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user != null) {
            return true;
        }
        //获得token
        String tokenStr = request.getHeader("Authorization"); //获得Token信息
        if (tokenStr == null) {
            tokenStr = request.getParameter("token");//url参数
        }

        if (tokenStr == null) {
            dealException(response, 401, "Token Not Exists");
            return false;
        }

        String token = tokenStr.replace("Basic ", "");
        //IM服务器认证
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("token", token);
        String result = null;
        try {
            result = HttpClientUtil.simpleGetInvoke(AUTH_URL, paramsMap, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            dealException(response, 401, "Authorization Service Failed");
            return false;
        }

        if (StringUtil.isEmpty(result)) {
            dealException(response, 401, "Authorization Failed!");
            return false;
        }
        AuthResult authResult = JSONConverter.fromJSON(AuthResult.class, result);
        if (authResult.getRet() != 0) {
            dealException(response, 401, authResult.getMsg());
            return false;
        }
        UserInfo userInfo = authResult.getUserInfo();
        session.setAttribute("user", userInfo);
        return true;
    }

    //处理错误信息
    private void dealException(HttpServletResponse response, int status, String msg) throws IOException {
        response.setStatus(status);
        response.getWriter().write(msg);

    }
}
