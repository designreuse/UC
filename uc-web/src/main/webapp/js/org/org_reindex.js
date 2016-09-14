/**
 /////////////////////////////////////////ReIndex-Module///////////////////////////////////////////////////////////
 * */

function reIndexOrg(event, treeId, treeNodes, targetNode, moveType, isCopy) {
    if (!targetNode)return;
    var pid = treeNodes[0].pid;

    function filter(node) {
        return (node.type == 'org' && node.pid == pid)
    }

    var treeObj = $.fn.zTree.getZTreeObj("orgTreeForIndex");
    var subOrgNodes = treeObj.getNodesByFilter(filter); // 查找节点集合
    var reIndexOrgRequestItemList = new Array();
    $.each(subOrgNodes, function (i, node) {
        if (node) {
            reIndexOrgRequestItemList.push({orgId: new Number(node.originalId), index: i + 1});
        }
    });
    $.ajax({
        url: createUrl("/org/reIndex"),
        type: "post",
        async: true,
        dataType: "json",
        traditional: true,
        data: JSON.stringify({parentOrgId: pid.replace("_org", ""), reIndexOrgRequestItemList: reIndexOrgRequestItemList}),
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            if (data.success) {
            } else {
                alertPromptMsgDlg(data.message, 2);
            }
        }
    });
}
