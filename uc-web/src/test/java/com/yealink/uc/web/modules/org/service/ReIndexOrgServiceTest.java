package com.yealink.uc.web.modules.org.service;

import junit.framework.Assert;
import java.util.ArrayList;
import java.util.List;

import com.yealink.uc.web.modules.org.TestConstant;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.web.modules.org.request.ReIndexOrgRequest;
import com.yealink.uc.web.modules.org.request.ReIndexOrgRequestItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * OrgService Tester.
 *
 * @author ChNan
 * @version 1.0
 */
public class ReIndexOrgServiceTest extends OrgCommonTest {
    @Autowired
    ReIndexOrgService reIndexOrgService;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() throws Exception {
        super.before();
    }

    @After
    public void after() throws Exception {
        super.after();
    }


    @Test
    public void testReIndex() throws Exception {
        Org org_1 = createOrg(TestConstant.ROOT_ORG_ID - 2);
        Org org_2 = createOrg(TestConstant.ROOT_ORG_ID - 3);
        ReIndexOrgRequest reIndexOrgRequest = new ReIndexOrgRequest();
        reIndexOrgRequest.setParentOrgId(org_1.get_id());

        List<ReIndexOrgRequestItem> reIndexOrgRequestItemList = new ArrayList<>();
        ReIndexOrgRequestItem item_1 = new ReIndexOrgRequestItem();
        ReIndexOrgRequestItem item_2 = new ReIndexOrgRequestItem();
        item_1.setOrgId(org_1.get_id());
        item_1.setIndex(2);
        item_2.setOrgId(org_2.get_id());
        item_2.setIndex(1);
        reIndexOrgRequestItemList.add(item_1);
        reIndexOrgRequestItemList.add(item_2);
        reIndexOrgRequest.setParentOrgId(TestConstant.ORG_ID);
        reIndexOrgRequest.setReIndexOrgRequestItemList(reIndexOrgRequestItemList);

        boolean result = reIndexOrgService.reIndex(reIndexOrgRequest, TestConstant.ENTERPRISE_ID);
        Assert.assertTrue(result);

    }
}
