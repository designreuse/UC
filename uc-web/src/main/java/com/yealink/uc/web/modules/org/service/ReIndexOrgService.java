package com.yealink.uc.web.modules.org.service;

import com.yealink.uc.web.modules.org.service.common.OrgValidateService;
import com.yealink.uc.web.modules.org.dao.OrgDao;
import com.yealink.uc.web.modules.org.producer.OrgMessageProducer;
import com.yealink.uc.web.modules.org.request.ReIndexOrgRequest;
import com.yealink.uc.web.modules.org.request.ReIndexOrgRequestItem;
import com.yealink.uc.web.modules.org.service.common.OrgCommonService;
import com.yealink.uc.platform.utils.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class ReIndexOrgService {
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private OrgMessageProducer orgMessageProducer;
    @Autowired
    private QueryOrgService queryOrgService;
    @Autowired
    private OrgValidateService orgValidateService;
    @Autowired
    private OrgCommonService orgCommonService;

    /**
     * 最终顺序以页面上传过来的顺序为准，全部重新排序。
     */
    @Caching(evict = {
        @CacheEvict(value = {"orgTreeNodes","orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff"}, key = "#enterpriseId")
    })
    public boolean reIndex(ReIndexOrgRequest reIndexOrgRequest, Long enterpriseId) {
        long totalCount = 0;
        for (ReIndexOrgRequestItem reIndexOrgRequestItem : reIndexOrgRequest.getReIndexOrgRequestItemList()) {
            long count = orgDao.updateByMap(reIndexOrgRequestItem.getOrgId(), enterpriseId, Maps.newMapChain().put("index", reIndexOrgRequestItem.getIndex()).getMap());
            if (count == 1) {
                orgMessageProducer.editOrg(reIndexOrgRequestItem.getOrgId());
            }
            totalCount += count;
        }
        boolean isReindexSuccess = totalCount > 0;
        if (isReindexSuccess) {
            orgCommonService.markOrgModified(reIndexOrgRequest.getParentOrgId(), enterpriseId);
        }
        return isReindexSuccess;
    }
}
