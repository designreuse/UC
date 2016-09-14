package com.yealink.uc.platform.web.permission.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yealink.uc.platform.web.permission.annotations.PermissionRequired;
import com.yealink.uc.platform.exception.PermissionForbiddenException;
import com.yealink.uc.platform.utils.ClassUtil;
import com.yealink.uc.platform.utils.JSONConverter;
import com.yealink.uc.platform.utils.SessionUtil;
import com.yealink.uc.platform.web.permission.vo.Operation;
import com.yealink.uc.platform.web.permission.vo.Resource;
import com.yealink.uc.platform.web.permission.vo.ResourceOperationView;
import com.yealink.uc.platform.web.permission.vo.ResourceOperationViews;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ChNan
 */
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        PermissionRequired permissionRequired = ClassUtil.findAnnotation(((HandlerMethod) handler), PermissionRequired.class);
        if (permissionRequired == null) return true;
        Resource resource = permissionRequired.resource();
        Operation operation = permissionRequired.operation();
        if (!hasPermission(request, resource, operation)) {
            throw new PermissionForbiddenException("system.permission.forbidden", operation.getCode(), resource.getCode());
        }
        return true;
    }

    private boolean hasPermission(HttpServletRequest request, Resource resource, Operation operation) {
        ResourceOperationViews resourceOperationViews = JSONConverter.fromJSON(ResourceOperationViews.class, (String) SessionUtil.get(request, "permission"));
        for (ResourceOperationView resourceOperation : resourceOperationViews.getResourceOperations()) {
            if (resource.getCode().equals(resourceOperation.getResource()) && operation.getCode().equals(resourceOperation.getOperation())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {

    }
}
