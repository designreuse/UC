<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="account.reset.password.title.name"/></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/cloud.ico"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/style.min.css">
</head>
<body class="password-reset-body">
<%@ include file="../common/page_top.jsp" %>
<!-- <div class="col-sm-offset-1 col-sm-10"><hr class="top-seperator"></div> -->

<div class="password-reset-content">
    <div class="password-reset-title">
        <spring:message code="account.reset.password.title.name"/>
    </div>

    <div class="form-horizontal password-reset-form">
        <div class="form-group" id="divNewPassword">
            <label class="control-label">
                <spring:message code="account.modify.password.label.password.new"/>
            </label>
            <input type="password" class="form-control  pwd-reset-input" id="newPassword"
                   maxlength="32" autocomplete="off"
                   placeholder="<spring:message code="account.modify.password.input.prompt.password.new" />"
                   title="<spring:message code="account.modify.password.input.prompt.password.new" />">
            <label id="errorNewPassword" class="control-label password-reset-error-label"></label>
        </div>

        <div class="form-group" id="divRepeatPassword">
            <label class="control-label">
                <spring:message code="account.modify.password.label.password.repeat"/>
            </label>
            <input type="password" class="form-control pwd-reset-input" id="repeatPassword"
                   maxlength="32" autocomplete="off"
                   title="<spring:message code="account.modify.password.input.prompt.password.repeat" />">
            <label id="errorRepeatPassword" class="control-label password-reset-error-label"></label>
        </div>

        <div class="form-footer-div">
            <button id="submitResetPwdBtn" class="btn btn-success btn-lg btn-block pull-left">
                <spring:message code="system.common.button.ok"/>
            </button>
            <a href="${pageContext.request.contextPath}/" class="btn btn-default btn-lg pull-right">
                <spring:message code="account.forgot.pwd.button.to.login"/>
            </a>
        </div>
    </div>
    <input type="hidden" id="basePath" value="${pageContext.request.contextPath}">
    <input type="hidden" id="activeCode" value="${activeCode}">
    <input type="hidden" id="csrfToken" value="${sessionScope.csrfToken}">
</div>
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/3rdLibrary/ie-compatible/ie.compatible.min.js"></script>
<![endif]-->
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/account/account.js"></script>
<script type="text/javascript">
    $(function () {
        readyResetPwdAfterForgot();
    });
</script>
</html>