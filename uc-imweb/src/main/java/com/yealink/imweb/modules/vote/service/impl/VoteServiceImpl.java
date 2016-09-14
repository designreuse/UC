package com.yealink.imweb.modules.vote.service.impl;

import com.yealink.imweb.modules.common.constant.ServiceConstant;
import com.yealink.imweb.modules.common.dao.IdGeneratorDao;
import com.yealink.imweb.modules.common.interceptor.vo.UserInfo;
import com.yealink.imweb.modules.common.util.DateUtil;
import com.yealink.imweb.modules.common.util.EntityUtil;
import com.yealink.imweb.modules.common.util.ListSortUtil;
import com.yealink.imweb.modules.common.util.ToolUtil;
import com.yealink.imweb.modules.vote.dao.VoteDao;
import com.yealink.imweb.modules.vote.dao.VoteItemDao;
import com.yealink.imweb.modules.vote.dao.VoteStaffDao;
import com.yealink.imweb.modules.vote.entity.Vote;
import com.yealink.imweb.modules.vote.entity.VoteItem;
import com.yealink.imweb.modules.vote.entity.VoteStaff;
import com.yealink.imweb.modules.vote.producer.VoteMessageProducer;
import com.yealink.imweb.modules.vote.request.CreateVoteRequest;
import com.yealink.imweb.modules.vote.request.DelVoteRequest;
import com.yealink.imweb.modules.vote.request.FindVoteRequest;
import com.yealink.imweb.modules.vote.request.SubmitVoteRequest;
import com.yealink.imweb.modules.vote.service.VoteService;
import com.yealink.imweb.modules.vote.vo.FindType;
import com.yealink.imweb.modules.vote.vo.StaffType;
import com.yealink.imweb.modules.vote.vo.VoteItemStatistics;
import com.yealink.imweb.modules.vote.vo.VoteStatus;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.web.pager.PagerRequest;
import com.yealink.uc.service.modules.staff.api.StaffRESTService;
import com.yealink.uc.service.modules.staff.response.GetStaffRESTResponse;
import com.yealink.uc.service.modules.staff.vo.StaffView;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yl1240 on 2016/7/15.
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private IdGeneratorDao idGeneratorDao;

    @Autowired
    private VoteDao voteDao;

    @Autowired
    private VoteItemDao voteItemDao;

    @Autowired
    private VoteStaffDao voteStaffDao;

    @Autowired
    VoteMessageProducer voteMessageProducer;

//    @Autowired
//    StaffRESTService staffRESTService;
    //创建投票
    @Override
    public boolean createVote(CreateVoteRequest createVoteRequest, UserInfo userInfo,String url) throws ParseException {
        //-----投票
        Vote vote = new Vote();
        Long voteId = idGeneratorDao.nextId(EntityUtil.getEntityCode(Vote.class));
        vote.set_id(voteId);
        vote = DataConverter.copy(vote, createVoteRequest);
        //要从ume传进来
        vote.setOriStaffId(userInfo.getUserId());
        vote.setOriStaffName(userInfo.getName());
        //开始时间：当前日期
        Long createDate = DateUtil.currentDate().getTime();
        vote.setCreateDate(createDate);
        //结束时间：结束日期+" 23:59:59"
        String finishDateStr = DateUtil.toString(createVoteRequest.getFinishDate(),"yyyy-MM-dd") + " 23:59:59";
        Date finishDate = DateUtil.toDate(finishDateStr, "yyyy-MM-dd HH:mm:ss");
        //如果结束时间早于提交的服务器时间，则以服务器时间+24小时生成投票时间
        vote.setFinishDate((finishDate.getTime() < createDate ? DateUtil.currentDate().getTime() + 24*60*60*1000 :finishDate.getTime()));
        //保存投票
        long insertCount = voteDao.addVote(vote);
        if (insertCount == 0) {
            return false;
        }
        //保存：投票参与人
        List<VoteStaff> voteStaffList = new ArrayList<>();
        String[] relStaffId = ToolUtil.string2Array(createVoteRequest.getRelStaffId(),";");
        String[] relStaffName = ToolUtil.string2Array(createVoteRequest.getRelStaffName(),";");
        //默认不包含发起人
        boolean isContainOriStaff = false;
        for (int i=0;i<relStaffId.length;i++){
            Integer staffType = StaffType.REL_STAFF.getValue();
            Long curId = Long.parseLong(relStaffId[i]);
            if(vote.getOriStaffId().equals(curId)){
                staffType = StaffType.REL_ORI_STAFF.getValue();
                //包含发起人
                isContainOriStaff = true;
            }
            VoteStaff voteStaff = new VoteStaff(voteId,Long.parseLong(relStaffId[i]),relStaffName[i],createDate,finishDate.getTime(),staffType);
            voteStaffList.add(voteStaff);
        }
        //加入发起人：不包含时，加入发起人，以便分页查询
        if(!isContainOriStaff){
            VoteStaff voteStaff = new VoteStaff(voteId,vote.getOriStaffId(),vote.getOriStaffName(),createDate,finishDate.getTime(),StaffType.ORI_STAFF.getValue());
            voteStaffList.add(voteStaff);
        }
        insertCount = voteStaffDao.batchAdd(voteStaffList);
        if (insertCount == 0) {
            return false;
        }
        //保存：投票选项
        List<VoteItem> voteItemList = createVoteRequest.getVoteItemList();
        for (int i = 0;i<voteItemList.size();i++){
            String voteItemId = voteId + "_" + i;
            VoteItem voteItem = voteItemList.get(i);
            voteItem.set_id(voteItemId);
            voteItem.setVoteId(voteId);
        }
        insertCount = voteItemDao.batchAdd(voteItemList);
        if (insertCount == 0) {
            return false;
        }
        //发送事件通知
        voteMessageProducer.createVote(vote,ToolUtil.array2String(relStaffId,";"),url);//参与人列表
        return true;
    }


    //查找投票
    public List<FindVoteRequest> findVote(PagerRequest pagerRequest, Long staffId, Long findType , HashMap<Long,StaffView>allStaffsMap) {
        List<FindVoteRequest> findVoteRequestList;
        List<Vote> voteList = new ArrayList<>();
        //未完成投票与已完成投票
        if(FindType.VOTING.getValue()==findType || FindType.VOTED.getValue() == findType){
            List<Long> voteIds = voteStaffDao.findMyRelOrOriVote(pagerRequest,staffId,findType);
            if(voteIds != null){voteList = voteDao.findVoteByIds(voteIds);}
            //持久层没有对无分布的排序功能，只能自己排序了
            ListSortUtil<Vote> sortUtil = new ListSortUtil(Vote.class);
            try {
                sortUtil.addDesc("createDate");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            sortUtil.sortList(voteList);
        } else if(FindType.MYVOTE.getValue()==findType){//我发起的投票
            voteList = voteDao.findMyOriVotes(pagerRequest,staffId);
        }
        //投票列表（投票信息|投票选项信息|参与投票信息）
        findVoteRequestList = getFindVoteRequest(voteList,staffId,allStaffsMap);
        return findVoteRequestList;
    }

    //查询投票列表
    public List<FindVoteRequest> getFindVoteRequest(List<Vote> voteList, Long staffId ,HashMap<Long,StaffView>allStaffsMap){
        List<FindVoteRequest> findVoteRequestList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(voteList)){
            for (int i=0;i<voteList.size();i++){
                FindVoteRequest findVoteRequest = new FindVoteRequest();
                Vote vote = voteList.get(i);
                findVoteRequest.setLineNum(i+1);//行号
                Long voteId = vote.get_id();
                DataConverter.copy(findVoteRequest,vote);
                findVoteRequest.setVoteId(voteId);
                //设置发起人头像
                findVoteRequest.setOriStaffAvatar(getStaffAvatar(staffId,allStaffsMap));
                if(staffId.equals(vote.getOriStaffId())){//先写死
                    findVoteRequest.setIsMyOri(true);
                }
                //查询投票选择:当事务不能保证时，如果为null，报错
                List<VoteItem> voteItemList = voteItemDao.findVoteItemsByVoteId(voteId);
                List<VoteItemStatistics> voteItemStatisticsList = new ArrayList();
                convertTo(voteItemList,voteItemStatisticsList);
                //查询投票参与者:当事务不能保证时，如果为null，报错
                List<VoteStaff> voteStaffList = voteStaffDao.findVoteStaffsByVoteId(voteId);
                List<VoteStaff> votedStaffList = new ArrayList<>();//已投票列
                Integer relVoteCnt = 0;
                Integer votedCnt = 0;
                Boolean isVoted = false;
                Integer myStaffType = StaffType.ORI_STAFF.getValue();//默认参与者，但后面一定有一条记录重新赋值
                if(!CollectionUtils.isEmpty(voteStaffList)){
                    for(int j = 0 ;j<voteStaffList.size(); j++){
                        String staffName = voteStaffList.get(j).getStaffName();
                        Long curStaffId = voteStaffList.get(j).getStaffId();
                        Integer staffType = voteStaffList.get(j).getStaffType();
                        String voteItems = voteStaffList.get(j).getVoteItems();
                        //设置投票人头像
                        voteStaffList.get(j).setAvatar(getStaffAvatar(curStaffId,allStaffsMap));
                        //我的选择
                        if(staffId.equals(curStaffId)){
                            myStaffType = staffType;
                            if(voteStaffList.get(j).getVoteDate() != null){//已有投票时间
                                isVoted = true;//选择
                            }
                            findVoteRequest.setMySelectItem(voteItems);//列表转字符串
                            isMySelect(voteItemStatisticsList,voteItems);
                        }

                       /* if(staffType == StaffType.REL_STAFF.getValue() || staffType == StaffType.REL_ORI_STAFF.getValue()){
                            if(staffId == vote.getOriStaffId()){
                                myStaffType = staffType;
                                if(voteStaffList.get(j).getVoteDate() != null){//已有投票时间
                                    isVoted = true;//选择
                                }
                                findVoteRequest.setMySelectItem(voteItems);//列表转字符串
                                isMySelect(voteItemStatisticsList,voteItems);
                            }
                        }*/
                        //设置统计信息
                        statisticsVoteStaff(voteItemStatisticsList,voteItems);

                        //邀请人数：排除发起人
                        if(staffType != StaffType.ORI_STAFF.getValue()){
                            relVoteCnt++;
                        }
                        findVoteRequest.setRelVoteCnt(relVoteCnt);
                        //投票人数
                        if(voteStaffList.get(j).getVoteDate()!=null){
                            votedStaffList.add(voteStaffList.get(j));
                            votedCnt++;
                        }
                        findVoteRequest.setVotedCnt(votedCnt);
                    }
                }
                //已投票详情
                findVoteRequest.setVoteStaffList(votedStaffList);
                //选项统计
                findVoteRequest.setVoteItemStatisticsList(voteItemStatisticsList);
                //获得状态
                String voteStatus = getVoteStatus(myStaffType,findVoteRequest.getFinishDate(),isVoted);
                findVoteRequest.setVoteStatus(voteStatus);
                findVoteRequestList.add(findVoteRequest);
            }
        }
        return  findVoteRequestList;
    }

    //获得头像URL
    private String getStaffAvatar(Long staffId ,HashMap<Long,StaffView> allStaffsMap) {
        //这部分由于每个参与人都要调用一次，情况相当慢，让伟群提供其它api，传入员工ID列表，返回员工列表。
//        GetStaffRESTResponse staff = staffRESTService.getStaff(staffId);
//        String avatar = staff.getStaff().getAvatar();

//        if(avatar != null){
//            avatarURL = String.format(ServiceConstant.FILE_SERVICE,avatar,48,48);
//        }
        StaffView view = allStaffsMap.get(staffId);
        if(view == null){
            return "";
        }
        String avatar = view.getAvatar();
        if(avatar != null){
            return String.format(ServiceConstant.FILE_SERVICE,avatar,48,48);
        }else{
            return "";
        }
    }

    //提交投票
    public boolean submitVoting(SubmitVoteRequest svr,Long staffId,Long date) {
        Long cnt = voteStaffDao.update(svr.getVoteId(),staffId,svr.getVoteItems(),date);

        //如果是最后一个投票的，就把投票的结束时间改成最后一次投票的时间。
        //同时还要更新vote staff的完成时间。这是一个缓存时间，跟投票创建的时间一致，这个时间在查找已完成跟未完成的时候用!!!
        List<VoteStaff>  unVotedlist = voteStaffDao.findUnVotedStaffsByVoteId(svr.getVoteId());
        if(unVotedlist == null || unVotedlist.size() == 0){
            voteDao.updateVote(svr.getVoteId(),date);
            voteStaffDao.updateFinishDate(svr.getVoteId(),date);
        }
        return cnt == 1;
    }

    //删除投票
    @Override
    public boolean delVote(Long voteId) {
        long delCnt;
        //删除VoteStaff
        delCnt = voteStaffDao.delVoteStaff(voteId);
        if (delCnt == 0) {
            return false;
        }
        //删除VoteItem
        delCnt = voteItemDao.delVoteItem(voteId);
        if (delCnt == 0) {
            return false;
        }
        //删除Vote
        delCnt = voteDao.delVote(voteId);
        if (delCnt == 0) {
            return false;
        }
        return true;
    }

    //获得投票状态
    private String getVoteStatus(Integer myStaffType, Long finishDate, Boolean isVoted) {
        Long nowTime = (new Date()).getTime();
        if(nowTime <= finishDate){//未完成
            if(myStaffType.equals(StaffType.ORI_STAFF.getValue())){//只是发起人，没有参与
                return VoteStatus.NOT_PART_IN.getValue();
            }else {
                if(isVoted){
                    return VoteStatus.HAVE_VOTE.getValue();
                }else {
                    return VoteStatus.NOT_VOTE.getValue();
                }
            }
        }else{
            return VoteStatus.HAVE_FINISH.getValue();
        }
    }
    //我的选择
    private void isMySelect(List<VoteItemStatistics> voteItemStatisticsList,String voteItems) {
        if(voteItems != null){
            for (VoteItemStatistics vis:voteItemStatisticsList) {
                if(voteItems.contains(vis.get_id())){
                    vis.setIsMySelect(true);
                }
            }
        }

    }
    //选项统计
    private void statisticsVoteStaff(List<VoteItemStatistics> voteItemStatisticsList,String voteItems){
        if(voteItems != null){
            for (int j = 0; j < voteItemStatisticsList.size(); j++) {
                if (voteItems.contains(voteItemStatisticsList.get(j).get_id())) {
                    Integer cnt = voteItemStatisticsList.get(j).getSelectCnt();
                    voteItemStatisticsList.get(j).setSelectCnt(cnt+1);
                }
            }
        }
    }

    //投票选项to投票选项统计
    private void convertTo(List<VoteItem> voteItemList,List<VoteItemStatistics> voteItemStatisticsList){
        if(!CollectionUtils.isEmpty(voteItemList)) {
            for (int i = 0; i < voteItemList.size(); i++) {
                VoteItemStatistics voteItemStatistics = new VoteItemStatistics();
                DataConverter.copy(voteItemStatistics, voteItemList.get(i));
                voteItemStatisticsList.add(voteItemStatistics);
            }
        }
    }

}
