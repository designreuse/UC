package com.yealink.uc.web.modules.org.service.common;

import java.util.List;

import com.yealink.uc.web.modules.org.producer.OrgMessageProducer;
import com.yealink.uc.web.modules.org.request.OrgCommonRequest;
import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.common.modules.org.entity.Org;
import com.yealink.uc.platform.utils.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class OrgCommonService {
    @Autowired
    OrgDao orgDao;

    @Autowired
    OrgMessageProducer orgMessageProducer;

    public void buildOrgCommonInfo(OrgCommonRequest orgCommonRequest, Org org) {
        Long now = System.currentTimeMillis();
        org.setName(orgCommonRequest.getName());
        org.setMail(orgCommonRequest.getMail());
        org.setIsExtAssistance(orgCommonRequest.getIsExtAssistance());
        org.setPhones(orgCommonRequest.getPhones());
        org.setModificationDate(now);
    }


    public void markOrgModified(Long orgId, Long enterpriseId) {
        orgDao.updateByMap(orgId, enterpriseId, Maps.newMapChain().put("modificationDate", System.currentTimeMillis()).getMap());
        orgMessageProducer.editOrg(orgId);
    }

    public void batchEditOrgModificationByIds(List<Long> orgIdList, Long modificationDate) {
        orgDao.batchUpdateModificationDate(Maps.newMapChain().put("modificationDate", modificationDate).getMap(), orgIdList);
    }

}
