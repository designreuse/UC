<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tag" uri="http://www.yealinkuc.com/tag"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<title>
	非正常状态人员列表
</title>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div id="divContent" class="col-sm-10 div-content-main">
				<div class="div-title-bar clearfix">
					<div class="form-inline pull-right">
						<div class="btn-group">
							<button class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"
									id="status_select" value="${queryCriteria.selectedStatus}">
								<c:choose>
									<c:when test="${queryCriteria.selectedStatus == -1 }">锁定</c:when>
									<c:when test="${queryCriteria.selectedStatus == -2 }">回收</c:when>
									<c:when test="${queryCriteria.selectedStatus == -3 }">删除</c:when>
									<c:otherwise>所有</c:otherwise>
								</c:choose>
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li id="select_all" value="0"><a href="#">所有</a></li>
								<li id="select_locked" value="-1"><a href="#">锁定</a></li>
								<li id="select_recycled" value="-2"><a href="#">回收</a></li>
								<li id="select_deleted" value="-3"><a href="#">删除</a></li>
							</ul>
						</div>
    					<div class="form-group">
    						<span class="block  input-icon input-icon-right">
    							<input class="form-control" id="searchKey" maxlength="128"
									title="请输入关键字搜索" placeholder="请输入关键字搜索"
									value="<c:out value='${page.searchKey}'/>" autocomplete="off"/>
								<i class="icon-search btn" id="btnSearch"></i>
							</span>
    					</div>
					</div>
				</div>

				<div class="div-list-table div-list-table-layout">
					<input type="hidden" value="${status }" id = "status"/>
					<table class="table fixed-table table-hover table-condensed">
						<thead>
							<tr>
								<th class="th-first list-sort-cursor">账号	</th>
								<th>姓名</th>
								<th width="15%">最后修改时间</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${staffList}" var="staff" varStatus="rownum">
								<c:set var="trCount" value="${rownum.count}" />
								<tr class="
									<c:if test="${trCount % 2 == 1}">
										table-striped-tr-odd
									</c:if>
									<c:if test="${trCount % 2 == 0}">
										table-striped-tr-even
									</c:if>	">
									<td>
										<label title="<c:out value="${staff.username}"/>">
											<c:out value="${staff.username}"/>
										</label>
									</td>
									<td>
										<label title="<c:out value="${staff.name}"/>">
											<c:out value="${staff.name}"/>
										</label>
									</td>
									<td>
										<label title="<tag:date value="${staff.modificationDate}" pattern="yyyy-MM-dd HH:mm:ss"/>">
											<tag:date value="${staff.modificationDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</label>
									</td>
									<td>
										<label>
											<c:choose>
												<c:when test="${staff.status == -1 }">锁定</c:when>
												<c:when test="${staff.status == -2 }">回收</c:when>
												<c:when test="${staff.status == -3 }">删除</c:when>
											</c:choose>
										</label>
									</td>
									<td>
										<div class="row td-div-operation">
											<c:if test="${staff.status == -1}">
												<label class="table-icon-label" id="unlock_${staff._id}">
													<i class="icon-unlock icon-large" title="解锁"></i>
												</label>
												<label class="table-icon-label" id="recycle_${staff._id}">
													<i class="icon-trash icon-large" title="回收"></i>
												</label>
											</c:if>
											<c:if test="${staff.status == -2}">
												<label class="table-icon-label" id="revert_${staff._id}">
													<i class="icon-undo icon-large" title="还原"></i>
												</label>
												<label class="table-icon-label" id="delete_${staff._id}">
													<i class="icon-remove-circle icon-large" title="删除"></i>
												</label>
											</c:if>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<%@include file="../../common/page_model.jsp" %>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/ztree/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/back/staff.js"></script>
	<script>
	</script>
</body>
</html>