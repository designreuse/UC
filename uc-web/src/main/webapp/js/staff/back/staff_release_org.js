
function releaseOrgMapping(staffId, orgId) {
    $.ajax({
        url: createUrl("/staff/releaseOrgMapping"),
        type: "post",
        data: {
            staffId: staffId,
            orgId: orgId
        },
        dataType: "json",
        success: function (msg) {
            if (msg.success) {
                alertPromptMsgDlg(msg.message, 1, reload);
            } else {
                alertPromptMsgDlg(msg.message, 3);
            }
        }
    });
}

function confirmReleaseOrgMapping() {
    var treeObj = $.fn.zTree.getZTreeObj("tree");
    var selectedStaffNodes = treeObj.getSelectedNodes();
    var staffId = selectedStaffNodes[0].originalId;
    var staffName = selectedStaffNodes[0].name;
    var orgNode = selectedStaffNodes[0].getParentNode();
    var orgId = orgNode.originalId;
    var orgName = orgNode.name;
    if (treeObj.getNodesByParam("id", staffId + "_staff").length == 1) {
        alertPromptMsgDlg("用户" + staffName + "只在组织" + orgName + "下存在，不能再移除组织关系", 2);
        return false;
    }
    alertConfirmationMsgDlg("确定将用户" + staffName + "解除与部门" + orgName + "的关系", releaseOrgMapping, staffId, orgId);
}