package com.yealink.uc.web.modules.staff.producer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yealink.dataservice.client.util.Event;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.common.modules.staff.vo.StaffEventType;
import com.yealink.uc.common.modules.stafforgmapping.entity.StaffOrgMapping;
import com.yealink.uc.web.modules.common.producer.MessageProducer;
import com.yealink.uc.web.modules.staff.listener.IStaffEventListener;

import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class StaffMessageProducer extends MessageProducer implements IStaffEventListener {
    private static final String SOURCE_STAFF = "Staff";

    @Override
    public void createStaff(final Long staffId) {
        Event event = createEvent(StaffEventType.CREATE.name());
        event.setResourceId(staffId);
        publishEvent(event);
    }

    @Override
    public void editStaff(final Long staffId) {
        Event event = createEvent(StaffEventType.EDIT.name());
        event.setResourceId(staffId);
        publishEvent(event);
    }

    @Override
    public void recycleStaff(final Long staffId) {
        Event event = createEvent(StaffEventType.RECYCLE.name());
        event.setResourceId(staffId);
        publishEvent(event);
    }

    @Override
    public void recoverStaff(List<Long> staffIds) {
        for(Long staffId : staffIds){
            createStaff(staffId);
        }
    }
    @Override
    public void deleteStaff(List<Long> staffIds) {
        for(Long staffId : staffIds){
            Event event = createEvent(StaffEventType.DELETE.name());
            event.setResourceId(staffId);
            publishEvent(event);
        }
    }
    @Override
    public void lockStaff(final Long staffId) {
        Event event = createEvent(StaffEventType.LOCK.name());
        event.setResourceId(staffId);
        publishEvent(event);
    }

    @Override
    public void moveStaff(List<StaffOrgMapping> oldStafforgMappings, List<StaffOrgMapping> newStafforgMappings) {
        createStaff(oldStafforgMappings.get(0).getStaffId());
        relieveStaff(oldStafforgMappings.get(0).getStaffId(), findReleaveOrgs(newStafforgMappings, oldStafforgMappings));
    }

    private List<Long> findReleaveOrgs(List<StaffOrgMapping> newStafforgMappings, List<StaffOrgMapping> oldStafforgMappings) {
        oldStafforgMappings.removeAll(newStafforgMappings);
        List<Long> orgIds = new ArrayList<>();
        for(StaffOrgMapping staffOrgMapping : oldStafforgMappings){
            orgIds.add(staffOrgMapping.getOrgId());
        }
        return orgIds;
    }

    private void relieveStaff(Long staffId, List<Long> orgIds){
        Map<String, Object> map = new HashMap<>();
        map.put("orgIds", orgIds);
        Event event = createEvent(StaffEventType.RELIEVE.name());
        event.setResourceId(staffId);
        event.setExValue(map);
        publishEvent(event);
    }

    @Override
    public void reindexStaff(Long orgId){
        Event event = createEvent(StaffEventType.REINDEX.name());
        event.setResourceId(orgId);
        publishEvent(event);
    }

    @Override
    public void importStaff(final List<Long> orgIds) {
        Event event = createEvent(StaffEventType.IMPORT.name());
        event.setSource("Org");
        event.setResourceId(orgIds);
        publishEvent(event);
    }

    @Override
    public void resetStaffPassword(final Staff staff) {
        Event event = createEvent(StaffEventType.EDIT.name());
        event.setResourceId(staff.get_id());
        publishEvent(event);
    }

    @Override
    protected String getSource() {
        return SOURCE_STAFF;
    }

    @Override
    protected String getTopic() {
        return Event.TOPIC_ORG_SERVICE;
    }
}
