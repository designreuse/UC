<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<div class="form-inline">
	每页记录：
	<select class="form-control" id="pageSizeSelect">
		<option value="10" <c:if test="${pageModel.pageSize == 10}"> selected="selected"</c:if> >10</option>
		<option value="20" <c:if test="${pageModel.pageSize == 20}"> selected="selected"</c:if>>20</option>
		<option value="50" <c:if test="${pageModel.pageSize == 50}"> selected="selected"</c:if>>50</option>
		<option value="100" <c:if test="${pageModel.pageSize == 100}"> selected="selected"</c:if>>100</option>
	</select>
</div>
