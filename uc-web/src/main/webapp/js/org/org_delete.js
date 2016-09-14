var content_right = $("#orgManagementRightContent");

$(content_right).on("click", "a[id^='deleteOrg_']", function () {
    var id = $(this).attr("id").replace("deleteOrg_", "");
    alertConfirmationMsgDlgDetail("删除组织","确定要删除此组织","删除", deleteOrg, id);
});
function deleteOrg(id) {
    $.ajax({
        url: createUrl("/org/delete"),
        type: "post",
        data: {orgIds: [id]},
        traditional: true,
        dataType: "json",
        success: function (msg) {
            if (msg.success) {
                alertPromptMsgDlg(msg.message, 1, refreshOrgIndex);
            } else {
                alertPromptMsgDlg(msg.message, 2, null);
            }
        }
    });
}
/** This is useless temporary ***/

function forwardToDelete() {
    $("#popUserOrOrgDelete").modal({backdrop: 'static', keyboard: false, remote: createUrl("/org/forwardToDelete")});
    $('#popUserOrOrgMove .modal-dialog').css('width', '560px');
    $('#popUserOrOrgMove .modal-dialog').css('height', '600px');
}

function readyDelete() {
    $("#search_org_delete").keyup(function (even) {
        getNodesByFuzzy(even, "resultUL_delete", "resultDiv_delete", "search_org_delete", $("#divSearch_delete").width() - 3, "org", "treeOrgForDelete", searchFilter);
    });

    $("#search_org_delete").keydown(function (e) {
        e = e || window.event;
        //回车
        if (e.keyCode == 13) {
            var nodeId = $("#resultUL_delete > li.selectLi").attr('nodeId');
            focusNodeByIdAndName(nodeId, "treeOrgForDelete");
            return false;
        }
    });

    var treeNodes = new Array();
    var deleteSetting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: "0_org"
            }
        },
        callback: {
            onClick: onClickTreeNodeForDelete
        },
        view: {
            showIcon: false
        }
    };
    $.ajax({
        url: createUrl("/org/showTreeNodesForIndex"),
        type: "post",
        async: true,
        dataType: "json",
        success: function (response) {
            $.each(response.data, function (i, item) {
                if (item.type == 'org') {
                    treeNodes.push(new OrgNode(item.id + "_" + item.type, item.pid + "_" + item.parentType,
                        item.name, item.isParent, item.type, item.pinyin, item.pinyinAlia, item.mail, item.id, item.pid
                    ));
                }
            });
            $.fn.zTree.init($("#treeOrgForDelete"), deleteSetting, treeNodes).setting.check.chkboxType = {"Y": "", "N": ""};
            //默认展开第一个节点
            var treeObj = $.fn.zTree.getZTreeObj("treeOrgForDelete");
            if (null != treeObj) {
                var treenode = treeObj.getNodesByFilter(function (node) {
                    return node.level == 0
                });
                treeObj.expandNode(treenode[0], true, true, true);
            }
        }
    });

    //模态窗口隐藏
    $('#popUserOrOrgDelete').on('hide.bs.modal', function () {
        $(this).removeData("bs.modal");
    });
}

function onClickTreeNodeForDelete(event, treeId, treeNode) {
    $("#search_org_delete").val("");
    $("#resultDiv_delete").hide();
    $("#resultUL_delete").html("");
}
