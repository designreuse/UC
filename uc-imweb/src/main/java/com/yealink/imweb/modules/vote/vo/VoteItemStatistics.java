package com.yealink.imweb.modules.vote.vo;

import com.yealink.imweb.modules.vote.entity.VoteItem;

/**
 * Created by yl1240 on 2016/7/26.
 */
public class VoteItemStatistics extends VoteItem {
    private boolean isMySelect;
    private Integer selectCnt = 0 ;


    public boolean getIsMySelect() {
        return isMySelect;
    }

    public void setIsMySelect(boolean mySelect) {
        isMySelect = mySelect;
    }

    public Integer getSelectCnt() {
        return selectCnt;
    }

    public void setSelectCnt(Integer selectCnt) {
        this.selectCnt = selectCnt;
    }
}
