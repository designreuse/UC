package com.yealink.uc.service.modules.staff.api;

import com.yealink.uc.service.modules.staff.request.FindStaffsRESTRequest;
import com.yealink.uc.service.modules.staff.response.FindAllStaffRESTResponse;
import com.yealink.uc.service.modules.staff.response.FindStaffsRESTResponse;
import com.yealink.uc.service.modules.staff.response.GetStaffRESTResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ChNan
 */
public interface StaffRESTService {
    @RequestMapping(value = "/staffs", method = RequestMethod.GET)
    public FindAllStaffRESTResponse findAllByEnterprise(@RequestParam(value = "enterpriseId") Long enterpriseId);

    @RequestMapping(value = "/staffs/{staffId}", method = RequestMethod.GET)
    public GetStaffRESTResponse getStaff(@PathVariable(value = "staffId") Long staffId);

    @RequestMapping(value = "/staffs", method = RequestMethod.POST)
    public FindStaffsRESTResponse findStaffs(@RequestBody FindStaffsRESTRequest request);
}
