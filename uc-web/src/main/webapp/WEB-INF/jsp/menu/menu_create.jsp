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
                <form class="form-horizontal" id="createMenuForm">
                    <div class="form-group" id="divMenuName_create">
                        <label class="control-label col-sm-3">菜单名称</label>
                        <div class="col-sm-6">
						<span id="spanMenuName_create">
							<input class="form-control" id="menuName_create" class="popover-content"
                                   maxlength="64"
                                   title="请输入菜单名称"
                                   placeholder="请输入菜单名称" name="name">
						</span>
                        </div>
                        <label id="errorMenuName_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divMenuDescription_create">
                        <label class="control-label col-sm-3">菜单描述</label>

                        <div class="col-sm-6">
						<span id="spanMenuDescription_create">
							<input class="form-control" id="menuDescription_create" name="description">
							<i id="iconMenuDescription_create"></i>
						</span>
                        </div>
                        <label id="errorMenuDescription_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divMenuUrl_create">
                        <label class="control-label col-sm-3">菜单链接</label>

                        <div class="col-sm-6">
						<span id="spanMenuUrl_create">
							<input class="form-control" id="menuUrl_create" name="url">
							<i id="iconMenuUrl_create"></i>
						</span>
                        </div>
                        <label id="errorMenuUrl_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divMenuModule_edit">
                        <label class="control-label col-sm-3">所属模块</label>

                        <div class="col-sm-7">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="input-group">
                                        <input type="text" class="hide" id="moduleId_menuCreateForm" value="${selectedModuleId}">
                                        <input type="text" class="form-control" id="moduleName_menuCreateForm" readonly="readonly" value="${parentMenuName}">

                                        <div class="input-group-btn">
                                            <button type="button" class="btn btn-default dropdown-toggle"
                                                    id="menuDropdownBtn"
                                                    onclick="showModuleTreeForMenuCreate(); return false;">
                                                <spring:message code="button.select" text="button.select"/> <span class="caret"></span>
                                            </button>
                                            <ul id="treeTradeMenuCreate"
                                                class="ztree dropdown-menu dropdown-menu-right"
                                                style="height: 280px; width:250px; overflow:auto;"></ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <label id="errorMenuModule_create" class="control-label"></label>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" id="createMenuBtn" onClick="createMenu();"><spring:message code="system.common.button.save" text="system.common.button.save"/></button>
    <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="system.common.button.cancel" text="system.common.button.cancel"/></button>
</div>
<script type="text/javascript">
    $(function () {
        readyCreateMenu();
    });
</script>
</body>
</html>

