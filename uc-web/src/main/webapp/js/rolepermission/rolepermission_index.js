/**
 * 异步树设置
 */
var settingForMenuInit = {
    check: {
        enable: true,
        chkStyle: "checkbox",
        chkboxType: {"Y": "ps", "N": "ps"}
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "-1_project"
        }
    },
    edit: {
        enable: false,
        showRemoveBtn: false,
        showRenameBtn: false
    },
    view: {
        showIcon: showMenuIcon
    }
};
function showMenuIcon(treeId, treeNode) {
    return (treeNode.pid == "-1_project" || treeNode.type == "menu");
};

/**
 * 显示菜单结构树
 */
function showMenuTreeForInit() {
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/menu/showTreeNodes?projectId=" + $('#projectId').val()),
        type: "get",
        async: true,
        dataType: "json",
        success: function (response) {
            if ((!response.success) && response.success == false) {
                alertPromptMsgDlg(response.message, 2, null);
                return;
            }
            $.each(response.data, function (i, item) {
                treeNodes.push(createPermissionNode(item));
                $.fn.zTree.init($("#treeForRolePermission"), settingForMenuInit, treeNodes);
            });

            //默认展开第一个节点
            var treeObj = $.fn.zTree.getZTreeObj("treeForRolePermission");
            if (null != treeObj) {
                var treenode = treeObj.getNodesByFilter(function (node) {
                    return node.level == 0
                });
                ajax('get', null, setUrlParam(setUrlParam("/rolepermission/list", "roleId", $('#roleId').val()), "projectId", $('#projectId').val()), function (response) {
                    if (response.data) {
                        response.data.forEach(function (item) {
                            if (item.type == 'menu') {
                                var menuNode = treeObj.getNodeByParam("id", item.menuId + "_menu");
                                treeObj.checkNode(menuNode, true, false);
                                //$('#' + menuNode.tId + '_check').trigger('click');
                            }
                        });
                    }
                });
                treeObj.expandNode(treenode[0], true, true, true);
                treeObj.selectNode(treenode[0]);
            }
        }
    });
}
var settingForInit = {
    check: {
        enable: true,
        chkStyle: "checkbox",
        chkboxType: {"Y": "ps", "N": "ps"}
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "-1_project"
        }
    },
    view: {
        showIcon: showResourceIcon
    }
};
function showResourceIcon(treeId, treeNode) {
    return (treeNode.pid == "-1_project" || treeNode.type == "resource" || treeNode.type == "operation");
};

/**
 * 显示菜单结构树
 */
function showResourceTreeForInit() {
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/resourceoperation/showAuthorizeTreeNodes?projectId=" + $('#projectId').val()),
        type: "get",
        async: true,
        dataType: "json",
        success: function (response) {
            if ((!response.success) && response.success == false) {
                alertPromptMsgDlg(response.message, 2, null);
                return;
            }
            $.each(response.data, function (i, item) {
                treeNodes.push(createPermissionNode(item));
                $.fn.zTree.init($("#treeForRolePermission"), settingForInit, treeNodes);
            });

            //默认展开第一个节点
            var treeObj = $.fn.zTree.getZTreeObj("treeForRolePermission");
            if (null != treeObj) {
                var treenode = treeObj.getNodesByFilter(function (node) {
                    return node.level == 0
                });
                ajax('get', null, setUrlParam(setUrlParam("/rolepermission/list", "roleId", $('#roleId').val()), "projectId", $('#projectId').val()), function (response) {
                    if (response.data) {
                        response.data.forEach(function (item) {
                            if (item.type == 'resource_operation') {
                                var resourceNode = treeObj.getNodeByParam("id", item.resourceOperationId + "_resource_operation");
                                treeObj.checkNode(resourceNode, true, false);
                            }
                        });
                    }

                });
                treeObj.expandNode(treenode[0], true, true, true);
                treeObj.selectNode(treenode[0]);
            }
        }
    });
}
/**
 * 菜单加载前准备工作
 */
function readyPermissionIndex() {
    var permissionType = $('#permissionType').val();
    if (permissionType == 'menu') {
        showMenuTreeForInit();
        searchInit("search_menu");
    } else if (permissionType == 'resource_operation') {
        showResourceTreeForInit();
        searchInit("search_resource");
    }
}
