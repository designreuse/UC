package com.yealink.uc.service.modules.org.api;

import com.yealink.uc.service.modules.org.response.FindAllOrgRESTResponse;
import com.yealink.uc.service.modules.org.response.ListOrgTreesRESTResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ChNan
 */
public interface OrgRESTService {
    @RequestMapping(value = "/orgs/trees", method = RequestMethod.GET)
    @ResponseBody
    public ListOrgTreesRESTResponse listOrgTrees(@RequestParam(value = "enterpriseId") Long enterpriseId);

    @RequestMapping(value = "/orgs", method = RequestMethod.GET)
    public FindAllOrgRESTResponse findAllByEnterprise(@RequestParam(value = "enterpriseId") Long enterpriseId);



}
