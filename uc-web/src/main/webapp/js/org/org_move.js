/***
 * ///////////////////////////////////// Move-Module //////////////////////////////////////////////////////////////
 * */

function readyMove() {
    $("#search_org_move").keyup(function (even) {
        getNodesByFuzzy(even, "resultUL_move", "resultDiv_move", "search_org_move", $("#divSearch_move").width() - 3, "org", "treeOrgForMove", searchFilter);
    });

    $("#search_org_move").keydown(function (e) {
        e = e || window.event;
        //回车
        if (e.keyCode == 13) {
            var nodeId = $("#resultUL_move > li.selectLi").attr('nodeId');
            var nodeName = $("#resultUL_move > li.selectLi").text();

            focusNodeByIdAndName(nodeId, "treeOrgForMove");
            return false;
        }
    });

    var params = {
        "moveOrgId": $("#move_org_id").val()
    };

    var treeNodes = new Array();
    var moveSetting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: "0_org"
            }
        },
        callback: {
            onClick: onClickTreeNodeForMove
        },
        view: {
            showIcon: false
        }
    };
    $.ajax({
        url: createUrl("/org/showTreeNodesForMove"),
        type: "post",
        async: true,
        data: params,
        dataType: "json",
        success: function (response) {
            $.each(response.data, function (i, item) {
                treeNodes.push(new OrgNode(item.id + "_" + item.type, item.pid + "_" + item.parentType,
                    item.name, item.isParent, item.type, item.pinyin, item.pinyinAlia, item.mail, item.id, item.pid
                ));
            });
            $.fn.zTree.init($("#treeOrgForMove"), moveSetting, treeNodes);

            //默认展开第一个节点
            var treeObj = $.fn.zTree.getZTreeObj("treeOrgForMove");
            if (null != treeObj) {
                var treenode = treeObj.getNodesByFilter(function (node) {
                    return node.level == 0
                });
                treeObj.expandNode(treenode[0], true, true, true);
            }
        }
    });
    //模态窗口隐藏
    $('#popUserOrOrgMove').on('hide.bs.modal', function () {
        $(this).removeData("bs.modal");
    });
}

function onClickTreeNodeForMove(event, treeId, treeNode) {
    $("#move_target").attr("value", treeNode.id.replace("_org", ""));
    $("#search_org_move").val("");
    $("#resultDiv_move").hide();
    $("#resultUL_move").html("");
}
function forwardToMove() {
    var treeObj = $.fn.zTree.getZTreeObj("orgTreeForIndex");
    var nodes = treeObj.getSelectedNodes();
    var treeNode = nodes[0];

    if (treeNode.pid == "0_org") {
        alertPromptMsgDlg("根节点部门不允许移动！", 2);
        return;
    }

    if (treeNode.type == "staff") {
        forwardToMoveStaff(treeNode.id.replace("_staff", ""));
    } else {
        forwardToMoveOrg(treeNode.id.replace("_org", ""));
    }
}
function forwardToMoveOrg(orgId) {
    $("#popUserOrOrgMove").modal({backdrop: 'static', keyboard: false, remote: createUrl("/org/forwardToMove/" + orgId)});
    $('#popUserOrOrgMove .modal-dialog').css('width', '560px');
    $('#popUserOrOrgMove .modal-dialog').css('height', '600px');
}

function hideIconForMove(treeId, treeNode) {
    return treeNode.pid == "0_org";
};

function submitMove() {
    var treeObj = $.fn.zTree.getZTreeObj("treeOrgForMove");
    var nodes = treeObj.getSelectedNodes();
    var treeNode = nodes[0];

    if (treeNode == undefined) {
        alertPromptMsgDlg("请选择要移动到的部门！", 2, null);
        return;
    }

    if ($("#move_type").val() == "org") {
        submitMoveOrg(treeNode.id.replace("_org", ""));
    }
}

function submitMoveOrg(targetOrgId) {
    var orgId = $("#move_org_id").val();
    var params = {
        "orgId": orgId,
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
function alertFailMsgDlgForMove(message) {
    $('#promptModalBody_MoveOrg').html("<i id='icon-class' class=' icon-large'/>" + message);
    $('#promptModalBody_MoveOrg').removeClass("alert").removeClass("alert-success").removeClass("alert-warning")
        .removeClass("alert-danger").removeClass("alert-info");
    $('#icon-class').addClass("icon-exclamation-sign custom-warn");
    $('#promptModal_MoveOrg').on(
        'shown.bs.modal',
        function () {
            $('#promptModalContinueBtn_MoveOrg').on(
                'click', function () {
                    $('#closePromtBtn').trigger('click');
                });
            $('#promptModalCancelBtn_MoveOrg').on(
                'click', function () {
                    $('#popUserOrOrgMove').find('.close').trigger('click');
                });
        }).modal('show');
}
