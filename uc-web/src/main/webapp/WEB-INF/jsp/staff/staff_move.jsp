<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<body>
<div class="modal-header custom-modal-header">
    <button id="closePswBtn" type="button" class="close custom-close" data-dismiss="modal"
            aria-hidden="true">&times;</button>
    <h4 id="Title" class="modal-title custom-modal-title" id="modalPopupLabel">
        <spring:message code="org.title.move"/>
    </h4>
</div>
<div id="modelBody" class="modal-body">
    <div class="fade in active">
        <form class="form-horizontal" id="createOrgForm">
            <input type="hidden" id="staffId_move" value="${staff._id}"/>
            <input type="hidden" id="currentOrgs" value="${currentOrgs}">
            <div class="form-group" id="divStaffName_move">
                <div class="col-sm-3"> <span class="pull-right">员工姓名</span> </div>
                <div class="col-sm-7">
                    <span id="spanStaffName_move">
                        <input class="form-control" id="staffName_move"
                               readonly name="staffName" value="${staff.name}">
                    </span>
                </div>
            </div>
            <div class="form-group" id="divOldParentOrgName_move">
                <div class="col-sm-3"> <span class="pull-right">移动前部门</span> </div>
                <div class="col-sm-7">
                    <span id="spanOldParentOrgName_move">
                        <input class="form-control" id="oldParentOrgName_move"
                               readonly name="oldParentOrgName">
                    </span>
                </div>
            </div>
            <div class="form-group" id="divNewParentOrg_move">
                <div class="col-sm-3"> <span class="pull-right">移动后部门</span> </div>
                <div class="col-sm-7">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="input-group">
                                <input type="text" class="hide" id="newParentOrgId_move">
                                <input type="text" class="form-control" id="newParentOrgName_move">

                                <div class="input-group-btn">
                                    <button type="button" class="btn btn-default dropdown-toggle"
                                            id="orgDropdownBtn"
                                            onclick="showOrgTreeForMoveOrgForStaff(); return false;">
                                        <spring:message code="button.select" text="button.select"/> <span class="caret"></span>
                                    </button>
                                    <ul id="selectOrgTree_moveStaff"
                                        class="ztree dropdown-menu dropdown-menu-right showIcon"
                                        style="height: 280px; width:266px; overflow:auto;"></ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <label id="errorNewParentOrg_move" class="control-label"></label>
            </div>
        </form>
    </div>
</div>

<div class="modal-footer custom-modal-footer">
    <div class="col-sm-12 custom-col-sm-no-padding custom-div-padding-left">
        <div class="col-sm-6 row pull-left">
            <button type="button" class="pull-left btn btn-success btn-lg" onclick="moveStaff();" id="moveStaffModalOkBtn">
                <spring:message code="org.button.move"/>
            </button>
        </div>
        <div class="col-sm-6 row pull-right">
            <button type="button" class="pull-right btn btn-default btn-block btn-lg" data-dismiss="modal">
                <spring:message code="system.common.button.cancel"/>
            </button>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        readyMoveStaff();
    });
</script>
</body>
</html>

