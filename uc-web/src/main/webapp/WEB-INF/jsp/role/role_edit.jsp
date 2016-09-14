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
                <form class="form-horizontal" id="createRoleForm">
                    <div class="form-group" id="divRoleName_edit">
                        <label class="control-label col-sm-3">角色名称</label>

                        <div class="col-sm-6">
						<span id="spanRoleName_edit">
							<input class="form-control" id="roleName_edit" class="popover-content"
                                   maxlength="64"
                                   title="请输入角色名称"
                                   placeholder="请输入角色名称" name="name" value="${role.name}">
						</span>
                        </div>
                        <label id="errorRoleName_edit" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divRoleDescription_edit">
                        <label class="control-label col-sm-3">角色描述</label>

                        <div class="col-sm-6">
						<span id="spanRoleDescription_edit">
							<input class="form-control" id="roleDescription_edit" name="description" value="${role.description}">
							<i id="iconRoleDescription_edit"></i>
						</span>
                        </div>
                        <label id="errorRoleDescription_edit" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divRoleLevel_edit">
                        <label class="control-label col-sm-3">角色级别</label>

                        <div class="col-sm-6">
						<span id="spanRoleLevel_edit">
                            <select name="roleLevelId" id="roleLevelId_edit" class="form-control">
                                <c:forEach var="rl" items="${roleLevelList}">
                                    <option value="${rl._id}" <c:if test="${role.roleLevelId eq rl._id}">selected</c:if>>${rl.name}</option>
                                </c:forEach>
                            </select>
							<i id="iconRoleLevel_edit"></i>
						</span>
                        </div>
                        <label id="errorRoleLevel_edit" class="control-label"></label>
                    </div>
                    <input type="hidden" id="roleId" value="${role._id}">
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" id="createRoleBtn" onClick="editRole();"><spring:message code="system.common.button.save" text="system.common.button.save"/></button>
    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="system.common.button.cancel" text="system.common.button.cancel"/></button>
</div>
<script type="text/javascript">
    $(function () {
        readyEditRole();
    });
</script>
</body>
</html>

