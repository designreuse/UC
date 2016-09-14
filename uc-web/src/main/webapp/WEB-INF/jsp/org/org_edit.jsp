<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="content-right-up">
    <div>
        <c:choose>
            <c:when test="${org.parentId eq 0}">编辑企业</c:when>
            <c:otherwise>编辑部门</c:otherwise>
        </c:choose>
    </div>
</div>
<div class="container-fluid content-right-down">
    <div class="row">
        <form class="form-horizontal" role="form">
            <input type="hidden" id="orgId_edit" value="${org._id}">
            <div class="form-group">
                <div class="col-sm-4"><span class="pull-left">部门名称</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divOrgName_edit">
                <div class="col-sm-6">
                    <span id="spanOrgName_edit">
                        <input class="form-control" id="orgName_edit" class="popover-content" value="${org.name}"
                               maxlength="64" title="请输入部门名称" placeholder="请输入部门名称" >
                        <i id="iconOrgName_edit"></i>
                    </span>
                </div>
                <label id="errorOrgName_edit" class="control-label"></label>
            </div>

            <c:if test="${org.parentId ne 0}">
                <div class="form-group">
                    <div class="col-sm-4"><span class="pull-left">上级部门</span><span style="color: red">*</span></div>
                </div>
                <div class="form-group" id="divParentOrg_edit">
                    <div class="col-sm-6">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="input-group">
                                    <input type="text" class="hide" id="orgId_editOrg" value="${parentOrg._id}">
                                    <input type="text" class="form-control" id="orgName_editOrg" value="${parentOrg.name}" readonly>

                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                id="orgDropdownBtn"
                                                onclick="showOrgTreeForOrgEdit(); return false;">
                                            <spring:message code="button.select" text="button.select"/> <span class="caret"></span>
                                        </button>
                                        <ul id="selectOrgTree_editOrg"
                                            class="ztree dropdown-menu dropdown-menu-right showIcon"
                                            style="height: 280px;overflow:auto;width:368px;"></ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <label id="errorParentOrg_create" class="control-label"></label>
                </div>
            </c:if>
            <%--<div class="form-group">--%>
                <%--<div class="col-sm-4"><span class="pull-left">隐藏部门</span></div>--%>
            <%--</div>--%>
            <%--<div class="form-group" id="divIsExtAssistance_edit">--%>
                <%--<div class="col-sm-6">--%>
						<%--<span id="spanIsExtAssistance_edit">--%>
							<%--<input type="checkbox" id="isExtAssistance_edit" <c:if test="${org.isExtAssistance}">checked="checked"</c:if>>--%>
							<%--<i id="iconIsExtAssistance_edit"></i>--%>
						<%--</span>--%>
                <%--</div>--%>
                <%--<label id="errorIsExtAssistance_edit" class="control-label"></label>--%>
            <%--</div>--%>
        </form>
    </div>
</div>
<div class="modal-footer">
    <div class="col-sm-2">
        <button type="button" class="btn btn-success" id="editOrgBtn" onClick="editOrg()">
            <spring:message code="system.common.button.save" text="system.common.button.save"/></button>
    </div>
    <div class="col-sm-2">
        <button type="button" class="btn btn-block"  onClick="goStaffListPage(1,10)">
            <spring:message code="system.common.button.cancel" text="system.common.button.cancel"/></button>
    </div>
</div>
<div id="addTitleTemplate" style="display: none;">
    <table>
        <tbody id="addTitleTbody">
        <tr name="titleRow">
            <td width="33%"><input type="text" name="title"></td>
            <td width="33%">
                <select name="staff">
                    <c:forEach items="${staffList}" var="staff">
                        <option value="${staff._id}">${staff.name}</option>
                    </c:forEach>
                </select>
            </td>
            <td class="text-center\" width="33%">
                <div style="vertical-align:middle;font-size:14px;">
                    <a href="#" onclick="titleDelete(this);">删除角色</a>
                    <i class="icon-minus icon-large icon-minus-phone" onclick="titleDelete(this);"></i>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</div>
<script type="text/javascript">
    $(function () {
        readyEditOrg();
    });
</script>
</body>
</html>
