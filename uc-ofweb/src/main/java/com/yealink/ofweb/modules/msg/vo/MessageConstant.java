package com.yealink.ofweb.modules.msg.vo;

/**
 * 消息常量
 * author:pengzhiyuan
 * Created on:2016/8/8.
 */
public class MessageConstant {
    private MessageConstant(){
    }

    //====以下为property表中 属性的_id值 ======
    // 撤回消息有效时间 属性名称 单位秒
    public static final String PROPERTY_NAME_REVERT_VALIDTIME = "msg.revert.validtime";
    // 消息存储时间 属性名称 单位天
    public static final String PROPERTY_NAME_MSG_SAVEDAYS = "msg.save.days";
    // 最近联系人保存的最大个数 属性名称
    public static final String PROPERTY_NAME_RECENTRELA_MAXNUMBER = "msg.recent.maxnumber";

}
