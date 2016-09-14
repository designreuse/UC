package com.yealink.uc.web.modules.org.aspect;

import javax.servlet.http.HttpServletRequest;

import com.yealink.uc.common.modules.operationlog.entity.OperationLog;
import com.yealink.uc.common.modules.security.project.vo.Project;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.DateUtil;
import com.yealink.uc.platform.utils.NetUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.utils.StringUtil;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
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
public class OrgOperationLogAspect {
    @Autowired
    OperationLogDao operationLogDao;
    public static final String STR_PREFIX_OPERATION = "operation.";
    public static final String STRING_SHORT_SIGNATURE_END = "(..)";

    @AfterReturning(value = "execution(* com.yealink.uc.web.modules.org.controller.OrgController.createOrg(..)) || " +
        "execution(* com.yealink.uc.web.modules.org.controller.OrgController.editOrg(..)) ||" +
        "execution(* com.yealink.uc.web.modules.org.controller.OrgController.deleteOrg(..)) || " +
        "execution(* com.yealink.uc.web.modules.org.controller.OrgController.moveOrg(..))", returning = "response")
    void orgOperation(JoinPoint joinPoint, Response response) {
        addOperationLog(joinPoint, response);
    }

    public void addOperationLog(JoinPoint joinPoint, Response response) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        String operation = signature.toShortString().replace(STRING_SHORT_SIGNATURE_END, "");
        operation = STR_PREFIX_OPERATION + operation;
        String operatorIP = NetUtil.getRemoteIP(request);
        UCAccountView UCAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        OperationLog operationLog = new OperationLog(StringUtil.generateUUID(), operation, UCAccountView.getName(),
            operatorIP, null, response.isSuccess(), DateUtil.currentDate(), response.getMessage(), Project.UC_WEB.getName());
        operationLogDao.save(operationLog);
    }

}
