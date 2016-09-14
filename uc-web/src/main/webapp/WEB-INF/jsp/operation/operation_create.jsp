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
                <form class="form-horizontal" id="createOperationForm">
                    <div class="form-group" id="divOperationName_create">
                        <label class="control-label col-sm-3">操作名称</label>

                        <div class="col-sm-6">
						<span id="spanOperationName_create">
							<input class="form-control" id="operationName_create" class="popover-content"
                                   maxlength="64"
                                   title="请输入操作名称"
                                   placeholder="请输入操作名称" name="name">
						</span>
                        </div>
                        <label id="errorOperationName_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divOperationDescription_create">
                        <label class="control-label col-sm-3">操作描述</label>

                        <div class="col-sm-6">
						<span id="spanOperationDescription_create">
							<input class="form-control" id="operationDescription_create" name="description">
							<i id="iconOperationDescription_create"></i>
						</span>
                        </div>
                        <label id="errorOperationDescription_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divOperationCode_create">
                        <label class="control-label col-sm-3">操作代码</label>

                        <div class="col-sm-6">
						<span id="spanOperationCode_create">
							<input class="form-control" id="operationCode_create" name="code">
							<i id="iconOperationCode_create"></i>
						</span>
                        </div>
                        <label id="errorOperationCode_create" class="control-label"></label>
                    </div>
                    <input type="hidden" id="operationId" value="${operation._id}">
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" id="createOperationBtn" onClick="createOperation();"><spring:message code="system.common.button.save" text="system.common.button.save"/></button>
    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="system.common.button.cancel" text="system.common.button.cancel"/></button>
</div>
<script type="text/javascript">
    $(function () {
        readyCreateOperation();
    });
</script>
</body>
</html>

