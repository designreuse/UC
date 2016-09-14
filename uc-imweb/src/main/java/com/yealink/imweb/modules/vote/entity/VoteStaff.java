package com.yealink.imweb.modules.vote.entity;

import com.yealink.imweb.modules.common.annotation.Entity;

import java.util.List;

/**
 * Created by yl1240 on 2016/7/15.
 */
@Entity(name = "VoteStaff")
public class VoteStaff {
    private String _id;

    private Long voteId;

    private Long staffId;

    private String staffName;

    private String avatar;

    /**
     * 1:参与者，2:发起者，3:参与者与发起者，一个相关都只保留一条记录
     */
    private Integer staffType;

    private String voteItems;

    private Long voteDate;

    private Long createDate;

    private Long finishDate;

    public VoteStaff(Long voteId, Long staffId, String staffName, Long createDate, Long finishDate ,Integer staffType) {
        this._id = voteId + "_" + staffId ;
        this.voteId = voteId;
        this.staffId = staffId;
        this.staffType = staffType;
        this.staffName = staffName;
        this.createDate = createDate;
        this.finishDate = finishDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public VoteStaff(){};

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getVoteItems() {
        return voteItems;
    }

    public void setVoteItems(String voteItems) {
        this.voteItems = voteItems;
    }

    public Long getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Long voteDate) {
        this.voteDate = voteDate;
    }

    public Long getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Long finishDate) {
        this.finishDate = finishDate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Integer getStaffType() {
        return staffType;
    }

    public void setStaffType(Integer staffType) {
        this.staffType = staffType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final VoteStaff org = (VoteStaff) o;

        if (_id != null ? !_id.equals(org._id) : org._id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return _id != null ? _id.hashCode() : 0;
    }
}
