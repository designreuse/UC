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
                <form class="form-horizontal" id="createRoleLevelForm">
                    <div class="form-group" id="divRoleLevelName_create">
                        <label class="control-label col-sm-3">角色级别名称</label>

                        <div class="col-sm-6">
						<span id="spanRoleLevelName_create">
							<input class="form-control" id="roleLevelName_create" class="popover-content"
                                   maxlength="64"
                                   title="请输入角色级别名称"
                                   placeholder="请输入角色级别名称" name="name">
						</span>
                        </div>
                        <label id="errorRoleLevelName_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divRoleLevelDescription_create">
                        <label class="control-label col-sm-3">角色级别描述</label>

                        <div class="col-sm-6">
						<span id="spanRoleLevelDescription_create">
							<input class="form-control" id="roleLevelDescription_create" name="description">
							<i id="iconRoleLevelDescription_create"></i>
						</span>
                        </div>
                        <label id="errorRoleLevelDescription_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divRoleLevelCode_create">
                        <label class="control-label col-sm-3">角色级别代码</label>

                        <div class="col-sm-6">
						<span id="spanRoleLevelCode_create">
							<input class="form-control" id="roleLevelCode_create" name="code" value="${roleLevel.code}">
							<i id="iconRoleLevelCode_create"></i>
						</span>
                        </div>
                        <label id="errorRoleLevelCode_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divRoleLevelType_create">
                        <label class="control-label col-sm-3">角色级别类型</label>

                        <div class="col-sm-6">
						<span id="spanRoleLevelType_create">
							<select name="roleLevelType" id="roleLevelType_create" class="form-control">
                                <option value="Platform">Platform</option>
                                <option value="External">External</option>
                            </select>
                                <i id="iconRoleLevelType_create"></i>
						</span>
                        </div>
                        <label id="errorRoleLevelType_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divRoleLevelPriority_create">
                        <label class="control-label col-sm-3">角色级别优先级</label>

                        <div class="col-sm-6">
						<span id="spanRoleLevelPriority_create">
							<input class="form-control" id="roleLevelPriority_create" name="priority">
							<i id="iconRoleLevelPriority_create"></i>
						</span>
                        </div>
                        <label id="errorRoleLevelPriority_create" class="control-label"></label>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" id="createRoleLevelBtn" onClick="createRoleLevel();"><spring:message code="system.common.button.save" text="system.common.button.save"/></button>
    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="system.common.button.cancel" text="system.common.button.cancel"/></button>
</div>
<script type="text/javascript">
    $(function () {
        readyCreateRoleLevel();
    });
</script>
</body>
</html>

