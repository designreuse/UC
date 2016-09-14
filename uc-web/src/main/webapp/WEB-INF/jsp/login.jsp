<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="./common/meta.jsp" %>
    <title><spring:message code="login.title.name"/></title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/cloud.ico"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/style.min.css">
</head>
<body class="login-body">
<div class="wrap">
    <%@ include file="common/page_top.jsp" %>
    <%-- for absolutely page layout, don't cover the login-content DIV--%>
    <div class="login-blank-div"></div>
    <div class="login-content">
        <div class="login-form">
            <%-- 根据java安全编程规范3.3.1完整性检查，添加csrfToken --%>
            <input type="hidden" id="csrfToken" value="${sessionScope.csrfToken }">

            <div id="div-login-welcome">
                <spring:message code="login.label.welcome"/>
            </div>

            <%-- form tag is used for username auto complete --%>
            <form onsubmit="return false;">
                <div class="form-horizontal">
                    <div class="has-error login-error-group">
                        <label id="errorLoginMessage" class="control-label login-error-msg-label"> </label>
                    </div>
                    <div class="form-group" id="divUsername">
                        <div class="col-sm-12">
                            <input class="form-control input-no-radius" id="username" maxlength="128" name="username"
                                   title="<spring:message code="login.input.prompt.username"/>"
                                   placeholder="<spring:message code="login.input.place.username"  text="login.input.place.username"/>"/>
                        </div>
                    </div>
                    <div class="form-group" id="divPassword">
                        <div class="col-sm-12">
                            <input type="password" class="form-control input-no-radius"
                                   id="password" maxlength="32"
                                   title="<spring:message code="login.input.prompt.password"/>"
                                   placeholder="<spring:message code="login.input.place.password"  text="login.input.place.password"/>"
                                   autocomplete="off"/>
                        </div>
                    </div>
                    <div class="form-group" id="divCaptcha" <c:if test="${sessionScope.login_error_count eq 0}">style="display: none" </c:if>>
                        <div class="col-sm-12">
                            <div class="block input-icon input-img-right">
                                <input class="form-control input-no-radius" id="captcha" maxlength="4" autocomplete="off"
                                       title="<spring:message code="login.input.prompt.captcha"/>"
                                       placeholder="<spring:message code="login.input.prompt.captcha"  text="login.input.prompt.captcha"/>"/>

                                <img alt="<spring:message code="login.alt.captcha"/>" src="${pageContext.request.contextPath}/images/loading.gif"
                                     id="img-captcha" align="middle" onclick="reloadCaptcha();"
                                     title="<spring:message code="login.label.captcha.change"/>" class="login-captcha-height">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="checkbox pull-left login-label-remember">
                                <label>
                                    <input type="checkbox" id="rememberMe" name="rememberMe" class="login-input-rememberMe">
                                    <spring:message code="login.label.remembeme"/>
                                </label>
                            </div>
                            <div class="pull-right">
                                <a href="${pageContext.request.contextPath}/account/password/forwardToForgot" class="login-a-forgot-pwd pull-right">
                                    <spring:message code="login.label.forget.password" text="login.label.forget.password"/>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <button id="submitLoginBtn" class="btn btn-success btn-lg btn-block btn-lg-adjusted">
                                <spring:message code="system.common.button.login"/>
                            </button>
                        </div>
                    </div>
                </div>
            </form>
            <input type="hidden" id="basePath" value="${pageContext.request.contextPath}">
        </div>
        <div class="login-copyright">
            ${sessionScope.copyright }
        </div>
    </div>

    <%-- 项目中浏览器版本提示框 --%>
    <div id="browserTips" class="browser-tips">
        <spring:message code="system.common.tooltip.browser.version.low" text="system.common.tooltip.browser.version.low"/>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/login.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/ZeroClipboard/ZeroClipboard.min.js"></script>
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/3rdLibrary/ie-compatible/ie.compatible.min.js"></script>
<![endif]-->
<script type="text/javascript">
    $(function () {
        //prompt ie6 && ie7 version low
        if (IEVersion() == 6 || IEVersion() == 7) {
            $('#browserTips').show();
        }
        readyLogin();
    });

</script>
</body>
</html>