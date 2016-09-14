<%@ page isELIgnored="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String path = request.getContextPath();
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<style>
	.wrap {
		width: 100%;
		height:100%;
  		display:table;
  		text-align: center;
	}
	
	.content {
		vertical-align:middle;
		display:table-cell;
  		width:100%;
	}
</style>
<html>
<body>
	<div class="wrap">
		<div class="content">
			<table>
				<tr>
					<td style="vertical-align: middle;"><img
						src="<%=path%>/static/images/emblem-important.png" /></td>
					<td style="vertical-align: middle;"><font size=5><spring:message code="${message }" text="${message }"></spring:message></font></td>
					<td align="left"><a href="${redirectUrl }" target="pageFrame"><font size=5>是</font></a></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
