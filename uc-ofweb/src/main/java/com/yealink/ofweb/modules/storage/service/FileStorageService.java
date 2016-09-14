package com.yealink.ofweb.modules.storage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yealink.ofweb.modules.common.Constants;
import com.yealink.ofweb.modules.fileshare.util.FileServerHttpClientUtil;
import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import com.yealink.ofweb.modules.msg.dao.MsgManagerDao;
import com.yealink.ofweb.modules.storage.entity.FileStorageSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件存储设置
 * author:pengzhiyuan
 * Created on:2016/8/25.
 */
@Service
public class FileStorageService {

    @Autowired
    private MsgManagerDao msgManagerDao;

    @Value("${fileshare_request_baseurl}")
    private String fileShareRequestUrl;
    @Value("${msg_request_baseurl}")
    private String msgRequestUrl;

    /**
     * 从数据库中获取文件存储设置相关属性
     *
     * @return
     */
    public FileStorageSetting getFileStorageSettingFromDb() {
        List<String> ids = new ArrayList<String>();
        ids.add(Constants.PROPERTY_STORAGE_SERVER_SAVEPATH);
        ids.add(Constants.PROPERTY_OFFLINE_SAVEDAYS);
        ids.add(Constants.PROPERTY_MSG_SAVEDAYS);
        ids.add(Constants.PROPERTY_IS_DELETEOFFLINE);
        ids.add(Constants.PROPERTY_IS_DELMSG);

        List<Map<String, Object>> propertyList = msgManagerDao.queryPropertys(ids);
        FileStorageSetting fileStorageSetting = new FileStorageSetting();
        for (Map<String, Object> map : propertyList) {
            String _id = PropertiesUtils.getString(map.get("_id"));
            if (Constants.PROPERTY_STORAGE_SERVER_SAVEPATH.equals(_id)) {
                fileStorageSetting.setServerSavePath(PropertiesUtils.getString(map.get("propValue")));
            }
            if (Constants.PROPERTY_OFFLINE_SAVEDAYS.equals(_id)) {
                String offlineSaveDays = PropertiesUtils.getString(map.get("propValue"));
                if (!offlineSaveDays.equals("")) {
                    fileStorageSetting.setOfflineSaveDays(offlineSaveDays);
                }
            }
            if (Constants.PROPERTY_MSG_SAVEDAYS.equals(_id)) {
                String msgSaveDays = PropertiesUtils.getString(map.get("propValue"));
                if (!msgSaveDays.equals("")) {
                    fileStorageSetting.setMsgSaveDays(msgSaveDays);
                }
            }
            if (Constants.PROPERTY_IS_DELETEOFFLINE.equals(_id)) {
                String isDelOffile = PropertiesUtils.getString(map.get("propValue"));
                if (!isDelOffile.equals("")) {
                    fileStorageSetting.setDelOnlineFile(Boolean.valueOf(isDelOffile));
                }
            }
            if (Constants.PROPERTY_IS_DELMSG.equals(_id)) {
                String isDelMsg = PropertiesUtils.getString(map.get("propValue"));
                if (!isDelMsg.equals("")) {
                    fileStorageSetting.setDelMsg(Boolean.valueOf(isDelMsg));
                }
            }
        }
        return fileStorageSetting;
    }

    /**
     * 文件存储设置保存
     * @param fileStorageSetting
     */
    public void saveFileStorageSetting(FileStorageSetting fileStorageSetting) throws Exception {
        ObjectMapper objMapper = new ObjectMapper();
        // 保存服务器存储路径到属性表
        String savePathId = Constants.PROPERTY_STORAGE_SERVER_SAVEPATH;
        Map<String, Object> savePathMap = msgManagerDao.queryPropertyById(savePathId);
        if (savePathMap != null) {
            msgManagerDao.updateProperty(savePathId, fileStorageSetting.getServerSavePath());
        } else {
            msgManagerDao.saveProperty(savePathId, fileStorageSetting.getServerSavePath());
        }

        // 下面2块更新应该合在一起
        // 更新离线文件存储设置到of
        Map<String, String> offlineFileMap = new HashMap<>();
        offlineFileMap.put("offlineSaveDays", PropertiesUtils.getString(fileStorageSetting.getOfflineSaveDays()));
        offlineFileMap.put("isDelOfflineFile", PropertiesUtils.getString(fileStorageSetting.getIsDelOnlineFile()));
        String result = FileServerHttpClientUtil.doPost(fileShareRequestUrl, offlineFileMap, "UTF-8");
        Map<String, String> resultMap = objMapper.readValue(result, Map.class);
        String code = resultMap.get("code");
        if (!"1".equals(code)) {
            throw new Exception("update fail!");
        }
        // 更新消息存储设置到of
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("msgSaveDays", PropertiesUtils.getString(fileStorageSetting.getMsgSaveDays()));
        paramMap.put("isDelMsg", PropertiesUtils.getString(fileStorageSetting.getIsDelMsg()));
        result = FileServerHttpClientUtil.doPost(msgRequestUrl, paramMap, "UTF-8");
        Map<String,Object> msgResultMap = objMapper.readValue(result, Map.class);
        Object ret = msgResultMap.get("ret");
        if (ret == null || Integer.parseInt(String.valueOf(ret)) != 200) {
            throw new Exception("update fail!");
        }
    }
}
