package com.yealink.uc.web.modules.org.service;

import junit.framework.Assert;

import com.yealink.uc.web.modules.org.TestConstant;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.web.modules.org.request.MoveOrgRequest;

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
public class MoveOrgServiceTest extends OrgCommonTest {
    @Autowired
    MoveOrgService moveOrgService;
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
    public void testMoveOrg() throws Exception {
        Org orgParent = super.createOrg(TestConstant.ROOT_ORG_ID - 2);
        MoveOrgRequest moveOrgRequest = new MoveOrgRequest();
        moveOrgRequest.setOrgId(TestConstant.ORG_ID);
        moveOrgRequest.setTargetOrgId(orgParent.get_id());
        boolean result = moveOrgService.moveOrg(TestConstant.ORG_ID, orgParent.get_id(), TestConstant.ENTERPRISE_ID);
        Assert.assertTrue(result);
    }

}
