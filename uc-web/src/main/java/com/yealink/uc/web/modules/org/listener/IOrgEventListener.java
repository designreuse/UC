package com.yealink.uc.web.modules.org.listener;

import com.yealink.uc.common.modules.org.entity.Org;

/**
 * @author ChNan
 */
public interface IOrgEventListener {
    public void createOrg(Long orgId);

    public void editOrg(Long orgId);

    public void deleteOrg(Org org);

    public void moveOrg(Org org, Long newParentOrgId);

}
