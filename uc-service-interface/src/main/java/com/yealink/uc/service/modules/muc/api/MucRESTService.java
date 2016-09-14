package com.yealink.uc.service.modules.muc.api;

import com.yealink.uc.service.modules.muc.request.SearchMucRoomRequest;
import com.yealink.uc.service.modules.muc.response.ListMucRoomResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChNan
 */
public interface MucRESTService {
    @RequestMapping(value = "/mucs/list", method = RequestMethod.POST)
    @ResponseBody
    public ListMucRoomResponse listMuc(@RequestBody SearchMucRoomRequest searchMucRoomRequest);

}
