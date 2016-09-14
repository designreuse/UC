package com.yealink.ofweb.modules.fileshare.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 文件服务器缓存管理
 * author:pengzhiyuan
 * Created on:2016/7/27.
 */
public class FileServerCacheManager {
    private FileServerCacheManager fileServerCacheManager = new FileServerCacheManager();
    /**
     * 文件服务器信息缓存
     */
    private Cache<String, String> fileServerInfoCache = null;

    private FileServerCacheManager () {
        fileServerInfoCache = CacheBuilder.newBuilder().build();
    }

    public FileServerCacheManager getInstance() {
        return fileServerCacheManager;
    }


}
