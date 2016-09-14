<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false"%>

<%
	String language=(String)session.getAttribute("language");
%>

<base href="${pageContext.request.contextPath}">

<div class="navbar navbar-language navbar-fixed-top" role="navigation">
	<%-- 系统的标题块 --%>
	<div class="navbar-header col-sm-offset-1">
		<a href="${pageContext.request.contextPath}">
			<img class="navbar-logo" alt="Yealink Cloud" src="${pageContext.request.contextPath}/images/logo.png">
		</a>
	</div>
	<div class="navbar-right navbar-language-select-login">
		<form class="navbar-form form-inline">
			<div class="form-group login-group-about">
         		<a class="login-a-about" href="
         		<c:choose>
					<c:when test="${fn:containsIgnoreCase(sessionScope.language,'zh')}">
					http://www.yealink.cn
					</c:when>
					<c:otherwise>
					http://www.yealink.com
					</c:otherwise>
				</c:choose>">
         			<spring:message code="login.navigation.about.yealink" text="login.navigation.about.yealink"/>
         		</a>
         		<hr class="login-about-separator">
         		<a class="login-a-about" href="
         		<c:choose>
					<c:when test="${fn:containsIgnoreCase(sessionScope.language,'zh')}">
					http://www.yealink.cn/product_list.aspx?ProductsCateID=363&parentcateid=363&cateid=363&BaseInfoCateId=363&Cate_Id=363&index=1
					</c:when>
					<c:otherwise>
					http://www.yealink.com/product_list.aspx?ProductsCateID=363&parentcateid=363&cateid=363&BaseInfoCateId=363&Cate_Id=363&index=1
					</c:otherwise>
				</c:choose>">
         			<spring:message code="login.navigation.about.vc" text="login.navigation.about.vc"/>
         		</a>
			</div>
			
			<div class="form-group">
				<select id="language" class="form-control navbar-language-select">
					<option value="en" <c:if test="${fn:containsIgnoreCase(sessionScope.language,'en')}">selected </c:if> >English</option>
					<option value="zh-CN"  <c:if test="${fn:containsIgnoreCase(sessionScope.language,'zh')}">selected </c:if> >中文</option>
				</select>
			</div>
      	</form> 
  	</div>
</div>

<%@ include file="select_language.jsp"%>
