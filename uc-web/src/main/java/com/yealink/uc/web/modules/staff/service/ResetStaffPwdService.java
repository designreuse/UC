package com.yealink.uc.web.modules.staff.service;

import com.yealink.uc.web.modules.staff.dao.StaffDao;
import com.yealink.uc.web.modules.staff.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yewl on 2016/6/20.
 */
@Service
public class ResetStaffPwdService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private UserDao userDao;

}
