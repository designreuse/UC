package com.yealink.imweb.modules.vote.request;

import javax.validation.constraints.NotNull;

/**
 * Created by yl1240 on 2016/7/29.
 */
public class DelVoteRequest {
    @NotNull
    private Long voteId;

    @NotNull
    private String findType;

    public String getFindType() {
        return findType;
    }

    public void setFindType(String findType) {
        this.findType = findType;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }
}
