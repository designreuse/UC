package com.yealink.ofweb.modules.immanager.service;

import com.yealink.dataservice.client.util.Pager;
import com.yealink.ofweb.modules.immanager.request.AuditSearchBean;
import com.yealink.ofweb.modules.operationLog.dao.OperationLogDao;
import com.yealink.uc.common.modules.operationlog.entity.OperationLog;
import com.yealink.uc.common.modules.security.project.vo.Project;
import com.yealink.ofweb.modules.util.DateUtil;
import com.yealink.uc.platform.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by chenkl on 2016/8/12.
 */
@Service
public class AuditService {
    @Autowired
    private OperationLogDao operationLogDao;

    public Pager<Map<String,Object>> getAuditList(AuditSearchBean searchBean){
      return operationLogDao.getOperationLogList(Project.UC_OFWEB,searchBean);
    }

    public long delete(String ids){
        if(StringUtil.isStrEmpty(ids)){
            return 0l;
        }
        return operationLogDao.deleteLogList(Project.UC_OFWEB,stringToList(ids));
    }

    private List<String> stringToList(String ids) {
        String[] arrayStr = ids.split(",");
        return Arrays.asList(arrayStr);
    }

    public Long getCount(AuditSearchBean searchBean){
        return operationLogDao.getCount(Project.UC_OFWEB,searchBean);
    }
}
