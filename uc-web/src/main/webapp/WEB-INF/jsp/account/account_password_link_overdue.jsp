<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <spring:message code="account.forgot.pwd.title.name" text="account.forgot.pwd.title.name"/>
    </title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/cloud.ico"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/style.min.css">
</head>
<body>
<%@ include file="../common/page_top.jsp" %>
<div class="col-sm-8 col-sm-offset-2">
    <div class="panel-body">
        <form class="form-horizontal" role="form">
            <h1 class="row">${message}</h1>

            <div class="col-sm-offset-3 form-footer-div">
                <div class="col-sm-offset-1 col-sm-2">
                    <a href="${pageContext.request.contextPath}/">
                        <button type="button" id="btnReturnLogin" class=" btn btn-success">
                            <spring:message code="account.forgot.pwd.button.to.login" text="account.forgot.pwd.button.to.login"/>
                        </button>
                    </a>
                </div>
            </div>
        </form>
    </div>
</div>
<input type="hidden" id="projectContext" value="${pageContext.request.contextPath}">
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
</body>
</html>