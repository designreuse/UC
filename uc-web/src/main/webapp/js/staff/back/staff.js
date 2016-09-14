//function getNodesName(nodes) {
//    var length = nodes.length;
//    var selectNodeStr = "";
//    for (var i = 0; i < length; i++) {
//        selectNodeStr += nodes[i].name + ",";
//    }
//    if (!validateNull(selectNodeStr)) {
//        selectNodeStr = selectNodeStr.substr(0, selectNodeStr.length - 1);
//    }
//    return selectNodeStr;
//}
//
//function defaultCheckOrg() {
//    var selectedNode = findTreeSelectNodes("tree")[0];
//    var orgNode = (selectedNode.type == "org") ? selectedNode : selectedNode.getParentNode();
//    var orgTreeCreate = $.fn.zTree.getZTreeObj("treeTrade_create");
//    var createOrgNode = orgTreeCreate.getNodeByParam("id", orgNode.id);
//    orgTreeCreate.checkNode(createOrgNode);
//    $("#org_create").val(createOrgNode.name);
//}
//
//function readyCreateStaff() {
//    listPhoneInput("inputList_creat", "addMobilePhone_create", "mobilePhone_create");
//    $("#birthday_create, #entryDate_create").datetimepicker({
//        autoclose: 1,
//        minView: 2,
//        format: 'yyyy-mm-dd'
//        //TODO 国际化
//        //language: $("#browserLang").val().replace("_", "-")
//    });
//
//    showOrgTreeForStaff("treeTrade_create");
//    $("#treeTrade_create").hide();
//    $("#orgDropdownBtn_create").click(function () {
//        $("#treeTrade_create").show();
//    });
//
//    defaultCheckOrg();
//
//    $("#treeTrade_create").mouseleave(function () {
//        $(this).hide();
//        var nodes = findTreeCheckNodes("treeTrade_create");
//        var selectNodeStr = getNodesName(nodes);
//        $("#org_create").val(selectNodeStr);
//        validateStaffOrg("org_create");
//    });
//
//    $("#username_create").blur(function () {
//        validateStaffUsername("username_create");
//    });
//    $("#name_create").blur(function () {
//        validateStaffName("name_create");
//    });
//    $("#email_create").blur(function () {
//        validateStaffEmail("email_create");
//    });
//    $("#workPhone_create").blur(function () {
//        validateStaffWorkPhone("workPhone_create");
//    });
//    $("div.addListItem > .addButton").blur(function () {
//        validateStaffMobilePhone("addListInput");
//    });
//    $("#createStaffBtn").click(function () {
//        createStaff();
//        return false;
//    })
//}
//
//function releaseOrgMapping(staffId, orgId) {
//    $.ajax({
//        url: createUrl("/staff/releaseOrgMapping"),
//        type: "post",
//        data: {
//            staffId: staffId,
//            orgId: orgId
//        },
//        dataType: "json",
//        success: function (msg) {
//            if (msg.success) {
//                alertPromptMsgDlg(msg.message, 1, reload);
//            } else {
//                alertPromptMsgDlg(msg.message, 3);
//            }
//        }
//    });
//}
//
//function confirmReleaseOrgMapping() {
//    var treeObj = $.fn.zTree.getZTreeObj("tree");
//    var selectedStaffNodes = treeObj.getSelectedNodes();
//    var staffId = selectedStaffNodes[0].originalId;
//    var staffName = selectedStaffNodes[0].name;
//    var orgNode = selectedStaffNodes[0].getParentNode();
//    var orgId = orgNode.originalId;
//    var orgName = orgNode.name;
//    if (treeObj.getNodesByParam("id", staffId + "_staff").length == 1) {
//        alertPromptMsgDlg("用户" + staffName + "只在组织" + orgName + "下存在，不能再移除组织关系", 2);
//        return false;
//    }
//    alertConfirmationMsgDlg("确定将用户" + staffName + "解除与部门" + orgName + "的关系", releaseOrgMapping, staffId, orgId);
//}
//
//function changeStaffStatus(status) {
//    if (status != 'lock' || status != 'unlock' || status != 'recycle') {
//        status == 'unlock';
//    }
//    $.ajax({
//        url: createUrl("/staff/" + status),
//        type: "post",
//        async: false,
//        data: {staffId: $("#staffId_edit").val()},
//        dataType: "json",
//        traditional: true,
//        success: function (msg) {
//            if (msg.success) {
//                alertPromptMsgDlg(msg.message, 1, reload);
//            } else {
//                alertPromptMsgDlg(msg.message, 2);
//            }
//        }
//    });
//}
//
//function readyEditStaff() {
//    listPhoneInput("inputList_edit", "addMobilePhone_edit", "mobilePhone_edit");
//    $("#birthday_edit, #entryDate_edit").datetimepicker({
//        autoclose: 1,
//        minView: 2,
//        bootcssVer: 3,
//        format: 'yyyy-mm-dd'
//        //TODO 国际化
//        //language: $("#browserLang").val().replace("_", "-")
//    });
//
//    $("#btnResetPassWord").click(function () {
//        confirmResetStaffPassword();
//    });
//    $("#btnReleaseOrgMapping").click(function () {
//        confirmReleaseOrgMapping();
//    });
//    showOrgTreeForStaff("treeTrade_edit");
//    $("#treeTrade_edit").hide();
//    $("#orgDropdownBtn_edit").click(function () {
//        $("#treeTrade_edit").show();
//    });
//
//    $("#treeTrade_edit").mouseleave(function () {
//        $("#treeTrade_edit").hide();
//        var nodes = findTreeCheckNodes("treeTrade_edit");
//        selectNodeStr = getNodesName(nodes);
//        $("#org_edit").val(selectNodeStr);
//        validateStaffOrg("org_edit");
//    });
//    $("#username_edit").blur(function () {
//        validateStaffUsername("username_edit");
//    });
//    $("#name_edit").blur(function () {
//        validateStaffName("name_edit");
//    });
//    $("#email_edit").blur(function () {
//        validateStaffEmail("email_edit");
//    });
//    $("#workPhone_edit").blur(function () {
//        validateStaffWorkPhone("workPhone_edit")
//    });
//
//    $("#updateUserBtn").click(function () {
//        editStaff($("#staffId_edit").val());
//    });
//    $("#lockUserBtn").click(function () {
//        alertConfirmationMsgDlg("确定要锁定此帐号", changeStaffStatus, 'lock');
//    });
//    $("#unlockUserBtn").click(function () {
//        alertConfirmationMsgDlg("确定要解锁此帐号", changeStaffStatus, 'unlock');
//    });
//    $("#recycleUserBtn").click(function () {
//        alertConfirmationMsgDlg("确定要回收此帐号", changeStaffStatus, 'recycle');
//    });
//}
//
///**
//* 跳转到添加用户
//*/
//function forwardToCreateStaff() {
//    $("#popUserCreate").modal({backdrop: 'static', keyboard: false, remote: createUrl("/staff/forwardToCreate")});
//    /*var url = createUrl("/staff/forwardToCreate");
//     //获取添加org的页面内容
//     $.get(url, function (data) {
//     $('#modelContent').html(data);
//     });*/
//}
//
///**
//* 显示用户编辑内容
//* @param id
//*/
//function forwardToEditUser(staffId) {
//    var url = createUrl("/staff/forwardToEdit/" + staffId);
//    //获取添加org的页面内容
//    $.get(url, function (data) {
//        $('#divEditContent').html(data);
//    });
//}
//
///**
//* 保存新建用户
//*/
//
//
//
//function createStaff() {
//    if (!validateStaffUsername("username_create") || !validateStaffName("name_create") || !validateStaffEmail("email_create") || !validateStaffWorkPhone("workPhone_create") || !validateStaffOrg("org_create")) {
//        return false;
//    }
//    var mobilePhones = getMobilePhoneListValue("mobilePhone_create");
//    var params = {
//        "username": $("#username_create").val(),
//        "name": $("#name_create").val(),
//        "orgMappings": getOrgIds(findTreeCheckNodes("treeTrade_create")),
//        "email": $("#email_create").val(),
//        "position": $("#position_create").val(),
//        "gender": $("#gender_create").val(),
//        "workPhone": $("#workPhone_create").val(),
//        "mobilePhones": mobilePhones,
//        "birthday": $("#birthday_create").val(),
//        "identity": $("#identity_create").val(),
//        "entryDate": $("#entryDate_create").val(),
//        "isIdentityVisible": $("#isIdentityVisible_create").prop("checked"),
//        "isBirthdayVisible": $("#isBirthdayVisible_create").prop("checked"),
//        "isEntryDateVisible": $("#isEntryDateVisible_create").prop("checked")
//    };
//    $.ajax({
//        url: createUrl("/staff/create"),
//        type: "post",
//        async: false,
//        data: params,
//        dataType: "json",
//        traditional: true,
//        success: function (msg) {
//            if (msg.success) {
//                alertPromptMsgDlg(msg.message, 2, reload);
//            } else {
//                alertPromptMsgDlg(msg.message, 2, null);
//            }
//        }
//    });
//}
//
//
//function mobileListCheck(mobilePhones) {
//    var result = true;
//    $.each(mobilePhones, function (i, e) {
//        if (!isNumber(e)) {
//            $("#mobilePhone_create" + i).focus();
//            renderInputField(false, "mobilePhone_create" + i, "移动电话只能为数字！");
//            result = false;
//        }
//    });
//    return result;
//}
//
//function getOrgIds(orgNodes) {
//    var orgMapping = new Array();
//    var orgLength = orgNodes.length;
//    for (var i = 0; i < orgLength; i++) {
//        orgMapping.push(orgNodes[i].originalId);
//    }
//    return orgMapping;
//}
//
//
//function editStaff(staffId) {
//    if (!validateStaffUsername("username_edit") || !validateStaffName("name_edit") || !validateStaffEmail("email_edit") || !validateStaffWorkPhone("workPhone_edit")) {
//        return false;
//    }
//
//    var params = {
//        "staffId": staffId,
//        "username": $("#username_edit").val(),
//        "name": $("#name_edit").val(),
//        "email": $("#email_edit").val(),
//        "gender": $("#sex_edit  option:selected").val(),
//        "workPhone": $("#workPhone_edit").val(),
//        "mobilePhones": getMobilePhoneListValue("mobilePhone_edit"),
//        "birthday": $("#birthday_edit").val(),
//        "isBirthdayVisible": $("#isBirthdayVisible_edit").prop("checked"),
//        "entryDate": $("#entryDate_edit").val(),
//        "isEntryDateVisible": $("#isEntryDateVisible_edit").prop("checked"),
//        "identity": $("#identity_edit").val(),
//        "isIdentityVisible": $("#isIdentityVisible_edit").prop("checked"),
//        "status": $("#status_edit").val()
//    };
//    $.ajax({
//        url: createUrl("/staff/edit"),
//        type: "post",
//        async: false,
//        traditional: true,
//        data: params,
//        dataType: "json",
//        success: function (msg) {
//            alertPromptMsgDlg(msg.message, 2, null);
//        }
//    });
//}
//
//function forwardToMoveStaff(staffId) {
//    $("#popUserOrOrgMove").modal({backdrop: 'static', keyboard: false, remote: createUrl("/staff/forwardToMove/" + staffId)});
//    $('#popUserOrOrgMove .modal-dialog').css('width', '560px');
//    $('#popUserOrOrgMove .modal-dialog').css('height', '500px');
//}
//
//function readyMoveStaff() {
//    showOrgTreeForStaff("treeOrgForStaffMove");
//    $("#treeOrgForStaffMove").show();
//    var currentOrgs = $("#currentOrgs").val();
//    var treeForMove = $.fn.zTree.getZTreeObj("treeOrgForStaffMove");
//    currentOrgs.split(";").forEach(function (orgId) {
//        treeForMove.checkNode(treeForMove.getNodeByParam("id", orgId + "_org"), true, false);
//    });
//    $("#submitStaffMoveBtn").click(function () {
//        moveStaff();
//    });
//
//    $('#popUserOrOrgMove').on('hide.bs.modal', function () {
//        $(this).removeData("bs.modal");
//    });
//}
//
//function moveStaff() {
//    $.ajax({
//        url: createUrl("/staff/move"),
//        type: "post",
//        data: {
//            "staffId": $("#staffId_move").val(),
//            "targetOrgIds": getOrgIds(findTreeCheckNodes("treeOrgForStaffMove"))
//        },
//        dataType: "json",
//        traditional: true,
//        success: function (msg) {
//            if (msg.success) {
//                alertPromptMsgDlg(msg.message, 1, reload);
//            } else {
//                alertPromptMsgDlg(msg.message, 3);
//            }
//        }
//    });
//}
//
//function confirmResetStaffPassword() {
//    var treeObj = $.fn.zTree.getZTreeObj("tree");
//    var node = treeObj.getSelectedNodes()[0];
//    var staffName = node.name;
//    var staffId = node.originalId;
//    alertConfirmationMsgDlg("确定重置 " + staffName + " 的密码", resetStaffPassword, staffId);
//}
//
//function resetStaffPassword(staffId) {
//    $.ajax({
//        url: createUrl("/staff/resetPassword"),
//        type: "post",
//        data: {
//            staffId: staffId
//        },
//        dataType: "json",
//        success: function (msg) {
//            if (msg.success) {
//                alertPromptMsgDlg(msg.message, 1, reload);
//            } else {
//                alertPromptMsgDlg(msg.message, 3);
//            }
//        }
//    });
//}
//
///**
//* 显示多选组织结构树
//* @param treeId
//*/
//function showOrgTreeForStaff(treeId) {
//    var orgSelectTreeSetting = {
//        check: {
//            enable: true,
//            chkboxType: {"Y": "", "N": ""}
//        },
//        data: {
//            simpleData: {
//                enable: true,
//                idKey: "id",
//                pIdKey: "pid",
//                rootPId: "0_org"
//            }
//        },
//        callback: {
//            //onClick: onClickTrade,
//            onNodeCreated: zTreeOnNodeCreated
//        },
//        view: {
//            showIcon: hideIconForRootAndUser
//        }
//    };
//    showTreeNodes($("#" + treeId), orgSelectTreeSetting);
//}
//
//function findTreeSelectNodes(treeId) {
//    var treObj = $.fn.zTree.getZTreeObj(treeId);
//    return treObj.getSelectedNodes();
//}
//
//function findTreeCheckNodes(treeId) {
//    var treObj = $.fn.zTree.getZTreeObj(treeId);
//    return treObj.getCheckedNodes();
//}
//
//function onDropStaffNode(event, treeId, treeNodes, targetNode, moveType, isCopy) {
//    if (moveType == null) {
//        return false;
//    }
//    if (isCopy) {
//        return false;
//    }
//    if (!isCopy) {
//        reIndexStaff(treeId, treeNodes, targetNode);
//    }
//}
//
//function reIndexStaff(treeId, treeNodes, targetNode) {
//    var orgNode = treeNodes[0].getParentNode();
//    var staffNodes = findStaffNodesInOrgNode(orgNode);
//    var staffIds = new Array();
//    staffNodes.forEach(function (node) {
//        staffIds.push(node.originalId);
//    });
//    $.ajax({
//        url: createUrl("/staff/reIndex"),
//        type: "post",
//        data: {
//            orgId: orgNode.originalId,
//            staffIdsByAsc: staffIds
//        },
//        traditional: true,
//        dataType: "json",
//        success: function (msg) {
//            if (!msg.success) {
//                alertPromptMsgDlg(msg.message, 3);
//            }
//        }
//    });
//
//}
//
//function findStaffNodesInOrgNode(orgNode) {
//    if (!orgNode.isParent) {
//        return null;
//    }
//    var childrenNodes = orgNode.children;
//    var staffNodes = new Array();
//    childrenNodes.forEach(function (node) {
//        if (node.type == "staff") {
//            staffNodes.push(node);
//        }
//    });
//    return staffNodes;
//}
//
//function readyImportStaff() {
//    setCurrentPageNavigation("", "sysLeftNavImportStaff");
//    $("#importFile").fileupload({
//        url: createUrl("/staff/import"),
//        autoUpload: true,
//        dataType: "html",
//        add: function (e, data) {
//            $.each(data.originalFiles, function (index, file) {
//                var ext = file.name.substr(file.name.lastIndexOf(".")).toLowerCase();
//                if (ext != ".xls" && ext != ".xlsx") {
//                    alertPromptMsgDlg("文件类型不正确，仅支持.xls或.xlsx文件类型", 2);
//                    return false;
//                }
//                if (file.size > 10 * 1024 * 1024) {
//                    alertPromptMsgDlg("上传文件过大，文件不能超过10M", 2);
//                    return false;
//                }
//                data.submit();
//            });
//        },
//        always: function (e, data) {
//            $('#importResultModalContent').html(data.result);
//            $('#importResultModal').modal();
//        }
//    });
//}
//
//
//function validateStaffUsername(usernameInputId) {
//    var value = $("#" + usernameInputId).val().trim();
//    if (validateNull(value)) {
//        renderInputField(false, usernameInputId, "账号不能为空！");
//        return false;
//    } else if (!isAccount(value)) {
//        renderInputField(false, usernameInputId, "账号只能为英文字母、数字、下划线、点号.，以英文字母开头");
//        return false;
//    }
//    restoreInputField(usernameInputId);
//    return true;
//}
//
//
//function validateStaffName(nameInputId) {
//    var value = $("#" + nameInputId).val().trim();
//    if (validateNull(value)) {
//        renderInputField(false, nameInputId, "用户姓名不能为空！");
//        return false;
//    }
//    restoreInputField(nameInputId);
//    return true;
//}
//
//function validateStaffEmail(emailinputId) {
//    var value = $("#" + emailinputId).val().trim();
//    if (!validateNull(value) && !isMail(value)) {
//        renderInputField(false, emailinputId, "邮箱格式不正确！");
//        return false;
//    }
//    restoreInputField(emailinputId);
//    return true;
//}
//
//function validateStaffWorkPhone(workPhoneInputId) {
//    var value = $("#" + workPhoneInputId).val();
//    if (!validateNull(value) && !isNumber(value)) {
//        renderInputField(false, workPhoneInputId, "话机号码只能为数字！");
//        return false;
//    }
//    restoreInputField(workPhoneInputId);
//    return true;
//}
//
//function validateStaffOrg(orgNameInputId) {
//    var value = $("#" + orgNameInputId).val();
//    if (validateNull(value)) {
//        renderInputField(false, orgNameInputId, "至少选择一个部门！");
//        return false;
//    }
//    restoreInputField(orgNameInputId);
//    return true;
//}
//
//function validateStaffMobilePhone(mobilePhoneinputId) {
//    var value = $("#" + mobilePhoneinputId).val();
//    if (!validateNull(value) && !isNumber(value)) {
//        renderInputField(false, "mobile_create", "移动电话只能为数字！");
//        return false;
//    }
//    restoreInputField(mobilePhoneinputId);
//    return true;
//}
//
//function getMobilePhoneListValue(inputIdPre) {
//    var mobilePhones = new Array();
//    $("input[id^='" + inputIdPre + "']").each(function () {
//        var inputVal = $(this).val();
//        if (!validateNull(inputVal)) {
//            mobilePhones.push(inputVal);
//        }
//    });
//    return mobilePhones;
//}
//
//function listPhoneInput(inputDivId, addButtonId, inputIdPre) {
//    var maxInputs = 3;
//    var inputsWrapper = $("#" + inputDivId);
//    var addButton = $("#" + addButtonId);
//    var x = $("input[id^=" + inputIdPre + "]").length;
//    $(addButton).click(function (e) {
//        if (x < maxInputs) {
//            $(inputsWrapper).append(mobilePhoneInputTemplet(inputIdPre, x));
//            x++;
//        }
//        return false;
//    });
//    $(inputsWrapper).on("click", "." + inputIdPre + "_remove", function (e) {
//        if (x > 1) {
//            $(this).parent('div').parent('div').remove();
//            x--;
//        }
//        return false;
//    })
//}
//
//function mobilePhoneInputTemplet(inputIdPrex, inputIndex) {
//    return "<div class='form-group'>" +
//        "<div class='col-sm-5 col-sm-offset-3'>" +
//        "<span>" +
//        "<input class='form-control' id='" + inputIdPrex + inputIndex + "' maxlength='20'>" +
//        "</span>" +
//        "</div>" +
//        "<div class='col-sm-1 row'>" +
//        "<label class='pull-right btn " + inputIdPrex + "_remove'><i class='icon-minus icon-large icon-minus-phone'/></label>" +
//        "</div>" +
//        "</div>";
//}
//
//function readyUnworkList() {
//    setCurrentPageNavigation("headerDatacenter", "sysLeftUnwork");
//    $("li[id^='select_']").click(function () {
//        $("#status_select").val($(this).val());
//        searchUnworkList();
//    });
//    $("#btnSearch").click(function () {
//        searchUnworkList();
//    });
//    searchInput("searchKey", null, searchUnworkList);
//
//    $("label[id^='unlock_']").click(function () {
//        var id = $(this).attr("id").replace("unlock_", "");
//        unlockStaff(id);
//    });
//    $("label[id^='delete_']").click(function () {
//        var id = $(this).attr("id").replace("delete_", "");
//        deleteStaff(id);
//    });
//    $("label[id^='revert_']").click(function () {
//        var id = $(this).attr("id").replace("revert_", "");
//        revertStaff(id);
//    });
//    $("label[id^='recycle_']").click(function () {
//        var id = $(this).attr("id").replace("recycle_", "");
//        recycleStaff(id);
//    });
//}
//
//function unlockStaff(staffId) {
//    $.ajax({
//        url: createUrl("/staff/forwardToUnworkList"),
//        type: "post",
//        data: {
//            staffId: staffId
//        },
//        dataType: "json",
//        success: function (msg) {
//            if (msg.success) {
//                alertPromptMsgDlg(msg.message, 1, reload);
//            } else {
//                alertPromptMsgDlg(msg.message, 3);
//            }
//        }
//    });
//}
////
// function deleteStaff(staffId) {
//    $.ajax({
//        url: createUrl("/staff/delete"),
//        type: "post",
//        data: {
//            staffIds: [staffId]
//        },
//        dataType: "json",
//        traditional: true,
//        success: function (msg) {
//            if (msg.success) {
//                alertPromptMsgDlg(msg.message, 1, reload);
//            } else {
//                alertPromptMsgDlg(msg.message, 3);
//            }
//        }
//    });
//}
//
//function recycleStaff(staffId) {
//    $.ajax({
//        url: createUrl("/staff/recycle"),
//        type: "post",
//        data: {
//            staffId: staffId
//        },
//        dataType: "json",
//        success: function (msg) {
//            if (msg.success) {
//                alertPromptMsgDlg(msg.message, 1, reload);
//            } else {
//                alertPromptMsgDlg(msg.message, 3);
//            }
//        }
//    });
//}
//
//function revertStaff(staffId) {
//    $.ajax({
//        url: createUrl("/staff/revert"),
//        type: "post",
//        data: {
//            staffIds: [staffId]
//        },
//        dataType: "json",
//        traditional: true,
//        success: function (msg) {
//            if (msg.success) {
//                alertPromptMsgDlg(msg.message, 1, reload);
//            } else {
//                alertPromptMsgDlg(msg.message, 3);
//            }
//        }
//    });
//}
//
//function searchUnworkList() {
//    var statusSelected = $("#status_select").val();
//    var searchKey = $("#searchKey").val();
//    var requestURL = createUrl("/staff/forwardToUnworkList");
//    requestURL = setUrlParam(requestURL, "selectedStatus", statusSelected);
//    requestURL = setUrlParam(requestURL, "searchKey", searchKey);
//    window.location.href = requestURL;
//}
