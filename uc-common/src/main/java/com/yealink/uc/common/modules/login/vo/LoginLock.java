package com.yealink.uc.common.modules.login.vo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ChNan
 */
public class LoginLock {
    public static final int LOCK_PASSWORD_ERROR_LIMIT = 3;

    public static final long LOCK_MINUTE = 3 * 60 * 1000;

    private AtomicInteger count = new AtomicInteger(1);

    private long unLockTime;


    public void incCount() {
        count.incrementAndGet();
    }

    public boolean isLocked() {
        return unLockTime > System.currentTimeMillis();
    }

    public boolean isLockOverdue() {
        return unLockTime > 0 && unLockTime < System.currentTimeMillis();
    }

    public boolean isLockCountOverLimit() {
        return count.get() >= LOCK_PASSWORD_ERROR_LIMIT;
    }

    public void startLock() {
        unLockTime = LOCK_MINUTE + System.currentTimeMillis();
    }

    public Integer getErrorCount() {
        return count.get();
    }
}
