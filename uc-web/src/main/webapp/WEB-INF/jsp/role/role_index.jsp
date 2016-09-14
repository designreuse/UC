<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" uri="http://www.yealinkuc.com/tag" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        角色列表
    </title>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div id="divContent" class="col-sm-12 div-content-main">
            <div class="div-title-bar clearfix">
                <div id="divCreateBtn" class="div-toolbar-button">
                    <button id="btnCreateRole" class="btn btn-default" data-toggle="modal" data-target="#modalPopup" onclick="forwardToCreateRole()">
                        创建角色
                    </button>
                </div>
            </div>

            <div class="div-list-table div-list-table-layout">
                <input type="hidden" value="${status }" id="status"/>
                <table class="table fixed-table table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="th-first list-sort-cursor">名称</th>
                        <th>描述</th>
                        <th>角色菜单授权</th>
                        <th>角色资源授权</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${roleList}" var="item" varStatus="rownum">
                        <c:set var="trCount" value="${rownum.count}"/>
                        <tr class="
									<c:if test="${trCount % 2 == 1}">
										table-striped-tr-odd
									</c:if>
									<c:if test="${trCount % 2 == 0}">
										table-striped-tr-even
									</c:if>	">
                            <td>
                                <label title="<c:out value="${item.name}"/>">
                                    <c:out value="${item.name}"/>
                                </label>
                            </td>
                            <td>
                                <label title="<c:out value="${item.description}"/>">
                                    <c:out value="${item.description}"/>
                                </label>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/rolepermission/index" method="get" class="form-horizontal">
                                    <select name="projectId">
                                        <c:forEach var="p" items="${projectList}">
                                            <option value="${p._id}">${p.name}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" name="roleId" value="${item._id}">
                                    <input type="hidden" name="permissionType" value="menu">
                                    <button type="button" class="btn btn-default"
                                            onclick="submit()">
                                        <spring:message code="button.authorize" text="button.authorize"/>
                                    </button>
                                </form>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/rolepermission/index" method="get" class="form-horizontal">
                                    <select name="projectId">
                                        <c:forEach var="p" items="${projectList}">
                                            <option value="${p._id}">${p.name}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" name="roleId" value="${item._id}">
                                    <input type="hidden" name="permissionType" value="resource_operation">
                                    <button type="button" class="btn btn-default"
                                            onclick="submit()">
                                        <spring:message code="button.authorize" text="button.authorize"/>
                                    </button>
                                </form>
                            </td>
                            <td>
                                <div class="row td-div-operation">
                                    <label id="editRole_${item._id}" class="table-icon-label">
                                        <i class="icon-edit icon-large"></i>
                                    </label>
                                    <label id="deleteRole_${item._id}" class="table-icon-label">
                                        <i class="icon-trash icon-large"></i>
                                    </label>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <%@include file="../common/page_model.jsp" %>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/tree_common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/role/role_common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/role/role_create.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/role/role_edit.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/role/role_delete.js"></script>
<script>
</script>
</body>
</html>