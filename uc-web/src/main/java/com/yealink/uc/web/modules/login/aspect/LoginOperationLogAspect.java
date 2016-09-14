package com.yealink.uc.web.modules.login.aspect;

import javax.servlet.http.HttpServletRequest;

import com.yealink.uc.common.modules.operationlog.entity.OperationLog;
import com.yealink.uc.common.modules.security.project.vo.Project;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.DateUtil;
import com.yealink.uc.platform.utils.NetUtil;
import com.yealink.uc.platform.utils.StringUtil;
import com.yealink.uc.web.modules.common.constant.OperationLogConstant;
import com.yealink.uc.web.modules.login.request.LoginRequest;
import com.yealink.uc.web.modules.operationlog.dao.OperationLogDao;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoginOperationLogAspect {
    @Autowired
    OperationLogDao operationLogDao;

    @AfterReturning(value = "execution(* com.yealink.uc.web.modules.login.controller.LoginController.login(..))", returning = "response")
    void loginOperation(JoinPoint joinPoint, Response response) {
        addOperationLog(joinPoint, response);
    }

    @After("execution(* com.yealink.uc.web.modules.login.controller.LoginController.logout(..))")
    void logoutOperation(JoinPoint joinPoint) {
        addOperationLog(joinPoint, Response.buildResponse(true, "success"));
    }

    public void addOperationLog(JoinPoint joinPoint, Response response) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        String operation = signature.toShortString().replace(OperationLogConstant.STRING_SHORT_SIGNATURE_END, "");
        operation = OperationLogConstant.STR_PREFIX_OPERATION + operation;
        String operatorIP = NetUtil.getRemoteIP(request);
        String accountName;
        if (operation.equals("operation.LoginController.logout")) {
            accountName = (String) request.getAttribute("account");
        } else {
            LoginRequest loginRequest = (LoginRequest) args[1];
            accountName = loginRequest.getUsername();
        }
        OperationLog operationLog = new OperationLog(StringUtil.generateUUID(), operation, accountName, operatorIP, null, response.isSuccess(),
            DateUtil.currentDate(), response.getMessage(), Project.UC_WEB.getName());
        operationLogDao.save(operationLog);
    }

}
