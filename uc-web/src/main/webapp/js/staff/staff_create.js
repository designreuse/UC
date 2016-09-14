/**
 * 跳转到添加用户
 */
function forwardToCreateStaff() {
    $.get("/staff/forwardToCreate",
        function (data) {
            $("#orgManagementRightContent").html(data);
        });
}

function readyCreateStaff() {
    initCreateStaffPasswordPin();

    checkCreateStaffEnablePhoneConfig();

    checkCreateStaffEnableExtensionConfig();

    staffAvatarFileUpload('#avatar_create', '#preview_create', createUrl('/staff/create'));

    listPhoneInput("inputList_create", "addMobilePhone_create", "mobilePhone_create");

    showOrgTree("selectOrgTree_createStaff", onClickOrgNodeWhenCreateStaff);

    validateStaffCreateInput();

    createStaffNextPrevAction();

    $("#saveStaffBtn").click(function () {
        createStaff();
        return false;
    })
}
function initCreateStaffPasswordPin() {
    $("#password_create").val(randomString(6));
    $("#pinCode_create").val(randomNumber(6));
    $("#extensionPassword_create").val(randomString(6));
    $("#extensionPinCode_create").val(randomNumber(6));
}

function checkCreateStaffEnableExtensionConfig() {
    $('#isSelectExtensionSetting_create').on("click", function () {
        if ($(this).prop('checked')) {
            $('#divConfigExtension').find('input,select').removeAttr('disabled');
        } else {
            $('#divConfigExtension').find('input,select').attr('disabled', true);
        }
        $(this).removeAttr('disabled');
    });
}
function checkCreateStaffEnablePhoneConfig() {
    $('#isSelectPhoneSetting_create').on("click", function () {
        if ($(this).prop('checked')) {
            $('#divConfigPhone').find('input,select').removeAttr('disabled');
        } else {
            $('#divConfigPhone').find('input,select').attr('disabled', true);
        }
        $(this).removeAttr('disabled');
    });
}


function createStaffNextPrevAction() {
    $('#prevStepBtn').click(function () {
        $('#staffBaseConfig_create').toggle();
        $('#staffExtensionPhoneConfig_create').toggle();
    });
    $("#nextStepBtn").click(function () {
        if (!validateStaffUsername("username_create") || !validateStaffName("name_create")
            || !validateStaffEmail("email_create") || !validateStaffWorkPhone("workPhone_create")
            || !validateStaffOrg('create') || !validateStaffPosition("position_create")) {
            return false;
        }
        $('#inputList_create').find("input[name='mobilePhone']").each(function () {
            if (validateStaffMobilePhone($(this).attr('id')))
                return false;
        });
        $('#staffBaseConfig_create').toggle();
        $('#staffExtensionPhoneConfig_create').toggle();
    });
}

function validateStaffCreateInput() {
    var selectOrgTree = $("#selectOrgTree_createStaff");

    $("#orgDropdownBtn_create").click(function () {
        $(selectOrgTree).width($("#email_create").width() + 12);
        $(selectOrgTree).toggle();
    });

    $(selectOrgTree).mouseleave(function () {
        $("#selectOrgTree_createStaff").hide();
        validateStaffOrg('create');
    });

    $("#username_create").blur(function () {
        validateStaffUsername("username_create");
    });
    $("#name_create").blur(function () {
        validateStaffName("name_create");
    });
    $("#email_create").blur(function () {
        validateStaffEmail("email_create");
    });
    $("#number_create").blur(function () {
        validateStaffNumber("number_create");
    });
    $("#position_create").blur(function () {
        validateStaffPosition("position_create");
    });

    validatePhone('create');
    validateExtension('create');
    $('#inputList_create').find("input[name='mobilePhone']").each(function () {
        $(this).on('blur', function () {
            validateStaffMobilePhone($(this).attr('id'));
        });
    })
}
function createStaff() {
    if(!validateWhenSave('create'))return false;

    var mobilePhones = getMobilePhoneListValue("mobilePhone_create");
    var params = {
        "username": $("#username_create").val(),
        "password": $("#password_create").val(),
        "pinCode": $("#pinCode_create").val(),
        "name": $("#name_create").val(),
        "gender": $("input[name='gender_create']:checked").val(),
        "mobilePhones": mobilePhones,
        "email": $("#email_create").val(),
        "orgMappings": [$('#orgId_createStaff').val()],
        "position": $("#position_create").val(),
        "number": $("#number_create").val(),
        "extensionNumber": $("#extensionNumber_create").val(),
        "extensionPassword": $("#extensionPassword_create").val(),
        "extensionPinCode": $("#extensionPinCode_create").val(),
        "phoneMac": $("#phoneMAC_create").val(),
        "phoneIP": $("#phoneIP_create").val(),
        "phoneModel": $("#phoneModel_create").val(),
        "phoneSettingTemplate": $("#phoneSettingTemplate_create").val(),
        "isSelectPhoneSetting": $("#isSelectPhoneSetting_create").prop('checked'),
        "isSelectExtensionSetting": $("#isSelectExtensionSetting_create").prop('checked')
    };

    var avatarData = $("#avatar_create").data();
    if (avatar_file != undefined && avatar_file != null && avatarData != undefined) {
        avatarData.formData = params;
        avatarData.submit();
        return;
    }

    $.ajax({
        url: createUrl("/staff/create"),
        type: "post",
        async: false,
        data: params,
        dataType: "json",
        traditional: true,
        success: function (msg) {
            if (msg.success) {
                uploadAvater();
                alertPromptMsgDlg(msg.message, 1, reload);
            } else {
                alertPromptMsgDlg(msg.message, 2, null);
            }
        }
    });
}

function uploadAvater() {

}
