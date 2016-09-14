package com.yealink.uc.platform.exception.resolver;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yealink.uc.platform.exception.SessionTimeoutException;
import com.yealink.uc.platform.response.Response;
import com.yealink.uc.platform.utils.JSONConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.RedirectView;

public class ExceptionResolver extends SimpleMappingExceptionResolver {
    Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
                                              HttpServletResponse response, Object handler, Exception ex) {
        logger.error("Catch exception:", ex);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ResponseBody body = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (body == null) {
            return doJSPResponseException(request, response, ex, handlerMethod);
        } else {
            return doJsonResponseException(response, ex);
        }
    }

    private ModelAndView doJSPResponseException(HttpServletRequest request, HttpServletResponse response, Exception ex, HandlerMethod handlerMethod) {
        ModelAndView modelAndView = super.doResolveException(request, response, handlerMethod, ex);
        if (ex instanceof SessionTimeoutException) {
            request.getSession().invalidate();
            modelAndView.setView(new RedirectView("/"));
        } else {
            modelAndView.addObject("errorMessage", ex.getMessage());
        }
        return modelAndView;
    }

    private ModelAndView doJsonResponseException(HttpServletResponse response, Exception ex) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            ex.printStackTrace();
            PrintWriter writer = response.getWriter();
            writeException(ex, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeException(final Exception ex, final PrintWriter writer) {
        if (ex instanceof NullPointerException) {
            writer.write(JSONConverter.toJSON(Response.buildResponse(false, "Internal Server Error", "/")));
        } else {
            writer.write(JSONConverter.toJSON(Response.buildResponse(false, ex.getMessage(), "/")));
        }

    }
}  