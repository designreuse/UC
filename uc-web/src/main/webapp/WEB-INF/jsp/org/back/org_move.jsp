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
            将 [${move_org_name}] 移动到...
        </h4>
    </div>
    <input type="hidden" id="move_org_id" value="${move_org_id}"/>
    <input type="hidden" id="move_type" value="${move_type}"/>
    <input type="hidden" id="move_target"/>

    <div style="width:86%;margin-left:6%;margin-top:20px;" id="divSearch_move">
        <input class="form-control" id="search_org_move" class="popover-content" maxlength="256"
               title="请输入组织名称搜索" placeholder="请输入组织名称搜索">
    </div>
    <div class="select_content" style="margin-left:6%;overflow:auto;height:150px;width:85%;display: none;" id="resultDiv_move">
        <ul id="resultUL_move">
        </ul>
    </div>
    <div class="caption" style="height:280px;overflow:auto">
        <ul id="treeOrgForMove" class="ztree" style="width:250px;"></ul>
    </div>
    <%-- 窗口的底部按钮 --%>
    <div class="modal-footer" style="text-align: center">
        <button type="button" class="btn btn-primary" id="moveBtn" onClick="submitMove();"><spring:message code="org.button.move" text="org.button.move"/></button>
    </div>
</div>
</body>
<script>
    readyMove();
</script>
</html>