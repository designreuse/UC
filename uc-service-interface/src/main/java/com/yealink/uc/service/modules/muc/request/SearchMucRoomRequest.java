package com.yealink.uc.service.modules.muc.request;

/**
 * Created by yl1184 on 2016/7/26.
 */
public class SearchMucRoomRequest {
    /**
     * 关键字查询
     */
    private String keyword;
    /**
     * 群组类型：1-群组 0-讨论组
     */
    private Integer mucRoomType;
    /**
     * 页面显示记录数
     */
    private int pageSize;
    /**
     * 页面号
     */
    private int pageNo;
    /**
     * 是否查询群组下群成员
     */
    private Integer member;
    /**
     * 查询包含user的群组
     */
    private Long user;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getMucRoomType() {
        return mucRoomType;
    }

    public void setMucRoomType(Integer mucRoomType) {
        this.mucRoomType = mucRoomType;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getMember() {
        return member;
    }

    public void setMember(Integer member) {
        this.member = member;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

}
