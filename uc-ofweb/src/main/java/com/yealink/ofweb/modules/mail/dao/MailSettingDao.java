package com.yealink.ofweb.modules.mail.dao;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.ofweb.modules.fileshare.util.DateUtil;
import com.yealink.ofweb.modules.mail.entity.MailSendInfo;
import com.yealink.ofweb.modules.mail.util.MailConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 邮箱设置
 * author:pengzhiyuan
 * Created on:2016/8/23.
 */
@Repository
public class MailSettingDao {
    private static IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();
    private static final Logger logger = LoggerFactory.getLogger(MailSettingDao.class);
    private static final String YL_MAIL_SETTING = "ylMailSetting";

    /**
     * 保存邮箱设置
     * @param mailSendInfo
     */
    public void saveMailSetting(MailSendInfo mailSendInfo) {
        String _id= UUID.randomUUID().toString();
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put("_id", _id);
        valueMap.put("createDate", DateUtil.formatDate(new Date()));

        valueMap.put("userName", mailSendInfo.getUserName());
        valueMap.put("passWord", mailSendInfo.getPassWord());
        valueMap.put("host", mailSendInfo.getMailServerHost());
        valueMap.put("port", mailSendInfo.getMailServerPort());
        valueMap.put("fromAddress", mailSendInfo.getFromAddress());
        String secure = MailConstant.MAIL_SECURE_COMMON;
        if (mailSendInfo.isSSlSend()) {
            secure = MailConstant.MAIL_SECURE_SSL;
        } else if (mailSendInfo.isTlsSend()) {
            secure = MailConstant.MAIL_SECURE_TLS;
        }
        valueMap.put("secure", secure);
        try {
            remoteDataService.insertOne(YL_MAIL_SETTING, valueMap);
        } catch (Exception e) {
            logger.error("remote save saveMailSetting error!" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除邮箱设置
     * @param id
     */
    public void deleteMailSettingById(String id) {
        remoteDataService.deleteOne(YL_MAIL_SETTING, id);
    }

    /**
     * 查询邮箱设置信息
     * @return
     */
    public Map<String,Object> queryMailSetting() {
        String queryFields = "_id,userName,passWord,host,port,fromAddress,secure";
        Map<String,Object> rs=remoteDataService.queryOne(YL_MAIL_SETTING,queryFields,new HashMap<String,Object>());
        return rs;
    }

}
