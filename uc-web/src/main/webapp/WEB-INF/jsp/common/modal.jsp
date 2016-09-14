<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- 定义用于项目中的弹出窗口 --%>
<%-- 提示模态化窗口 --%>
<div class="modal fade" id="promptModal" tabindex="-1" role="dialog" data-backdrop="static"
     aria-hidden="true">
    <div class="modal-dialog custom-modal-size">
        <div class="modal-content">
            <div class="modal-header custom-modal-header">
                <button type="button" class="close custom-close" data-dismiss="modal"
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

<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" data-backdrop="static"
     aria-hidden="true">
    <div class="modal-dialog custom-modal-size">
        <div class="modal-content">
            <div class="modal-header custom-modal-header">
                <h4 class="modal-title custom-modal-title " id="confirmModalTitle">
                </h4>
            </div>
            <div class="modal-body  custom-confirm-modal-body" id="confirmModalBody">

            </div>
            <div class="modal-footer custom-button-margin custom-modal-footer">
                <div class="col-sm-10 custom-col-sm-no-padding custom-div-padding-left">
                    <div class="col-sm-6 row pull-left">
                        <button type="button" class="btn  btn-success btn-lg" id="confirmModalOkBtn"
                                data-dismiss="modal"><spring:message code="system.common.button.ok" text="system.common.button.ok"/>
                        </button>
                    </div>
                    <div class="col-sm-6 row pull-right">
                        <button type="button" class="btn btn-default btn-block btn-lg"
                                data-dismiss="modal"><spring:message code="system.common.button.cancel" text="system.common.button.cancel"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%-- Modal弹出框 --%>
<div class="modal fade" id="modalPopupCreateOrg" tabindex="-1" role="dialog"
     aria-labelledby="modalPopupLabel" aria-hidden="true">
    <!-- 这里可以调节弹出框的大小 -->
    <div class="modal-dialog">
        <div id="modelContentCreateOrg" class="modal-content">
        </div>
    </div>
</div>

<div class="modal fade" id="modalStaffMove" tabindex="-1" role="dialog" data-backdrop="static"
     aria-hidden="true">
    <%-- 这里可以调节弹出框的大小 --%>
    <div class="modal-dialog account-info-modal">
        <div id="modalContent" class="modal-content">
        </div>
    </div>
</div>

<%-- 个人资料 Modal弹出框 --%>
<div class="modal fade" id="modelPersonal" tabindex="-1" role="dialog" data-backdrop="static"
     aria-hidden="true">
    <%-- 这里可以调节弹出框的大小 --%>
    <div class="modal-dialog account-info-modal">
        <div id="divHeaderContent" class="modal-content">
        </div>
    </div>
</div>
