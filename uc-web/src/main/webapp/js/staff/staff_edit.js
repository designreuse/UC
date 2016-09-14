$("#orgManagementRightContent").on("click", "a[id^='edit_']", function () {
    var id = $(this).attr("id").replace("edit_", "");
    $.ajax({
        url: createUrl("/staff/forwardToEdit/" + id),
        type: "get",
        async: true,
        success: function (data) {
            $('#orgManagementRightContent').html(data);
        }
    });
});

function readyEditStaff() {
    listPhoneInput("inputList_edit", "addMobilePhone_edit", "mobilePhone_edit");

    monitorStaffOperation();

    initExtensionPhoneConfig();

    monitorExtensionOperation();

    checkIsSelectPhoneConfig();

    checkIsSelectExtensionConfig();

    staffAvatarFileUpload('#avatar_edit','#preview_edit', createUrl("/staff/edit"));

    initEditStaffOrgTree();

    validateEditStaffInput();

    editStaffNextPrevAction();
}

/**
 * 初始化部门选择树
 */
function initEditStaffOrgTree() {
    showOrgTree("selectOrgTree_editStaff", onClickOrgNodeWhenEditStaff);
    var treeObj = $.fn.zTree.getZTreeObj("selectOrgTree_editStaff");
    var currentOrgs = $("#currentOrgs").val().split(";");
    var currentOrgsStr = "";
    for (var i = 0; i < currentOrgs.length; i++) {
        var treeNode = treeObj.getNodeByParam("id", currentOrgs[i] + "_org");
        treeObj.checkNode(treeObj.getNodeByParam("id", currentOrgs[i] + "_org"), true, false);
        currentOrgsStr += ";" + treeNode.name;
    }
    $("#orgName_editStaff").val(currentOrgsStr.substring(1));
    $("#orgId_editStaff").val(currentOrgs[0]); // for now ,one staff just belong to one org, but it will belong to multi orgs in the future
}

function initExtensionPhoneConfig() {
    if (!$('#isSelectExtensionSetting_edit').prop('checked')) {
        $('#extensionPinCode_edit_reset,#extension_password_edit_reset').attr('disabled', true);

        if (!$("#extensionPassword_edit").val())
            $("#extensionPassword_edit").val(randomString(6));
        if (!$("#extensionPinCode_edit").val())
            $("#extensionPinCode_edit").val(randomNumber(6));
    }

    if (!$('#isSelectPhoneSetting_edit').prop('checked')) {
        $('#divConfigPhone').find('input,select').attr('disabled', true);
        $('#isSelectPhoneSetting_edit').removeAttr('disabled');
    }
}

function monitorStaffOperation() {
    $('#password_edit_reset').on('click', function () {
        $("#password_edit").val(randomString(6));
    });
    $('#pinCode_edit_reset').on('click', function () {
        $("#pinCode_edit").val(randomNumber(6));
    });
}
function monitorExtensionOperation() {
    $('#extension_password_edit_reset').on('click', function () {
        $("#extensionPassword_edit").val(randomString(6));
    });
    $('#extensionPinCode_edit_reset').on('click', function () {
        $("#extensionPinCode_edit").val(randomNumber(6));
    });
}
function validateEditStaffInput() {
    $("#orgDropdownBtn_edit").click(function () {
        $("#selectOrgTree_editStaff").width($("#email_edit").width() + 12);
        $("#selectOrgTree_editStaff").toggle();
    });

    $("#selectOrgTree_editStaff").mouseleave(function () {
        $("#selectOrgTree_editStaff").hide();
        validateStaffOrg("edit");
    });

    $("#name_edit").blur(function () {
        validateStaffName("name_edit");
    });
    $("#number_edit").blur(function () {
        validateStaffNumber("number_edit");
    });
    $("#email_edit").blur(function () {
        validateStaffEmail("email_edit");
    });
    $("#workPhone_edit").blur(function () {
        validateStaffWorkPhone("workPhone_edit")
    });
    $("#position_edit").blur(function () {
        validateStaffPosition("position_edit");
    });
    $("#extensionNumber_edit").blur(function () {
        validateExtensionNumber("extensionNumber_edit");
    });
    validatePhone('edit')
    validateExtension('edit');
}
function editStaffNextPrevAction() {
    $('#prevStepBtn').click(function () {
        $('#staffBaseConfig_edit').toggle();
        $('#staffExtensionPhoneConfig_edit').toggle();
    });
    $("#nextStepBtn").click(function () {
        if (!validateStaffName("name_edit") || !validateStaffEmail("email_edit") || !validateStaffWorkPhone("workPhone_edit") || !validateStaffPosition("position_edit")
            || !validateStaffOrg("edit")) {
            return false;
        }
        $('#staffBaseConfig_edit').toggle();
        $('#staffExtensionPhoneConfig_edit').toggle();
    });

    $("#updateStaffBtn").click(function () {
        editStaff($("#staffId_edit").val());
    });
}
function checkIsSelectPhoneConfig() {
    $('#isSelectPhoneSetting_edit').on("click", function () {
        if ($(this).prop('checked')) {
            $('#divConfigPhone').find('input,select').removeAttr('disabled');
        } else {
            $('#divConfigPhone').find('input,select').attr('disabled', true);
        }
        $(this).removeAttr('disabled');
    });
}
function checkIsSelectExtensionConfig() {
    $('#isSelectExtensionSetting_edit').on("click", function () {
        if ($(this).prop('checked')) {
            $('#divConfigExtension').find('input,select').removeAttr('disabled');
            $('#extensionPinCode_edit_reset,#extension_password_edit_reset').removeAttr('disabled');
        } else {
            $('#divConfigExtension').find('input,select').attr('disabled', true);
            $('#extensionPinCode_edit_reset,#extension_password_edit_reset').attr('disabled', true);
        }
        $(this).removeAttr('disabled');
    });
}
function editStaff(staffId) {
    if(!validateWhenSave('edit'))return false;

    var mobilePhones = getMobilePhoneListValue("mobilePhone_edit");
    var params = {
        "staffId": staffId,
        "username": $("#username_edit").val(),
        "password": $("#password_edit").val(),
        "pinCode": $("#pinCode_edit").val(),
        "name": $("#name_edit").val(),
        "gender": $("input[name='gender_edit']:checked").val(),
        "mobilePhones": mobilePhones,
        "email": $("#email_edit").val(),
        "orgMappings": [$('#orgId_editStaff').val()],
        "position": $("#position_edit").val(),
        "number": $("#number_edit").val(),
        "extensionNumber": $("#extensionNumber_edit").val(),
        "extensionPassword": $("#extensionPassword_edit").val(),
        "extensionPinCode": $("#extensionPinCode_edit").val(),
        "phoneMac": $("#phoneMAC_edit").val(),
        "phoneIP": $("#phoneIP_edit").val(),
        "phoneModel": $("#phoneModel_edit").val(),
        "phoneSettingTemplate": $("#phoneSettingTemplate_edit").val(),
        "isSelectPhoneSetting": $("#isSelectPhoneSetting_edit").prop('checked'),
        "isSelectExtensionSetting": $("#isSelectExtensionSetting_edit").prop('checked')
    };

    var avaterData = $("#avatar_edit").data();
    if (avatar_file != undefined && avatar_file != null && avaterData != undefined) {
        avaterData.formData = params;
        avaterData.submit();
        return;
    }

    $.ajax({
        url: createUrl("/staff/edit"),
        type: "post",
        async: false,
        traditional: true,
        data: params,
        dataType: "json",
        success: function (msg) {
            if(msg.success){
                alertPromptMsgDlg(msg.message, 1, refreshOrgIndex);
            }else{
                alertPromptMsgDlg(msg.message, 3);
            }

        }
    });
}


function confirmResetStaffPassword() {
    var treeObj = $.fn.zTree.getZTreeObj("tree");
    var node = treeObj.getSelectedNodes()[0];
    var staffName = node.name;
    var staffId = node.originalId;
    alertConfirmationMsgDlg("确定重置 " + staffName + " 的密码", resetStaffPassword, staffId);
}

function resetStaffPassword(staffId) {
    $.ajax({
        url: createUrl("/staff/resetPassword"),
        type: "post",
        data: {
            staffId: staffId
        },
        dataType: "json",
        success: function (msg) {
            if (msg.success) {
                alertPromptMsgDlg(msg.message, 1, reload);
            } else {
                alertPromptMsgDlg(msg.message, 3);
            }
        }
    });
}

