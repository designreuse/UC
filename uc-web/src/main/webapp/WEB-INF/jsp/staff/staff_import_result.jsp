<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/3rdLibrary/jquery-ui/themes/jquery-ui.min.css">
</head>
<body>
	<div class="modal-header custom-modal-header">
		<button id="closePswBtn" type="button" class="close custom-close" data-dismiss="modal"
				aria-hidden="true">&times;</button>
		<h4 class="modal-title custom-modal-title ">
			导入结果：${success},${message}
		</h4>
	</div>
	<div class="modal-body  custom-confirm-modal-body div-list-table div-list-table-layout" id="confirmModalBody">
		<table class="table fixed-table table-hover table-condensed">
			<thead>
			<tr>
				<th>行号</th>
				<th>姓名</th>
				<th>账号</th>
				<th width="30%">错误信息</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${errorImportStaffList}" var="errorStaff" varStatus="rownum">
				<c:set var="trCount" value="${rownum.count}" />
				<tr class="
					<c:if test="${trCount % 2 == 1}">
						table-striped-tr-odd
					</c:if>
					<c:if test="${trCount % 2 == 0}">
						table-striped-tr-even
					</c:if>">
					<td><label title="${errorStaff.rowNum }"> ${errorStaff.rowNum }</label></td>
					<td>
						<label title="<c:out value="${errorStaff.name }" />" >
							<c:out value="${errorStaff.name}" />
						</label>
					</td>
					<td>
						<label title="<c:out value="${errorStaff.username }" />" >
							<c:out value="${errorStaff.username}" />
						</label>
					</td>
					<td>
						<label title="<c:out value="${errorStaff.errorMessage }" />" >
							<c:out value="${errorStaff.errorMessage }" />
						</label>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="modal-footer custom-button-margin custom-modal-footer">
		<div class="col-sm-10 custom-col-sm-no-padding custom-div-padding-left">
			<div class="col-sm-6 row pull-left">
				<button type="button" class="btn  btn-block btn-lg" id="confirmModalOkBtn"
						data-dismiss="modal"><spring:message code="system.common.button.ok" text="system.common.button.ok" />
				</button>
			</div>
			<div class="col-sm-6 row pull-right">
				<button type="button" class="btn btn-default btn-block btn-lg"
						data-dismiss="modal"><spring:message code="system.common.button.cancel" text="system.common.button.cancel" />
				</button>
			</div>
		</div>
	</div>

	<%--<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/staff.js"></script>--%>
	<%--<script>
		$(function(){
			readyStaffUpload();
		})
	</script>--%>
</body>
</html>