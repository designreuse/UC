package com.yealink.uc.web.modules.security.rolelevel.controller;

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
import com.yealink.uc.web.modules.security.rolelevel.request.CreateRoleLevelRequest;
import com.yealink.uc.web.modules.security.rolelevel.request.EditRoleLevelRequest;
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
public class RoleLevelController {
    @Autowired
    RoleLevelService roleLevelService;

    @RequestMapping(value = "/rolelevel/index", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.ROLE_LEVEL, operation = Operation.READ)
    public String orgIndex(HttpServletRequest request, Model model) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        model.addAttribute("roleLevelList", roleLevelService.findAllByEnterprise(ucAccountView.getEnterpriseId()));
        return "rolelevel/rolelevel_index";
    }

    @RequestMapping(value = "/rolelevel/forwardToCreate", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.ROLE_LEVEL, operation = Operation.READ)
    public String forwardToCreateRoleLevel(HttpServletRequest request, Model model) {
        return "rolelevel/rolelevel_create";
    }

    @RequestMapping(value = "/rolelevel/forwardToEdit/{roleLevelId}", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.ROLE_LEVEL, operation = Operation.READ)
    public String forwardToEditRoleLevel(HttpServletRequest request, Model model, @PathVariable("roleLevelId") Long roleLevelId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        model.addAttribute("roleLevel", roleLevelService.get(roleLevelId, ucAccountView.getEnterpriseId()));
        return "rolelevel/rolelevel_edit";
    }

    @RequestMapping(value = "/rolelevel/create", method = RequestMethod.POST)
    @PermissionRequired(resource = Resource.ROLE_LEVEL, operation = Operation.CREATE)
    @ResponseBody
    public Response createRoleLevel(HttpServletRequest request,
                                  @Valid CreateRoleLevelRequest createRoleLevelRequest,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isCreateSuccess = roleLevelService.create(createRoleLevelRequest, ucAccountView.getEnterpriseId());
        if (isCreateSuccess) {
            return Response.buildResponse(true, "Create success");
        } else {
            return Response.buildResponse(false, "Create failed");
        }
    }

    @RequestMapping(value = "/rolelevel/edit", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.ROLE_LEVEL, operation = Operation.UPDATE)
    public Response editRoleLevel(HttpServletRequest request, @Valid EditRoleLevelRequest editRoleLevelRequest,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isEditSuccess = roleLevelService.edit(editRoleLevelRequest, ucAccountView.getEnterpriseId());
        if (isEditSuccess) {
            return Response.buildResponse(true, "Edit success");
        } else {
            return Response.buildResponse(false, "Edit failed");
        }
    }

    @RequestMapping(value = "/rolelevel/delete/{roleLevelId}", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.ROLE_LEVEL, operation = Operation.DELETE)
    public Response deleteRoleLevel(HttpServletRequest request, @PathVariable("roleLevelId") Long roleLevelId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isDeleteSuccess = roleLevelService.delete(roleLevelId, ucAccountView.getEnterpriseId());
        if (isDeleteSuccess) {
            return Response.buildResponse(true, "Delete success");
        } else {
            return Response.buildResponse(false, "Delete failed");
        }
    }
}
