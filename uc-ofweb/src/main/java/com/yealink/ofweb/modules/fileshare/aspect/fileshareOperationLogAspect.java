package com.yealink.ofweb.modules.fileshare.aspect;

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
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenkl on 2016/8/12.
 */
@Component
@Aspect
public class fileshareOperationLogAspect {
    @Autowired
    OperationLogDao operationLogDao;

    @AfterReturning(value = "execution(* com.yealink.ofweb.modules.fileshare.controller.FileShareMaintainController.save(..))", returning = "result")
    void saveOperation(JoinPoint joinPoint, String result) {
        saveOperationLog(joinPoint, result);
    }

    @AfterReturning(value = "execution(* com.yealink.ofweb.modules.fileshare.controller.FileShareQueryController.deleteAvatar(..))", returning = "result")
    void deleteOperation(JoinPoint joinPoint, String result) {
        deleteOperationLog(joinPoint, result);
    }

    @AfterReturning(value = "execution(* com.yealink.ofweb.modules.fileshare.controller.FileServerManagerController.closeSession(..))", returning = "result")
    void closeOperation(JoinPoint joinPoint, String result) {
        closeOperationLog(joinPoint, result);
    }


    private void saveOperationLog(JoinPoint joinPoint, String result) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        String operation = signature.toShortString().replace(Constants.STRING_SHORT_SIGNATURE_END, "");
        operation = Constants.STR_PREFIX_OPERATION + operation;
        String operatorIP = NetUtil.getRemoteIP(request);
        Model model = (Model) args[1];
        boolean success =model.asMap().get("code").equals(com.yealink.ofweb.modules.fileshare.util.Constants.SUCCESS_CODE);
        String message =Message.getMessage("fileshare.msg.maintan.savefailed",request.getLocale()) ;
        if(success){
            message = Message.getMessage("fileshare.msg.maintan.savesucce",request.getLocale()) ;
        }
        String targetName = Message.getMessage("fileshare.label.maintain.offlinesavedays",request.getLocale())+":"+
                model.asMap().get("offlineSaveDays").toString()+Message.getMessage("fileshare.label.maintain.day",request.getLocale())+" "+
                Message.getMessage("fileshare.label.maintain.filemaxsize",request.getLocale())+":"+
                model.asMap().get("fileMaxSize").toString()+Message.getMessage("fileshare.label.maintain.byte",request.getLocale())+" "+
                Message.getMessage("fileshare.label.maintain.avatarfileformat",request.getLocale())+":"+
                model.asMap().get("avatarFileTypes").toString();
        //// TODO: 2016/8/12 获取登录者的信息
        OperationLog operationLog = new OperationLog(StringUtil.generateUUID(), operation,"admin", operatorIP, targetName, success, DateUtil.currentDate(), message, Project.UC_OFWEB.getName());
        operationLogDao.save(operationLog);
    }

    private void deleteOperationLog(JoinPoint joinPoint, String result) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        String operation = signature.toShortString().replace(Constants.STRING_SHORT_SIGNATURE_END, "");
        operation = Constants.STR_PREFIX_OPERATION + operation;
        String operatorIP = NetUtil.getRemoteIP(request);
        boolean success =result.equals("1");
        String message =Message.getMessage("fileshare.common.msg.delfail",request.getLocale()) ;
        if(success){
            message = Message.getMessage("fileshare.common.msg.delsuccess",request.getLocale()) ;
        }
        String targetName =(String) args[1];
        //// TODO: 2016/8/12 获取登录者的信息
        OperationLog operationLog = new OperationLog(StringUtil.generateUUID(), operation,"admin", operatorIP, targetName, success, DateUtil.currentDate(), message, Project.UC_OFWEB.getName());
        operationLogDao.save(operationLog);
    }


    private void closeOperationLog(JoinPoint joinPoint, String result) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[0];
        String operation = signature.toShortString().replace(Constants.STRING_SHORT_SIGNATURE_END, "");
        operation = Constants.STR_PREFIX_OPERATION + operation;
        String operatorIP = NetUtil.getRemoteIP(request);
        boolean success =result.equals("1");
        String message =Message.getMessage("fileshare.msg.fileserver.closefail",request.getLocale()) ;
        if(success){
            message = Message.getMessage("fileshare.msg.fileserver.closesuccess",request.getLocale()) ;
        }
        String targetName ="jid:"+(String) args[1];
        //// TODO: 2016/8/12 获取登录者的信息
        OperationLog operationLog = new OperationLog(StringUtil.generateUUID(), operation,"admin", operatorIP, targetName, success, DateUtil.currentDate(), message, Project.UC_OFWEB.getName());
        operationLogDao.save(operationLog);
    }

}
