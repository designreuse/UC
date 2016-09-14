package com.yealink.uc.web.modules.org.service;

import junit.framework.Assert;
import java.util.ArrayList;
import java.util.List;

import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.web.modules.org.request.EditOrgRequest;
import com.yealink.uc.web.modules.org.request.EditOrgRequestItem;

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
public class EditOrgServiceTest extends OrgCommonTest {
    @Autowired
    EditOrgService editOrgService;
    @Autowired
    IdGeneratorDao idGeneratorDao;
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
    public void testEditOrg() throws Exception {
        Enterprise enterprise = enterpriseDao.findOne(Long.MAX_VALUE);
        EditOrgRequest editOrgRequest = new EditOrgRequest();
        editOrgRequest.setOrgId(Long.MAX_VALUE - 1);
        List<EditOrgRequestItem> editOrgRequestItemList = new ArrayList<>();
        EditOrgRequestItem editOrgRequestItem1 = new EditOrgRequestItem();
        editOrgRequestItem1.setStaffId(Long.MAX_VALUE);
        editOrgRequestItem1.setTitle("组长");
        editOrgRequest.setEditOrgRequestItemList(editOrgRequestItemList);
        boolean result = editOrgService.editOrg(editOrgRequest, enterprise.get_id());
        Assert.assertTrue(result);
    }
}
