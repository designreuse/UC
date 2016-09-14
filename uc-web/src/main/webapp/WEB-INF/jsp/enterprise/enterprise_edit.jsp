<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/css/jquery.fileupload.css">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>编辑企业</title>
</head>
<body>
<div class="content-right-up">
	<div>
		编辑企业
	</div>
</div>
<div class="container-fluid content-right-down">
	<div class="row">
		<form class="form-horizontal" role="form">
			<input type="hidden" id="enterpriseId_edit" value="${enterprise._id}">
			<div class="form-group">
				<div class="col-sm-1"><span class="pull-left">企业名称</span><span style="color: red">*</span></div>
			</div>
			<div class="form-group" id="divEnterpriseName_edit">
				<div class="col-sm-6">
					<span id="spanEnterpriseName_edit">
						<input class="form-control" id="enterpriseName_edit" maxlength="64"
							   value="<c:out value='${enterprise.name}'/>"
							   title='请输入企业名称' placeholder='请输入企业名称'/>
					</span>
				</div>
				<label id="errorEnterpriseName_edit" class="control-label"></label>
			</div>
			<%--<div class="form-group">--%>
				<%--<div class="col-sm-4"><span class="pull-left">隐藏部门</span></div>--%>
			<%--</div>--%>
			<%--<div class="form-group" id="divIsExtAssistance_edit">--%>
				<%--<div class="col-sm-6">--%>
						<%--<span id="spanIsExtAssistance_edit">--%>
							<%--<input type="checkbox" id="isExtAssistance_edit" <c:if test="${org.isExtAssistance}">checked="checked"</c:if>>--%>
							<%--<i id="iconIsExtAssistance_edit"></i>--%>
						<%--</span>--%>
				<%--</div>--%>
				<%--<label id="errorIsExtAssistance_edit" class="control-label"></label>--%>
			<%--</div>--%>
			<div class="form-group">
				<div class="col-sm-4"><span class="pull-left">企业Logo</span><span style="color: red">*</span></div>
			</div>
			<div class="form-group" id="divEnterpriseLogo_edit">
				<div class="col-sm-6">
                    <span class="btn fileinput-button">
                        <span id="enterprise_logo_preview_edit">
							<c:choose>
								<c:when test="${enterprise.logo == null}">
									<img width="100" height="100" src="/images/enterprise/enterprise_default_logo.png">
								</c:when>
								<c:otherwise>
									<img width="100" height="100" src="${fileServiceUrl}/image?id=${enterprise.logo}&x=100&y=100">
								</c:otherwise>
							</c:choose>
                        </span>
						<span>单击图片上传，仅支持1024*768大小的jpg，png图片</span>
                        <input class="form-control" id="enterpriseLogo_edit" name="enterpriseLogo"
							   type="file" accept=".png,.jpg,.jpeg"/>
                    </span>
				</div>
			</div>
		</form>
	</div>
</div>
<div class="modal-footer">
	<div class="col-sm-2">
		<button type="button" class="btn btn-success" id="updateEnterpriseBtn">
			<spring:message code="system.common.button.save" text="system.common.button.save"/></button>
	</div>
	<div class="col-sm-2">
		<button type="button" class="btn btn-block"  onClick="goStaffListPage(1,10)">
			<spring:message code="system.common.button.cancel" text="system.common.button.cancel"/></button>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/enterprise/enterprise.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/vendor/canvas-to-blob.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/vendor/load-image.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/jquery.fileupload-image.js"></script>

<script>
	$(document).ready(function(){
		readyEnterpriseEdit();
	});
</script>
</body>
</html>