package com.yealink.uc.web.modules.security.menu.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.yealink.uc.common.modules.security.menu.entity.Menu;
import com.yealink.uc.common.modules.security.menu.vo.MenuResourceTreeNode;
import com.yealink.uc.common.modules.security.module.entity.Module;
import com.yealink.uc.platform.web.permission.annotations.PermissionRequired;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.web.permission.vo.Operation;
import com.yealink.uc.platform.web.permission.vo.Resource;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.web.modules.security.menu.request.CreateMenuRequest;
import com.yealink.uc.web.modules.security.menu.request.EditMenuRequest;
import com.yealink.uc.web.modules.security.menu.service.MenuService;
import com.yealink.uc.web.modules.security.module.service.ModuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
public class MenuController {
    @Autowired
    MenuService menuService;
    @Autowired
    ModuleService moduleService;
    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/menu/index", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.MENU, operation = Operation.READ)
    public String index(HttpServletRequest request, Model model, @RequestParam("projectId") Long projectId) throws IOException {
        model.addAttribute("projectId", projectId);
        return "menu/menu_index";
    }

    @RequestMapping(value = "/menu/forwardToCreate", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.MENU, operation = Operation.READ)
    public String forwardToCreateMenu(HttpServletRequest request, Model model) {
        return "menu/menu_create";
    }

    @RequestMapping(value = "/menu/showTreeNodes", method = RequestMethod.GET)
    @ResponseBody
    @PermissionRequired(resource = Resource.MENU, operation = Operation.READ)
    public Response showTreeNodes(HttpServletRequest request, @RequestParam("projectId") Long projectId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        List<MenuResourceTreeNode> nodes = menuService.showTreeNodes(projectId, ucAccountView.getEnterpriseId());
        return new Response<>(nodes, true);
    }

    @RequestMapping(value = "/menu/forwardToEdit/{menuId}", method = RequestMethod.GET)
    @PermissionRequired(resource = Resource.MENU, operation = Operation.READ)
    public String forwardToEditMenu(HttpServletRequest request, Model model, @PathVariable Long menuId) {
        Menu menu = menuService.get(menuId);
        Module module = moduleService.get(menu.getModuleId());
        model.addAttribute("menu", menu);
        model.addAttribute("module", module);
        return "menu/menu_edit";
    }


    @RequestMapping(value = "/menu/create", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.MENU, operation = Operation.CREATE)
    public Response create(HttpServletRequest request,
                                  @Valid CreateMenuRequest createMenuRequest,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isCreateSuccess = menuService.create(createMenuRequest, ucAccountView.getEnterpriseId());
        if (isCreateSuccess) {
            return Response.buildResponse(true, "Create success");
        } else {
            return Response.buildResponse(false, "Create failed");
        }
    }

    @RequestMapping(value = "/menu/edit", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.MENU, operation = Operation.UPDATE)
    public Response edit(HttpServletRequest request, @Valid EditMenuRequest editMenuRequest,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isEditSuccess = menuService.edit(editMenuRequest, ucAccountView.getEnterpriseId());
        if (isEditSuccess) {
            return Response.buildResponse(true, "Edit success");
        } else {
            return Response.buildResponse(false, "Edit failed");
        }
    }

    @RequestMapping(value = "/menu/delete/{menuId}", method = RequestMethod.POST)
    @ResponseBody
    @PermissionRequired(resource = Resource.MENU, operation = Operation.DELETE)
    public Response delete(HttpServletRequest request, @PathVariable("menuId") Long menuId) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        boolean isDeleteSuccess = menuService.delete(menuId, ucAccountView.getEnterpriseId());
        if (isDeleteSuccess) {
            return Response.buildResponse(true, "Delete success");
        } else {
            return Response.buildResponse(false, "Delete failed");
        }
    }
}
