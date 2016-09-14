<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="account.modify.password.title.name" text="account.modify.password.title.name"/></title>
</head>
<body>
	<div class="modal-header custom-modal-header">
		<button id="closePswBtn" type="button" class="close custom-close" data-dismiss="modal"
			aria-hidden="true">&times;</button>
		<h4 id="Title" class="modal-title custom-modal-title custom-title" id="modalPopupLabel">
			<spring:message code="account.modify.password.modal.title" text="account.modify.password.modal.title"/>
		</h4>
	</div>
	<div id="modelBody" class="modal-body">
		<div class="fade in active">
			<form class="form-horizontal account-edit-pwd-form" role="form" autocomplete="off">
				<c:if test="${username != null }">
				<div class="form-group" id="divUsername">
					<label class="control-label">
						<spring:message code="account.label.username" text="account.label.username"/>
					</label>
					<div>
						<span id="spanCurrentPsw">
							<input disabled="disabled" class="form-control" class="popover-content"
							value="${username}" id="modifyPwdUsername">
						</span>
					</div>
				</div>
				</c:if>
				<c:if test="${username == null }">
				<div class="form-group" id="divCurrentPsw">
					<label class="control-label" for="currentPsw">
						<spring:message code="account.modify.password.label.password.old" text="account.modify.password.label.password.old"/>
					</label>
					<div class="div-input-pwd-edit">
						<input type="password" class="form-control" id="currentPsw"
								class="popover-content" maxlength="32" autocomplete="off"
								title="<spring:message code="account.modify.password.input.prompt.password.old" text="account.modify.password.input.prompt.password.old"/>">
					</div>
					<label id="errorCurrentPsw" class="control-label account-edit-pwd-error-label"></label>
				</div>
				</c:if>
				<div class="form-group" id="divNewPsw">
					<label class="control-label" for="newPsw">
						<spring:message code="account.modify.password.label.password.new" text="account.modify.password.label.password.new"/>
					</label>
					<div class="div-input-pwd-edit">
						<input type="password" class="form-control" id="newPsw"
							class="popover-content" maxlength="32" autocomplete="off"
							placeholder="<spring:message code="account.modify.password.input.prompt.password.new" text="account.modify.password.input.prompt.password.new"/>"
							title="<spring:message code="account.modify.password.input.prompt.password.new" text="account.modify.password.input.prompt.password.new"/>">
					</div>
					<label id="errorNewPsw" class="control-label account-edit-pwd-error-label"></label>
				</div>
				<div class="form-group" id="divRepeatPsw">
					<label class="control-label" for="repeatPsw">
						<spring:message code="account.modify.password.label.password.repeat" text="account.modify.password.label.password.repeat"/>
					</label>
					<div class="div-input-pwd-edit">
						<input type="password" class="form-control" id="repeatPsw"
								class="popover-content" maxlength="32" autocomplete="off"
								title="<spring:message code="account.modify.password.input.prompt.password.repeat" text="account.modify.password.input.prompt.password.repeat"/>">
					</div>
					<label id="errorRepeatPsw" class="control-label account-edit-pwd-error-label"></label>
				</div>
			</form>
		</div>
	</div>
	
	<div class="modal-footer custom-modal-footer">
		<div>
			<button type="button" class="btn btn-success pull-left" id="modifyPswBtn">
				<spring:message code="system.common.button.save" text="system.common.button.save"/>
			</button>
			<button type="button" class="btn btn-default pull-right" id ="modifyPswCancelBtn" data-dismiss="modal" >
				<spring:message code="system.common.button.cancel" text="system.common.button.cancel"/>
			</button>
		</div>
	</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script>
$(function(){
	readyFormPsw();
});
</script>
</body>
</html>