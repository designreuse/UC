package com.yealink.uc.service.modules.staff.controller;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.service.modules.staff.api.StaffRESTService;
import com.yealink.uc.service.modules.staff.request.FindStaffsRESTRequest;
import com.yealink.uc.service.modules.staff.response.FindAllStaffRESTResponse;
import com.yealink.uc.service.modules.staff.response.FindStaffsRESTResponse;
import com.yealink.uc.service.modules.staff.response.GetStaffRESTResponse;
import com.yealink.uc.service.modules.staff.service.StaffService;
import com.yealink.uc.service.modules.staff.vo.StaffView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChNan
 */

@RestController
public class StaffRESTController implements StaffRESTService {
    @Autowired
    StaffService staffService;

    @Override
    @RequestMapping(value = "/staffs", method = RequestMethod.GET)
    public FindAllStaffRESTResponse findAllByEnterprise(@RequestParam(value = "enterpriseId") final Long enterpriseId) {
        List<Staff> orgList = staffService.findAllAvailable(enterpriseId);
        List<StaffView> staffViewList = Lists.transform(orgList, new Function<Staff, StaffView>() {
            @Override
            public StaffView apply(final Staff input) {
                return DataConverter.copy(new StaffView(), input);
            }
        });
        FindAllStaffRESTResponse findAllStaffResponse = new FindAllStaffRESTResponse();
        findAllStaffResponse.setStaffs(staffViewList);
        return findAllStaffResponse;
    }

    @Override
    @RequestMapping(value = "/staffs/{staffId}", method = RequestMethod.GET)
    public GetStaffRESTResponse getStaff(@PathVariable(value = "staffId") Long staffId) {
        Staff staff = staffService.getStaff(staffId);
        return new GetStaffRESTResponse(DataConverter.copy(new StaffView(), staff));
    }

    @Override
    @RequestMapping(value = "/staffs", method = RequestMethod.POST)
    public FindStaffsRESTResponse findStaffs(@RequestBody FindStaffsRESTRequest request) {
        List<Staff> staffs = staffService.findStaffs(request.getStaffIds());
        return new FindStaffsRESTResponse(Lists.transform(staffs, new Function<Staff, StaffView>() {
            @Override
            public StaffView apply(final Staff input) {
                return DataConverter.copy(new StaffView(), input);
            }
        }));
    }

//    @Override
//    @RequestMapping(value = "/staffs", method = RequestMethod.PUT)
//    public FindStaffsRESTResponse findStaffs(@RequestBody UpdateStaffsRESTRequest request) {
//        List<Staff> staffs = staffService.findStaffs(request.getStaffIds());
//        return new FindStaffsRESTResponse(Lists.transform(staffs, new Function<Staff, StaffView>() {
//            @Override
//            public StaffView apply(final Staff input) {
//                return DataConverter.copy(new StaffView(), input);
//            }
//        }));
//    }
}
