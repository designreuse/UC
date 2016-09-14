<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="yealink" uri="http://www.yealinkuc.com/tag" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0026)http://localhost/org/index -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.2.0/css/bootstrap.min.css">

    <link rel="stylesheet" href=${pageContext.request.contextPath}/3rdLibrary/ztree/zTreeStyle/zTreeStyle.css>
    <%-- 页面统一先加载CSS文件，style.css和bootstrap.css可不用加载，为了提高页面的响应速度，JS文件可以放在文件末尾加载 --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/3rdLibrary/font-awesome/css/font-awesome.min.css">
    <%-- 多选下拉列表样式 --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/3rdLibrary/chosen/chosen.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/css/global.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/template-debug.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>

    <decorator:head />
    <style type="text/css">

    </style>
    <%@ include file="../WEB-INF/jsp/common/select_language.jsp" %>
    <%@include file="../WEB-INF/jsp/common/modal.jsp" %>
</head>
<body>

<div id="container">
    <%-- 本项目的context --%>
    <input type="hidden" id="projectContext" value="${pageContext.request.contextPath}">
    <input type="hidden" id="projectURL" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}">

    <input type="hidden" id="browserLang" value="${sessionScope.language }">

    <%-- 根据java安全编程规范3.3.1完整性检查，添加csrfToken --%>
    <input type="hidden" id="csrfToken" value="${sessionScope.csrfToken}">

    <div id="header">
        <span class="white-color">厦门亿联网络技术股份有限公司</span>
    </div>
    <div id="nav">
        <div class="server-name"><span>Yealink | UC Server</span></div>

        <div class="btn-group account_management">
            <button type="button" class="btn btn-sm" data-toggle="dropdown">
                <span class="white-color bold-font" id="account_name">
                     <c:choose>
                         <c:when test="${fn:length(sessionScope.sessionAccount.name) >= 15 }">
                             ${fn:substring(sessionScope.sessionAccount.name, 0, 15) }...
                         </c:when>
                         <c:otherwise>
                             ${sessionScope.sessionAccount.name}
                         </c:otherwise>
                     </c:choose>
                </span><span class="caret white-color"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="javascript:void(0);" id="accountInfo">账号设置</a></li>
                <li><a href="${pageContext.request.contextPath}/logout">退出</a></li>
            </ul>
        </div>


        <div class="admin_avatar">
            <img src="${pageContext.request.contextPath}/images/nav/admin-white.png"/>
        </div>
        <div class="nav_message">
            <img src="${pageContext.request.contextPath}/images/nav/message.png"/>
        </div>
        <div class="nav_search_box">
            <form class="searchbox" action="${pageContext.request.contextPath}/org/index" method="get">
                <input type="search" name="search" class="searchbox-input" onkeyup="buttonUp();" required>
                <span class="searchbox-icon"> <img src="${pageContext.request.contextPath}/images/nav/search-white.png"/></span>
            </form>
        </div>
    </div>
    <div id="content">
        <div id="content_left">
            <ul class="nav navbar-nav">
                <li><a class="no-underline" href="${pageContext.request.contextPath}/org/index">
                    <img class="sidebar-img" src="${pageContext.request.contextPath}/images/menu/org.png"/><br>
                    <span>组织管理</span>
                </a>
                </li>
                <li class="active">
                    <a class="no-underline" href="${pageContext.request.contextPath}/org/index">
                        <img class="sidebar-img" src="${pageContext.request.contextPath}/images/menu/meeting.png"/><br>
                        <span>会议室</span>
                    </a>
                </li>
                <li>
                    <a class="no-underline" href="${pageContext.request.contextPath}/org/index">
                        <img class="sidebar-img" src="${pageContext.request.contextPath}/images/menu/call.png"/>
                        <br>
                        <span>呼叫业务</span>
                    </a>
                </li>
                <li>
                    <a class="no-underline" href="${pageContext.request.contextPath}/org/index">
                        <img class="sidebar-img" src="${pageContext.request.contextPath}/images/menu/device.png"/>
                        <br><span>设备管理</span>
                    </a>
                </li>
                <li id="sessionFirstNav">
                    <a class="no-underline" href="${pageContext.request.contextPath}/ofweb/session/list">
                        <img class="sidebar-img" src="${pageContext.request.contextPath}/images/menu/session.png"/>
                        <br><span>会话管理</span>
                    </a>
                </li>
                <li id="systemManageFirstNav">
                    <a class="no-underline" href="${pageContext.request.contextPath}/ofweb/warning/warningSetting">
                        <img class="sidebar-img" src="${pageContext.request.contextPath}/images/menu/setting.png"/>
                        <br><span>系统管理</span>
                    </a>
                </li>
            </ul>
        </div>
        <div id="content_middle-right">
            <decorator:body/>
        </div>
    </div>
    <div class="clear"><!--如何你上面用到float,下面布局开始前最好清除一下。--></div>
    <%--<div>--%>
    <%--<nav id="footer" class="navbar navbar-copyright navbar-fixed-bottom">--%>
    <%--<div class="container copyright">--%>
    <%--Copyright©2016 厦门亿联网络技术股份有限公司 All rights reserved--%>
    <%--</div>--%>
    <%--</nav>--%>
    <%--</div>--%>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/global.js"></script>

</body>


</html>