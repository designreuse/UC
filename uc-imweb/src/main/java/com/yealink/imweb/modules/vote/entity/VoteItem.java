package com.yealink.imweb.modules.vote.entity;

import com.yealink.imweb.modules.common.annotation.Entity;

import javax.validation.constraints.NotNull;

/**
 * Created by yl1240 on 2016/7/11.
 */
@Entity(name = "VoteItem")
public class VoteItem {

    private String _id;

    private Long voteId;

    private String voteItem;

    private boolean isHavePicture;

    private String picturePath;

    private String itemDesc;

    private String itemIndex;

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

    public String getVoteItem() {
        return voteItem;
    }

    public void setVoteItem(String voteItem) {
        this.voteItem = voteItem;
    }

    public boolean getIsHavePicture() {
        return isHavePicture;
    }

    public void setIsHavePicture(boolean havePicture) {
        isHavePicture = havePicture;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(String itemIndex) {
        this.itemIndex = itemIndex;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final VoteItem org = (VoteItem) o;

        if (_id != null ? !_id.equals(org._id) : org._id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return _id != null ? _id.hashCode() : 0;
    }
}
