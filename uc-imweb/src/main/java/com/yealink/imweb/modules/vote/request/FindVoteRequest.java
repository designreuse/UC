package com.yealink.imweb.modules.vote.request;

import com.yealink.imweb.modules.vote.entity.VoteStaff;
import com.yealink.imweb.modules.vote.vo.VoteItemStatistics;

import java.util.Date;
import java.util.List;

/**
 * Created by yl1240 on 2016/7/26.
 */
public class FindVoteRequest {
    //查看类型
    String findType;
    //创建人信息
    private Long oriStaffId;
    private String oriStaffName;
    private String oriStaffAvatar;
    //投票信息
    private int lineNum;
    private Long voteId;
    private String voteDesc;
    private String voteTheme;
    private Long createDate;
    private Long finishDate;
    private boolean isPublic;
    private boolean isMultiSelect;
    private Integer minItem;
    private Integer maxItem;
    private boolean isAnonymous;
    private Integer relVoteCnt = 0;//邀请人数
    private Integer votedCnt = 0;//投票人数
    private String voteStatus;//投票状态
    private boolean isMyOri;//是否我发起
    //投票详情
    private List<VoteStaff> voteStaffList;
    //投票
    private String mySelectItem;//我的选择
    private List<VoteItemStatistics> voteItemStatisticsList;//投票选项及统计

    public Long getOriStaffId() {
        return oriStaffId;
    }

    public void setOriStaffId(Long oriStaffId) {
        this.oriStaffId = oriStaffId;
    }

    public String getOriStaffName() {
        return oriStaffName;
    }

    public void setOriStaffName(String oriStaffName) {
        this.oriStaffName = oriStaffName;
    }

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public String getVoteDesc() {
        return voteDesc;
    }

    public void setVoteDesc(String voteDesc) {
        this.voteDesc = voteDesc;
    }

    public String getVoteTheme() {
        return voteTheme;
    }

    public String getFindType() {
        return findType;
    }

    public void setFindType(String findType) {
        this.findType = findType;
    }

    public void setVoteTheme(String voteTheme) {
        this.voteTheme = voteTheme;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getFinishDate() {
        return finishDate;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public void setFinishDate(Long finishDate) {
        this.finishDate = finishDate;
    }

    public boolean getIsMultiSelect() {
        return isMultiSelect;
    }

    public void setIsMultiSelect(boolean multiSelect) {
        isMultiSelect = multiSelect;
    }

    public Integer getRelVoteCnt() {
        return relVoteCnt;
    }

    public void setRelVoteCnt(Integer relVoteCnt) {
        this.relVoteCnt = relVoteCnt;
    }

    public Integer getVotedCnt() {
        return votedCnt;
    }

    public void setVotedCnt(Integer votedCnt) {
        this.votedCnt = votedCnt;
    }

    public String getVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(String voteStatus) {
        this.voteStatus = voteStatus;
    }

    public boolean getIsMyOri() {
        return isMyOri;
    }

    public void setIsMyOri(boolean myOri) {
        isMyOri = myOri;
    }

    public List<VoteStaff> getVoteStaffList() {
        return voteStaffList;
    }

    public void setVoteStaffList(List<VoteStaff> voteStaffList) {
        this.voteStaffList = voteStaffList;
    }

    public String getMySelectItem() {
        return mySelectItem;
    }

    public void setMySelectItem(String mySelectItem) {
        this.mySelectItem = mySelectItem;
    }

    public List<VoteItemStatistics> getVoteItemStatisticsList() {
        return voteItemStatisticsList;
    }

    public void setVoteItemStatisticsList(List<VoteItemStatistics> voteItemStatisticsList) {
        this.voteItemStatisticsList = voteItemStatisticsList;
    }

    public Integer getMinItem() {
        return minItem;
    }

    public void setMinItem(Integer minItem) {
        this.minItem = minItem;
    }

    public Integer getMaxItem() {
        return maxItem;
    }

    public void setMaxItem(Integer maxItem) {
        this.maxItem = maxItem;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        isPublic = isPublic;
    }

    public boolean getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getOriStaffAvatar() {
        return oriStaffAvatar;
    }

    public void setOriStaffAvatar(String oriStaffAvatar) {
        this.oriStaffAvatar = oriStaffAvatar;
    }
}
