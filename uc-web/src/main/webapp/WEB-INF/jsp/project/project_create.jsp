<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-7 div-title-height-fix  div-content-main">
            <div class="panel-body panel-body-fix">
                <form class="form-horizontal" id="createProjectForm">
                    <div class="form-group" id="divProjectName_create">
                        <label class="control-label col-sm-3">项目名称</label>

                        <div class="col-sm-6">
						<span id="spanProjectName_create">
							<input class="form-control" id="projectName_create" class="popover-content"
                                   maxlength="64"
                                   title="请输入项目名称"
                                   placeholder="请输入项目名称" name="name">
						</span>
                        </div>
                        <label id="errorProjectName_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divProjectDescription_create">
                        <label class="control-label col-sm-3">项目描述</label>

                        <div class="col-sm-6">
						<span id="spanProjectDescription_create">
							<input class="form-control" id="projectDescription_create" name="description">
							<i id="iconProjectDescription_create"></i>
						</span>
                        </div>
                        <label id="errorProjectDescription_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divProjectUrl_create">
                        <label class="control-label col-sm-3">项目地址</label>

                        <div class="col-sm-6">
						<span id="spanProjectUrl_create">
							<input class="form-control" id="projectUrl_create" name="url">
							<i id="iconProjectUrl_create"></i>
						</span>
                        </div>
                        <label id="errorProjectUrl_create" class="control-label"></label>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" id="createProjectBtn" onClick="createProject();"><spring:message code="system.common.button.save" text="system.common.button.save"/></button>
    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="system.common.button.cancel" text="system.common.button.cancel"/></button>
</div>
<script type="text/javascript">
    $(function () {
        readyCreateProject();
    });
</script>
</body>
</html>

