<%--
	404错误显示页面。系统出现404错误时由该页面进行比较友好的提示
	404错误一般是找不到访问页面的错误，异常框架捕获不到，因此需要另外在web.xml配置404错误对应的提示页面
	author:yjz
	since:2014-9-30
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
	<title><spring:message code="system.common.title.vcCloud" text="system.common.title.vcCloud"/></title>
</head>
	<body>
		<table height="100%" bgcolor="" cellpadding="0" cellspacing="0" align="center" border="0" bordercolor="#000000">
			<tr>	
				<td valign="middle">
					<img src="<%=path%>/images/emblem-important.png"/>
				</td>	
				<td valign="middle">
					<font size=5>
						<spring:message code="system.common.error.page.404" text="system.common.error.page.404"/>
					</font>
				</td>
			</tr>
		</table>
		<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
	</body>
</html>	