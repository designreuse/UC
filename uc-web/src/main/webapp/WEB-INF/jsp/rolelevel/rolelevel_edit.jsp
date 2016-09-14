<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                    <div class="form-group" id="divRoleLevelName_edit">
                        <label class="control-label col-sm-3">角色级别名称</label>

                        <div class="col-sm-6">
						<span id="spanRoleLevelName_edit">
							<input class="form-control" id="roleLevelName_edit" class="popover-content"
                                   maxlength="64"
                                   title="请输入角色级别名称"
                                   placeholder="请输入角色级别名称" name="name" value="${roleLevel.name}">
						</span>
                        </div>
                        <label id="errorRoleLevelName_edit" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divRoleLevelDescription_edit">
                        <label class="control-label col-sm-3">角色级别描述</label>

                        <div class="col-sm-6">
						<span id="spanRoleLevelDescription_edit">
							<input class="form-control" id="roleLevelDescription_edit" name="description" value="${roleLevel.description}">
							<i id="iconRoleLevelDescription_edit"></i>
						</span>
                        </div>
                        <label id="errorRoleLevelDescription_edit" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divRoleLevelCode_edit">
                        <label class="control-label col-sm-3">角色级别代码</label>

                        <div class="col-sm-6">
						<span id="spanRoleLevelCode_edit">
							<input class="form-control" id="roleLevelCode_edit" name="code" value="${roleLevel.code}">
							<i id="iconRoleLevelCode_edit"></i>
						</span>
                        </div>
                        <label id="errorRoleLevelCode_edit" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divRoleLevelType_edit">
                        <label class="control-label col-sm-3">角色级别类型</label>

                        <div class="col-sm-6">
						<span id="spanRoleLevelType_edit">
							<select name="roleLevelType" id="roleLevelType_edit" class="form-control">
                                <option value="Platform" <c:if test="${roleLevel.type eq 'Platform'}">selected</c:if>>Platform</option>
                                <option value="External" <c:if test="${roleLevel.type eq 'External'}">selected</c:if>>External</option>
                            </select>
							<i id="iconRoleLevelType_edit"></i>
						</span>
                        </div>
                        <label id="errorRoleLevelType_edit" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divRoleLevelPriority_edit">
                        <label class="control-label col-sm-3">角色级别优先级</label>

                        <div class="col-sm-6">
						<span id="spanRoleLevelPriority_edit">
							<input class="form-control" id="roleLevelPriority_edit" name="code" value="${roleLevel.priority}" readonly>
							<i id="iconRoleLevelPriority_edit"></i>
						</span>
                        </div>
                        <label id="errorRoleLevelPriority_edit" class="control-label"></label>
                    </div>
                    <input type="hidden" id="roleLevelId" value="${roleLevel._id}">
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" id="createRoleLevelBtn" onClick="editRoleLevel();"><spring:message code="system.common.button.save" text="system.common.button.save"/></button>
    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="system.common.button.cancel" text="system.common.button.cancel"/></button>
</div>
<script type="text/javascript">
    $(function () {
        readyEditRoleLevel();
    });
</script>
</body>
</html>

