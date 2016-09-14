package com.yealink.uc.web.modules.security.resourceoperation.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yealink.uc.common.modules.security.menu.vo.MenuResourceTreeNode;
import com.yealink.uc.platform.web.permission.annotations.PermissionRequired;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.web.permission.vo.Operation;
import com.yealink.uc.platform.web.permission.vo.Resource;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.web.modules.security.module.service.ModuleService;
import com.yealink.uc.web.modules.security.operation.service.OperationService;
import com.yealink.uc.web.modules.security.project.service.ProjectService;
import com.yealink.uc.web.modules.security.resourceoperation.request.CreateResourceOperationRequest;
import com.yealink.uc.web.modules.security.resourceoperation.service.ResourceOperationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ChNan
 */
@Controller
@LoginRequired
public class ResourceOperationController {
    @Autowired
    ResourceOperationService resourceoperationService;
    @Autowired
    ProjectService projectService;
    @Autowired
    OperationService operationService;
    @Autowired
    ModuleService moduleService;
    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/resourceoperation/index", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.RESOURCE_OPERATION, operation = Operation.READ)
    public String index(HttpServletRequest request, Model model, @RequestParam("projectId") Long projectId) throws IOException {
        model.addAttribute("projectId", projectId);
        return "resourceoperation/resourceoperation_index";
    }

    @RequestMapping(value = "/resourceoperation/forwardToCreate", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.RESOURCE_OPERATION, operation = Operation.READ)
    public String forwardToCreate(HttpServletRequest request, Model model) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        model.addAttribute("operations", operationService.findAllByEnterprise(ucAccountView.getEnterpriseId()));
        return "resourceoperation/resourceoperation_create";
    }

    @RequestMapping(value = "/resourceoperation/showTreeNodes", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRequired(resource = Resource.RESOURCE_OPERATION, operation = Operation.READ)
    public Response showTreeNodes(HttpServletRequest request, @RequestParam("projectId") Long projectId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        List<MenuResourceTreeNode> nodes = resourceoperationService.showTreeNodes(projectId, ucAccountView.getEnterpriseId());
        return new Response<>(nodes, true);
    }

    @RequestMapping(value = "/resourceoperation/showAuthorizeTreeNodes", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRequired(resource = Resource.RESOURCE_OPERATION, operation = Operation.READ)
    public Response showTreeNodesForAuthorize(HttpServletRequest request, @RequestParam("projectId") Long projectId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        List<MenuResourceTreeNode> nodes = resourceoperationService.showTreeNodesForAuthorize(projectId, ucAccountView.getEnterpriseId());
        return new Response<>(nodes, true);
    }

    @RequestMapping(value = "/resourceoperation/create", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.RESOURCE_OPERATION, operation = Operation.CREATE)
    public Response create(HttpServletRequest request,
                                 @Valid CreateResourceOperationRequest createResourceRequest,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isCreateSuccess = resourceoperationService.create(createResourceRequest, ucAccountView.getEnterpriseId());
        if (isCreateSuccess) {
            return Response.buildResponse(true, "Create success");
        } else {
            return Response.buildResponse(false, "Create failed");
        }
    }

    @RequestMapping(value = "/resourceoperation/delete", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.RESOURCE_OPERATION, operation = Operation.DELETE)
    public Response delete(HttpServletRequest request, @RequestParam("resourceId") Long resourceId, @RequestParam("operationId") Long operationId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isDeleteSuccess = resourceoperationService.delete(resourceId, operationId, ucAccountView.getEnterpriseId());
        if (isDeleteSuccess) {
            return Response.buildResponse(true, "Delete success");
        } else {
            return Response.buildResponse(false, "Delete failed");
        }
    }

}
