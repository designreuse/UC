package com.yealink.uc.web.modules.enterprise.service;

import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.DateUtil;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.web.modules.enterprise.dao.EnterpriseDao;
import com.yealink.uc.web.modules.enterprise.producer.EnterpriseMessageProducer;
import com.yealink.uc.web.modules.enterprise.request.EditEnterpriseRequest;
import com.yealink.uc.web.modules.org.producer.OrgMessageProducer;
import com.yealink.uc.web.modules.org.service.EditOrgService;
import com.yealink.uc.web.modules.staff.service.SearchStaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class EnterpriseService {
    @Autowired
    private EnterpriseDao enterpriseDao;
    @Autowired
    private SearchStaffService staffService;

    @Autowired
    EditOrgService editOrgService;
    @Autowired
    OrgMessageProducer orgMessageProducer;
    @Autowired
    EnterpriseMessageProducer enterpriseMessageProducer;

    public Enterprise getEnterprise(Long enterpriseId) {
        Enterprise enterprise = enterpriseDao.findOne(enterpriseId);
        if (enterprise == null) {
            throw new BusinessHandleException(MessageUtil.getMessage("enterprise.exception.not.found", enterpriseId));
        }
        return enterprise;
    }

    public boolean editEnterprise(EditEnterpriseRequest editEnterpriseRequest) {
        Enterprise currentEnterprise = getEnterprise(editEnterpriseRequest.getId());
        if (!currentEnterprise.getName().equals(editEnterpriseRequest.getName())) {
            editOrgService.updateRootOrg(editEnterpriseRequest.getName(), editEnterpriseRequest.getId());
        }
        DataConverter.copy(currentEnterprise, editEnterpriseRequest);
        currentEnterprise.set_id(editEnterpriseRequest.getId());
        currentEnterprise.setModificationDate(DateUtil.currentDate().getTime());
        boolean updateSuccess = enterpriseDao.update(currentEnterprise) == 1;

        if (updateSuccess) {
            enterpriseMessageProducer.enterpriseEdited(currentEnterprise.get_id());
        }
        return updateSuccess;
    }


}
