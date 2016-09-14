package com.yealink.uc.service.modules.login.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yealink.uc.common.modules.login.vo.LoginLock;

import org.springframework.stereotype.Service;

/**
 * @author ChNan
 */
@Service
public class LoginLockService {
    ConcurrentHashMap<String, LoginLock> lockCache = new ConcurrentHashMap<>(3);

    public boolean isLocked(String userName) {
        if (!isContain(userName)) return false;
        LoginLock loginLock = lockCache.get(userName);
        return loginLock.isLocked();
    }

    private boolean isContain(final String userName) {
        LoginLock loginLock = lockCache.get(userName);
        if (loginLock == null) return false;
        return true;
    }

    public boolean unLock(String userName) {
        lockCache.remove(userName);
        return true;
    }

    public Integer recordLoginError(String userName) {
        if (!isContain(userName)) {
            lockCache.put(userName, new LoginLock());
            return 1;
        } else {
            LoginLock loginLock = lockCache.get(userName);
            loginLock.incCount();
            if (loginLock.isLockCountOverLimit()) {
                loginLock.startLock();
            }
            return loginLock.getErrorCount();
        }
    }

    public boolean removeLoginErrorLock(String userName) {
        lockCache.remove(userName);
        return true;
    }

    // todo for job
    public void removeOverdueLock() {
        for (Map.Entry<String, LoginLock> entry : lockCache.entrySet()) {
            if (entry.getValue().isLockOverdue()) {
                lockCache.remove(entry.getKey());
            }
        }
    }


    public Integer getErrorCount(String userName) {
        if (!isContain(userName)) {
            return 0;
        } else {
            LoginLock loginLock = lockCache.get(userName);
            return loginLock.getErrorCount();
        }
    }
}
