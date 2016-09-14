package com.yealink.imweb.modules.vote.dao;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.imweb.modules.common.util.EntityUtil;
import com.yealink.imweb.modules.vote.entity.Vote;
import com.yealink.imweb.modules.vote.entity.VoteItem;
import com.yealink.uc.platform.utils.DataConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;

/**
 * Created by yl1240 on 2016/7/15.
 */
@Repository
public class VoteItemDao {
    public static final String VOTE_ITEM_ENTITY_NAME = EntityUtil.getEntityName(VoteItem.class);
    private RemoteServiceFactory remoteServiceFactory = RemoteServiceFactory.getInstance();
    private IRemoteDataService remoteDataService = remoteServiceFactory.getRemoteDataService();

    //批量保存
    public Long batchAdd(List<VoteItem> voteItemList) {
        return remoteDataService.insertMany(VOTE_ITEM_ENTITY_NAME, voteItemList);
    }

    //通过投票ID找投票选项
    public List<VoteItem> findVoteItemsByVoteId(Long voteId) {
        List<Map<String, Object>> voteItemMap = remoteDataService.query(VOTE_ITEM_ENTITY_NAME, null, eq("voteId", voteId).toMap());
        if (CollectionUtils.isEmpty(voteItemMap)) {
            return null;
        }
       return convertToVoteItems(voteItemMap);
    }

    //map->List
    private List<VoteItem> convertToVoteItems(List<Map<String, Object>> voteItemMapList) {
        if (CollectionUtils.isEmpty(voteItemMapList)) return null;
        return (List<VoteItem>) CollectionUtils.collect(voteItemMapList, new Transformer() {
            @Override
            public Object transform(final Object input) {
                return DataConverter.copyFromMap(new VoteItem(), (Map<String, Object>) input);
            }
        });
    }

    public long delVoteItem(Long voteId) {
        return remoteDataService.deleteMany(VOTE_ITEM_ENTITY_NAME, eq("voteId", voteId).toMap());
    }
}
