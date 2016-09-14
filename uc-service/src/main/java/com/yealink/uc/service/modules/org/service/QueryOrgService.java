package com.yealink.uc.service.modules.org.service;

import java.util.List;

import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.service.modules.org.dao.OrgDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class QueryOrgService {
    @Autowired
    OrgDao orgDao;

    public List<Org> findAll(Long enterpriseId) {
        return orgDao.findAll(enterpriseId);
    }
    public List<Org> findOrgs(List<Long> orgIds) {
        return orgDao.find(orgIds);
    }
    public Org getOrg(Long orgId, Long enterpriseId) {
        return orgDao.get(orgId, enterpriseId);
    }

}
