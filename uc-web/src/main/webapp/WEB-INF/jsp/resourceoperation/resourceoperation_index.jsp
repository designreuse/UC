<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>资源管理</title>
    <link rel="stylesheet" href=${pageContext.request.contextPath}/3rdLibrary/ztree/zTreeStyle/zTreeStyle.css>
    <%-- 页面统一先加载CSS文件，style.css和bootstrap.css可不用加载，为了提高页面的响应速度，JS文件可以放在文件末尾加载 --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/3rdLibrary/font-awesome/css/font-awesome.min.css">
    <%-- 多选下拉列表样式 --%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/3rdLibrary/chosen/chosen.min.css">
</head>
<body>
<%--100% 宽度 --%>
<input type="hidden" id="currentResourceId">
<input type="hidden" id="currentOperationId">

<div class="container-fluid div-nav-left-page">
    <div class="row">
        <div id="divContent" class="col-md-10 col-md-push-2">
            <div class="panel panel-default">
                <%-- 列表上部的工具栏块，统一使用类即可有样式 --%>
                <div class="clearfix div-list-toolbar">
                    <%-- 按钮使用JQuery自带的控件，ID用于后面为按钮绑定事件，前辍统一使用btn --%>
                    <div id="divCreateBtn" class="div-toolbar-button">
                        <button id="btnCreateOrg" class="btn btn-default" data-toggle="modal" data-target="#modalPopup" onclick="forwardToCreateResource()">
                            <i class="icon-plus"></i>
                            添加资源操作
                        </button>
                        <button id="btnDeleteOrg" class="btn btn-default" onclick="confirmDeleteResource()">
                            <i class="icon-plus"></i>
                            删除资源操作
                        </button>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3">
                        <div class="thumbnail" style="margin-left: 10px;margin-top: 10px">
                            <div style="width:86%;margin-left:7%;margin-top:15px" id="divSearch">
                                <input class="form-control" id="search_resourceoperation" class="popover-content" maxlength="256"
                                       title="请输入资源名称搜索"
                                       placeholder="请输入资源名称搜索">
                            </div>
                            <div class="select_content" style="margin-left:6%;overflow:auto;height:150px;width:71%;" id="resultDiv">
                                <ul id="resultUL">
                                </ul>
                            </div>
                            <div class="caption" style="overflow:auto;height:700px;">
                                <ul id="resourceoperationTreeForInit" class="ztree"></ul>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-9">
                        <div class="thumbnail" style="margin-right: 10px;margin-top: 10px">
                            <div id="divEditContent" class="ui-layout-center"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- Modal弹出框 --%>
<div class="modal fade" id="modalPopup" tabindex="-1" role="dialog"
     aria-labelledby="modalPopupLabel" aria-hidden="true">
    <!-- 这里可以调节弹出框的大小 -->
    <div class="modal-dialog" style="width: 800px;">
        <div id="modelContent" class="modal-content">
        </div>
    </div>
</div>
<div class="modal fade" id="popUserCreate" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:800px; height:500px;">
        <div class="modal-content">
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/tree_common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/resourceoperation/resourceoperation_common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/resourceoperation/resourceoperation_index.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/resourceoperation/resourceoperation_create.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/resourceoperation/resourceoperation_delete.js"></script>
<div id="script"></div>
<script>
    $(document).ready(function () {
        readyResourceOperationIndex();
    });
</script>
</body>
</html>