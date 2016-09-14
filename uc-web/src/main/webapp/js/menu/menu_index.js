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
        drag: {
            autoExpandTrigger: true,
            prev: dropPrev,
            inner: dropInner,
            next: dropNext
        },
        enable: true,
        showRemoveBtn: false,
        showRenameBtn: false
    },
    callback: {
        onClick: onClickTreeNode,
        beforeDrag: beforeDrag,
        onDrop: onDropNode
    },
    view: {
        showIcon: showIcon
    }
};
function showIcon(treeId, treeNode) {
    return ( treeNode.type == "project" || treeNode.type == "menu");
};

/**
 * 显示菜单结构树
 */
function showTreeNodesForInit() {
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
                treeNodes.push(createMenuNode(item));
                $.fn.zTree.init($("#menuTreeForInit"), settingForInit, treeNodes);
            });

            //默认展开第一个节点
            var treeObj = $.fn.zTree.getZTreeObj("menuTreeForInit");
            if (null != treeObj) {
                var treenode = treeObj.getNodesByFilter(function (node) {
                    return node.level == 0
                });
                treeObj.expandNode(treenode[0], true, false, true);
                treeObj.selectNode(treenode[0]);
                treeObj.setting.callback.onClick(null, treenode[0].id, treenode[0]);
            } else {
                //如果树不存在，弹出创建菜单结构窗口
                $("#btnCreateMenu").click();
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
    $("#search_menu").val("");
    $("#resultUL").html("");
    $("#resultDiv").hide();

    //只点击菜单节点有效
    if (treeNode.type == "menu") {
        $('#currentMenuId').val(treeNode.originalId);
        forwardToEditMenu(treeNode.originalId);
    } else {
        $('#divEditContent').html('');
    }
}

/**
 * 菜单加载前准备工作
 */
function readyMenuIndex() {
    setCurrentPageNavigation("headerMenu", "sysLeftNavMenu");
    //显示树
    showTreeNodesForInit();

    $("#search_menu").keyup(function (e) {
        getNodesByFuzzy(e, "resultUL", "resultDiv", "search_menu", $("#divSearch").width() - 2, "", "menuTreeForInit",searchFilter);
    });

    $("#search_menu").keydown(function (e) {
        e = e || window.event;
        //回车
        if (e.keyCode == 13) {
            var nodeId = $("#resultUL > li.selectLi").attr('nodeId');
            var nodeName = $("#resultUL > li.selectLi").text();
            focusNodeByIdAndName(nodeId, "menuTreeForInit");
            return false;
        }
    });
}

function dropPrev(treeId, nodes, targetNode) {
    var pNode = targetNode.getParentNode();
    if (pNode && pNode.dropInner === false) {
        return false;
    } else {
        for (var i = 0, l = curDragNodes.length; i < l; i++) {
            var curPNode = curDragNodes[i].getParentNode();
            if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                return false;
            }
        }
    }
    return true;
}
function dropInner(treeId, nodes, targetNode) {
    if (targetNode && targetNode.dropInner === false) {
        return false;
    } else {
        for (var i = 0, l = curDragNodes.length; i < l; i++) {
            if (!targetNode && curDragNodes[i].dropRoot === false) {
                return false;
            } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
                return false;
            }
        }
    }
    return true;
}
function dropNext(treeId, nodes, targetNode) {
    var pNode = targetNode.getParentNode();
    if (pNode && pNode.dropInner === false) {
        return false;
    } else {
        for (var i = 0, l = curDragNodes.length; i < l; i++) {
            var curPNode = curDragNodes[i].getParentNode();
            if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                return false;
            }
        }
    }
    return true;
}

var curDragNodes;
function beforeDrag(treeId, treeNodes) {
    for (var i = 0, l = treeNodes.length; i < l; i++) {
        if (treeNodes[i].drag === false) {
            curDragNodes = null;
            return false;
        } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
            curDragNodes = null;
            return false;
        }
    }
    curDragNodes = treeNodes;
    return true;
}

function onDropNode(event, treeId, treeNodes, targetNode, moveType, isCopy) {
    if (treeNodes[0].type == 'staff') {
        onDropStaffNode(event, treeId, treeNodes, targetNode, moveType, isCopy);
    }
    if (treeNodes[0].type == 'org') {
        reIndexOrg(event, treeId, treeNodes, targetNode, moveType, isCopy);
    }
}
