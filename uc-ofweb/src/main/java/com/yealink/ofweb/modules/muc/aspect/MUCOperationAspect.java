package com.yealink.ofweb.modules.muc.aspect;

import com.yealink.dataservice.client.util.Result;
import com.yealink.ofweb.modules.base.Message;
import com.yealink.ofweb.modules.common.Constants;
import com.yealink.ofweb.modules.operationLog.dao.OperationLogDao;
import com.yealink.uc.common.modules.operationlog.entity.OperationLog;
import com.yealink.uc.common.modules.security.project.vo.Project;
import com.yealink.uc.platform.utils.DateUtil;
import com.yealink.uc.platform.utils.NetUtil;
import com.yealink.uc.platform.utils.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenkl on 2016/8/12.
 */
@Component
@Aspect
public class MUCOperationAspect {
    @Autowired
    OperationLogDao operationLogDao;

    @AfterReturning(value = "execution(* com.yealink.ofweb.modules.muc.controller.MucRoomController.edit(..))", returning = "result")
    void editOperation(JoinPoint joinPoint, Result result) {
        editOperationLog(joinPoint, result);
    }


    private void editOperationLog(JoinPoint joinPoint, Result result) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        String operation = signature.toShortString().replace(Constants.STRING_SHORT_SIGNATURE_END, "");
        operation = Constants.STR_PREFIX_OPERATION + operation;
        String operatorIP = NetUtil.getRemoteIP(request);
        boolean success =result.getRet()>=0;
        String message = result.getMsg();
//        String targetName = Message.getMessage("locale.language",request.getLocale())+":"+(String) args[1]+
//                Message.getMessage("locale.timezones",request.getLocale())+":"+(String)args[2];
        //// TODO: 2016/8/12 获取登录者的信息
//        OperationLog operationLog = new OperationLog(StringUtil.generateUUID(), operation,"admin", operatorIP, targetName, success, DateUtil.currentDate(), message, Project.UC_OFWEB.getName());
//        operationLogDao.save(operationLog);
    }
}
