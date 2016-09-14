package com.yealink.uc.service.modules.muc.vo;

/**
 * Created by yl1227 on 2016/8/23.
 */
public class MucRoomMember {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户JID
     */
    private String jid;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户在群组中的角色：
     * 1.owner  群主
     * 2.admin  管理员
     * 3.member 普通成员
     */
    private String affliation;

    public MucRoomMember() {
    }

    public MucRoomMember(Long userId, String jid, String name, String affliation) {
        this.userId = userId;
        this.jid = jid;
        this.name = name;
        this.affliation = affliation;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAffliation() {
        return affliation;
    }

    public void setAffliation(String affliation) {
        this.affliation = affliation;
    }
}
