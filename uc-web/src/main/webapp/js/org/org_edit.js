$("#orgManagementRightContent").on("click", "a[id^='editOrg_']", function () {
    var id = $(this).attr("id").replace("editOrg_", "");
    var parentId = $(this).attr("parentId");
    if (parentId == 0) {
        $.ajax({
            url: createUrl("/enterprise/forwardToEdit"),
            type: "get",
            async: true,
            success: function (data) {
                $('#orgManagementRightContent').html(data);
            }
        });
    } else {
        $.ajax({
            url: createUrl("/org/forwardToEdit/" + id),
            type: "get",
            async: true,
            success: function (data) {
                $('#orgManagementRightContent').html(data);
            }
        });
    }

});


function showOrgTreeForOrgEdit() {
    $("#selectOrgTree_editOrg").width($("#orgName_edit").width() + 12);
    $('#selectOrgTree_editOrg').toggle();
}

function readyEditOrg() {
    showOrgTreeForEditOrg("selectOrgTree_editOrg", onClickOrgEdit);
    $("#orgName_edit").blur(function () {
        var value = $("#orgName_edit").val().trim();
        if (validateNull(value)) {
            renderInputField(false, "orgName_edit", "名称不能为空");
        } else {
            renderInputField(true, "orgName_edit", "");
        }
    });
}

function editOrg() {
    var addOrgStaffTitleItems = new Array();
    $("tr[name='titleRow']").each(function (index) {
        if ($(this).parent().attr('id') != 'addTitleTbody') {
            var title = $(this).find("input[name=title]").val();
            var staffId = $(this).find("select[name=staff]").val();
            addOrgStaffTitleItems.push({"title": title, "staffId": new Number(staffId)});
        }
    });

    var orgName = $("#orgName_edit").val();
    var mail = $("#mail_edit").val();

    if (validateNull(orgName)) {
        $("#orgName_edit").focus();
        renderInputField(false, "orgName_edit", "名称不能为空");
    } else if (!validateNull(orgName) && $.trim(orgName).length > 64) {
        $("#orgName_edit").focus();
        renderInputField(false, "orgName_edit", "名称长度不能超过64位");
    } else if (!validateNull(mail) && !isMail(mail)) {
        $("#mail_edit").focus();
        renderInputField(false, "mail_edit", "邮箱格式不正确！");
    } else {
        var params = {
            orgId: $("#orgId_edit").val(),
            parentId: $("#orgId_editOrg").val(),
            name: orgName,
            mail: mail,
            isExtAssistance: $("#isExtAssistance_edit").prop("checked"),
            editOrgRequestItemList: addOrgStaffTitleItems
        };
        $.ajax({
            url: createUrl("/org/edit"),
            type: "post",
            async: false,
            dataType: "json",
            data: JSON.stringify(params),
            contentType: "application/json; charset=utf-8",
            success: function (msg) {
                if (msg.success) {
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
function showOrgTreeForEditOrg(treeId, onClick) {
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
    showTreeNodesForEdit($("#" + treeId), orgSelectTreeSetting);
}
/**
 * 显示单选树
 */
function showTreeNodesForEdit(divTreeId, setting) {
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/org/showTreeNodesForMove"),
        type: "post",
        async: false,
        data: {"moveOrgId": $("#orgId_edit").val()},
        dataType: "json",
        success: function (response) {
            $.each(response.data, function (i, item) {
                if (item.type == 'org') {
                    var name = item.name;
                    if (name.length > 25) {
                        name = name.substring(0, 25) + "...";
                    }
                    treeNodes.push(new OrgNode(item.id + "_" + item.type, item.pid + "_" + item.parentType,
                        name, item.isParent, item.type, item.pinyin, item.pinyinAlia, item.mail, item.id, item.pid
                    ));
                }
            });
            $.fn.zTree.init(divTreeId, setting, treeNodes);
        }
    });
};

function titleAdd() {
    var $objectAfter = $("#orgTitleList");
    $($("#addTitleTbody").html()).appendTo($objectAfter);
}

function titleDelete(valueDOM) {
    $(valueDOM).parent().parent().parent().remove();
}