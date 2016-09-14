package com.yealink.uc.web.modules.security.module.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yealink.uc.common.modules.enterprise.entity.Enterprise;
import com.yealink.uc.common.modules.security.module.entity.Module;
import com.yealink.uc.common.modules.security.module.vo.ModuleTreeNode;
import com.yealink.uc.common.modules.security.project.entity.Project;
import com.yealink.uc.platform.web.permission.annotations.PermissionRequired;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.web.permission.vo.Operation;
import com.yealink.uc.platform.web.permission.vo.Resource;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.web.modules.security.module.request.CreateModuleRequest;
import com.yealink.uc.web.modules.security.module.request.EditModuleRequest;
import com.yealink.uc.web.modules.security.module.service.ModuleService;
import com.yealink.uc.web.modules.security.project.service.ProjectService;

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
public class ModuleController {
    @Autowired
    ModuleService moduleService;
    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/module/index", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.MODULE, operation = Operation.READ)
    public String index(HttpServletRequest request, Model model, @RequestParam("projectId") Long projectId) {
        model.addAttribute("projectId", projectId);
        return "module/module_index";
    }

    @RequestMapping(value = "/module/showTreeNodes", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRequired(resource = Resource.MODULE, operation = Operation.READ)
    public Response showTreeNodes(HttpServletRequest request, @RequestParam("projectId") Long projectId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        List<ModuleTreeNode> nodes = moduleService.showTreeNodes(projectId, ucAccountView.getEnterpriseId());
        return new Response<>(nodes, true);
    }

    @RequestMapping(value = "/module/showTreeNodesForEdit", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRequired(resource = Resource.MODULE, operation = Operation.READ)
    public Response showTreeNodesForEdit(HttpServletRequest request, @RequestParam("projectId") Long projectId,@RequestParam("moduleId") Long moduleId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        List<ModuleTreeNode> nodes = moduleService.showTreeNodesForEdit(projectId, ucAccountView.getEnterpriseId(), moduleId);
        return new Response<>(nodes, true);
    }

    @RequestMapping(value = "/module/forwardToCreate", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.MODULE, operation = Operation.READ)
    public String forwardToCreateModule(HttpServletRequest request, Model model) {
        return "module/module_create";
    }

    @RequestMapping(value = "/module/forwardToEdit/{moduleId}", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.MODULE, operation = Operation.READ)
    public String forwardToEditModule(HttpServletRequest request, Model model, @PathVariable Long moduleId) {
        Module module = moduleService.get(moduleId);
        Enterprise enterprise = SessionUtil.get(request, SessionConstant.CURRENT_ENTERPRISE);
        if (module.getParentId() == 0) {
            Project project = projectService.get(module.getProjectId(), enterprise.get_id());
            model.addAttribute("parentName", project.getName());
        } else {
            Module parentModule = moduleService.get(module.getParentId());
            model.addAttribute("parentName", parentModule.getName());
        }
        model.addAttribute("parentId", module.getParentId());
        model.addAttribute("module", module);

        return "module/module_edit";
    }


    @RequestMapping(value = "/module/create", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.MODULE, operation = Operation.CREATE)
    public Response create(HttpServletRequest request,
                                @Valid CreateModuleRequest createModuleRequest,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isCreateSuccess = moduleService.create(createModuleRequest, ucAccountView.getEnterpriseId());
        if (isCreateSuccess) {
            return Response.buildResponse(true, "Create success");
        } else {
            return Response.buildResponse(false, "Create failed");
        }
    }

    @RequestMapping(value = "/module/edit", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.MODULE, operation = Operation.UPDATE)
    public Response edit(HttpServletRequest request, @Valid EditModuleRequest editModuleRequest,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isEditSuccess = moduleService.edit(editModuleRequest, ucAccountView.getEnterpriseId());
        if (isEditSuccess) {
            return Response.buildResponse(true, "Edit success");
        } else {
            return Response.buildResponse(false, "Edit failed");
        }
    }

    @RequestMapping(value = "/module/delete/{moduleId}", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.MODULE, operation = Operation.DELETE)
    public Response delete(HttpServletRequest request, @PathVariable("moduleId") Long moduleId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isDeleteSuccess = moduleService.delete(moduleId, ucAccountView.getEnterpriseId());
        if (isDeleteSuccess) {
            return Response.buildResponse(true, "Delete success");
        } else {
            return Response.buildResponse(false, "Delete failed");
        }
    }
}
