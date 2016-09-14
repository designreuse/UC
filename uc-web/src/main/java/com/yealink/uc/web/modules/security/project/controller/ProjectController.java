package com.yealink.uc.web.modules.security.project.controller;

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
import com.yealink.uc.web.modules.security.project.request.CreateProjectRequest;
import com.yealink.uc.web.modules.security.project.request.EditProjectRequest;
import com.yealink.uc.web.modules.security.project.service.ProjectService;

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
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/project/index", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.PROJECT, operation = Operation.READ)
    public String index(HttpServletRequest request, Model model) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        model.addAttribute("projectList", projectService.findAllByEnterprise(ucAccountView.getEnterpriseId()));
        return "project/project_index";
    }

    @RequestMapping(value = "/project/forwardToCreate", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.PROJECT, operation = Operation.READ)
    public String forwardToCreate(HttpServletRequest request, Model model) {
        return "project/project_create";
    }

    @RequestMapping(value = "/project/forwardToEdit/{projectId}", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.PROJECT, operation = Operation.READ)
    public String forwardToEdit(HttpServletRequest request, Model model, @PathVariable("projectId") Long projectId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        model.addAttribute("project", projectService.get(projectId, ucAccountView.getEnterpriseId()));
        return "project/project_edit";
    }

    @RequestMapping(value = "/project/create", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.PROJECT, operation = Operation.CREATE)
    public Response create(HttpServletRequest request,
                           @Valid CreateProjectRequest createProjectRequest,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isCreateSuccess = projectService.create(createProjectRequest, ucAccountView.getEnterpriseId());
        if (isCreateSuccess) {
            return Response.buildResponse(true, "Create success");
        } else {
            return Response.buildResponse(false, "Create failed");
        }
    }

    @RequestMapping(value = "/project/edit", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.PROJECT, operation = Operation.UPDATE)
    public Response edit(HttpServletRequest request, @Valid EditProjectRequest editProjectRequest,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isEditSuccess = projectService.edit(editProjectRequest, ucAccountView.getEnterpriseId());
        if (isEditSuccess) {
            return Response.buildResponse(true, "Edit success");
        } else {
            return Response.buildResponse(false, "Edit failed");
        }
    }

    @RequestMapping(value = "/project/delete/{projectId}", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.PROJECT, operation = Operation.DELETE)
    public Response delete(HttpServletRequest request, @PathVariable("projectId") Long projectId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isDeleteSuccess = projectService.delete(projectId, ucAccountView.getEnterpriseId());
        if (isDeleteSuccess) {
            return Response.buildResponse(true, "Delete success");
        } else {
            return Response.buildResponse(false, "Delete failed");
        }
    }
}
