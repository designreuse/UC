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
                <form class="form-horizontal" id="createModuleForm">
                    <input type="hidden" id="moduleId_edit" value="${module._id}">
                    <div class="form-group" id="divModuleName_edit">
                        <label class="control-label col-sm-3">模块名称</label>

                        <div class="col-sm-6">
						<span id="spanModuleName_edit">
							<input class="form-control" id="moduleName_edit" class="popover-content"
                                   maxlength="64"
                                   title="请输入模块名称"
                                   placeholder="请输入模块名称" name="name" value="${module.name}">
						</span>
                        </div>
                        <label id="errorModuleName_edit" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divModuleDescription_edit">
                        <label class="control-label col-sm-3">模块描述</label>

                        <div class="col-sm-6">
						<span id="spanModuleDescription_edit">
							<input class="form-control" id="moduleDescription_edit" name="description" value="${module.description}">
							<i id="iconModuleDescription_edit"></i>
						</span>
                        </div>
                        <label id="errorModuleDescription_edit" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divModuleModule_edit">
                        <label class="control-label col-sm-3">所属模块</label>
                        <div class="col-sm-6">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="input-group">
                                        <input type="text" class="hide" id="parentModuleId_moduleEditForm" value="${parentId}">
                                        <input type="text" class="form-control" id="parentModuleName_moduleEditForm" value="${parentName}">

                                        <div class="input-group-btn">
                                            <button type="button" class="btn btn-default dropdown-toggle"
                                                    id="moduleDropdownBtn"
                                                    onclick="showModuleTreeForModuleEdit(); return false;">
                                                <spring:message code="button.select" text="button.select"/> <span class="caret"></span>
                                            </button>
                                            <ul id="treeTradeModuleEdit"
                                                class="ztree dropdown-menu dropdown-menu-right"
                                                style="height: 280px; width:250px; overflow:auto;"></ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <label id="errorModuleModule_edit" class="control-label"></label>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" id="createModuleBtn" onClick="editModule();"><spring:message code="system.common.button.save" text="system.common.button.save"/></button>
</div>
<script type="text/javascript">
    $(function () {
        readyEditModule();
    });
</script>
</body>
</html>

