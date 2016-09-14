package com.yealink.imweb.modules.vote.service;

import com.yealink.imweb.modules.common.interceptor.vo.UserInfo;
import com.yealink.imweb.modules.vote.request.CreateVoteRequest;
import com.yealink.imweb.modules.vote.request.DelVoteRequest;
import com.yealink.imweb.modules.vote.request.FindVoteRequest;
import com.yealink.imweb.modules.vote.request.SubmitVoteRequest;
import com.yealink.uc.platform.web.pager.PagerRequest;
import com.yealink.uc.service.modules.staff.vo.StaffView;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yl1240 on 2016/7/15.
 */
public interface VoteService {

    public boolean createVote(CreateVoteRequest createVoteRequest, UserInfo userInfo,String url) throws ParseException;
    public List<FindVoteRequest> findVote(PagerRequest pagerRequest, Long staffId, Long findType, HashMap<Long,StaffView>allStaffsMap);
    public boolean submitVoting(SubmitVoteRequest svr,Long staffId,Long date);
    boolean delVote(Long voteId);
}
