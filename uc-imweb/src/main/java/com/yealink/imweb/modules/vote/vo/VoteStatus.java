package com.yealink.imweb.modules.vote.vo;

/**
 * Created by yl1240 on 2016/7/26.
 */
public enum VoteStatus {
    //未参与
    NOT_PART_IN("00"),
    //未投票
    NOT_VOTE("01"),
    //已投票
    HAVE_VOTE("02"),
    //未完成
    NOT_FINISH("03"),
    //已完成
    HAVE_FINISH("04");

    private final String value;

    VoteStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
