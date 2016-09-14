package com.yealink.ofweb.modules.fileshare.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yealink.dataservice.client.IMQSender;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.Event;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.ofweb.modules.fileshare.dao.FileServerManagerDao;
import com.yealink.ofweb.modules.fileshare.request.FileSharePropertyRequest;
import com.yealink.ofweb.modules.fileshare.request.FileShareQueryRequest;
import com.yealink.ofweb.modules.fileshare.request.PageRequest;
import com.yealink.ofweb.modules.fileshare.util.Constants;
import com.yealink.ofweb.modules.fileshare.util.FileServerHttpClientUtil;
import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件服务器管理
 * author:pengzhiyuan
 * Created on:2016/7/27.
 */
@Service
public class FileServerManagerService {
    private static final Logger LOG = LoggerFactory.getLogger(FileServerManagerService.class);
    private static IMQSender mqSender = RemoteServiceFactory.getInstance().getMQSender();

    @Autowired
    private FileServerManagerDao fileServerManagerDao;
    private ObjectMapper objMapper = new ObjectMapper();

    @Value("${fileshare_request_baseurl}")
    private String fileShareRequestUrl;

    @Value("${fileserver_one_baseurl}")
    private String fileServerBaseUrl;

    /**
     * 获取文件服务器列表及对应状态信息
     * @return
     */
    public List<Map<String, String>> getUsableFileServerList() {
        LOG.debug("run IM getUsableFileServerList.");
        String fileServerList = FileServerHttpClientUtil.execGetRequest(fileShareRequestUrl);
        LOG.debug("getUsableFileServerList result=" + fileServerList);

        if (fileServerList != null) {
            try {
                List<Map<String, String>> fileServerMapList = objMapper.readValue(fileServerList,
                        List.class);
                // 到文件服务器中获取对应的当前状态信息
                Map<String, String> monitorMap = null;
                PropertiesUtils utils = new PropertiesUtils();
                utils.setPropertiesObject("/config/application.properties");
                utils.parse();
                for (Map<String, String> fileServerMap : fileServerMapList) {
                    String jid = StringUtils.trimToEmpty(fileServerMap.get("jid"));
                    // 通过jid到配置文件中获取对应服务器的http服务的baseurl
                    String jidUrl = utils.readValue(jid);
                    if (jidUrl != null) {
                        monitorMap = getFileServerInfo(jidUrl);
                        if (monitorMap != null) {
                            // 磁盘使用情况 '可用/总数'
                            monitorMap.put("diskUseInfo", monitorMap.get("diskFree")+"/"+monitorMap.get("diskTotal"));
                            fileServerMap.putAll(monitorMap);
                        }
                    }
                }
                return fileServerMapList;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取文件服务器实时状态信息
     * @return
     */
    public Map<String, String> getFileServerInfo(String baseUrl) {
        LOG.debug("run FileServer getServerInfo.");
        String requestUrl = baseUrl + "/monitor?method=getFileServerInfo";
        String fileServerInfo = FileServerHttpClientUtil.execGetRequest(requestUrl);
        LOG.debug("getFileServerInfo result=" + fileServerInfo);

        Map<String, String> fileServerMap = null;
        try {
            fileServerMap = objMapper.readValue(fileServerInfo, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileServerMap;
    }

    /**
     * 通过服务器JID获取实时状态信息
     * @param jid
     * @return
     */
    public Map<String, String> getFileServerInfoByJid(String jid) {
        String jidUrl = getFileServerUrlByJid(jid);
        return getFileServerInfo(jidUrl);
    }

    /**
     * 查询 服务器 历史状态信息列表
     * @param jid - 服务器的jid
     * @param interval 视图展现分钟间隔
     * @param total -获取总记录数
     * @return
     */
    public List<Map<String, Object>> queryFileServerStat(String jid, int interval, int total) {
        List<Map<String, Object>> curMapList = new ArrayList<>();
        // 获取总记录数
        List<Map<String, Object>> totalMapList = fileServerManagerDao.queryFileServerStat(jid, 1, total);
        if (totalMapList != null) {
            for (int i=0; i<totalMapList.size(); i++) {
                if (i==0 || (i+1>=interval && (i+1)%interval == 0)) {
                    curMapList.add(totalMapList.get(i));
                }
            }
        }
        return curMapList;
    }

    /**
     *  通过JID获取对应服务器 http服务地址
     * @param jid
     * @return
     */
    private String getFileServerUrlByJid(String jid) {
        PropertiesUtils utils = new PropertiesUtils();
        utils.setPropertiesObject("/config/application.properties");
        utils.parse();
        String jidUrl = utils.readValue(jid);
        return jidUrl;
    }

    /**
     * 获取所有连接会话
     *
     * @return
     */
    public String getAllConnectionInfo(String jid, PageRequest<Map<String, String>> pageRequest) throws JsonProcessingException {
        LOG.debug("run FileServer getAllConnectionInfo.");
        String jidUrl = getFileServerUrlByJid(jid);
        String requestUrl = jidUrl + "/monitor?method=getAllConnectionInfo";
        // sortOrder:pageSize:pageNumber
        String param = pageRequest.getSortOrder()+":"+pageRequest.getPageSize()+":"+pageRequest.getPageNumber();
        requestUrl = requestUrl + "&value=" + param;
        String connectionInfos = FileServerHttpClientUtil.execGetRequest(requestUrl);
        LOG.debug("getAllConnectionInfo result=" + connectionInfos);

        return connectionInfos;
    }

    /**
     * 根据digest关闭会话连接
     * @param jid
     * @param digest
     * @return
     */
    public String closeConnByDigest(String jid, String digest) {
        LOG.debug("run FileServer closeConnByDigest.");
        String jidUrl = getFileServerUrlByJid(jid);
        String requestUrl = jidUrl + "/monitor?method=closeConnByDigest&value="
                + digest;
        String result = FileServerHttpClientUtil.execGetRequest(requestUrl);
        LOG.debug("closeConnByDigest result=" + result);
        return result;
    }

    /**
     * 查询文件共享全局属性
     *
     * @return
     */
    public FileSharePropertyRequest queryFileShareProperty() {
        List<Map<String, Object>> propertyList = fileServerManagerDao.queryFileShareProperty();
        FileSharePropertyRequest request = new FileSharePropertyRequest();
        for (Map<String, Object> map : propertyList) {
            String _id = PropertiesUtils.getString(map.get("_id"));
            if (Constants.FILE_SHARE_PROPERTY_OFFLINE_SAVEDAYS.equals(_id)) {
                request.setOfflineSaveDays(PropertiesUtils.getString(map.get("propValue")));
            }
            if (Constants.FILE_SHARE_PROPERTY_MAXSIZE.equals(_id)) {
                request.setFileMaxSize(PropertiesUtils.getString(map.get("propValue")));
            }
            if (Constants.FILE_SHARE_PROPERTY_FILETYPE.equals(_id)) {
                String avatarFileTypes = PropertiesUtils.getString(map.get("propValue"));
                request.setAvatarFileTypes(avatarFileTypes);
            }
        }
        return request;
    }

    /**
     *  更新全局属性
     * @param request
     * @return
     */
    public Map<String, String> updateFileShareProperty(FileSharePropertyRequest request) throws Exception {
        Map<String, String> paramMap = new HashMap<>();

        paramMap.put("offlineSaveDays", request.getOfflineSaveDays());
        paramMap.put("fileMaxSize", request.getFileMaxSize());
        paramMap.put("avatarFileType", request.getAvatarFileTypes());
        paramMap.put("isDelOfflineFile", "true");
        String result = FileServerHttpClientUtil.doPost(fileShareRequestUrl, paramMap, "UTF-8");

        try {
            return objMapper.readValue(result, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询头像信息
     * @param pageRequest
     * @return
     */
    public Pager<Map<String,Object>> queryAvatarFileList(FileShareQueryRequest<Map<String, Object>> pageRequest) {
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();

        int startIndex = 0;
        startIndex = (pageNumber-1)*pageSize;

        Pager<Map<String,Object>> pager = fileServerManagerDao.queryAvatarFileList(pageRequest, startIndex, pageSize);
        if (pager != null) {
            List<Map<String,Object>> rows = pager.getData();
            Map<String, Object> userMap = null;
            for (Map<String,Object> avatarMap : rows) {
                String userName = PropertiesUtils.getString(avatarMap.get("userName"));
                if (!userName.equals("")) {
                    userMap = fileServerManagerDao.queryUser(userName);
                    if (userMap != null) {
                        avatarMap.put("email",PropertiesUtils.getString(userMap.get("email")));
                        avatarMap.put("mobilePhones",PropertiesUtils.getString(userMap.get("mobilePhones")));
                        avatarMap.put("domain",userMap.get("domain"));
                    }
                }
                // 图像url 先默认48x48大小
                avatarMap.put("avatarUrl", fileServerBaseUrl+"/avatar?id="+avatarMap.get("_id")+"&x=48&y=48");
            }
            pager.setData(rows);
        }
        return pager;
    }

    /**
     * 删除
     * @param ids
     * @throws Exception
     */
    public void deleteAvatarFile(String ids) throws Exception {
        String[] idArr = ids.split(",");
        Map<String, Object> fileMap = null;
        for (String id : idArr) {
            fileMap = fileServerManagerDao.queryAvatarPictureFile(id);
            if (fileMap != null) {
                // 删除头像记录
                fileServerManagerDao.deleteAvatarById(id);
                // 发送消息到文件服务器删除文件系统的头像数据
                String savePath = PropertiesUtils.getString(fileMap.get("savePath"));
                sendMsgForDelFile(id, PropertiesUtils.getFormatPath(savePath));
            }
        }
    }

    /**
     * 发送消息通知 删除文件系统数据
     * @param id
     * @param savePath - 文件保存路径
     */
    private void sendMsgForDelFile(String id, String savePath) {
        Event fileEvent = new Event();
        fileEvent.setTopic(Event.TOPIC_FILE_SERVICE);
        fileEvent.setOperation(Constants.OPT_DELETE_OFFLINEFILE);
        fileEvent.setEventTime(new Date().getTime());
        fileEvent.setResourceId(id);
        fileEvent.setSource(savePath);
        mqSender.publishEvent(fileEvent);
    }

    /**
     * 统计文件发送情况
     * @param paramMap
     * @return
     */
    public Map<String, Long> statFileSendRecord(Map<String, String> paramMap) {
        String fileType = PropertiesUtils.getString(paramMap.get("fileType"));
        Map<String, Long> resultMap = new HashMap<String, Long>();
        // 查询全部文件类别
        if (fileType.equals("")) {
            // 获取在线离线文件个数
            long onlineCount = fileServerManagerDao.countFileSendRecord(Constants.FILE_TYPE_ONLINE, paramMap);
            long offlineCount = fileServerManagerDao.countFileSendRecord(Constants.FILE_TYPE_OFFLINE, paramMap);
            // 获取群文件 消息图片和头像文件个数
            long groupCount = fileServerManagerDao.countFile(Constants.FILE_TYPE_GROUP, paramMap);
            long chatCount = fileServerManagerDao.countFile(Constants.FILE_TYPE_CHAT, paramMap);
            long avatarCount = fileServerManagerDao.countFile(Constants.FILE_TYPE_AVATAR, paramMap);

            resultMap.put(Constants.FILE_TYPE_ONLINE, onlineCount);
            resultMap.put(Constants.FILE_TYPE_OFFLINE, offlineCount);
            resultMap.put(Constants.FILE_TYPE_GROUP, groupCount);
            resultMap.put(Constants.FILE_TYPE_CHAT, chatCount);
            resultMap.put(Constants.FILE_TYPE_AVATAR, avatarCount);
        } else {
            // 计算单个类别文件个数
            long singleCount = 0;
            if (fileType.equals(Constants.FILE_TYPE_ONLINE) ||
                    fileType.equals(Constants.FILE_TYPE_OFFLINE)) {
                singleCount = fileServerManagerDao.countFileSendRecord(fileType, paramMap);
            } else {
                singleCount = fileServerManagerDao.countFile(fileType, paramMap);
            }
            resultMap.put(fileType, singleCount);
        }
        return resultMap;
    }

}
