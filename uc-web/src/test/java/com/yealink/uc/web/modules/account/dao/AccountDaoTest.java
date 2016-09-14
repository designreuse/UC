package com.yealink.uc.web.modules.account.dao;

import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.SpringTestInitializer;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ChNan
 */
public class AccountDaoTest extends SpringTestInitializer {

    @Autowired
    AccountDao accountDao;

    @Autowired
    IdGeneratorDao idGeneratorDao;

    @After
    public void tearDown() throws Exception {
    }

    @Before
    public void before() throws Exception {
        Account account = new Account();
        account.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(Account.class)));
        account.setUsername("fangwq@yealink.com");
        account.setPassword("password");
        account.setEnterpriseId(1L);
        account.setStatus(1);
        account.setActiveCode("activeCode");
        accountDao.save(account);
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testFindByUsername() throws Exception {

    }

    @Test
    public void testFindByMail() throws Exception {

    }

    @Test
    public void testGet() throws Exception {
        Account account = accountDao.get(2L);
    }

    @Test
    public void testFindByActiveCodeAndResetDate() throws Exception {

    }

    @Test
    public void testUpdateAccount() throws Exception {

    }


} 
