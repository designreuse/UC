<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
    <title>
        文件共享查询---------该页面暂弃用
    </title>
    <!-- css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/style.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap-table/css/bootstrap-table.css" />
    <!-- js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap-table/js/bootstrap-table.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap-table/js/bootstrap-table-zh-CN.js"></script>
</head>
<body>
<ul id="fileShareQueryTab" class="nav nav-tabs">
    <li class="active">
        <a href="#avatar" data-toggle="tab">
            头像查询
        </a>
    </li>
</ul>
<div id="fileShareQueryTabContent" class="tab-content">
    <div class="tab-pane fade in active" id="avatar">
        <iframe width="100%" height="100%" id="avatarFrame" name="avatarFrame" src="/fileshare/query/avatarQuery"></iframe>
    </div>
</div>
<script>
    $(function () {

    });
</script>
</body>
</html>