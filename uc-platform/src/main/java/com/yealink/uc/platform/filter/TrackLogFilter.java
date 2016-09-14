package com.yealink.uc.platform.filter;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yealink.uc.platform.log.track.TrackLogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class TrackLogFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(TrackLogFilter.class);
    private TrackLogger trackLogger = TrackLogger.get();

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        try {
            trackLogger.initialize();
            logger.debug("================== start  request processing ==================");
            logRequest(request, trackLogger);
            filterChain.doFilter(request, response);
        } finally {
            logResponse(response);
            logger.debug("================== finish request processing ==================");
            trackLogger.cleanUp();
        }
    }

    private void logRequest(HttpServletRequest request, TrackLogger trackLogger) throws IOException {
        logger.debug("request_url={}", request.getRequestURL());
        trackLogger.logContext("uri", request.getRequestURI());
        logger.debug("server_port={}", request.getServerPort());
        logger.debug("context_path={}", request.getContextPath());
        trackLogger.logContext("method", request.getMethod());
        logger.debug("local_port={}", request.getLocalPort());
        logHeaders(request);
        logParameters(request);
        logger.debug("remote_address={}", request.getRemoteAddr());
        trackLogger.logContext("ip", request.getRemoteAddr());
    }

    private void logHeaders(HttpServletRequest request) {
        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = (String) headers.nextElement();
            logger.debug("[header] {}={}", headerName, request.getHeader(headerName));
        }
    }

    void logParameters(HttpServletRequest request) {
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            logger.debug("[param] {}={}", paramName, request.getParameter(paramName));
        }
    }

    private void logResponse(HttpServletResponse response) {
        trackLogger.logContext("status", String.valueOf(response.getStatus()));
        logHeaders(response);
    }

    private void logHeaders(HttpServletResponse response) {
        for (String name : response.getHeaderNames()) {
            logger.debug("[header] {}={}", name, response.getHeader(name));
        }
    }
}
