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
                    <input type="hidden" id="resourceId_edit" value="${resource._id}">
                    <div class="form-group" id="divResourceName_edit">
                        <label class="control-label col-sm-3">资源名称</label>

                        <div class="col-sm-6">
						<span id="spanResourceName_edit">
							<input class="form-control" id="resourceName_edit" class="popover-content"
                                   maxlength="64"
                                   title="请输入资源名称"
                                   placeholder="请输入资源名称" name="name" value="${resource.name}">
						</span>
                        </div>
                        <label id="errorResourceName_edit" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divResourceDescription_edit">
                        <label class="control-label col-sm-3">资源描述</label>

                        <div class="col-sm-6">
						<span id="spanResourceDescription_edit">
							<input class="form-control" id="resourceDescription_edit" name="description" value="${resource.description}">
							<i id="iconResourceDescription_edit"></i>
						</span>
                        </div>
                        <label id="errorResourceDescription_edit" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divResourceCode_edit">
                        <label class="control-label col-sm-3">资源代码</label>

                        <div class="col-sm-6">
						<span id="spanResourceCode_edit">
							<input class="form-control" id="resourceCode_edit" name="code" value="${resource.code}">
							<i id="iconResourceCode_edit"></i>
						</span>
                        </div>
                        <label id="errorResourceCode_edit" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divResourceModule_edit">
                        <label class="control-label col-sm-3">所属模块</label>
                        <div class="col-sm-6">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="input-group">
                                        <input type="text" class="hide" id="moduleId_resourceEditForm" value="${resource.moduleId}">
                                        <input type="text" class="form-control" id="moduleName_resourceEditForm" value="${module.name}">

                                        <div class="input-group-btn">
                                            <button type="button" class="btn btn-default dropdown-toggle"
                                                    id="resourceDropdownBtn"
                                                    onclick="showModuleTreeForResourceEdit(); return false;">
                                                <spring:message code="button.select" text="button.select"/> <span class="caret"></span>
                                            </button>
                                            <ul id="treeTradeResourceEdit"
                                                class="ztree dropdown-menu dropdown-menu-right"
                                                style="height: 280px; width:250px; overflow:auto;"></ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <label id="errorResourceModule_edit" class="control-label"></label>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" id="createResourceBtn" onClick="editResource();"><spring:message code="system.common.button.save" text="system.common.button.save"/></button>
</div>
<script type="text/javascript">
    $(function () {
        readyEditResource();
    });
</script>
</body>
</html>

