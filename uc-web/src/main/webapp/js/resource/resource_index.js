/**
 /////////////////////////////////////////Common-Module///////////////////////////////////////////////////////////
 **/
/**
 * 异步树设置
 */
var settingForInit = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "-1_project"
        }
    },
    edit: {
        enable: true,
        showRemoveBtn: false,
        showRenameBtn: false
    },
    callback: {
        onClick: onClickTreeNode
    },
    view: {
        showIcon: showIcon
    }
};
function showIcon(treeId, treeNode) {
    return (treeNode.pid == "-1_project" || treeNode.type == "resource");
};

/**
 * 显示菜单结构树
 */
function showTreeNodesForInit() {
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/resource/showTreeNodes?projectId=" + $('#projectId').val()),
        type: "get",
        async: true,
        dataType: "json",
        success: function (response) {
            if ((!response.success) && response.success == false) {
                alertPromptMsgDlg(response.message, 2, null);
                return;
            }
            $.each(response.data, function (i, item) {
                createResourceTreeNode(item, treeNodes);
                $.fn.zTree.init($("#resourceTreeForInit"), settingForInit, treeNodes);
            });

            //默认展开第一个节点
            var treeObj = $.fn.zTree.getZTreeObj("resourceTreeForInit");
            if (null != treeObj) {
                var treenode = treeObj.getNodesByFilter(function (node) {
                    return node.level == 0
                });
                treeObj.expandNode(treenode[0], true, false, true);
                treeObj.selectNode(treenode[0]);
                treeObj.setting.callback.onClick(null, treenode[0].id, treenode[0]);
            } else {
                //如果树不存在，弹出创建菜单结构窗口
                $("#btnCreateResource").click();
            }
        }
    });
}

/**
 * 点击树节点
 * @param e
 * @param treeId
 * @param treeNode
 */
function onClickTreeNode(e, treeId, treeNode) {
    $("#search_resource").val("");
    $("#resultUL").html("");
    $("#resultDiv").hide();

    //只点击菜单节点有效
    if (treeNode.type == "resource") {
        $('#currentResourceId').val(treeNode.originalId);
        forwardToEditResource(treeNode.originalId);
    } else {
        $('#divEditContent').html('');
    }
}

/**
 * 菜单加载前准备工作
 */
function readyResourceIndex() {
    setCurrentPageNavigation("headerResource", "sysLeftNavResource");
    //显示树
    showTreeNodesForInit();

    $("#search_resource").keyup(function (e) {
        getNodesByFuzzy(e, "resultUL", "resultDiv", "search_resource", $("#divSearch").width() - 2, "", "resourceTreeForInit",searchFilter);
    });

    $("#search_resource").keydown(function (e) {
        e = e || window.event;
        //回车
        if (e.keyCode == 13) {
            var nodeId = $("#resultUL > li.selectLi").attr('nodeId');
            var nodeName = $("#resultUL > li.selectLi").text();

            focusNodeByIdAndName(nodeId, "resourceTreeForInit");
            return false;
        }
    });
}

function createResourceNode(treeId) {
    $().dropdown('toggle');
    var zTree = $.fn.zTree.getZTreeObj(treeId), nodes = zTree
        .getSelectedNodes();
    var resourceId = "", resourceName = "";
    nodes.sort(function compare(a, b) {
        return a.originalId - b.originalId;
    });
    for (var i = 0; i < nodes.length; i++) {
        resourceId += nodes[i].originalId + ",";
        resourceName += nodes[i].name + ",";
    }
    if (resourceId.length > 0)
        resourceId = resourceId.substring(0, resourceId.length - 1);
    if (resourceName.length > 0)
        resourceName = resourceName.substring(0, resourceName.length - 1);
    return {resourceId: resourceId, resourceName: resourceName};
}
