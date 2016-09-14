package com.yealink.ofweb.modules.fileshare.dao;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.ConditionItem;
import com.yealink.dataservice.client.util.Filter;
import com.yealink.dataservice.client.util.OrderBy;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.ofweb.modules.fileshare.entity.FileServerMonitorRecord;
import com.yealink.ofweb.modules.fileshare.entity.FileServerMonitorSet;
import com.yealink.ofweb.modules.fileshare.entity.FileServerStat;
import com.yealink.ofweb.modules.fileshare.request.FileShareQueryRequest;
import com.yealink.ofweb.modules.fileshare.util.Constants;
import com.yealink.ofweb.modules.fileshare.util.DateUtil;
import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import com.yealink.uc.platform.utils.EntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 文件服务器管理
 * author:pengzhiyuan
 * Created on:2016/7/27.
 */
@Repository
public class FileServerManagerDao {
    private static final Logger logger = LoggerFactory.getLogger(FileServerManagerDao.class);

    private static final String YL_FILESERVERSTAT_ENTITYNAME = EntityUtil.getEntityName(FileServerStat.class);
    private static final String YL_FILESERVERMONITORSET_ENTITYNAME = EntityUtil.getEntityName(FileServerMonitorSet.class);
    private static final String YL_FILESERVERMONITORRECORD_ENTITYNAME = EntityUtil.getEntityName(FileServerMonitorRecord.class);
    private static final String YL_AVATARFILE_ENTITYNAME="ylAvatarPicFile";
    private static final String YL_PROPERTY_ENTITYNAME="Property";
    private static final String YL_USER_ENTITYNAME = "User";

    private static final String YL_FILESENDRECORD_ENTITYNAME = "ylFileSendRecord";
    private static final String YL_GROUPFILE_ENTITYNAME = "ylGroupFile";
    private static final String YL_OFFLINEFILE_ENTITYNAME = "ylOfflineFile";
    private static final String YL_CHATFILE_ENTITYNAME = "ylChatPictureFile";

    private static IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();

    /**
     * 查询 服务器状态信息列表
     * @param jid - 服务器的jid
     * @param startIndex
     * @param numResults
     * @return
     */
    public List<Map<String, Object>> queryFileServerStat(String jid, int startIndex,
                                                         int numResults) {
        String queryFields = "_id,jid,cpuRate,memRate,ioRate,createDate";
        Pager pager = new Pager();
        List<OrderBy> orderbys=new ArrayList<OrderBy>();
        orderbys.add(new OrderBy("createDate", OrderBy.DESC));
        pager.setOrderbys(orderbys);
        pager.setSkip(startIndex);
        pager.setLimit(numResults);
        List<Map<String, Object>> result=remoteDataService.query(YL_FILESERVERSTAT_ENTITYNAME,
                queryFields, pager, Filter.eq("jid", jid).toMap()).getData();
        return result;
    }

    /**
     * 查询 id在一定范围区间是否超过设置的阈值
     * @param item - 监控项
     * @param start
     * @param end
     * @param threshold - 阈值
     * @return
     */
    public boolean isFsItemOverThreshold(String jid, String item,
                                         long start, long end, float threshold) {
        boolean isOver = false;
        String itemField = "";
        if ("cpu".equalsIgnoreCase(item)) {
            itemField = "cpuRate";
        } else if ("mem".equalsIgnoreCase(item)) {
            itemField = "memRate";
        } else if ("io".equalsIgnoreCase(item)) {
            itemField = "ioRate";
        }
        Map<String, Object> result = remoteDataService.queryOne(YL_FILESERVERSTAT_ENTITYNAME, "_id",
                Filter.and(Filter.lte("_id",end),
                        Filter.gte("_id", start),
                        Filter.eq("jid", jid),
                        Filter.lt(itemField, threshold)).toMap());
        // 不存在小于该阈值的数据，则需要报警
        if (result == null) {
            isOver = true;
        }
        return isOver;
    }

    /**
     * 保存文件服务器监控设置
     * @param fileServerMonitorSet
     * @return
     */
    public void saveFileServerMonitorSet(FileServerMonitorSet fileServerMonitorSet) {
        String _id= UUID.randomUUID().toString();
        fileServerMonitorSet.set_id(_id);
        fileServerMonitorSet.setCreateDate(DateUtil.formatDate(new Date()));
        try {
            remoteDataService.insertOne(YL_FILESERVERMONITORSET_ENTITYNAME, fileServerMonitorSet);
        } catch (Exception e) {
            logger.error("remote save fileservermonitorset error!" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据文件服务器JID获取监控设置信息列表
     * @param jid
     * @return
     */
    public List<Map<String, Object>> queryFileServerMonitorSetByJid(String jid) {
        String queryFields = "jid,item,startFlag,monitorTime,threshold,alarmWay";
        List<Map<String, Object>> fsMonitorSetMapList = remoteDataService.query(YL_FILESERVERMONITORSET_ENTITYNAME, queryFields,
                Filter.eq("jid", jid).toMap());
        return fsMonitorSetMapList;
    }

    /**
     * 更新监控设置
     * @param _id
     * @param fileServerMonitorSet 主要更新属性：startFlag,monitorTime,threshold,alarmWay
     */
    public void updateFileServerMonitorSet(String _id, FileServerMonitorSet fileServerMonitorSet) {
        remoteDataService.updateOne(YL_FILESERVERMONITORSET_ENTITYNAME, _id, fileServerMonitorSet);
    }

    /**
     * 保存文件服务器报警记录
     * @param fileServerMonitorRecord
     * @return
     */
    public void saveFileServerMonitorRecord(FileServerMonitorRecord fileServerMonitorRecord) {
        String _id= UUID.randomUUID().toString();
        fileServerMonitorRecord.set_id(_id);
        fileServerMonitorRecord.setCreateDate(DateUtil.formatDate(new Date()));
        try {
            remoteDataService.insertOne(YL_FILESERVERMONITORRECORD_ENTITYNAME, fileServerMonitorRecord);
        } catch (Exception e) {
            logger.error("remote save fileservermonitorrecord error!" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    /**
     * 根据文件服务器JID查询 报警历史记录
     * @param jid
     * @param startIndex
     * @param numResults
     * @return
     */
    public Pager<Map<String,Object>> queryFileServerMonitorRecord(String jid, int startIndex,
                                                                  int numResults) {
        Pager pager = new Pager();
        List<OrderBy> orderbys = new ArrayList<OrderBy>();
        orderbys.add(new OrderBy("createDate", OrderBy.DESC));
        pager.setOrderbys(orderbys );
        if ((startIndex==0) && (numResults==Integer.MAX_VALUE)){
            pager.setSkip(null);
            pager.setLimit(null);
        }else{
            pager.setSkip(startIndex);
            pager.setLimit(numResults);
        }
        String queryFields = "_id,jid,item,threshold,alarmWay,notifyUser,createDate";
        Pager<Map<String, Object>> result=remoteDataService.query(YL_FILESERVERMONITORRECORD_ENTITYNAME, queryFields,
                pager, Filter.eq("jid", jid).toMap());
        return result;
    }

    /**
     * 查询文件共享全局属性
     *
     * @return
     */
    public List<Map<String, Object>> queryFileShareProperty() {
        String queryFields = "propValue";
        List<String> ids = new ArrayList<String>();
        ids.add(Constants.FILE_SHARE_PROPERTY_OFFLINE_SAVEDAYS);
        ids.add(Constants.FILE_SHARE_PROPERTY_MAXSIZE);
        ids.add(Constants.FILE_SHARE_PROPERTY_FILETYPE);
        List<Map<String, Object>> propertyList = remoteDataService.query(YL_PROPERTY_ENTITYNAME, queryFields,
                Filter.in("_id", ids).toMap());
        return propertyList;
    }

    /**
     * 查询头像信息
     * @param pageRequest
     * @param startIndex
     * @param numResults
     * @return
     */
    public Pager<Map<String,Object>> queryAvatarFileList(FileShareQueryRequest<Map<String, Object>> pageRequest, int startIndex,
                                                         int numResults) {
        String userName = PropertiesUtils.getString(pageRequest.getUserName());
        String isUsing = PropertiesUtils.getString(pageRequest.getIsUsing());
        String sortOrder = PropertiesUtils.getString(pageRequest.getSortOrder());

        // 条件
        int sort = OrderBy.ASC;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            sort = OrderBy.DESC;
        }
        List<ConditionItem> conds = new ArrayList<ConditionItem>();
        if (!userName.equals("")) {
            conds.add(Filter.eq("userName", userName));
        }
        if (!isUsing.equals("")) {
            boolean isUsingBool = false;
            if (isUsing.equals("1")) {
                isUsingBool = true;
            }
            conds.add(Filter.eq("isUsing", isUsingBool));
        }

        Map<String, Object> contidionMap = null;
        if (conds.size() > 0) {
            contidionMap = Filter.and(conds).toMap();
        }

        Pager pager = new Pager();
        List<OrderBy> orderbys = new ArrayList<OrderBy>();
        orderbys.add(new OrderBy("createDate", sort));
        pager.setOrderbys(orderbys);
        pager.setAutoCount(true);
        if ((startIndex==0) && (numResults==Integer.MAX_VALUE)){
            pager.setSkip(null);
            pager.setLimit(null);
        }else{
            pager.setSkip(startIndex);
            pager.setLimit(numResults);
        }
        String queryFields = "_id,sender,userId,sendDate,fileName,savePath,receiveFileSize,mimeType,md5,userName,isUsing";
        Pager<Map<String, Object>> result=remoteDataService.query(YL_AVATARFILE_ENTITYNAME, queryFields,
                pager, contidionMap);
        return result;
    }

    /**
     * 查询用户信息
     * @param username
     * @return
     */
    public Map<String,Object> queryUser(String username) {
        Map<String, Object> condition=new LinkedHashMap<>();
        condition.put("username",username);
        String queryFields = "_id,username,domain,name,birthday,birthdayVisible,email,mobilePhones,workPhone,discription,gender,avatar,position,modificationDate,enterpriseId,status";
        Map<String,Object> rs=remoteDataService.queryOne(YL_USER_ENTITYNAME,queryFields,condition);
        return rs;
    }

    /**
     * 删除头像记录
     * @param id
     */
    public void deleteAvatarById(String id) {
        remoteDataService.deleteOne(YL_AVATARFILE_ENTITYNAME, id);
    }

    /**
     * 查询头像图片记录
     * @param id
     * @return
     */
    public Map<String, Object> queryAvatarPictureFile(String id) {
        String queryFields = "savePath";
        Map<String, Object> fileMap = remoteDataService.queryOne(YL_AVATARFILE_ENTITYNAME, queryFields,
                Filter.eq("_id", id).toMap());
        return fileMap;
    }


    //=========== 以下为统计查询 ===================== //

    /**
     * 统计离线和在线文件个数
     * @param fileType
     * @param paramMap
     * @return
     */
    public long countFileSendRecord(String fileType, Map<String, String> paramMap) {
        String beginDate = PropertiesUtils.getString(paramMap.get("beginSendDate"));
        String endSendDate = PropertiesUtils.getString(paramMap.get("endSendDate"));
        // 截至时间查询加一天进行小于比较
        endSendDate = DateUtil.getDateAddDay(endSendDate, 1);

        List<ConditionItem> conds = new ArrayList<ConditionItem>();
        if (!beginDate.equals("")) {
            conds.add(Filter.gt("sendDate", beginDate));
        }
        if (!endSendDate.equals("")) {
            conds.add(Filter.lt("sendDate", endSendDate));
        }
        if (!fileType.equals("")) {
            conds.add(Filter.eq("fileType", fileType));
        }

        Map<String, Object> contidionMap = null;
        if (conds.size() > 0) {
            contidionMap = Filter.and(conds).toMap();
        }
        return remoteDataService.queryCount(YL_FILESENDRECORD_ENTITYNAME, contidionMap);
    }

    /**
     * 统计群文件、消息图片和头像文件个数
     * @param fileType -- 文件类别：群文件，消息图片和头像文件
     * @param paramMap - 入参
     * @return
     */
    public long countFile(String fileType, Map<String, String> paramMap) {
        String beginDate = PropertiesUtils.getString(paramMap.get("beginSendDate"));
        String endSendDate = PropertiesUtils.getString(paramMap.get("endSendDate"));
        // 截至时间查询加一天进行小于比较
        endSendDate = DateUtil.getDateAddDay(endSendDate, 1);

        List<ConditionItem> conds = new ArrayList<ConditionItem>();
        if (!beginDate.equals("")) {
            conds.add(Filter.gt("sendDate", beginDate));
        }
        if (!endSendDate.equals("")) {
            conds.add(Filter.lt("sendDate", endSendDate));
        }

        Map<String, Object> contidionMap = null;
        if (conds.size() > 0) {
            contidionMap = Filter.and(conds).toMap();
        }

        String entityName = "";
        if (Constants.FILE_TYPE_GROUP.equals(fileType)) {
            entityName = YL_GROUPFILE_ENTITYNAME;
        } else if (Constants.FILE_TYPE_CHAT.equals(fileType)) {
            entityName = YL_CHATFILE_ENTITYNAME;
        } else if (Constants.FILE_TYPE_AVATAR.equals(fileType)) {
            entityName = YL_AVATARFILE_ENTITYNAME;
        }

        return remoteDataService.queryCount(entityName, contidionMap);
    }

}
