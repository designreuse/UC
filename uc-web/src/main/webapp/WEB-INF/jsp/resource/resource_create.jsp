<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-7 div-title-height-fix  div-content-main">
            <div class="panel-body panel-body-fix">
                <form class="form-horizontal" id="createResourceForm">
                    <div class="form-group" id="divResourceName_create">
                        <label class="control-label col-sm-3">资源名称</label>
                        <div class="col-sm-6">
						<span id="spanResourceName_create">
							<input class="form-control" id="resourceName_create" class="popover-content"
                                   maxlength="64"
                                   title="请输入资源名称"
                                   placeholder="请输入资源名称" name="name">
						</span>
                        </div>
                        <label id="errorResourceName_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divResourceDescription_create">
                        <label class="control-label col-sm-3">资源描述</label>

                        <div class="col-sm-6">
						<span id="spanResourceDescription_create">
							<input class="form-control" id="resourceDescription_create" name="description">
							<i id="iconResourceDescription_create"></i>
						</span>
                        </div>
                        <label id="errorResourceDescription_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divResourceCode_create">
                        <label class="control-label col-sm-3">资源代码</label>

                        <div class="col-sm-6">
						<span id="spanResourceCode_create">
							<input class="form-control" id="resourceCode_create" name="Code">
							<i id="iconResourceCode_create"></i>
						</span>
                        </div>
                        <label id="errorResourceCode_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divResourceModule_edit">
                        <label class="control-label col-sm-3">所属模块</label>

                        <div class="col-sm-7">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="input-group">
                                        <input type="text" class="hide" id="moduleId_resourceCreateForm" value="${selectedModuleId}">
                                        <input type="text" class="form-control" id="moduleName_resourceCreateForm" readonly="readonly" value="${parentResourceName}">

                                        <div class="input-group-btn">
                                            <button type="button" class="btn btn-default dropdown-toggle"
                                                    id="resourceDropdownBtn"
                                                    onclick="showModuleTreeForResourceCreate(); return false;">
                                                <spring:message code="button.select" text="button.select"/> <span class="caret"></span>
                                            </button>
                                            <ul id="treeTradeResourceCreate"
                                                class="ztree dropdown-menu dropdown-menu-right"
                                                style="height: 280px; width:250px; overflow:auto;"></ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <label id="errorResourceModule_create" class="control-label"></label>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" id="createResourceBtn" onClick="createResource();"><spring:message code="system.common.button.save" text="system.common.button.save"/></button>
    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="system.common.button.cancel" text="system.common.button.cancel"/></button>
</div>
<script type="text/javascript">
    $(function () {
        readyCreateResource();
    });
</script>
</body>
</html>

