<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="decorator"  uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="yealink" uri="http://www.yealinkuc.com/tag"%>
<!doctype html>
<html>
    <head>
        <%@ include file="../WEB-INF/jsp/common/meta.jsp"%>
        <title>
            <decorator:title/>
        </title>
        <%@ include file="../WEB-INF/jsp/common/includes.jsp"%>
        <!--[if lt IE 9]>
        <script src="${pageContext.request.contextPath}/3rdLibrary/ie-compatible/html5shiv.js"></script>
        <script src="${pageContext.request.contextPath}/3rdLibrary/ie-compatible/respond.min.js"></script>
        <![endif]-->
        <decorator:head />
    </head>
    <body>
        <decorator:body />
    </body>
</html>
