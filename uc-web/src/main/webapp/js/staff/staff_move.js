$("#orgManagementRightContent").on("click", "a[id^='move_']", function () {
    var id = $(this).attr("id").replace("move_", "");
    forwardToMoveStaff(id);
});
function showOrgTreeForMoveOrgForStaff() {
    $('#selectOrgTree_moveStaff').toggle();
}

function forwardToMoveStaff(staffId) {
    $("#modalStaffMove").modal({keyboard: false, remote: createUrl("/staff/forwardToMove/" + staffId)});
    $('#modalStaffMove .modal-dialog').css('width', '510px');
    $('#modalStaffMove .modal-dialog').css('height', '300px');
}

function onClickOrgNodeWhenMoveStaff(e, treeId, treeNode) {
    var __ret = extractedOrgNode(treeId);
    var orgName = __ret.orgName;
    var orgId = __ret.orgId;
    $("#newParentOrgId_move").val(orgId);
    $("#newParentOrgName_move").val(orgName);
    $('#selectOrgTree_moveStaff').toggle();
}

function readyMoveStaff() {
    showOrgTree("selectOrgTree_moveStaff", onClickOrgNodeWhenMoveStaff);
    var treeObj = $.fn.zTree.getZTreeObj("selectOrgTree_moveStaff");
    var currentOrgs = $("#currentOrgs").val().split(";");
    var currentOrgsStr = "";
    for(var i=0; i<currentOrgs.length; i++){
        var treeNode = treeObj.getNodeByParam("id",  currentOrgs[i] + "_org");
        treeObj.checkNode(treeObj.getNodeByParam("id",  currentOrgs[i] + "_org"),  true, false);
        currentOrgsStr += ";" + treeNode.name;
    }
    $("#oldParentOrgName_move").val(currentOrgsStr.substring(1)); // for now ,one staff just belong to one org, but it will belong to multi orgs in the future
}

function moveStaff() {
    if (validateNull($('#newParentOrgId_move').val())) {
        renderInputField(false, "NewParentOrg_move", "不能为空！");
        return false;
    }
    $.ajax({
        url: createUrl("/staff/move"),
        type: "post",
        data: {
            "staffId": $("#staffId_move").val(),
            "targetOrgIds": [$('#newParentOrgId_move').val()]
        },
        dataType: "json",
        traditional: true,
        success: function (msg) {
            $("#modalStaffMove").modal("hide")
            if (msg.success) {
                alertPromptMsgDlg(msg.message, 1, reload);
            } else {
                alertPromptMsgDlg(msg.message, 3);
            }
        }
    });
}


