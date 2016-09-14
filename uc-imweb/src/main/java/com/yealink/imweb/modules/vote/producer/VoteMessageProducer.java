package com.yealink.imweb.modules.vote.producer;

import com.yealink.dataservice.client.util.Event;
import com.yealink.imweb.modules.common.producer.MessageProducer;
import com.yealink.imweb.modules.common.util.DateUtil;
import com.yealink.imweb.modules.vote.entity.Vote;
import com.yealink.imweb.modules.vote.listener.IVoteEventListener;
import com.yealink.imweb.modules.vote.vo.VoteEventType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yl1240 on 2016/8/8.
 */
@Service
public class VoteMessageProducer extends MessageProducer implements IVoteEventListener {
    private String SOURCE_VOTE = "vote";
    private String DATA_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    @Override
    public void createVote(Vote vote,String relStaffId,String url) {
        Event event = createEvent(VoteEventType.CREATE.name());
        event.setResourceId(vote.get_id());
        Map<String, Object> map = new HashMap<>();
        map.put("note",SOURCE_VOTE);
        map.put("title","投票邀请：" + vote.getVoteTheme());
        map.put("body","您好，诚邀您百忙之中，抽空投出你珍贵的一票,截止时间：" + DateUtil.timeToStr(vote.getCreateDate(),DATA_FORMAT) +"!");
        map.put("published", DateUtil.timeToStr(vote.getCreateDate(),DATA_FORMAT));
        map.put("to",relStaffId);//jid列表：1;2;3;4
        map.put("publisher",vote.getOriStaffId());//发送都Jid
        map.put("url",url);//发送给谁:EMAIL:wup@yealink.com
        event.setExValue(map);
        publishEvent(event);
    }

    @Override
    protected String getSource() {
        return SOURCE_VOTE;
    }

    @Override
    protected String getTopic() {
        return Event.TOPIC_OTHER;
    }
}
