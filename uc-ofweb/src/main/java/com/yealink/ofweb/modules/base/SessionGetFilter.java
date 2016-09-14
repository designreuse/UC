package com.yealink.ofweb.modules.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 集成部署tomcat session获取
 * author:pengzhiyuan
 * Created on:2016/9/1.
 */
public class SessionGetFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(SessionGetFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new CrossHttpServletRequestWrapper(request, response), response);
    }
}
