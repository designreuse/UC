package com.yealink.imweb.modules.vote.listener;

import com.yealink.imweb.modules.vote.entity.Vote;

/**
 * Created by yl1240 on 2016/8/8.
 */
public interface IVoteEventListener {
    public void createVote(Vote vote, String relStaffId, String url);
}
