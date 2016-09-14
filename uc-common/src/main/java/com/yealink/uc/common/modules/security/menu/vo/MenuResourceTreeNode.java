package com.yealink.uc.common.modules.security.menu.vo;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

public class MenuResourceTreeNode {
    private Long id;
    private Long pid;
    private String name;
    private String url;
    private String pinyin;
    private String pinyinAlia;
    private String type;
    private String parentType;

    public String getUrl() {
        return url;
    }

    public Long getId() {
        return id;
    }

    public Long getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setPid(final Long pid) {
        this.pid = pid;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(final String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPinyinAlia() {
        return pinyinAlia;
    }

    public void setPinyinAlia(final String pinyinAlia) {
        this.pinyinAlia = pinyinAlia;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(final String parentType) {
        this.parentType = parentType;
    }


    public MenuResourceTreeNode(Long id, String name, Long pid, String url, String type, String parentType) {
        this.id = id;
        this.pid = pid;
        this.url = url;
        this.name = name;
        this.pinyin = PinyinHelper.convertToPinyinString(this.name, "", PinyinFormat.WITHOUT_TONE);
        this.pinyinAlia = PinyinHelper.getShortPinyin(this.name);
        this.type = type;
        this.parentType = parentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuResourceTreeNode that = (MenuResourceTreeNode) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
