package com.yealink.uc.web.modules.org.dao;

import junit.framework.Assert;

import com.yealink.uc.web.SpringTestInitializer;
import com.yealink.uc.common.modules.org.entity.Org;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrgDaoTest extends SpringTestInitializer {
    @Autowired
    private OrgDao orgDao;

    @Test
    public void selectAttribute() {
        Org org = orgDao.get(1L, 1L);
        Assert.assertNotNull(org);
    }
}
