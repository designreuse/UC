<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
    <title>
    	<spring:message code="account.forgot.pwd.title.name" />
	</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/cloud.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/style.min.css">
</head>
<body class="forgot-pwd-body">
<%@ include file="../common/page_top.jsp" %>

<div class="forgot-form-div">
	<div class="col-sm-offset-1 col-sm-10">
		<div class="forgot-pwd-welcome ">
			<spring:message code="account.forgot.pwd.title.name"/>
			<hr class="forgot-pwd-seperate-line">
		</div>

		<div class="description">
			<spring:message code="account.forgot.pwd.prompt.text" />
		</div>

		<div class="forgot-form">
			<form class="form-horizontal" role="form" autocomplete="off">
				<div class="form-group custom-form-group" id="divEmail">
					<div class="col-sm-12">
						<input class="form-control input-no-radius" id="email" name="email" maxlength="64"
								title="<spring:message code="account.forgot.pwd.input.prompt.mail" />"
								placeholder="<spring:message code="account.forgot.pwd.input.prompt.mail" />"/>
					</div>
				</div>
				<label id="errorEmail" class="custom-tip-font-size custom-has-error">&nbsp;</label>
		
				<div class="form-group custom-form-group" id="divCaptcha">
					<div class="col-sm-12">
						<span class="block input-icon input-img-right">
							<input class="input-no-radius form-control"
								id="captcha" maxlength="4" autocomplete="off"
								placeholder="<spring:message code="login.input.prompt.captcha" />"
								title="<spring:message code="login.input.prompt.captcha"/>"/>
							<img alt="<spring:message code="login.alt.captcha"/>"  src="${pageContext.request.contextPath}/images/loading.gif"
							id="img-captcha" align="middle" onclick="reloadCaptcha();" 
							title="<spring:message code="login.label.captcha.change"/>" class="login-captcha-height">
						</span>
					</div>
				</div>
				<label id="errorCaptcha" class="custom-tip-font-size custom-has-error">&nbsp;</label>
				
				<div class="form-group forgot-pwd-footer-div">
					<div class="forgot-pwd-col">
						<div class="col-sm-6">
				   			<button id="forgotSub" class="btn btn-success btn-lg btn-block btn-lg-adjusted">
				   				<spring:message code="system.common.button.ok" />
				   			</button>
			   			</div>
						<div class="col-sm-6">
							<a href="${pageContext.request.contextPath}/" class="btn btn-default btn-lg btn-block">
								<spring:message code="account.forgot.pwd.button.to.login" />
							</a>
						</div>
		   			</div>
		     	</div>
			</form>
		<input type="hidden" id="projectContext" value="${pageContext.request.contextPath}">
		<input type="hidden" id="csrfToken" value="<c:out value="${sessionScope.csrfToken }"></c:out>">
		</div>
	</div>
</div>
<!--[if lt IE 9]>
	<script src="${pageContext.request.contextPath}/3rdLibrary/ie-compatible/ie.compatible.min.js"></script>
<![endif]-->
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/account/account.js"></script>
<script type="text/javascript">
$(function() {
	readyForgotPwd();
});
</script>
</body>
</html>