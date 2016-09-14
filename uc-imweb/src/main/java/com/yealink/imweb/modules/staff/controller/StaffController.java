package com.yealink.imweb.modules.staff.controller;

import java.util.List;
import javax.servlet.http.HttpSession;

import com.yealink.imweb.modules.common.constant.Constant;
import com.yealink.imweb.modules.common.interceptor.vo.UserInfo;
import com.yealink.imweb.modules.staff.service.StaffService;
import com.yealink.imweb.modules.staff.vo.MucRoomTreeNodeView;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.service.modules.org.vo.OrgTreeNodeView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yl1240 on 2016/7/8.
 */
@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    StaffService selectStaffService;

    @RequestMapping(value = "/selectStaff", method = RequestMethod.GET)
    public String selectStaff() {
        return "staff/staff";
    }

    @RequestMapping(value = "/showOrgStaffTreeNodes", method = RequestMethod.GET)
    @ResponseBody
    public Response showOrgStaffTreeNodes(HttpSession httpSession) {
        UserInfo userInfo = (UserInfo) httpSession.getAttribute(Constant.USER);
        List<OrgTreeNodeView> notesList = selectStaffService.showOrgStaffTreeNodes(userInfo.getEnterpriseId());
        //用户一定要在某个组织架构中
        if (notesList==null || notesList.size() == 0) {
            return Response.buildResponse(false, "Ora No Exist!");
        } else {
            return new Response(notesList,true);
        }
    }

    @RequestMapping(value = "/showRoomStaffTreeNodes", method = RequestMethod.GET)
    @ResponseBody
    public Response showRoomStaffTreeNodes(HttpSession httpSession) {
        UserInfo userInfo = (UserInfo) httpSession.getAttribute(Constant.USER);
        List<MucRoomTreeNodeView> notesList = selectStaffService.showRoomStaffTreeNodes(userInfo.getUserId());
        //用户可以没有群
        return new Response(notesList,true);
    }
}
