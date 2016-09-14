package com.yealink.uc.platform.config.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching(proxyTargetClass = true)
public class CacheConfig extends CachingConfigurerSupport {
    private Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    @Override
    public CacheManager cacheManager() {
        return createCacheManager();
    }

    // todo : Onpremise version can use local guava cache manager, but on cloud version need to change to memcached or redis cache manager
    private CacheManager createCacheManager() {
        CacheProvider provider = CacheProvider.LOCAL;
        if (CacheProvider.REDIS.equals(provider)) {
        } else if (CacheProvider.LOCAL.equals(provider)) {
            return guavaCacheManager();
        }
        throw new IllegalStateException("not supported cache provider, provider=" + provider);
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new CacheKeyGenerator();
    }

    CacheManager guavaCacheManager() {
        GuavaCacheManager cacheManager = new GuavaCacheManager("orgTreeNodes", "orgStaffTreeNodes","orgTreeNodesWithForbiddenStaff");
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(2, TimeUnit.HOURS)
            .removalListener(new RemovalListener<Object, Object>() {
                @Override
                public void onRemoval(RemovalNotification<Object, Object> notification) {
                    logger.info(notification.getKey() + " was removed, cause is " + notification.getCause());
                }
            });
        cacheManager.setCacheBuilder(cacheBuilder);
        return cacheManager;
    }

}
