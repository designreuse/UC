package com.yealink.ofweb.modules.base;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * servletContextUtil
 * Created by pzy on 2016/9/4.
 */
@Component
public class ServletContextUtil implements ServletContextAware {
    private static ServletContext servletContext;
    @Override
    public void setServletContext(ServletContext context) {
        servletContext = context;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }
}
