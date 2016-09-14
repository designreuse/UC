package com.yealink.imweb.modules.vote.dao;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.OrderBy;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.imweb.modules.common.util.EntityUtil;
import com.yealink.imweb.modules.vote.entity.VoteItem;
import com.yealink.imweb.modules.vote.entity.VoteStaff;
import com.yealink.imweb.modules.vote.request.FindVoteRequest;
import com.yealink.imweb.modules.vote.vo.FindType;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.DateUtil;
import com.yealink.uc.platform.web.pager.PagerRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.yealink.dataservice.client.util.Filter.and;
import static com.yealink.dataservice.client.util.Filter.eq;
import static com.yealink.dataservice.client.util.Filter.gt;
import static com.yealink.dataservice.client.util.Filter.lte;

/**
 * Created by yl1240 on 2016/7/15.
 */
@Repository
public class VoteStaffDao {
    public static final String VOTE_STAFF_ENTITY_NAME = EntityUtil.getEntityName(VoteStaff.class);
    private RemoteServiceFactory remoteServiceFactory = RemoteServiceFactory.getInstance();
    private IRemoteDataService remoteDataService = remoteServiceFactory.getRemoteDataService();


    //更新
    public long update(Long voteId, Long staffId, String voteItems,Long voteDate ) {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("voteItems", voteItems);
        updateMap.put("voteDate", voteDate);
        return remoteDataService.updateMany(VOTE_STAFF_ENTITY_NAME, and(eq("voteId", voteId), eq("staffId", staffId)).toMap(), updateMap);
    }

    public long updateFinishDate(Long voteId,Long voteDate ) {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("finishDate", voteDate);
        return remoteDataService.updateMany(VOTE_STAFF_ENTITY_NAME, and(eq("voteId", voteId)).toMap(), updateMap);
    }
    //批量保存
    public Long batchAdd(List<VoteStaff> VoteStaffList) {
        return remoteDataService.insertMany(VOTE_STAFF_ENTITY_NAME, VoteStaffList);
    }

    //查询投票详情
    public List<VoteStaff> findVoteStaffsByVoteId(Long voteId){
        List<Map<String, Object>> voteStaffList = remoteDataService.query(VOTE_STAFF_ENTITY_NAME, null, and(eq("voteId", voteId)).toMap());
        return convertToVoteStaffs(voteStaffList);
    }

    //查询未投票的投票人
    public List<VoteStaff> findUnVotedStaffsByVoteId(Long voteId){
        List<Map<String, Object>> voteStaffList = remoteDataService.query(VOTE_STAFF_ENTITY_NAME, null, and(eq("voteId", voteId),eq("voteItems", null)).toMap());
        return convertToVoteStaffs(voteStaffList);
    }

    private List<VoteStaff> convertToVoteStaffs(List<Map<String, Object>> voteStaffList) {
        if (CollectionUtils.isEmpty(voteStaffList)) return null;
        return (List<VoteStaff>) CollectionUtils.collect(voteStaffList, new Transformer() {
            @Override
            public Object transform(final Object input) {
                return DataConverter.copyFromMap(new VoteStaff(), (Map<String, Object>) input);
            }
        });
    }
    //获得投票ID列表:我参与的
    public List<Long> findMyRelOrOriVote(PagerRequest pagerRequest, Long staffId, Long findType) {
        //查询条件
        Long nowDate = (new Date()).getTime();
        List<Map<String,Object>> andArray=new ArrayList<>();
        andArray.add(eq("staffId", staffId).toMap());
        if(FindType.VOTING.getValue()==findType){
            andArray.add(gt("finishDate", nowDate).toMap());
        }else{
            andArray.add(lte("finishDate", nowDate).toMap());
        }
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
        pager =remoteDataService.query(VOTE_STAFF_ENTITY_NAME, "voteId", pager, conMap);
        if (CollectionUtils.isEmpty(pager.getData())) {
            return null;
        }
        //设置总记录数
        pagerRequest.setTotal(pager.getTotal());
        //返回结果
        return (List<Long>) CollectionUtils.collect(pager.getData(), new Transformer() {
            @Override
            public Long transform(Object input) {
                Number result = ((Map<String, Long>) input).get("voteId");
                return result.longValue();
            }
        });
    }


    public long delVoteStaff(Long voteId) {
        return remoteDataService.deleteMany(VOTE_STAFF_ENTITY_NAME, eq("voteId", voteId).toMap());
    }

}
