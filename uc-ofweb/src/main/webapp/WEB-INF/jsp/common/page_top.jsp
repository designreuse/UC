<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false"%>

<%
	String language=(String)session.getAttribute("language");
%>

<base href="${pageContext.request.contextPath}">

<div class="navbar navbar-language navbar-fixed-top" role="navigation">
	<div class="navbar-right navbar-language-select-login navbar-offset-right">
		<form class="navbar-form form-inline">
			<div class="form-group">
         		<a class="login-a-about" target="blank" href="
         		<c:choose>
					<c:when test="${fn:containsIgnoreCase(sessionScope.language,'zh')}">
					http://www.yealink.cn
					</c:when>
					<c:otherwise>
					http://www.yealink.com
					</c:otherwise>
				</c:choose>">
         			<spring:message code="login.navigation.about.yealink" text="login.navigation.about.yealink"/>
         		</a>
         		<a class="login-a-about" target="blank" href="
         		<c:choose>
					<c:when test="${fn:containsIgnoreCase(sessionScope.language,'zh')}">
					http://www.yealink.cn/product_list.aspx?ProductsCateID=363&parentcateid=363&cateid=363&BaseInfoCateId=363&Cate_Id=363&index=1
					</c:when>
					<c:otherwise>
					http://www.yealink.com/product_list.aspx?ProductsCateID=363&parentcateid=363&cateid=363&BaseInfoCateId=363&Cate_Id=363&index=1
					</c:otherwise>
				</c:choose>">
         			<spring:message code="login.navigation.about.vc" text="login.navigation.about.vc"/>
         		</a>
         		<a class="login-a-about" target="blank" href="http://support.yealink.com/documentFront/forwardToDocumentFrontDisplayPage?BaseInfoCateId=1313&NewsCateId=1313&CateId=1313#">
         			<spring:message code="login.navigation.about.support" text="login.navigation.about.support"/>
         		</a>
			</div>

			<div class="form-group div-language-select">
				<div class="dropdown">
				   <div class="navbar-language-select" id="languageMenu"
				      data-toggle="dropdown">
					      <label>
					      	 <c:if test="${fn:containsIgnoreCase(sessionScope.language,'en')}">English</c:if>
					      	 <c:if test="${fn:containsIgnoreCase(sessionScope.language,'zh')}">中文</c:if>
					      </label>
					   	  <i class="caret"></i>
				   </div>
				   <ul class="dropdown-menu" role="menu" aria-labelledby="languageMenu">
				      <li role="presentation">
				         <a role="menuitem" tabindex="-1" onclick="changeLang('en')">English</a>
				      </li>
				      <li role="presentation">
				         <a role="menuitem" tabindex="-1" onclick="changeLang('zh-CN')">中文</a>
				      </li>
				   </ul>
				</div>
			</div>
      	</form>
  	</div>
</div>

<div class="navbar navbar-default" role="navigation">
	<div class="navbar-header col-sm-offset-2">
	</div>
</div>

<%@ include file="select_language.jsp"%>

<div class="modal fade" id="promptModal" tabindex="-1" role="dialog" data-backdrop="static"
	 aria-hidden="true">
	<div class="modal-dialog custom-modal-size">
		<div class="modal-content">
			<div class="modal-header custom-modal-header">
				<button id="closePswBtn" type="button" class="close custom-close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
				<h4 class="modal-title custom-modal-title">
					<spring:message code="system.common.title.prompt" text="system.common.title.prompt"/>
				</h4>
			</div>
			<div class="modal-body  custom-confirm-modal-body" id="promptModalBody">

			</div>
			<div class="modal-footer  custom-modal-footer">
				<div class="col-sm-offset-4  col-sm-4 custom-button-col-no-padding">
					<button type="button" class="btn btn-success btn-lg btn-block" id="promptModalOkBtn"
							data-dismiss="modal"><spring:message code="system.common.button.ok" text="system.common.button.ok"/>
					</button>
				</div>
			</div>
		</div>
	</div>
</div>
<%-- 项目中公用的模态化进度条，只以动画的形式显示 --%>
<div class="modal fade" data-backdrop="static" data-keyboard="false"
	aria-hidden="true" id="modalProgressBar">
	<div class="modal-dialog div-progress-bar-dialog-location custom-modal-size">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title custom-modal-title custom-no-padding-left" id="progressBarTitle">
					<%-- 此处的提示语在common.js showProgress中填充 --%>
				</h4>
			</div>
			<div class="modal-body">
				<div class="progress progress-striped active">
					<div class="progress-bar progress-bar-success div-progress-bar-dialog-body" role="progressbar">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
