<%--
	通用异常显示页面。由异常处理框架捕获到的异常，统一跳转至该页面进行提示
	author:yjz
	since:2014-10-8
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<html>
	<body>
		<table height="100%" bgcolor="" cellpadding="0" cellspacing="0" align="center" border="0" bordercolor="#000000">
			<tr>
				<td valign="middle">
					<img src="<%=path%>/images/emblem-important.png"/>
				</td>
				<td valign="middle">
					<font size=5>
						${errorMessage}
					</font>
				</td>
			</tr>
		</table>
	<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
	</body>
</html>