package com.yealink.uc.web.modules.org.service;

import junit.framework.Assert;

import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.web.modules.org.request.CreateOrgRequest;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.core.IsAnything.anything;

/**
 * OrgService Tester.
 *
 * @author ChNan
 * @version 1.0
 */

public class CreateOrgServiceTest extends OrgCommonTest {
    @Autowired
    CreateOrgService createOrderService;
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
    public void testCreateOrg() throws Exception {
        Enterprise enterprise = enterpriseDao.findOne(Long.MAX_VALUE);
        Assert.assertNotNull(enterprise);
        Org rootOrg = orgDao.get(Long.MAX_VALUE, enterprise.get_id());
        Assert.assertNotNull(rootOrg);

        CreateOrgRequest createOrgRequest = new CreateOrgRequest();
        createOrgRequest.setParentId(rootOrg.get_id());
        createOrgRequest.setMail("fangwq@yealink.com");
        createOrgRequest.setIsExtAssistance(false);
        createOrgRequest.setName("组织单元测试_001");
        boolean result = createOrderService.createOrg(createOrgRequest, enterprise);

        Assert.assertTrue(result);

        testCreateOrgDuplicateException(enterprise, rootOrg);

    }

    // Test-CreateOrg-1
    private void testCreateOrgDuplicateException(final Enterprise enterprise, final Org rootOrg) {
        thrown.expect(BusinessHandleException.class);
        Matcher<String> msg = anything("name is duplicate ");
        //test message
        thrown.expectMessage(msg);
        // test name duplicate exception
        CreateOrgRequest createOrgRequestNameDuplicate = new CreateOrgRequest();
        createOrgRequestNameDuplicate.setParentId(rootOrg.get_id());
        createOrgRequestNameDuplicate.setMail("fangwq@yealink.com");
        createOrgRequestNameDuplicate.setIsExtAssistance(false);
        createOrgRequestNameDuplicate.setName("OrgForOperation");
        createOrderService.createOrg(createOrgRequestNameDuplicate, enterprise);
    }

}
