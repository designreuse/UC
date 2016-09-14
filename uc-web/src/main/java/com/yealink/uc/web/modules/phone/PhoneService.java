package com.yealink.uc.web.modules.phone;

import com.yealink.uc.common.modules.phone.Phone;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.web.modules.common.dao.IdGeneratorDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class PhoneService {
    @Autowired
    PhoneDao phoneDao;

    @Autowired
    IdGeneratorDao idGeneratorDao;

    public void saveStaffPhone(String phoneIp, String phoneMac, String phoneModel, String phoneSettingTemplate, Long staffId, boolean status) {
        Phone phone = new Phone();
        phone.set_id(idGeneratorDao.nextId(EntityUtil.getEntityName(Phone.class)));
        phone.setStaffId(staffId);
        buildPhoneBaseInfo(phoneIp, phoneMac, phoneModel, phoneSettingTemplate, phone, status);
        phoneDao.save(phone);
    }


    public void editStaffPhone(String phoneIp, String phoneMac, String phoneModel, String phoneSettingTemplate, boolean status,Phone phone) {
        buildPhoneBaseInfo(phoneIp, phoneMac, phoneModel, phoneSettingTemplate, phone, status);
        phoneDao.update(phone);
    }

    private void buildPhoneBaseInfo(final String phoneIp, final String phoneMac, final String phoneModel, final String phoneSettingTemplate, final Phone phone, boolean status) {
        phone.setIp(phoneIp);
        phone.setMac(phoneMac);
        phone.setModel(phoneModel);
        phone.setSettingTemplate(phoneSettingTemplate);
        phone.setStatus(status);
    }

    public Phone getByStaff(Long staffId) {
        return phoneDao.getByStaff(staffId);
    }
}
