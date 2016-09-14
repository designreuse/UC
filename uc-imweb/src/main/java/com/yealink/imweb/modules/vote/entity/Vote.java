package com.yealink.imweb.modules.vote.entity;

import com.yealink.imweb.modules.common.annotation.Entity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by yl1240 on 2016/7/11.
 */
@Entity(name = "Vote")
public class Vote {

    private Long _id;

    private String voteTheme;

    private boolean isAnonymous;

    private boolean isPublic;

    private Long createDate;

    private Long finishDate;

    private boolean isMultiSelect;

    private Integer minItem;

    private Integer maxItem;

    private String voteDesc;

    private Long oriStaffId;

    private String oriStaffName;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getVoteTheme() {
        return voteTheme;
    }

    public void setVoteTheme(String voteTheme) {
        this.voteTheme = voteTheme;
    }

    public boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Long finishDate) {
        this.finishDate = finishDate;
    }

    public boolean getIsMultiSelect() {
        return isMultiSelect;
    }

    public void setIsMultiSelect(boolean multiSelect) {
        isMultiSelect = multiSelect;
    }

    public Integer getMinItem() {
        return minItem;
    }

    public void setMinItem(Integer minItem) {
        this.minItem = minItem;
    }

    public Integer getMaxItem() {
        return maxItem;
    }

    public void setMaxItem(Integer maxItem) {
        this.maxItem = maxItem;
    }

    public String getOriStaffName() {
        return oriStaffName;
    }

    public void setOriStaffName(String oriStaffName) {
        this.oriStaffName = oriStaffName;
    }

    public Long getOriStaffId() {
        return oriStaffId;
    }

    public void setOriStaffId(Long oriStaffId) {
        this.oriStaffId = oriStaffId;
    }

    public String getVoteDesc() {
        return voteDesc;
    }

    public void setVoteDesc(String voteDesc) {
        this.voteDesc = voteDesc;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Vote org = (Vote) o;

        if (_id != null ? !_id.equals(org._id) : org._id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return _id != null ? _id.hashCode() : 0;
    }
}
