package com.yealink.uc.platform.rest.client;

import com.yealink.uc.platform.utils.JSONConverter;

class RequestBodyHandler {
    private final int paramIndex;

    RequestBodyHandler(int paramIndex) {
        this.paramIndex = paramIndex;
    }

    String body(Object[] params) {
        return JSONConverter.toJSON(params[paramIndex]);
    }
}
