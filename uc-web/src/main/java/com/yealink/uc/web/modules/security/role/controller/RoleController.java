package com.yealink.uc.web.modules.security.role.controller;

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
import com.yealink.uc.web.modules.security.project.service.ProjectService;
import com.yealink.uc.web.modules.security.role.request.CreateRoleRequest;
import com.yealink.uc.web.modules.security.role.request.EditRoleRequest;
import com.yealink.uc.web.modules.security.role.service.RoleService;
import com.yealink.uc.web.modules.security.rolelevel.service.RoleLevelService;

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
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    ProjectService projectService;

    @Autowired
    RoleLevelService roleLevelService;

    @RequestMapping(value = "/role/index", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.ROLE, operation = Operation.READ)
    public String orgIndex(HttpServletRequest request, Model model) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        model.addAttribute("roleList", roleService.findAllByEnterprise(ucAccountView.getEnterpriseId()));
        model.addAttribute("projectList", projectService.findAllByEnterprise(ucAccountView.getEnterpriseId()));
        return "role/role_index";
    }

    @RequestMapping(value = "/role/forwardToCreate", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.ROLE, operation = Operation.READ)
    public String forwardToCreateRole(HttpServletRequest request, Model model) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        model.addAttribute("roleLevelList", roleLevelService.findAllByEnterprise(ucAccountView.getEnterpriseId()));
        return "role/role_create";
    }

    @RequestMapping(value = "/role/forwardToEdit/{roleId}", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.ROLE, operation = Operation.READ)
    public String forwardToEditRole(HttpServletRequest request, Model model, @PathVariable("roleId") Long roleId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        model.addAttribute("role", roleService.get(roleId, ucAccountView.getEnterpriseId()));
        model.addAttribute("roleLevelList", roleLevelService.findAllByEnterprise(ucAccountView.getEnterpriseId()));
        return "role/role_edit";
    }

    @RequestMapping(value = "/role/create", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.ROLE, operation = Operation.CREATE)
    public Response createRole(HttpServletRequest request,
                               @Valid CreateRoleRequest createRoleRequest,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isCreateSuccess = roleService.create(createRoleRequest, ucAccountView.getEnterpriseId());
        if (isCreateSuccess) {
            return Response.buildResponse(true, "Create success");
        } else {
            return Response.buildResponse(false, "Create failed");
        }
    }

    @RequestMapping(value = "/role/edit", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.ROLE, operation = Operation.UPDATE)
    @ResponseBody
    public Response editRole(HttpServletRequest request, @Valid EditRoleRequest editRoleRequest,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isEditSuccess = roleService.edit(editRoleRequest, ucAccountView.getEnterpriseId());
        if (isEditSuccess) {
            return Response.buildResponse(true, "Edit success");
        } else {
            return Response.buildResponse(false, "Edit failed");
        }
    }

    @RequestMapping(value = "/role/delete/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.ROLE, operation = Operation.DELETE)
    public Response deleteRole(HttpServletRequest request, @PathVariable("roleId") Long roleId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isDeleteSuccess = roleService.delete(roleId, ucAccountView.getEnterpriseId());
        if (isDeleteSuccess) {
            return Response.buildResponse(true, "Delete success");
        } else {
            return Response.buildResponse(false, "Delete failed");
        }
    }
}
