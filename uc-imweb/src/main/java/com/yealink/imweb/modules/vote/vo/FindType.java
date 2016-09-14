package com.yealink.imweb.modules.vote.vo;

/**
 * Created by yl1240 on 2016/7/26.
 */
public enum FindType {
    //未参与
    VOTING(1),
    //未投票
    VOTED(2),
    //已投票
    MYVOTE(3);

    private final int value;

    FindType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
