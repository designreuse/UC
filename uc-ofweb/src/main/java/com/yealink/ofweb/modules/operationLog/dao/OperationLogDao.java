package com.yealink.ofweb.modules.operationLog.dao;

import com.yealink.dataservice.client.IRemoteDataService;
import com.yealink.dataservice.client.RemoteServiceFactory;
import com.yealink.dataservice.client.util.ConditionItem;
import com.yealink.dataservice.client.util.Filter;
import com.yealink.dataservice.client.util.Pager;
import com.yealink.ofweb.modules.immanager.request.AuditSearchBean;
import com.yealink.uc.common.modules.operationlog.entity.OperationLog;
import com.yealink.uc.common.modules.security.project.vo.Project;
import com.yealink.uc.platform.utils.EntityUtil;
import com.yealink.uc.platform.utils.StringUtil;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenkl on 2016/8/11.
 */
@Repository
public class OperationLogDao {
    public static final String OPERATION_ENTITY_NAME = EntityUtil.getEntityName(OperationLog.class);
    private IRemoteDataService remoteDataService = RemoteServiceFactory.getInstance().getRemoteDataService();

    public long save(OperationLog operationLog) {
        return remoteDataService.insertOne(OPERATION_ENTITY_NAME, operationLog);
    }

    public Pager<Map<String,Object>> getOperationLogList(Project projectName, AuditSearchBean searchBean){
        Map<String,Object> condition = new HashMap<>();
        condition.put("projectName",projectName.getName());
        if(!StringUtil.isStrEmpty(searchBean.getSearchKey())){
            condition.putAll(Filter.regex("operatorName",".*?"+searchBean.getSearchKey()+".*?").toMap());
        }
        if(searchBean.getStartTime()!=null&&searchBean.getEndTime()!=null){
            condition.putAll(Filter.and(Filter.lte("operationTime",searchBean.getEndTime().getTime()),Filter.gte("operationTime",searchBean.getStartTime().getTime())).toMap());
        }
        Pager<Map<String,Object>> pager = new Pager();
        pager.setLimit(searchBean.getPageSize());
        pager.setSkip((searchBean.getPageNo()-1)*searchBean.getPageSize());
        Pager<Map<String,Object>> temp= remoteDataService.query(OPERATION_ENTITY_NAME,null,pager,condition);
        if(temp!=null){
          return temp;
        }else {
            return null;
        }
    }

    public Long deleteLogList(Project projectName,List<String> ids){
        Map<String,Object> condition = new HashMap<>();
        condition.put("projectName",projectName.getName());
        condition.putAll(Filter.in("_id",ids).toMap());
        return remoteDataService.deleteMany(OPERATION_ENTITY_NAME,condition);
    }

    public Long getCount(Project projectName, AuditSearchBean searchBean){
        Map<String,Object> condition = new HashMap<>();
        condition.put("projectName",projectName.getName());
        if(!StringUtil.isStrEmpty(searchBean.getSearchKey())){
            condition.putAll(Filter.regex("operatorName",".*?"+searchBean.getSearchKey()+".*?").toMap());
        }
        if(searchBean.getStartTime()!=null&&searchBean.getEndTime()!=null){
            condition.putAll(Filter.and(Filter.lte("operationTime",searchBean.getEndTime().getTime()),Filter.gte("operationTime",searchBean.getStartTime().getTime())).toMap());
        }
        return remoteDataService.queryCount(OPERATION_ENTITY_NAME,condition);
    }
}
