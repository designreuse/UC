package com.yealink.uc.web.modules.org.service;

import java.util.List;

import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.web.modules.org.dao.OrgDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class QueryOrgService {
    @Autowired
    OrgDao orgDao;

    public Org getOrg(Long orgId, Long enterpriseId) {
        Org org = orgDao.get(orgId, enterpriseId);
        if (org == null)
            throw new BusinessHandleException("org.exception.find.not.found", orgId);
        return org;
    }

    public Org findParentOrg(Long parentId, Long enterpriseId) {
        Org parentOrg = orgDao.get(parentId, enterpriseId);
        if (parentOrg == null)
            throw new BusinessHandleException("org.exception.find.parent.not.found", parentId);
        return parentOrg;
    }

    public List<Org> findByIds(List<Long> ids, Long enterpriseId) {
        List<Org> orgs = orgDao.findByIds(ids, enterpriseId);
        if (orgs == null)
            throw new BusinessHandleException("org.exception.not.exist.in.enterprise");
        return orgs;
    }
}
