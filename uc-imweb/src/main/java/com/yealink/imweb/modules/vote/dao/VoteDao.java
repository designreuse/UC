package com.yealink.imweb.modules.vote.dao;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.OrderBy;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.imweb.modules.common.util.EntityUtil;
import com.yealink.imweb.modules.vote.entity.Vote;
import com.yealink.imweb.modules.vote.entity.VoteStaff;
import com.yealink.imweb.modules.vote.vo.FindType;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.web.pager.PagerRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.ne;
import static com.yealink.dataservice.client.util.Filter.lt;
import static com.yealink.dataservice.client.util.Filter.in;

/**
 * Created by yl1240 on 2016/7/15.
 */
@Repository
public class VoteDao {

    public static final String VOTE_ENTITY_NAME = EntityUtil.getEntityName(Vote.class);
    private RemoteServiceFactory remoteServiceFactory = RemoteServiceFactory.getInstance();
    private IRemoteDataService remoteDataService = remoteServiceFactory.getRemoteDataService();



    //增加投票
    public long addVote(Vote vote) {
        return remoteDataService.insertOne(VOTE_ENTITY_NAME, vote);
    }

    //通过ID查询投票
    public Vote findVoteById(Long voteId) {
        Map<String, Object> voteMap = remoteDataService.queryOne(VOTE_ENTITY_NAME, null, and(eq("_id", voteId)).toMap());
        if (voteMap == null) return null;
        return DataConverter.copyFromMap(new Vote(), voteMap);
    }
    //通过IDS查询投票
    public List<Vote> findVoteByIds(List<Long> ids) {
        List<Map<String, Object>> voteMapList = remoteDataService.query(VOTE_ENTITY_NAME, null, in("_id", ids).toMap());
        if (voteMapList == null) return null;
        return mapConvertToList(voteMapList);
    }
    private List<Vote> mapConvertToList(List<Map<String, Object>> voteMapList) {
        if (CollectionUtils.isEmpty(voteMapList)) return null;
        Collection resourceList = CollectionUtils.collect(voteMapList, new Transformer() {
            @Override
            public Object transform(Object input) {
                return DataConverter.copyFromMap(new Vote(), (Map<String, Object>) input);
            }
        });
        return (List<Vote>) resourceList;
    }
    //查询发起人发起的投票
    public List<Vote> findMyOriVotes(PagerRequest pagerRequest, Long oriStaffId) {
        //查询条件
        Long finishDate = (new Date()).getTime();
        List<Map<String,Object>> andArray=new ArrayList<>();
        andArray.add(eq("oriStaffId", oriStaffId).toMap());
        Map conMap = new HashMap();
        conMap.put("$and", andArray);
        //分页条件
        Pager<Map<String, Object>> pager = new Pager();
        pager.setAutoCount(true);
        pager.setSkip(pagerRequest.getSkip());
        pager.setLimit(pagerRequest.getPageSize());
        //排序
        pager.setOrderbys(Collections.singletonList(new OrderBy("createDate", OrderBy.DESC)));

        //VoteStaff
        pager =remoteDataService.query(VOTE_ENTITY_NAME, null, pager, conMap);
        //设置总记录数
        pagerRequest.setTotal(pager.getTotal());
        return convertToVotes(pager.getData());
    }

    private List<Vote> convertToVotes(List<Map<String, Object>> mapList) {
        if (CollectionUtils.isEmpty(mapList)) return null;
        return (List<Vote>) CollectionUtils.collect(mapList, new Transformer() {
            @Override
            public Object transform(final Object input) {
                return DataConverter.copyFromMap(new Vote(), (Map<String, Object>) input);
            }
        });
    }

    //删除投票
    public long delVote(Long voteId) {
        return remoteDataService.deleteOne(VOTE_ENTITY_NAME, voteId);
    }

    //更新投票
    public long updateVote(long voteId,long finishDate) {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("finishDate", finishDate);
        return remoteDataService.updateMany(VOTE_ENTITY_NAME, and(eq("_id", voteId)).toMap(), updateMap);
    }
}
