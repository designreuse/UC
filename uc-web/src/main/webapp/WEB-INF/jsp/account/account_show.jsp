<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <spring:message code="account.info.title.name"/>
    </title>
    <link href="${pageContext.request.contextPath}/3rdLibrary/chosen/chosen.min.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="modal-header custom-modal-header">
    <button id="closePswBtn" type="button" class="close custom-close" data-dismiss="modal"
            aria-hidden="true">&times;</button>
    <h4 id="Title" class="modal-title custom-modal-title custom-title" id="modalPopupLabel">
        <spring:message code="account.info.title.name"/>
    </h4>
</div>
<div id="modelBody" class="modal-body">
    <div class="fade in active">
        <form class="form-horizontal account-info-form" role="form">

            <div class="form-group" id="divAccountUsername">
                <div class="col-sm-4"> <span class="pull-right"><spring:message code="account.label.username"/></span> </div>
                <div class="col-sm-8">
                    <div class="row">
                        <div class="col-lg-12 div-edit" hidden="true">
                            <div class="input-group">
                                <input class="form-control" id="accountUsername" value="${account.username }"
                                       maxlength="128" autocomplete="off"
                                       title="<spring:message code="account.inpunt.prompt.username" />"
                                        >
                            </div>
                        </div>
                        <div class="col-lg-12 div-info">
                            <div class="input-group">
                                <input class="form-control" value="${account.username }" disabled="disabled"/>
                            </div>
                        </div>
                    </div>
                </div>

                <label id="errorAccountUsername" class="control-label account-info-error-label" hidden="true"></label>
            </div>
            <div class="form-group" id="divAccountFullName">
                <div class="col-sm-4"> <span class="pull-right"><spring:message code="account.label.full.name"/></span> </div>
                <div class="col-sm-8">
                    <div class="row">
                        <div class="col-lg-12 div-edit" hidden="true">
                            <div class="input-group">
                                <input class="form-control" id="accountFullName" value="${account.name }"
                                       maxlength="32" autocomplete="off"
                                       title="<spring:message code="account.inpunt.prompt.full.name" />">
                            </div>
                        </div>
                        <div class="col-lg-12 div-info">
                            <div class="input-group">
                                <input class="form-control" value="${account.name }" disabled="disabled"/>
                            </div>
                        </div>
                    </div>
                </div>

                <label id="errorAccountFullName" class="control-label account-info-error-label" hidden="hidden"></label>
            </div>
            <div class="form-group" id="divAccountEnterprise">
                <div class="col-sm-4"> <span class="pull-right"><spring:message code="account.label.company"/></span> </div>
                <div class="col-sm-8">
                    <div class="row">
                        <div class="col-lg-12 div-edit" hidden="true">
                            <div class="input-group">
                                <input class="form-control" id="accountEnterprise" value="${enterprise.name }"
                                       maxlength="128" autocomplete="off"
                                       title="<spring:message code="account.inpunt.prompt.company" />">
                            </div>
                        </div>
                        <div class="col-lg-12 div-info">
                            <div class="input-group">
                                <input class="form-control" value="${enterprise.name }" disabled="disabled"/>
                            </div>
                        </div>
                    </div>
                </div>

                <label id="errorAccountEnterprise" class="control-label account-info-error-label" hidden="true"></label>
            </div>
            <div class="form-group" id="divCurrentPsw" hidden="true">
                <div class="col-sm-4"> <span class="pull-right"><spring:message code="account.modify.password.label.password.old"/></span> </div>
                <div class="col-sm-8">
                    <div class="row">
                        <div class="col-lg-12 div-input-pwd-edit">
                            <div class="input-group">
                                <input type="password" class="form-control" id="currentPsw"
                                       class="popover-content" maxlength="32" autocomplete="off"
                                       title="<spring:message code="account.modify.password.input.prompt.password.old"/>">
                            </div>
                        </div>
                    </div>
                </div>

                <label id="errorCurrentPsw" class="control-label account-edit-pwd-error-label"></label>
            </div>
            <div class="form-group" id="divNewPsw" hidden="true">
                <div class="col-sm-4">
                    <span class="pull-right">
                        <spring:message code="account.modify.password.label.password.new" text="account.modify.password.label.password.new"/>
                    </span>
                </div>
                <div class="col-sm-8">
                    <div class="row">
                        <div class="col-lg-12 div-input-pwd-edit">
                            <div class="input-group">
                                <input type="password" class="form-control" id="newPsw"
                                       class="popover-content" maxlength="32" autocomplete="off"
                                       placeholder="<spring:message code="account.modify.password.input.prompt.password.new" text="account.modify.password.input.prompt.password.new"/>"
                                       title="<spring:message code="account.modify.password.input.prompt.password.new" text="account.modify.password.input.prompt.password.new"/>">
                            </div>
                        </div>
                    </div>
                </div>

                <label id="errorNewPsw" class="control-label account-edit-pwd-error-label"></label>
            </div>
            <div class="form-group" id="divRepeatPsw" hidden="true">
                <div class="col-sm-4">
                    <span class="pull-right">
                        <spring:message code="account.modify.password.label.password.repeat" text="account.modify.password.label.password.repeat"/>
                    </span>
                </div>
                <div class="col-sm-8">
                    <div class="row">
                        <div class="col-lg-12 div-input-pwd-edit">
                            <div class="input-group">
                                <input type="password" class="form-control" id="repeatPsw"
                                       class="popover-content" maxlength="32" autocomplete="off"
                                       title="<spring:message code="account.modify.password.input.prompt.password.repeat" text="account.modify.password.input.prompt.password.repeat"/>">
                            </div>
                        </div>

                    </div>
                </div>

                <label id="errorRepeatPsw" class="control-label account-edit-pwd-error-label"></label>
            </div>
            <input id="editId" value="${account._id }" hidden="true">
        </form>
    </div>
</div>

<div class="modal-footer custom-modal-footer">
    <div id="detailOpDiv" class="col-sm-12 custom-col-sm-no-padding custom-div-padding-left">
        <div class="col-sm-6 row pull-left">
            <button type="button" class="pull-left btn btn-success btn-lg" id="toEditBtn">
                <spring:message code="account.button.info.edit"/>
            </button>
        </div>
        <div class="col-sm-6 row pull-right">
            <button type="button" class="pull-right btn btn-default btn-lg" id="toPwdBtn">
                <spring:message code="account.button.pwd.edit"/>
            </button>
        </div>
    </div>
    <div id="editOpDiv" class="col-sm-12 custom-col-sm-no-padding custom-div-padding-left"  hidden="true">
        <div class="col-sm-6 row pull-left">
            <button type="button" class="pull-left btn btn-success btn-lg" id="saveInfoBtn">
                <spring:message code="system.common.button.save"/>
            </button>
        </div>
        <div class="col-sm-6 row pull-right">
            <button type="button" class="pull-right btn btn-default btn-lg" id="toInfoBtn">
                <spring:message code="system.common.button.back"/>
            </button>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/chosen/chosen.jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/account/account.js"></script>
<script>
    $(document).ready(function () {
        readyAccountInfo();
    });
</script>
</body>
</html>
