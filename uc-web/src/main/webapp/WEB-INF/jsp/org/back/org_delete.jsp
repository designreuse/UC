<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
<div style="width:100%;height:470px;border: 1px solid #dddddd;">
    <div class="modal-header">
        <button type="button" class="close"
                data-dismiss="modal" aria-hidden="true">
            &times;
        </button>
        <h4 class="modal-title" id="title">
            请选择要删除的部门...
        </h4>
    </div>

    <div style="width:86%;margin-left:6%;margin-top:20px;" id="divSearch_delete">
        <input class="form-control" id="search_org_delete" class="popover-content" maxlength="256"
               title="请输入部门名称搜索" placeholder="请输入部门名称搜索">
    </div>
    <div class="select_content" style="margin-left:6%;overflow:auto;height:150px;width:85%;display: none;" id="resultDiv_delete">
        <ul id="resultUL_delete">
        </ul>
    </div>
    <div class="caption" style="height:280px;overflow:auto">
        <ul id="treeOrgForDelete" class="ztree" style="width:250px;"></ul>
    </div>
    <%-- 窗口的底部按钮 --%>
    <div class="modal-footer" style="text-align: center">
        <button type="button" class="btn btn-primary" id="deleteBtn" onClick="deleteOrg();">确定</button>
    </div>
</div>
</body>
<script>
    readyDelete();
</script>
</html>