package com.yealink.uc.platform.web.permission;

import javax.annotation.PostConstruct;

/**
 * @author ChNan
 */
public class PermissionInitializer {
    @PostConstruct
    public void init() {
        System.out.print("This is permission init");
    }

}
