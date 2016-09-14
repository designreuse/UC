package com.yealink.uc.web.modules.system.request;

import javax.validation.constraints.NotNull;

/**
 * @author ChNan
 */
public class LocaleChangeRequest {
    @NotNull
    private String language;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }
}
