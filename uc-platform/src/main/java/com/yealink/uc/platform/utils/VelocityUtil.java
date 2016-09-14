package com.yealink.uc.platform.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

/**
 * @author ChNan
 */
public class VelocityUtil {
    static VelocityEngineFactoryBean velocityEngineFactoryBean;

    static {
        velocityEngineFactoryBean = new VelocityEngineFactoryBean();
        velocityEngineFactoryBean.setResourceLoaderPath("classpath:/vm");
        Map<String, Object> map = new HashMap<>();
        map.put("default.contentType", "text/html; charset=utf-8");
        map.put("output.encoding", "utf-8");
        map.put("input.encoding", "utf-8");
        velocityEngineFactoryBean.setVelocityPropertiesMap(map);
    }

    public static VelocityEngine getVelocityEngine() throws IOException {
        return velocityEngineFactoryBean.createVelocityEngine();
    }
}
