package com.yealink.uc.web.modules.security.operation.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yealink.uc.platform.web.permission.annotations.PermissionRequired;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.web.permission.vo.Operation;
import com.yealink.uc.platform.web.permission.vo.Resource;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.web.modules.security.operation.request.CreateOperationRequest;
import com.yealink.uc.web.modules.security.operation.request.EditOperationRequest;
import com.yealink.uc.web.modules.security.operation.service.OperationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ChNan
 */
@Controller
@LoginRequired
public class OperationController {
    @Autowired
    OperationService operationService;

    @RequestMapping(value = "/operation/index", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.OPERATION, operation = Operation.READ)
    public String orgIndex(HttpServletRequest request, Model model) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        model.addAttribute("operationList", operationService.findAllByEnterprise(ucAccountView.getEnterpriseId()));
        return "operation/operation_index";
    }

    @RequestMapping(value = "/operation/forwardToCreate", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.OPERATION, operation = Operation.READ)
    public String forwardToCreate(HttpServletRequest request, Model model) {
        return "operation/operation_create";
    }

    @RequestMapping(value = "/operation/forwardToEdit/{operationId}", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.OPERATION, operation = Operation.READ)
    public String forwardToEdit(HttpServletRequest request, Model model, @PathVariable("operationId") Long operationId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        model.addAttribute("operation", operationService.get(operationId, ucAccountView.getEnterpriseId()));
        return "operation/operation_edit";
    }

    @RequestMapping(value = "/operation/create", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.OPERATION, operation = Operation.CREATE)
    public Response create(HttpServletRequest request,
                           @Valid CreateOperationRequest createOperationRequest,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isCreateSuccess = operationService.create(createOperationRequest, ucAccountView.getEnterpriseId());
        if (isCreateSuccess) {
            return Response.buildResponse(true, "Create success");
        } else {
            return Response.buildResponse(false, "Create failed");
        }
    }

    @RequestMapping(value = "/operation/edit", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.OPERATION, operation = Operation.UPDATE)
    public Response edit(HttpServletRequest request, @Valid EditOperationRequest editOperationRequest,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isEditSuccess = operationService.edit(editOperationRequest, ucAccountView.getEnterpriseId());
        if (isEditSuccess) {
            return Response.buildResponse(true, "Edit success");
        } else {
            return Response.buildResponse(false, "Edit failed");
        }
    }

    @RequestMapping(value = "/operation/delete/{operationId}", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.OPERATION, operation = Operation.DELETE)
    public Response delete(HttpServletRequest request, @PathVariable("operationId") Long operationId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isDeleteSuccess = operationService.delete(operationId, ucAccountView.getEnterpriseId());
        if (isDeleteSuccess) {
            return Response.buildResponse(true, "Delete success");
        } else {
            return Response.buildResponse(false, "Delete failed");
        }
    }
}
