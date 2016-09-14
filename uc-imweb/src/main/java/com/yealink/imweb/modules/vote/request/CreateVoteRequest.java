package com.yealink.imweb.modules.vote.request;

import java.util.Date;
import java.util.List;

import com.yealink.imweb.modules.vote.entity.VoteItem;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by yl1240 on 2016/7/11.
 */
public class CreateVoteRequest {

    private String voteTheme;

    private boolean isAnonymous;

    private boolean isPublic;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishDate;

    private boolean isMultiSelect;

    private Integer minItem;

    private Integer maxItem;

    private String voteDesc;

    private String oriStaffId;

    private String oriStaffName;

    //参与人ID列表1;2,3
    private String relStaffId;

    //参与人名称列表:张三;王五;李四
    private String relStaffName;

    //投票选项
    private List<VoteItem> voteItemList;

    public String getRelStaffName() {
        return relStaffName;
    }

    public void setRelStaffName(String relStaffName) {
        this.relStaffName = relStaffName;
    }

    public String getOriStaffId() {
        return oriStaffId;
    }

    public void setOriStaffId(String oriStaffId) {
        this.oriStaffId = oriStaffId;
    }

    public String getOriStaffName() {
        return oriStaffName;
    }

    public void setOriStaffName(String oriStaffName) {
        this.oriStaffName = oriStaffName;
    }

    public String getRelStaffId() {
        return relStaffId;
    }

    public void setRelStaffId(String relStaffId) {
        this.relStaffId = relStaffId;
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

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
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

    public String getVoteDesc() {
        return voteDesc;
    }

    public void setVoteDesc(String voteDesc) {
        this.voteDesc = voteDesc;
    }

    public List<VoteItem> getVoteItemList() {
        return voteItemList;
    }

    public void setVoteItemList(List<VoteItem> voteItemList) {
        this.voteItemList = voteItemList;
    }
}


