package com.yealink.imweb.modules.vote.request;

import com.yealink.uc.platform.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by yl1240 on 2016/7/28.
 */
public class SubmitVoteRequest {
    @NotNull
    private Long voteId;
    @NotNull
    private String voteItems;

    private String voteStaffName;

    private String voteDate;

    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getVoteStaffName() {
        return voteStaffName;
    }

    public void setVoteStaffName(String voteStaffName) {
        this.voteStaffName = voteStaffName;
    }

    public String getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(String voteDate) {
        this.voteDate = voteDate;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public String getVoteItems() {
        return voteItems;
    }

    public void setVoteItems(String voteItems) {
        this.voteItems = voteItems;
    }


}
