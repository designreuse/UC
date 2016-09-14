package com.yealink.uc.service.modules.org.controller;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.service.modules.org.api.OrgRESTService;
import com.yealink.uc.service.modules.org.response.FindAllOrgRESTResponse;
import com.yealink.uc.service.modules.org.response.ListOrgTreesRESTResponse;
import com.yealink.uc.service.modules.org.service.OrgService;
import com.yealink.uc.service.modules.org.vo.OrgTreeNodeView;
import com.yealink.uc.service.modules.org.vo.OrgView;
import com.yealink.uc.service.modules.staff.service.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChNan
 */
@RestController
public class OrgRESTController implements OrgRESTService {
    @Autowired
    OrgService orgService;
    @Autowired
    StaffService staffService;

    @Override
    @RequestMapping(value = "/orgs/trees", method = RequestMethod.GET)
    @ResponseBody
    public ListOrgTreesRESTResponse listOrgTrees(@RequestParam(value = "enterpriseId") Long enterpriseId) {
        List<OrgTreeNodeView> orgTreeNodeList = orgService.showOrgTrees(enterpriseId);
        ListOrgTreesRESTResponse listOrgTreesResponse = new ListOrgTreesRESTResponse();
        listOrgTreesResponse.setOrgTreeNodeList(orgTreeNodeList);
        return listOrgTreesResponse;
    }

    @Override
    @RequestMapping(value = "/orgs", method = RequestMethod.GET)
    public FindAllOrgRESTResponse findAllByEnterprise(@RequestParam(value = "enterpriseId") Long enterpriseId) {
        List<Org> orgList = orgService.findAll(enterpriseId);
        List<OrgView> orgViewList = Lists.transform(orgList, new Function<Org, OrgView>() {
            @Override
            public OrgView apply(final Org input) {
                return DataConverter.copy(new OrgView(), input);
            }
        });

        FindAllOrgRESTResponse findAllOrgResponse = new FindAllOrgRESTResponse();
        findAllOrgResponse.setOrgList(orgViewList);
        return findAllOrgResponse;
    }
}
