package com.yealink.uc.platform.log;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.pattern.MessageConverter;
import java.util.Map;

public class FilterMessagePattern extends PatternLayout {
    private static FilterMessagePattern INSTANCE = new FilterMessagePattern();

    FilterMessagePattern() {
        super();
        Map<String, String> converters = getDefaultConverterMap();
        converters.put("m", MessageConverter.class.getName());
        converters.put("msg", MessageConverter.class.getName());
        converters.put("message", MessageConverter.class.getName());
        setPattern("%d [%thread] %-5level %logger{36} - %msg%n");
    }

    public static FilterMessagePattern get() {
        return INSTANCE;
    }
}