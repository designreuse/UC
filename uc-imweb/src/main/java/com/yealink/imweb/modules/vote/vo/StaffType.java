package com.yealink.imweb.modules.vote.vo;

/**
 * Created by yl1240 on 2016/7/26.
 */
public enum StaffType {
    //参与者
    REL_STAFF(1),
    //发起者
    ORI_STAFF(2),
    //参与者与发起者
   REL_ORI_STAFF(3);

    private final Integer value;

    StaffType(Integer value) {
        this.value = value;
    }
    public Integer getValue() {
        return value;
    }
}
