package com.yealink.uc.service.modules.org.vo;

public class OrgTreeNodeView {
    private Long id;
    private String name;
    private Long pid;
    private String type;
    private String parentType;
    private boolean isParent;
    private String gender;
    private String pinyin;
    private String pinyinAlia;
    private String mail;
    private int index;
    private String extensionNumber;
    private String phoneIP;

    public OrgTreeNodeView() {
    }

    public OrgTreeNodeView(final Long id, final String name, final Long pid,
                           final String type, final String parentType, final boolean isParent,
                           final String gender, final String pinyin, final String pinyinAlia,
                           final String mail, final int index, final String extensionNumber, final String phoneIP) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.type = type;
        this.parentType = parentType;
        this.isParent = isParent;
        this.gender = gender;
        this.pinyin = pinyin;
        this.pinyinAlia = pinyinAlia;
        this.mail = mail;
        this.index = index;
        this.extensionNumber = extensionNumber;
        this.phoneIP = phoneIP;
    }

    public OrgTreeNodeView(final Long id, final String name, final Long pid,
                           final String type, final String parentType, final boolean isParent,
                           final String gender, final String pinyin, final String pinyinAlia,
                           final String mail, final int index) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.type = type;
        this.parentType = parentType;
        this.isParent = isParent;
        this.gender = gender;
        this.pinyin = pinyin;
        this.pinyinAlia = pinyinAlia;
        this.mail = mail;
        this.index = index;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPinyinAlia() {
        return pinyinAlia;
    }

    public void setPinyinAlia(String pinyinAlia) {
        this.pinyinAlia = pinyinAlia;
    }

    public boolean isParent() {
        return isParent;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(final String parentType) {
        this.parentType = parentType;
    }

    public String getExtensionNumber() {
        return extensionNumber;
    }

    public void setExtensionNumber(final String extensionNumber) {
        this.extensionNumber = extensionNumber;
    }

    public String getPhoneIP() {
        return phoneIP;
    }

    public void setPhoneIP(final String phoneIP) {
        this.phoneIP = phoneIP;
    }
}
