package com.yealink.uc.common.modules.security.module.vo;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

/**
 * @author ChNan
 */
public class ModuleTreeNode {
    private Long id;
    private String name;
    private String pinyin;
    private String pinyinAlia;
    private Long pid;
    private String type;
    private String parentType;

    public ModuleTreeNode(Long id, String name, Long pid, String type, String parentType) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.pinyin = PinyinHelper.convertToPinyinString(this.name, "", PinyinFormat.WITHOUT_TONE);
        this.pinyinAlia = PinyinHelper.getShortPinyin(this.name);
        this.type = type;
        this.parentType = parentType;
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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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
}
