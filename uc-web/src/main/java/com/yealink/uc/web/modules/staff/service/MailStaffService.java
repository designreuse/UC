package com.yealink.uc.web.modules.staff.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yealink.uc.common.modules.account.entity.Account;
import com.yealink.uc.common.modules.account.vo.AccountType;
import com.yealink.uc.common.modules.businessaccount.entity.BusinessAccount;
import com.yealink.uc.common.modules.businessaccount.vo.BusinessType;
import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.staff.entity.Staff;
import com.yealink.uc.platform.crypto.AuthFactory;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.utils.MailUtil;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.VelocityUtil;
import com.yealink.uc.web.modules.account.dao.AccountDao;
import com.yealink.uc.web.modules.businessaccount.BusinessAccountService;
import com.yealink.uc.web.modules.enterprise.service.EnterpriseService;
import com.yealink.uc.web.modules.mail.service.MailService;
import com.yealink.uc.web.modules.mail.vo.Mail;
import com.yealink.uc.web.modules.staff.response.UCAccountDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * @author ChNan
 */
@Service
public class MailStaffService {
    @Autowired
    private QueryStaffService queryStaffService;
    @Autowired
    private MailService mailService;
    @Autowired
    AccountDao accountDao;
    @Autowired
    BusinessAccountService businessAccountService;
    @Autowired
    EnterpriseService enterpriseService;
    @Value("${logined.url}")
    String loginedUrl;

    public boolean batchMail(List<Long> staffIdList, Long enterpriseId) {
        Enterprise enterprise = enterpriseService.getEnterprise(enterpriseId);
        List<Staff> staffs = queryStaffService.findStaffs(staffIdList, enterpriseId);
        List<Long> staffIds = Lists.transform(staffs, new Function<Staff, Long>() {
            @Override
            public Long apply(final Staff input) {
                return input.get_id();
            }
        });
        List<BusinessAccount> imAccounts = businessAccountService.findByStaffIdsAndType(staffIds, BusinessType.IM.getCode());
        List<Account> ucAccounts = accountDao.findByStaffAndType(staffIds, AccountType.PRIMARY.getCode());
        Map<Long, BusinessAccount> staffIdIMAccountMap = createStaffIdImAccountMap(imAccounts);
        Map<Long, Account> staffIdUCAccountMap = createStaffIdUCAccountMap(ucAccounts);
        for (Staff staff : staffs) {
            doSendMail(enterprise, staffIdIMAccountMap, staffIdUCAccountMap, staff);
        }
        return true;
    }

    private void doSendMail(final Enterprise enterprise, final Map<Long, BusinessAccount> staffIdIMAccountMap, final Map<Long, Account> staffIdUCAccountMap, final Staff staff) {
        String mail = staff.getEmail();
        if (mail == null) {
            throw new BusinessHandleException("account.tips.mail.null");
        }
        if (!MailUtil.isEmail(mail)) {
            throw new BusinessHandleException("account.tips.mail.format.error");
        }
        try {
            BusinessAccount imAccount = staffIdIMAccountMap.get(staff.get_id());
            Account ucAccount = staffIdUCAccountMap.get(staff.get_id());
            Map<String, Object> map = buildMap(staff, imAccount, ucAccount, enterprise);
            String content = VelocityEngineUtils.mergeTemplateIntoString(VelocityUtil.getVelocityEngine(), "notice_account.vm", "utf-8", map);
            String subject = MessageUtil.getMessage("mail.template.subject.notice.account");
            mailService.sendMail(convertTemplateToMail(mail, content, subject));
        } catch (IOException e) {
            throw new BusinessHandleException("mail.exception.send");
        }
    }

    private Map<Long, Account> createStaffIdUCAccountMap(final List<Account> ucAccounts) {
        return Maps.uniqueIndex(ucAccounts, new Function<Account, Long>() {
            @Override
            public Long apply(final Account input) {
                return input.getStaffId();
            }
        });
    }

    private Map<Long, BusinessAccount> createStaffIdImAccountMap(final List<BusinessAccount> imAccounts) {
        return Maps.uniqueIndex(imAccounts, new Function<BusinessAccount, Long>() {
            @Override
            public Long apply(final BusinessAccount input) {
                return input.getStaffId();
            }
        });
    }

    private Map<String, Object> buildMap(Staff staff, BusinessAccount imAccount, Account ucAccount, Enterprise enterprise) {
        UCAccountDetail ucAccountDetail = new UCAccountDetail(ucAccount.getUsername(),
            AuthFactory.decryptPassword(imAccount.getEncryptedPassword()), ucAccount.getPinCode());
        Map<String, Object> map = new HashMap<>();
        map.put("staff", staff);
        map.put("ucAccount", ucAccountDetail);
        map.put("enterpriseName", enterprise.getName());
        map.put("loginUrl", loginedUrl);
        return map;
    }

    private Mail convertTemplateToMail(String receiver, String content, String subject) {
        Mail mail = new Mail();
        mail.setReceivers(new String[]{receiver});
        mail.setSubject(subject);
        mail.setContent(content);
        return mail;
    }
}
