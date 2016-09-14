package com.yealink.uc.service.modules.muc.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by yl1227 on 2016/8/23.
 */
public class MucRoomView {
    private Long roomId;
    private String roomJid;
    private int roomType;
    private boolean isPublic;
    private String roomNaturalName;
    private String desc;
    private Date creationDate;
    private List<MucRoomMember> members;

    public MucRoomView() {
    }

    public MucRoomView(Long roomId, String roomJid, int roomType, boolean isPublic, String roomNaturalName, String desc, Date creationDate, List<MucRoomMember> members) {
        this.roomId = roomId;
        this.roomJid = roomJid;
        this.roomType = roomType;
        this.isPublic = isPublic;
        this.roomNaturalName = roomNaturalName;
        this.desc = desc;
        this.creationDate = creationDate;
        this.members = members;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomJid() {
        return roomJid;
    }

    public void setRoomJid(String roomJid) {
        this.roomJid = roomJid;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getRoomNaturalName() {
        return roomNaturalName;
    }

    public void setRoomNaturalName(String roomNaturalName) {
        this.roomNaturalName = roomNaturalName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public List<MucRoomMember> getMembers() {
        return members;
    }

    public void setMembers(List<MucRoomMember> members) {
        this.members = members;
    }
}
