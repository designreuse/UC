package com.yealink.ofweb.modules.msg.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yealink.ofweb.modules.fileshare.util.FileServerHttpClientUtil;
import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import com.yealink.ofweb.modules.msg.dao.MsgManagerDao;
import com.yealink.ofweb.modules.msg.vo.MessageConstant;
import com.yealink.ofweb.modules.msg.vo.MsgProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息管理
 * author:pengzhiyuan
 * Created on:2016/8/8.
 */
@Service
public class MsgManagerService {

    @Autowired
    private MsgManagerDao msgManagerDao;
    @Value("${msg_request_baseurl}")
    private String msgRequestUrl;

    /**
     * 从数据库中获取消息相关的属性
     * @return
     */
    public MsgProperty getMsgPropertyFromDb() {
        List<String> ids = new ArrayList<String>();
        ids.add(MessageConstant.PROPERTY_NAME_REVERT_VALIDTIME);
        ids.add(MessageConstant.PROPERTY_NAME_MSG_SAVEDAYS);
        ids.add(MessageConstant.PROPERTY_NAME_RECENTRELA_MAXNUMBER);

        List<Map<String, Object>> propertyList = msgManagerDao.queryPropertys(ids);
        MsgProperty property = new MsgProperty();
        for (Map<String, Object> map : propertyList) {
            String _id = PropertiesUtils.getString(map.get("_id"));
            if (MessageConstant.PROPERTY_NAME_REVERT_VALIDTIME.equals(_id)) {
                property.setRevertMsgValidTimes(PropertiesUtils.getString(map.get("propValue")));
            }
            if (MessageConstant.PROPERTY_NAME_MSG_SAVEDAYS.equals(_id)) {
                property.setMsgSaveDays(PropertiesUtils.getString(map.get("propValue")));
            }
            if (MessageConstant.PROPERTY_NAME_RECENTRELA_MAXNUMBER.equals(_id)) {
                property.setRecentMaxNumbers(PropertiesUtils.getString(map.get("propValue")));
            }
        }
        return property;
    }

    /**
     *  更新全局属性
     * @param property
     * @return
     */
    public Map<String, String> updateMsgProperty(MsgProperty property) throws Exception {
        Map<String, String> paramMap = new HashMap<>();

        paramMap.put("revertMsgValidTimes", property.getRevertMsgValidTimes());
        paramMap.put("msgSaveDays", property.getMsgSaveDays());
        paramMap.put("recentMaxNumbers", property.getRecentMaxNumbers());
        paramMap.put("isDelMsg", "true");
        String result = FileServerHttpClientUtil.doPost(msgRequestUrl, paramMap, "UTF-8");

        try {
            ObjectMapper objMapper = new ObjectMapper();
            return objMapper.readValue(result, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
