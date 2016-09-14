package com.yealink.uc.service.modules.muc.controller;

import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.util.Result;
import com.yealink.uc.service.modules.muc.api.MucRESTService;
import com.yealink.uc.service.modules.muc.request.SearchMucRoomRequest;
import com.yealink.uc.service.modules.muc.response.ListMucRoomResponse;
import com.yealink.uc.service.modules.muc.response.Pager;
import com.yealink.uc.service.modules.muc.service.MucRoomService;
import com.yealink.uc.service.modules.muc.vo.MucRoomView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChNan
 */
@RestController
public class MucRESTController implements MucRESTService {
    @Autowired
    MucRoomService mucRoomService;

    @Override
    @RequestMapping(value = "/mucs/list", method = RequestMethod.POST)
    @ResponseBody
    public ListMucRoomResponse listMuc(@RequestBody SearchMucRoomRequest searchMucRoomRequest) {
        Result result = mucRoomService.list(searchMucRoomRequest);
        ListMucRoomResponse listMucRoomResponse = new ListMucRoomResponse();
        listMucRoomResponse.setMucRoomViews((List<MucRoomView>) result.getData());
        Map<String,Object> pageMap = result.getAttrs();
        Pager pager = new Pager();
        pager.setPageNo((int)pageMap.get("pageNo"));
        pager.setPageSize((int)pageMap.get("pageSize"));
        pager.setTotal((int)pageMap.get("total"));
        pager.setTotalPage((int)pageMap.get("totalPage"));
        listMucRoomResponse.setPager(pager);
        return listMucRoomResponse;
    }
}
