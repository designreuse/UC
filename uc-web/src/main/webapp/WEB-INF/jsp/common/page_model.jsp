<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<%--表格数据的的页码生成，可以直接点击下一页，也可以直接输入页码跳转 --%>
<c:set var="total" value="${pageModel.total}" />
<c:set var="totalPage" value="${pageModel.totalPage}" />
<c:set var="pageSize" value="${pageModel.pageSize}"/> 
<c:set var="pageNum" value="${pageModel.pageNum}"/>
<%-- 显示多少个页码 --%>
<c:set var="pages" value="5"></c:set>
<c:choose>
	<c:when test="${pageNum <= pages}">
		<c:set var="startPage" value="1" />
	</c:when>
	<c:otherwise>
		<c:set var="startPage" value="${pageNum-pages + 1}" />
	</c:otherwise>
</c:choose>

<c:if test="${total > 0 }">
	<input type="hidden" id="totalPage" value="${totalPage}" />
	<input type="hidden" id="url" value="${pageModel.url}">
	<input type="hidden" id="orderbyField" value="${pageModel.orderbyField }">	
	<input type="hidden" id="orderbyType" value="${pageModel.orderbyType }">

	<div class="clearfix">
		<div class="pull-left page-total-records">
			 <spring:message code='system.common.pagemodel.record' text='system.common.pagemodel.record'/>${total }
		</div>

		<%-- 至少2页时显示出页码 --%>
		<c:if test="${totalPage > 1}">
			<div class="pull-right pagination-number-div">
				<spring:message code="system.common.pagemodel.label.forward" text="system.common.pagemodel.label.forward" />
				<input class="input-page-number" type="text" maxlength="10" id="pageNumInput" name="pageNumInput" value="${pageNum}"
					onkeydown="return inputPageNum(event);"/>
				<spring:message code="system.common.pagemodel.label.page" text="system.common.pagemodel.label.page" />
				<button  type="button" class="btn pagination-go-btn btn-sm" id="pageGo">Go</button>
			</div>

			<div class="pull-right">
			<ul class="pagination">
				<%-- 从第2页起才显示出上一页和首页 --%>
				<c:if test="${pageNum > 1}">
					<li>
						<a href="#" onclick="clickPageNumHref('1'); return false;" >
							<span title="<spring:message code='system.common.pagemodel.first' text='system.common.pagemodel.first'/>">&laquo;</span>
						</a>
					</li>
					<li>
						<a href="#" onclick="clickPageNumHref('${pageNum-1}'); return false;" >
							<span title="<spring:message code='system.common.pagemodel.prev' text='system.common.pagemodel.prev'/>">&lt;</span>
						</a>
					</li>
				</c:if>

				<c:set var="currentPage" value="${startPage}"></c:set>
				<c:forEach var="navVal" begin="1" end="${totalPage - startPage + 1}" varStatus="status">
					<c:if test="${status.count <= pages}">
						<c:choose>
							<c:when test="${currentPage == pageNum}">
								<li class="active" id="nav${navVal+1}"><a>${currentPage}</a></li>
							</c:when>
							<c:otherwise>
								<li id="nav${navVal+1}">
									<a onclick='clickPageNumHref("${currentPage}"); return false;' href='#'>${currentPage}</a>
								</li>
							</c:otherwise>
						</c:choose>
						<c:set var="currentPage" value="${currentPage + 1}"></c:set>
					</c:if>
				</c:forEach>
				
				<c:if test="${pageNum < totalPage}">
					<li>
						<a href="#" onclick="clickPageNumHref('${pageNum+1}');return false;" >
							<span title="<spring:message code='system.common.pagemodel.next' text='system.common.pagemodel.next'/>">&gt;</span>
						</a>
					</li>
					<li>
						<a href="#" onclick="clickPageNumHref('${totalPage}');return false;" >
							<span title="<spring:message code='system.common.pagemodel.last' text='system.common.pagemodel.last'/>">&raquo;</span>
						</a>
					</li>
				</c:if>
			</ul>
			</div>
		</c:if>
	</div>
</c:if>
