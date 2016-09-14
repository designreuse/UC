package com.yealink.uc.web.modules.login.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yealink.uc.common.modules.common.NodeType;
import com.yealink.uc.common.modules.security.menu.entity.Menu;
import com.yealink.uc.common.modules.security.menu.vo.MenuResourceTree;
import com.yealink.uc.common.modules.security.menu.vo.MenuResourceTreeNode;
import com.yealink.uc.common.modules.security.module.entity.Module;
import com.yealink.uc.common.modules.security.operation.entity.Operation;
import com.yealink.uc.common.modules.security.resource.entity.Resource;
import com.yealink.uc.common.modules.security.resourceoperation.ResourceOperation;
import com.yealink.uc.common.modules.security.rolepermission.entity.RolePermission;
import com.yealink.uc.platform.annotations.LoginRequired;
import com.yealink.uc.platform.exception.BusinessHandleException;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.DataConverter;
import com.yealink.uc.platform.utils.MessageUtil;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.web.permission.vo.ResourceOperationView;
import com.yealink.uc.platform.web.permission.vo.ResourceOperationViews;
import com.yealink.uc.service.modules.account.vo.UCAccountView;
import com.yealink.uc.service.modules.login.api.LoginRESTService;
import com.yealink.uc.service.modules.login.request.LoginRESTRequest;
import com.yealink.uc.service.modules.login.response.LoginRESTResponse;
import com.yealink.uc.web.modules.MenuTreeBuilder;
import com.yealink.uc.web.modules.common.annotation.CSRFTokenRequired;
import com.yealink.uc.web.modules.common.annotation.CaptchaRequired;
import com.yealink.uc.web.modules.common.constant.SessionConstant;
import com.yealink.uc.web.modules.enterprise.service.EnterpriseService;
import com.yealink.uc.web.modules.login.request.LoginRequest;
import com.yealink.uc.web.modules.login.service.CSRFTokenService;
import com.yealink.uc.web.modules.login.service.LoginSessionService;
import com.yealink.uc.web.modules.security.menu.service.MenuService;
import com.yealink.uc.web.modules.security.module.service.ModuleService;
import com.yealink.uc.web.modules.security.operation.service.OperationService;
import com.yealink.uc.web.modules.security.project.service.ProjectService;
import com.yealink.uc.web.modules.security.resource.service.ResourceService;
import com.yealink.uc.web.modules.security.resourceoperation.service.ResourceOperationService;
import com.yealink.uc.web.modules.security.role.service.RoleService;
import com.yealink.uc.web.modules.security.rolepermission.service.RolePermissionService;
import com.yealink.uc.web.modules.staffrolemapping.service.StaffRoleMappingService;
import com.yealink.uc.web.modules.system.service.SystemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author ChNan
 */
@Controller
public class LoginController {
    @Autowired
    CSRFTokenService cSRFTokenService;
    @Autowired
    LoginSessionService loginSessionService;
    @Autowired
    SystemService systemService;
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    LoginRESTService loginRESTService;
    @Autowired
    MenuService menuService;
    @Autowired
    ProjectService projectService;
    @Autowired
    ModuleService moduleService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    OperationService operationService;
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    RoleService roleService;
    @Autowired
    StaffRoleMappingService staffRoleMappingService;
    @Autowired
    ResourceOperationService resourceOperationService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String forwardToLogin(HttpServletRequest request) throws MethodArgumentNotValidException {
        Locale locale = systemService.getSessionLocale(request);
        systemService.setSessionLocale(request, locale);
        loginSessionService.setCopyright(request);
        loginSessionService.setLoginErrorCount(request);
        cSRFTokenService.setCsrfToken(request, null);
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @CSRFTokenRequired
    @CaptchaRequired(allowErrorCount = 1)
    public Response login(HttpServletRequest request, @Valid LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.buildResponse(false, MessageUtil.createBindingErrorMessage(bindingResult));
        }
        try {
            LoginRESTRequest loginRESTRequest = DataConverter.copy(new LoginRESTRequest(), loginRequest);
            LoginRESTResponse loginResponse = loginRESTService.login(loginRESTRequest);
            loginSessionService.clearCookie(request);
            loginSessionService.clearSession(request);
            loginSessionService.setCopyright(request);
            Locale locale = systemService.getSessionLocale(request);
            systemService.setSessionLocale(request, locale);
            cSRFTokenService.setCsrfToken(request, loginRequest.getUsername());
            SessionUtil.set(request, SessionConstant.CURRENT_SESSION_ACCOUNT, loginResponse.getUcAccount());

            SessionUtil.set(request, SessionConstant.CURRENT_ENTERPRISE, enterpriseService.getEnterprise(loginResponse.getUcAccount().getEnterpriseId()));
            SessionUtil.remove(request, SessionConstant.CURRENT_LOGIN_ERROR_COUNT);
            return Response.buildResponseURL(true, "/org/index");

            //todo alpha version does not need.
//            List<Long> roleIds = staffRoleMappingService.findRoleIds(loginResponse.getLoginAccount().getStaffId());
//
//            List<RolePermission> rolePermissionList = rolePermissionService.findByRoleIdList(roleIds);//todo
//            List<RolePermission> resourcePermissions = rolePermissionService.extractPermissions(rolePermissionList, PermissionType.RESOURCE_OPERATION.getCode());
//            List<RolePermission> menuPermissionList = rolePermissionService.extractPermissions(rolePermissionList, PermissionType.MENU.getCode());

//            List<Long> menuIds = rolePermissionService.extractMenuIds(menuPermissionList);
//            MenuTreeBuilder menuTreeBuilder = createMenuTreeBuilder(menuIds);
//
//            ResourceOperationViews resourceOperationViews = getPermissions(resourcePermissions);
//            SessionUtil.set(request, SessionConstant.CURRENT_MENU, menuTreeBuilder.createMenuTreeNode());
//            SessionUtil.set(request, SessionConstant.CURRENT_PERMISSION, JSONConverter.toJSON(resourceOperationViews));
        } catch (BusinessHandleException e) {
            SessionUtil.set(request, SessionConstant.CURRENT_LOGIN_ERROR_COUNT, loginRESTService.loginErrorCount(loginRequest.getUsername()));
            return Response.buildResponse(false, e.getMessage());
        }
    }

    private ResourceOperationViews getPermissions(List<RolePermission> resourcePermissions) {
        if (CollectionUtils.isEmpty(resourcePermissions)) throw new BusinessHandleException("Resource permission error!");
        List<ResourceOperationView> resourceOperationViewList = new ArrayList<>();
        List<Long> resourceOperationIds = Lists.transform(resourcePermissions, new Function<RolePermission, Long>() {
            @Override
            public Long apply(final RolePermission input) {
                return input.getResourceOperationId();
            }
        });
        List<ResourceOperation> resourceOperations = resourceOperationService.find(resourceOperationIds);
        for (ResourceOperation resourceOperation : resourceOperations) { // todo change to find by
            Operation operation = operationService.get(resourceOperation.getOperationId());
            Resource resource = resourceService.get(resourceOperation.getResourceId());
            ResourceOperationView resourceOperationView = new ResourceOperationView(resource.getCode(), operation.getCode());
            resourceOperationViewList.add(resourceOperationView);
        }
        ResourceOperationViews resourceOperationViews = new ResourceOperationViews();
        resourceOperationViews.setResourceOperations(resourceOperationViewList);
        return resourceOperationViews;
    }

    private MenuTreeBuilder createMenuTreeBuilder(List<Long> menuIds) {
        List<Menu> menuList = menuService.findByIds(menuIds);

        List<Long> moduleIds = Lists.transform(menuList, new Function<Menu, Long>() {
            @Override
            public Long apply(final Menu input) {
                return input.getModuleId();
            }
        });
        List<Module> moduleList = moduleService.findByIds(moduleIds);
        Map<Long, MenuResourceTree> menuTreeMap = new HashMap<>();
        for (Module module : moduleList) {
            MenuResourceTree menuTree;
            if (module.getParentId() == 0) {
                menuTree = new MenuResourceTree(new MenuResourceTreeNode(module.get_id(), module.getName(), module.getParentId(), "#", NodeType.MODULE.getType(), NodeType.PROJECT.getType()));
            } else { // the module is child of other module
                menuTree = new MenuResourceTree(new MenuResourceTreeNode(module.get_id(), module.getName(), module.getParentId(), "#", NodeType.MODULE.getType(), NodeType.MODULE.getType()));
            }
            menuTreeMap.put(module.get_id(), menuTree);
        }


        for (Menu menu : menuList) {
            MenuResourceTree menuTree = menuTreeMap.get(menu.getModuleId());
            if (menuTree != null) {
                menuTree.getChildNodeList().add(new MenuResourceTreeNode(menu.get_id(), menu.getName(), menu.getModuleId(), menu.getUrl(), NodeType.MENU.getType(), NodeType.MODULE.getType()));
            }
        }
        return new MenuTreeBuilder(menuTreeMap);
    }

    @LoginRequired
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        UCAccountView ucAccountView = SessionUtil.get(request, SessionConstant.CURRENT_SESSION_ACCOUNT);
        request.setAttribute("account", ucAccountView.getName());
        loginSessionService.clearSession(request);
        loginSessionService.clearCookie(request);
        return new ModelAndView(new RedirectView("/"));
    }

}
