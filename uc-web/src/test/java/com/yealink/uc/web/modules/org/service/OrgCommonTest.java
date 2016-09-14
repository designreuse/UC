package com.yealink.uc.web.modules.org.service;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.BasicDataInitializer;
import com.yealink.uc.web.SpringTestInitializer;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import com.yealink.uc.web.modules.enterprise.dao.EnterpriseDao;
import com.yealink.uc.web.modules.org.TestConstant;
import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.web.modules.staff.dao.StaffDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ChNan
 */
public class OrgCommonTest extends SpringTestInitializer {
    @Autowired
    EnterpriseDao enterpriseDao;
    @Autowired
    OrgDao orgDao;
    @Autowired
    StaffDao staffDao;
    @Autowired
    IdGeneratorDao idGeneratorDao;
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before() throws Exception {
        Enterprise enterprise = enterpriseDao.findOne(TestConstant.ENTERPRISE_ID);
        Org rootOrg = orgDao.get(TestConstant.ROOT_ORG_ID, enterprise.get_id());
        Org orgForOperation = new Org();
        orgForOperation.set_id(TestConstant.ORG_ID);
        orgForOperation.setIndex(1);
        orgForOperation.setName("OrgForOperation");
        orgForOperation.setModificationDate(System.currentTimeMillis());
        orgForOperation.setOrgPath(rootOrg.getOrgPath() + ":" + orgForOperation.get_id());
        orgForOperation.setIsExtAssistance(false);
        orgForOperation.setMail(enterprise.getEmail());
        orgForOperation.setEnterpriseId(enterprise.get_id());
        orgForOperation.setParentId(rootOrg.get_id());
        orgDao.save(orgForOperation);

        Staff staff = new Staff();
        staff.set_id(Long.MAX_VALUE);
//        staff.setUsername("fangwq" + Long.MAX_VALUE);
//        staff.setEncryptedPassword(AuthFactory.encryptPassword("11111111"));
        staffDao.save(staff);
    }

    /**
     * @see BasicDataInitializer destroy()
     */
    @After
    public void after() throws Exception {
        orgDao.delete(TestConstant.ORG_ID);
        deleteStaff(TestConstant.STAFF_ID);
    }

    public Org createOrg(Long orgId) {
        Org orgParent = new Org();
        orgParent.set_id(orgId);
        orgParent.setIndex(orgId.intValue());
        orgParent.setName("Org:" + orgId);
        orgParent.setModificationDate(System.currentTimeMillis());
        orgParent.setOrgPath(TestConstant.ROOT_ORG_ID + ":" + orgParent.get_id());
        orgParent.setIsExtAssistance(false);
        orgParent.setEnterpriseId(TestConstant.ENTERPRISE_ID);
        orgParent.setParentId(TestConstant.ORG_ID);
        orgDao.save(orgParent);
        return orgParent;
    }

    private void deleteStaff(Long staffId) {
        remoteDataService.deleteOne(EntityUtil.getEntityName(Staff.class), staffId);
    }
}
