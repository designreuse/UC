package com.yealink.imweb.modules.staff.vo;

import java.util.Date;

/**
 * Created by yl1240 on 2016/8/23.
 */
public class MucRoomTreeNodeView {
    private Long id;
    private String name;
    private Long pid;
    private String type;
    private String parentType;
    private boolean isParent;
/*    private String gender;
    private String pinyin;
    private String pinyinAlia;
    private String mail;*/
    private int index;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
