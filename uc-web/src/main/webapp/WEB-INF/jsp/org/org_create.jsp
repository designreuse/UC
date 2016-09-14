<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        <spring:message code="org.title.create"/>
    </title>
</head>
<body>
<div id="modelBody" class="modal-body">
    <div class="fade in active">
        <form class="form-horizontal" id="createOrgForm">
            <div class="form-group" id="divOrgName_create">
                <div class="col-sm-2"> <span class="pull-right">部门名称</span></div>
                <div class="col-sm-8">
                <span id="spanOrgName_create">
                    <input class="form-control" id="orgName_create"
                           maxlength="64"
                           title="请输入部门名称"
                           placeholder="请输入部门名称" name="name">
                </span>
                </div>
                <label id="errorOrgName_create" class="control-label"></label>
            </div>
            <div class="form-group" id="divParentOrg_create">
                <div class="col-sm-2"> <span class="pull-right">上级部门</span></div>
                <div class="col-sm-8">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="input-group">
                                <input type="text" class="hide" id="orgId_orgCreateForm">
                                <input type="text" class="form-control" id="orgName_orgAddForm" readonly="readonly">

                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle"
                                            id="orgDropdownBtn"
                                            onclick="showOrgTreeForOrgCreate(); return false;">
                                        <spring:message code="button.select" text="button.select"/> <span class="caret"></span>
                                    </button>
                                    <ul id="selectOrgTree_createOrg"
                                        class="ztree dropdown-menu dropdown-menu-right showIcon"
                                        style="height: 280px; width:368px; overflow:auto;"></ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <label id="errorParentOrg_create" class="control-label"></label>
            </div>
            <%--<div class="form-group" id="divIsExtAssistance_create">--%>
                <%--<div class="col-sm-3"> <span class="pull-right">是否外协部门</span></div>--%>
                <%--<div class="col-sm-6">--%>
                <%--<span id="spanIsExtAssistance_create">--%>
                    <%--<input type="checkbox" id="isExtAssistance_create" name="isExtAssistance">--%>
                    <%--<i id="iconIsExtAssistance_create"></i>--%>
                <%--</span>--%>
                <%--</div>--%>
                <%--<label id="errorIsExtAssistance_create" class="control-label"></label>--%>
            <%--</div>--%>
        </form>
    </div>
</div>

<div class="modal-footer custom-modal-footer">
    <div class="col-sm-12 custom-col-sm-no-padding custom-div-padding-left">
        <div class="col-sm-6 row pull-left">
            <button type="button" class="pull-left btn btn-success btn-lg" onclick="createOrg();">
                <spring:message code="system.common.button.save"/>
            </button>
        </div>
        <div class="col-sm-6 row pull-right">
            <button type="button" class="pull-right btn btn-default btn-block btn-lg" data-dismiss="modal">
                <spring:message code="system.common.button.cancel"/>
            </button>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        readyCreateOrg();
    });
</script>
</body>
</html>

