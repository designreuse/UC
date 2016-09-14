package com.yealink.uc.web.modules.security.resource.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yealink.uc.common.modules.security.menu.vo.MenuResourceTreeNode;
import com.yealink.uc.common.modules.security.module.entity.Module;
import com.yealink.uc.common.modules.security.resource.entity.Resource;
import com.yealink.uc.platform.web.permission.annotations.PermissionRequired;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.web.permission.vo.Operation;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.web.modules.security.module.service.ModuleService;
import com.yealink.uc.web.modules.security.resource.request.CreateResourceRequest;
import com.yealink.uc.web.modules.security.resource.request.EditResourceRequest;
import com.yealink.uc.web.modules.security.resource.service.ResourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ChNan
 */
@Controller
@LoginRequired
public class ResourceController {
    @Autowired
    ResourceService resourceService;
    @Autowired
    ModuleService moduleService;

    @RequestMapping(value = "/resource/index", method = RequestMethod.GET)
    @PermissionRequired(resource = com.yealink.uc.platform.web.permission.vo.Resource.RESOURCE, operation = Operation.READ)
    public String index(HttpServletRequest request, Model model, @RequestParam("projectId") Long projectId) throws IOException {
        model.addAttribute("projectId", projectId);
        return "resource/resource_index";
    }

    @RequestMapping(value = "/resource/forwardToCreate", method = RequestMethod.GET)
    @PermissionRequired(resource = com.yealink.uc.platform.web.permission.vo.Resource.RESOURCE, operation = Operation.READ)
    public String forwardToCreate(HttpServletRequest request, Model model) {
        return "resource/resource_create";
    }

    @RequestMapping(value = "/resource/showTreeNodes", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRequired(resource = com.yealink.uc.platform.web.permission.vo.Resource.RESOURCE, operation = Operation.READ)
    public Response showTreeNodes(HttpServletRequest request, @RequestParam("projectId") Long projectId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        List<MenuResourceTreeNode> nodes = resourceService.showTreeNodes(projectId, ucAccountView.getEnterpriseId());
        return new Response<>(nodes, true);
    }

    @RequestMapping(value = "/resource/forwardToEdit/{resourceId}", method = RequestMethod.GET)
    @PermissionRequired(resource = com.yealink.uc.platform.web.permission.vo.Resource.RESOURCE, operation = Operation.READ)
    public String forwardToEdit(HttpServletRequest request, Model model, @PathVariable Long resourceId) {
        Resource resource = resourceService.get(resourceId);
        Module module = moduleService.get(resource.getModuleId());
        model.addAttribute("resource", resource);
        model.addAttribute("module", module);
        return "resource/resource_edit";
    }

    @RequestMapping(value = "/resource/create", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = com.yealink.uc.platform.web.permission.vo.Resource.RESOURCE, operation = Operation.CREATE)
    public Response create(HttpServletRequest request,
                           @Valid CreateResourceRequest createResourceRequest,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isCreateSuccess = resourceService.create(createResourceRequest, ucAccountView.getEnterpriseId());
        if (isCreateSuccess) {
            return Response.buildResponse(true, "Create success");
        } else {
            return Response.buildResponse(false, "Create failed");
        }
    }

    @RequestMapping(value = "/resource/edit", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = com.yealink.uc.platform.web.permission.vo.Resource.RESOURCE, operation = Operation.UPDATE)
    public Response edit(HttpServletRequest request, @Valid EditResourceRequest editResourceRequest,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isEditSuccess = resourceService.edit(editResourceRequest, ucAccountView.getEnterpriseId());
        if (isEditSuccess) {
            return Response.buildResponse(true, "Edit success");
        } else {
            return Response.buildResponse(false, "Edit failed");
        }
    }

    @RequestMapping(value = "/resource/delete/{resourceId}", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = com.yealink.uc.platform.web.permission.vo.Resource.RESOURCE, operation = Operation.DELETE)
    public Response delete(HttpServletRequest request, @PathVariable("resourceId") Long resourceId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isDeleteSuccess = resourceService.delete(resourceId, ucAccountView.getEnterpriseId());
        if (isDeleteSuccess) {
            return Response.buildResponse(true, "Delete success");
        } else {
            return Response.buildResponse(false, "Delete failed");
        }
    }

}
