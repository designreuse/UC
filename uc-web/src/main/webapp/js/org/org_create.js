
function showOrgTreeForOrgCreate() {
    $('#selectOrgTree_createOrg').toggle();
}

function forwardToCreateOrg() {
    $("#modalPopupCreateOrg").modal({backdrop: 'static', keyboard: false, remote: createUrl("/org/forwardToCreate/" + $('#currentSelectedOrg').val())});
}

function readyCreateOrg() {
    showOrgTreeForCreateOrg("selectOrgTree_createOrg", onClickOrgCreate);

    $("#orgName_create").blur(function () {
        var value = $("#orgName_create").val().trim();
        if (validateNull(value)) {
            renderInputField(false, "orgName_create", "不能为空");
        } else if (!validName(value)) {
            renderInputField(false, "orgName_create", "不合法");
        } else {
            renderInputField(true, "orgName_create", "");
        }
    });

    $("#index_create").blur(function () {
        var value = $("#index_create").val();
        if (!validateNull(value)) {
            if (!isNumber(value)) {
                renderInputField(false, "index_create", "显示顺序只能为数字！");
            } else {
                renderInputField(true, "index_create", "");
            }
        } else {
            restoreInputField("index_create");
        }
    });
    $("#mail_create").blur(function () {
        var mail = $("#mail_create").val();
        if (!validateNull(mail) && !isMail(mail)) {
            $("#mail_create").focus();
            renderInputField(false, "Mail_create", "邮箱格式不正确！");
        } else {
            restoreInputField("Mail_create");
        }
    });

}

function createOrg() {
    var treeObj = $.fn.zTree.getZTreeObj("selectOrgTree_createOrg");
    var parentOrgId = $("#orgId_orgCreateForm").val();

    if (null != treeObj) {
        var nodes = treeObj.getSelectedNodes();
        if (nodes.length == 0) {
            renderInputField(false, "ParentOrg_create", "不能为空");
            return;
        }else{
            restoreInputField("ParentOrg_create");
        }
    }

    var orgName = $("#orgName_create").val();
    var mail = $("#mail_create").val();
    var isExtAssistance = $("#isExtAssistance_create").prop("checked");
    if (validateNull(orgName)) {
        $("#orgName_create").focus();
        renderInputField(false, "OrgName_create", "不能为空");
    } else if (!validName(orgName)) {
        $("#orgName_create").focus();
        renderInputField(false, "OrgName_create", "不合法");
    } else if (!validateNull(orgName) && $.trim(orgName).length > 64) {
        $("#orgName_create").focus();
        renderInputField(false, "OrgName_create", "长度不能超过64位");
    } else if (!validateNull(mail) && !isMail(mail)) {
        $("#mail_create").focus();
        renderInputField(false, "Mail_create", "邮箱格式不正确！");
    } else {
        var params = {
            "name": orgName,
            "parentId": parentOrgId,
            "mail": mail,
            "isExtAssistance": isExtAssistance
        };
        $.ajax({
            url: createUrl("/org/create"),
            type: "post",
            async: false,
            dataType: "json",
            data: params,
            success: function (msg) {
                if (msg.success) {
                    $('#modalPopup').modal('hide');
                    alertPromptMsgDlg(msg.message, 1, refreshOrgIndex);
                } else {
                    alertPromptMsgDlg(msg.message, 2);
                }
            }
        });
    }
}


/**
 * 显示多选组织结构树
 * @param treeId
 */
function showOrgTreeForCreateOrg(treeId, onClick) {
    var orgSelectTreeSetting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: "0_org"
            }
        },
        callback: {
            onClick: onClick,
            onNodeCreated: zTreeOnNodeCreated
        },
        view: {
            showIcon: false,
            showLine: false,
            addDiyDom: addDiyDom
        }
    };
    showTreeNodes($("#" + treeId), orgSelectTreeSetting);
}