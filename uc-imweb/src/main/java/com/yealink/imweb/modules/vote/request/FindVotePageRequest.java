package com.yealink.imweb.modules.vote.request;

import java.util.List;

/**
 * Created by yl1240 on 2016/8/25.
 */
public class FindVotePageRequest {
    List<FindVoteRequest> voteList;
    VotePageRequest page;

    public List<FindVoteRequest> getVoteList() {
        return voteList;
    }

    public void setVoteList(List<FindVoteRequest> voteList) {
        this.voteList = voteList;
    }

    public VotePageRequest getPage() {
        return page;
    }

    public void setPage(VotePageRequest page) {
        this.page = page;
    }
}
