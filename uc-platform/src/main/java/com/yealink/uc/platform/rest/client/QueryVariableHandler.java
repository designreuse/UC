package com.yealink.uc.platform.rest.client;

import java.util.List;

import com.yealink.uc.platform.utils.EncodingUtil;

public class QueryVariableHandler {
    private final ParameterPosition[] positions;

    QueryVariableHandler(List<ParameterPosition> positions) {
        this.positions = positions.toArray(new ParameterPosition[positions.size()]);
    }

    String urlParams(Object[] arguments) {
        StringBuilder builder = new StringBuilder("?");
        int index = 0;
        for (ParameterPosition position : positions) {
            Object argument = arguments[position.index];
            if (argument == null) continue;
            if (index > 0) {
                builder.append('&');
            }
            builder.append(position.name).append('=').append(EncodingUtil.url(String.valueOf(argument)));
            index++;
        }
        return builder.toString();
    }
}
