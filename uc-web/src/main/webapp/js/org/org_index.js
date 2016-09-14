/**
 * 异步树设置
 */
var settingForInit = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "0_org"
        }
    },
    edit: {
        drag: {
            autoExpandTrigger: true,
            dropPrev: dropPrev
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
        showIcon: false,
        showLine: false,
        addDiyDom: addDiyDom,
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom
    }
};
var IDMark_A = "_a";
function addHoverDom(treeId, treeNode) {
    if (treeNode.originalPid == 0) return;
    if (treeNode.originalId == -1) return;
    var aObj = $("#" + treeNode.tId + IDMark_A);

    if ($("#diyBtn1_" + treeNode.id).length > 0) return;
    if ($("#diyBtn2_" + treeNode.id).length > 0) return;
    var editStr = "<div style=\"float:right\"><a id=\"diyBtn1_" + treeNode.id + "\" onclick=\"javascript:movePrev('" + treeNode.tId + "');\" style=\"width: 10px;margin-right: -4px;margin-top: 5px;\">" +
        "<img style=\"width:10px;height:15px;margin-right:-2px\" src=\"../../../images/tree/up.png\" title=\"上移\">" +
        "</a>" +
        "<a id=\"diyBtn2_" + treeNode.id + "\" onclick=\"javascript:moveNext('" + treeNode.tId + "');\" style=\"width: 10px;margin-right: 11px;margin-top: 5px;\">" +
        "<img style=\"width:10px;height:15px\" src=\"../../../images/tree/down.png\" title=\"下移\">" +
        "</a></div>";
    aObj.append(editStr);

}

function movePrev(tid) {
    var treeObj = $.fn.zTree.getZTreeObj("orgTreeForIndex");
    var curNode = treeObj.getNodeByTId(tid);
    if (curNode.getPreNode()) {
        treeObj.moveNode(curNode.getPreNode(), curNode, "prev");
        reIndexWhenMoveOrg(treeObj, curNode);
    }

}
function moveNext(tid) {
    var treeObj = $.fn.zTree.getZTreeObj("orgTreeForIndex");
    var curNode = treeObj.getNodeByTId(tid);
    if (curNode.getNextNode() && curNode.getNextNode().originalId != -1) { // 后面的节点不是禁用人员
        treeObj.moveNode(curNode.getNextNode(), curNode, "next");
        reIndexWhenMoveOrg(treeObj, curNode);
    }

}

function reIndexWhenMoveOrg(treeObj, curNode) {
    function filter(node) {
        return (node.type == 'org' && node.pid == curNode.pid)
    }

    var subOrgNodes = treeObj.getNodesByFilter(filter);
    var reIndexOrgRequestItemList = new Array();
    $.each(subOrgNodes, function (i, node) {
        if (node) {
            reIndexOrgRequestItemList.push({orgId: node.originalId, index: i + 1});
        }
    });
    $.ajax({
        url: createUrl("/org/reIndex"),
        type: "post",
        async: true,
        dataType: "json",
        traditional: true,
        data: JSON.stringify({parentOrgId: curNode.getParentNode().originalId, reIndexOrgRequestItemList: reIndexOrgRequestItemList}),
        contentType: "application/json; charset=utf-8",
        success: function (data) {
            if (data.success) {
            } else {
                alertPromptMsgDlg(data.message, 2);
            }
        }
    });
}
function removeHoverDom(treeId, treeNode) {
    if (treeNode.originalPid == 0) return;

    $("#diyBtn1_" + treeNode.id).unbind().remove();
    $("#diyBtn2_" + treeNode.id).unbind().remove();

}
var settingForSearch = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "0_org"
        }
    },
    edit: {
        drag: {
            autoExpandTrigger: true,
            dropPrev: dropPrev
        },
        enable: true,
        showRemoveBtn: false,
        showRenameBtn: false
    },
    callback: {
        onClick: onClickSearchTreeNode,
        beforeDrag: beforeDrag,
        onDrop: onDropNode
    },
    view: {
        showIcon: false
    }
};


/**
 * 定义树节点
 */
function StaffNode(id, pid, name, isParent, type, gender, pinyin, pinyinAlia, mail, originalId, originalPid, extensionNumber, phoneIP) {
    this.id = id;
    this.pid = pid;
    this.originalId = originalId;
    this.originalPid = originalPid;
    this.pidNotChangedWhenDrag = pid;
    this.gender = gender;
    this.pinyin = pinyin;
    this.pinyinAlia = pinyinAlia;
    this.fullname = name;
    this.mail = mail;
    this.open = true;
    this.childOuter = false;
    this.dropInner = true;
    this.dropRoot = true;
    this.extensionNumber = extensionNumber;
    this.phoneIP = phoneIP;
    if (name.length != null && name.length > 25) {
        this.name = name.substring(0, 25) + "...";
    } else {
        this.name = name
    }
    this.isParent = isParent;
    this.type = type;

    if (this.type == "staff") {
        if (this.gender == "0") {
            this.iconSkin = "sIconWomen";
        } else {
            this.iconSkin = "sIconMen";
        }
    }

    if (this.pid == "0_org") {
        this.iconSkin = "pIcon";
    }
}


function OrgNode(id, pid, name, isParent, type, pinyin, pinyinAlia, mail, originalId, originalPid) {
    this.id = id;
    this.pid = pid;
    this.originalId = originalId;
    this.originalPid = originalPid;
    this.pinyin = pinyin;
    this.pinyinAlia = pinyinAlia;
    this.mail = mail;
    this.fullname = name;

    if (name.length > 25) {
        this.name = name.substring(0, 25) + "...";
    } else {
        this.name = name
    }
    this.isParent = isParent;
    this.type = type;

    if (this.pid == "0_org") {
        this.iconSkin = "pIcon";
    }
}

/**
 * 显示组织结构树
 */
function showTreeNodesForInit() {
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/org/treeNodesWithForbiddenStaff"),
        type: "post",
        async: true,
        dataType: "json",
        success: function (response) {
            if ((!response.success) && response.success == false) {
                alertPromptMsgDlg(response.message, 2, null);
                return;
            }
            $.each(response.data, function (i, item) {
                treeNodes.push(new StaffNode(item.id + "_" + item.type, item.pid + "_" + item.parentType,
                    item.name, item.isParent,
                    item.type, item.gender, item.pinyin, item.pinyinAlia, item.mail,
                    item.id, item.pid, item.extensionNumber, item.phoneIP
                ));
                $.fn.zTree.init($("#orgTreeForIndex"), settingForInit, treeNodes);
            });

            //默认展开第一个节点
            var treeObj = $.fn.zTree.getZTreeObj("orgTreeForIndex");
            if (null != treeObj) {
                var treenode = treeObj.getNodesByFilter(function (node) {
                    return node.level == 0
                });
                treeObj.expandNode(treenode[0], true, true, true);
                treeObj.selectNode(treenode[0]);
                treeObj.setting.callback.onClick(null, treenode[0].id, treenode[0]);
            } else {
                //如果树不存在，弹出创建组织结构窗口
                $("#btnCreateOrg").click();
            }
        }
    });
}

function showOrgStaffTreeNodesForSearch() {
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/org/showOrgStaffTreeNodes"),
        type: "post",
        async: true,
        dataType: "json",
        success: function (response) {
            if ((!response.success) && response.success == false) {
                return;
            }
            $.each(response.data, function (i, item) {
                treeNodes.push(new StaffNode(item.id + "_" + item.type, item.pid + "_" + item.parentType,
                    item.name, item.isParent,
                    item.type, item.gender, item.pinyin, item.pinyinAlia, item.mail,
                    item.id, item.pid, item.extensionNumber, item.phoneIP
                ));
                $.fn.zTree.init($("#staffOrgTreeForSearch"), settingForSearch, treeNodes);
            });
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
    $('#currentSearchType').val('org')
    $('#currentSelectedOrg').val(treeNode.originalId)
    goStaffListPage(1, 10)
}

function onClickSearchTreeNode(e, treeId, treeNode) {
    $("#search_org").val("");
    $("#resultUL").html("");
    $("#resultDiv").hide();
    if (treeNode.type == 'org') {
        $('#currentSearchType').val('org');
        $('#currentSelectedOrg').val(treeNode.originalId)
    } else if (treeNode.type == 'staff') {
        $('#currentSearchType').val('staff');
        $('#currentSelectedStaff').val(treeNode.originalId)
    }
    goStaffListPage(1, 10)
}
function goStaffListPage(pageNo, pageSize) {
    var status = new Array();
    status.push(1);
    status.push(-1);
    var data;
    if ($('#currentSearchType').val() == 'org') {
        data = JSON.stringify({
            "initStatusList": status, "pageNo": pageNo, "pageSize": pageSize == undefined ? 10 : pageSize,
            "orgIdFilter": $('#currentSelectedOrg').val(),
            "orgIdDetail": $('#currentSelectedOrg').val()
        })
    } else if ($('#currentSearchType').val() == 'staff') {
        data = JSON.stringify({
            "initStatusList": status, "pageNo": pageNo, "pageSize": pageSize == undefined ? 10 : pageSize,
            "staffIdFilter": $('#currentSelectedStaff').val(),
            "orgIdDetail": $('#currentSelectedOrg').val()
        })
    }
    $.ajax({
        url: createUrl("/staff/search"),
        type: "post",
        async: true,
        data: data,
        contentType: "application/json; charset=utf-8",
        success: function (ret) {
            var pageModelRet = executePageModel(ret);
            document.getElementById('orgManagementRightContent').innerHTML = template('orgManagementRightContentTemp', pageModelRet);

            $("#orgManagementRightContent a[id^='more_']").each(function () {
                var staffId = $(this).attr("id").replace("more_", "");
                $(this).popover({
                    trigger: 'manual',
                    placement: 'bottom', //top, bottom, left or right
                    html: 'true',
                    content: context(staffId)
                }).on("click", function () {
                    var _this = this;
                    $(this).popover("show");
                    $(this).siblings(".popover").on("mouseleave", function () {
                        $(_this).popover('hide');
                    });
                }).on("mouseleave", function () {
                    var _this = this;
                    setTimeout(function () {
                        if (!$(".popover:hover").length) {
                            $(_this).popover("hide")
                        }
                    }, 100);
                });
            });
            function context(staffId) {
                return $('#orgManagementRightContent #operationDiv_' + staffId).html();
            };
        }
    });
}


$("#orgManagementRightContent").on("click", "#searchStaffOrg", function () {
    searchStaffOrg();
});

function searchStaffOrg() {
    var searchKey = $("#searchKey").val();
    var status = new Array();
    status.push(1);
    status.push(-1);
    $.ajax({
        url: createUrl("/staff/search"),
        type: "post",
        async: true,
        data: JSON.stringify({"initStatusList": status, "orgId": $('#currentSelectedOrg').val(), "searchKey": searchKey, "pageNo": 1, "pageSize": 10}),
        contentType: "application/json; charset=utf-8",
        success: function (ret) {
            var pageModelRet = executePageModel(ret);
            document.getElementById('orgManagementRightContent').innerHTML = template('orgManagementRightContentTemp', pageModelRet);
        }
    });
}


/**
 * 组织加载前准备工作
 */
function readyOrgIndex() {
    var orgManagementContent = $("#orgManagementRightContent");
    showTreeNodesForInit();
    $(orgManagementContent).on('change', "#pageSizeSelect", function () {
        goStaffListPage(1, $(this).val())
    });
    $(orgManagementContent).on('change', "#pageNoSelect", function () {
        goStaffListPage($(this).val(), 10)
    });
    showOrgStaffTreeNodesForSearch();
    $(orgManagementContent).on('keyup', "#search_org", function (e) {
        getNodesByFuzzy(e, "resultUL", "resultDiv", "search_org", $("#divSearch").width() - 2, "", "staffOrgTreeForSearch", searchFilter);
    });

    $(orgManagementContent).on('keydown', "#search_org", function (e) {
        e = e || window.event;
        //回车
        if (e.keyCode == 13) {
            var nodeId = $("#resultUL > li.selectLi").attr('nodeId');
            var nodeName = $("#resultUL > li.selectLi").text();

            focusNodeByIdAndName(nodeId, "staffOrgTreeForSearch");
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
//function dropInner(treeId, nodes, targetNode) {
//    if (targetNode && targetNode.dropInner === false) {
//        return false;
//    } else {
//        for (var i = 0, l = curDragNodes.length; i < l; i++) {
//            if (!targetNode && curDragNodes[i].dropRoot === false) {
//                return false;
//            } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
//                return false;
//            }
//        }
//    }
//    return true;
//}
//function dropNext(treeId, nodes, targetNode) {
//    var pNode = targetNode.getParentNode();
//    if (pNode && pNode.dropInner === false) {
//        return false;
//    } else {
//        for (var i = 0, l = curDragNodes.length; i < l; i++) {
//            var curPNode = curDragNodes[i].getParentNode();
//            if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
//                return false;
//            }
//        }
//    }
//    return true;
//}

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
    if (!moveType)return;
    if (treeNodes[0].type == 'staff') {
        onDropStaffNode(event, treeId, treeNodes, targetNode, moveType, isCopy);
    }
    if (treeNodes[0].type == 'org') {
        if (moveType == 'inner') {
            moveOrg(targetNode.originalId, treeNodes[0].originalId);
        } else { // 否则就是跨部门的移动
            if (targetNode.pid == treeNodes[0].pidNotChangedWhenDrag) { // 如果目标节点和要移动的节点的父节点一样，表示是在同一个部门内部移动
                reIndexOrg(event, treeId, treeNodes, targetNode, moveType, isCopy);
            } else { // 否则就是跨部门的移动
                moveOrg(targetNode.originalId, treeNodes[0].originalId);
            }
        }
    }
}
function executePageModel(ret) {
    var totalPages = Math.ceil(ret.staffPageModel.total / ret.staffPageModel.pageSize);
    var pageArray = getPageNoArray(ret.staffPageModel.pageNo, totalPages);
    return {"pageModel": ret.staffPageModel, "org": ret.org, "totalPages": totalPages, "pageArray": pageArray};
}

function moveOrg(targetOrgId, curentOrgId) {
    var params = {
        "orgId": curentOrgId,
        "targetOrgId": targetOrgId
    };


    $.ajax({
        url: createUrl("/org/move"),
        type: "post",
        data: params,
        dataType: "json",
        success: function (msg) {
            if (msg.success) {
                alertPromptMsgDlg(msg.message, 1, refreshOrgIndex);
            } else {
                alertFailMsgDlgForMove(msg.message);
            }
        }
    });
}

function generateNavi(pageModel) {
    var totalPages = Math.ceil(pageModel.total / pageModel.pageSize);
    var pageArray = getPageNoArray(pageModel.pageNo, totalPages);
    var obj = {"pageModel": pageModel, "totalPages": totalPages, "pageArray": pageArray};
    var pageHtml = template('pageTemp', obj);
    document.getElementById('navDiv').innerHTML = pageHtml;
}

function getPageNoArray(pageNo, totalPage) {
    var b = pageNo - 2;
    if (b < 1) b = 1;
    if (b + 5 > totalPage) {
        b = totalPage - 5;
        if (b < 1) b = 1;
    }
    var pages = new Array();
    for (var i = b; i < (b + 5) && i <= totalPage; i++) {
        pages.push(i);
    }
    return pages;
}