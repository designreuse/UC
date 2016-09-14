<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="yealink" uri="http://www.yealinkuc.com/tag" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0026)http://localhost/org/index -->
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/css/org/org.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/css/staff/staff.css">
    <title>组织管理2</title>
</head>
<body>
<div id="content_middle">
    <div class="content-middle-up">
        <div class="content-middle-up-down" >
            <div>
                <a class="no-underline" class="no-underline"
                   href="javascript:void(0);" onclick="forwardToImportStaff()">
                    <img src="${pageContext.request.contextPath}/images/operation/import.png"/>
                </a>
                <span>导入</span>
            </div>
            <div>
                <a class="no-underline"  href="${pageContext.request.contextPath}/staff/export">
                    <img src="${pageContext.request.contextPath}/images/operation/export.png"/>
                </a>
                <span>导出</span>
            </div>
        </div>
        <div class="content-middle-up-down">
            <div>
                <a class="no-underline" href="javascript:void(0);" onclick="forwardToCreateStaff()">
                    <img src="${pageContext.request.contextPath}/images/operation/add.png"/></a>
                <span>添加人员</span>
            </div>
            <div>
                <a class="no-underline"  href="javascript:void(0);" onclick="forwardToCreateOrg()">
                    <img src="${pageContext.request.contextPath}/images/operation/add.png"/></a>
                <span>添加部门</span>
            </div>
        </div>

    </div>
    <div class="content-middle-down" style="height: 600px;">
        <ul id="orgTreeForIndex" class="ztree showIcon">

        </ul>
    </div>
</div>
<div id="content_right">
    <div>
        <input type="hidden" id="currentSearchType" value="org">
        <input type="hidden" id="currentSelectedOrg" value="1">
        <input type="hidden" id="currentSelectedStaff">

        <div id="orgManagementRightContent">
        </div>
    </div>
</div>
<div style="display: none">
    <ul id="staffOrgTreeForSearch">

    </ul>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/tree_common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/org/org_common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/org/org_index.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/org/org_create.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/org/org_edit.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/org/org_delete.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/org/org_move.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/org/org_reindex.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/staff_common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/staff_create.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/staff_edit.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/back/staff_unworking.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/staff_reindex.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/staff_import.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/back/staff_release_org.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/staff_move.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/staff_operation.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/account/account.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/enterprise/enterprise.js"></script>

<script>
    $(document).ready(function () {
        readyOrgIndex();
//        accountBind();
    });
</script>

<script id="orgManagementRightContentTemp" type="text/html">
    <%@include file="org_info.template" %>
    <%@include file="../staff/staff_list.template" %>
    <%@include file="../common/pager.template" %>
</script>

<script>
    $(document).ready(function () {

    });
</script>
</body>


</html>