package com.yealink.uc.service.modules.muc.response;

import com.yealink.uc.service.modules.muc.vo.MucRoomView;

import java.util.List;
import java.util.Map;

/**
 * Created by yl1227 on 2016/8/23.
 */
public class ListMucRoomResponse {

    List<MucRoomView> mucRoomViews;
    private Pager pager;

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public List<MucRoomView> getMucRoomViews() {
        return mucRoomViews;
    }

    public void setMucRoomViews(List<MucRoomView> mucRoomViews) {
        this.mucRoomViews = mucRoomViews;
    }
}
