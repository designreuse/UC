package com.yealink.uc.common.modules.security.project.vo;

/**
 * Created by chenkl on 2016/8/11.
 */
public enum Project {
    UC_OFWEB("uc_ofweb"),UC_WEB("uc_web");
    private String name;
    Project(String name){
        this.name=name;
    };

    public String getName(){
        return name;
    }
}
