package com.yealink.uc.web.modules.staff.listener;

import java.util.List;

import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;

/**
 * @author ChNan
 */
public interface IStaffEventListener {
    public void createStaff(Long staffId);

    public void editStaff(Long staffId);

    public void recycleStaff(Long staffId);

    public void recoverStaff(List<Long> staffIds);

    public void lockStaff(Long staffId);

    public void moveStaff(List<StaffOrgMapping> oldStafforgMappings, List<StaffOrgMapping> newStafforgMappings);

    public void reindexStaff(Long orgId);

    public void importStaff(final List<Long> orgIds);

    public void resetStaffPassword(Staff staff);

    public void deleteStaff(List<Long> staffIds);
}
