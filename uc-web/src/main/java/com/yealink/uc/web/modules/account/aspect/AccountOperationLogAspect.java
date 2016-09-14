package com.yealink.uc.web.modules.account.aspect;

import javax.servlet.http.HttpServletRequest;

import com.yealink.uc.common.modules.operationlog.entity.OperationLog;
import com.yealink.uc.common.modules.security.project.vo.Project;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.DateUtil;
import com.yealink.uc.platform.utils.JSONConverter;
import com.yealink.uc.platform.utils.NetUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.utils.StringUtil;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.web.modules.common.constant.OperationLogConstant;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.web.modules.operationlog.dao.OperationLogDao;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AccountOperationLogAspect {
    @Autowired
    OperationLogDao operationLogDao;

    @AfterReturning(value = "execution(* com.yealink.uc.web.modules.account.controller.AccountController.editAccount(..)) || " +
        "execution(* com.yealink.uc.web.modules.account.controller.AccountController.editPassword(..))", returning = "result")
    void orgOperation(JoinPoint joinPoint, String result) {
        addOperationLog(joinPoint, result);
    }

    public void addOperationLog(JoinPoint joinPoint, String result) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        String operation = signature.toShortString().replace(OperationLogConstant.STRING_SHORT_SIGNATURE_END, "");
        operation = OperationLogConstant.STR_PREFIX_OPERATION + operation;
        String operatorIP = NetUtil.getRemoteIP(request);
        Response response = JSONConverter.fromJSON(Response.class, result);
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        OperationLog operationLog = new OperationLog(StringUtil.generateUUID(), operation, ucAccountView.getName(),
            operatorIP, null, response.isSuccess(), DateUtil.currentDate(), response.getMessage(), Project.UC_WEB.getName());
        operationLogDao.save(operationLog);
    }

}
