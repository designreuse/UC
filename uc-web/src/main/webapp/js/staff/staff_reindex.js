
function reIndexStaff(treeId, treeNodes, targetNode) {
    var orgNode = treeNodes[0].getParentNode();
    var staffNodes = findStaffNodesInOrgNode(orgNode);
    var staffIds = new Array();
    staffNodes.forEach(function (node) {
        staffIds.push(node.originalId);
    });
    $.ajax({
        url: createUrl("/staff/reIndex"),
        type: "post",
        data: {
            orgId: orgNode.originalId,
            staffIdsByAsc: staffIds
        },
        traditional: true,
        dataType: "json",
        success: function (msg) {
            if (!msg.success) {
                alertPromptMsgDlg(msg.message, 3);
            }
        }
    });

}