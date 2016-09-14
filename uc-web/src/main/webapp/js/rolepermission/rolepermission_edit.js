function submitPermission() {
    var treObj = $.fn.zTree.getZTreeObj("treeForRolePermission");
    var checkedNodes = treObj.getCheckedNodes();
    var permissionType = $('#permissionType').val();
    var menuIdList = new Array();
    var resourceOperationItemList = new Array();
    var nodesLength = checkedNodes.length;
    for (var i = 0; i < nodesLength; i++) {
        if (checkedNodes[i].type == 'menu')
            menuIdList.push(checkedNodes[i].originalId);
        else if (checkedNodes[i].type == 'resource_operation') {
            resourceOperationItemList.push(checkedNodes[i].originalId);
        }
    }

    $.ajax({
        url: createUrl("/rolepermission/edit"),
        type: "post",
        data: JSON.stringify({
            "roleId": $("#roleId").val(),
            "menuIdList": menuIdList,
            "resourceOperationIdList": resourceOperationItemList,
            "type": permissionType,
            "projectId": $('#projectId').val()
        }),
        dataType: "json",
        traditional: true,
        contentType: "application/json; charset=utf-8",
        success: function (msg) {
            if (msg.success) {
                alertPromptMsgDlg(msg.message, 1, reload);
            } else {
                alertPromptMsgDlg(msg.message, 3);
            }
        }
    });
}
