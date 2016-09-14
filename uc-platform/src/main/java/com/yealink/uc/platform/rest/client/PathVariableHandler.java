package com.yealink.uc.platform.rest.client;

import java.util.List;

import com.yealink.uc.platform.utils.EncodingUtil;

class PathVariableHandler {
    private final ParameterPosition[] positions;

    PathVariableHandler(List<ParameterPosition> positions) {
        this.positions = positions.toArray(new ParameterPosition[positions.size()]);
    }

    String url(String pattern, Object[] arguments) {
        String url = pattern;
        for (ParameterPosition position : positions) {
            url = url.replace("{" + position.name + "}", EncodingUtil.urlPath(String.valueOf(arguments[position.index])));
        }
        return url;
    }
}
