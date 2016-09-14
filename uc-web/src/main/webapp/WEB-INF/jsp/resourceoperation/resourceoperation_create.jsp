<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
<input type="hidden" id="currentResourceCreateId">
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-7 div-title-height-fix  div-content-main">
            <div class="panel-body panel-body-fix">
                <form class="form-horizontal" id="createResourceForm">

                    <div class="form-group" id="divResource_create">
                        <label class="control-label col-sm-3">所属资源</label>

                        <div class="col-sm-7">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div class="input-group">
                                        <input type="text" class="hide" id="resourceId_resourceoperationCreateForm" value="${selectedModuleId}">
                                        <input type="text" class="form-control" id="resourceName_resourceoperationCreateForm" readonly="readonly" value="${parentResourceName}">

                                        <div class="input-group-btn">
                                            <button type="button" class="btn btn-default dropdown-toggle"
                                                    id="resourceoperationDropdownBtn"
                                                    onclick="showResourceTree(); return false;">
                                                <spring:message code="button.select" text="button.select"/> <span class="caret"></span>
                                            </button>
                                            <ul id="treeTradeResourceOperationCreate"
                                                class="ztree dropdown-menu dropdown-menu-right"
                                                style="height: 280px; width:250px; overflow:auto;"></ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <label id="errorResource_create" class="control-label"></label>
                    </div>
                    <div class="form-group" id="divResourceName_create">
                        <label class="control-label col-sm-3">选择操作</label>

                        <div class="col-sm-6">
						<span id="spanResourceName_create">
                            <select name="operationId" class="form-control" id="operationId_create">
                                <c:forEach items="${operations}" var="item">
                                    <option value="${item._id}">${item.name}</option>
                                </c:forEach>
                            </select>
						</span>
                        </div>
                        <label id="errorOperationId_create" class="control-label"></label>
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

