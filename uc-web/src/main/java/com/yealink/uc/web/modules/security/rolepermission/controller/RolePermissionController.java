package com.yealink.uc.web.modules.security.rolepermission.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yealink.uc.common.modules.security.rolepermission.entity.RolePermission;
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
import com.yealink.uc.web.modules.security.role.service.RoleService;
import com.yealink.uc.web.modules.security.rolepermission.request.EditRolePermissionRequest;
import com.yealink.uc.web.modules.security.rolepermission.service.RolePermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ChNan
 */
@Controller
@LoginRequired
public class RolePermissionController {
    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    RoleService roleService;

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/rolepermission/index", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.ROLE_PERMISSION, operation = Operation.READ)
    public String index(HttpServletRequest request, Model model,
                        @RequestParam("roleId") Long roleId,
                        @RequestParam("projectId") Long projectId,
                        @RequestParam("permissionType") String permissionType) throws IOException {
        model.addAttribute("projectId", projectId);
        model.addAttribute("roleId", roleId);
        model.addAttribute("permissionType", permissionType);
        List<RolePermission> rolePermissionList = rolePermissionService.find(roleId);
        model.addAttribute("rolePermissionList", rolePermissionList);
        return "rolepermission/rolepermission_index";
    }

    @RequestMapping(value = "/rolepermission/list", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.ROLE_PERMISSION, operation = Operation.READ)
    @ResponseBody
    public Response findRolePermission(HttpServletRequest request, @RequestParam("roleId") Long roleId, @RequestParam("projectId") Long projectId) {
        return new Response<>(rolePermissionService.find(roleId, projectId), true);
    }

    @RequestMapping(value = "/rolepermission/edit", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.ROLE_PERMISSION, operation = Operation.CREATE)
    @ResponseBody
    public Response authorize(HttpServletRequest request, @RequestBody @Valid EditRolePermissionRequest editRolePermissionRequest,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isEditSuccess = rolePermissionService.authorize(editRolePermissionRequest, ucAccountView.getEnterpriseId());
        if (isEditSuccess) {
            return Response.buildResponse(true, "Edit success");
        } else {
            return Response.buildResponse(false, "Edit failed");
        }
    }
}
