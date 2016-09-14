function getNodesName(nodes) {
    var length = nodes.length;
    var selectNodeStr = "";
    for (var i = 0; i < length; i++) {
        selectNodeStr += nodes[i].name + ",";
    }
    if (!validateNull(selectNodeStr)) {
        selectNodeStr = selectNodeStr.substr(0, selectNodeStr.length - 1);
    }
    return selectNodeStr;
}

//function defaultCheckOrg() {
//    var selectedNode = findTreeSelectNodes("tree")[0];
//    var orgNode = (selectedNode.type == "org") ? selectedNode : selectedNode.getParentNode();
//    var orgTreeCreate = $.fn.zTree.getZTreeObj("selectOrgTree_createStaff");
//    var createOrgNode = orgTreeCreate.getNodeByParam("id", orgNode.id);
//    orgTreeCreate.checkNode(createOrgNode);
//    $("#org_create").val(createOrgNode.name);
//}


function mobileListCheck(mobilePhones) {
    var result = true;
    $.each(mobilePhones, function (i, e) {
        if (!isNumber(e)) {
            $("#mobilePhone_create" + i).focus();
            renderInputField(false, "mobilePhone_create" + i, "移动电话只能为数字！");
            result = false;
        }
    });
    return result;
}

/**
 * 显示多选组织结构树
 * @param treeId
 */
function showOrgTree(treeId, onClick) {
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

function onClickOrgNodeWhenCreateStaff(e, treeId, treeNode) {
    var __ret = extractedOrgNode(treeId);
    var orgName = __ret.orgName;
    var orgId = __ret.orgId;
    $("#orgId_createStaff").val(orgId);
    $("#orgName_createStaff").val(orgName);
    $('#selectOrgTree_createStaff').toggle();
}

function onClickOrgNodeWhenEditStaff(e, treeId, treeNode) {
    var __ret = extractedOrgNode(treeId);
    var orgName = __ret.orgName;
    var orgId = __ret.orgId;
    $("#orgId_editStaff").val(orgId);
    $("#orgName_editStaff").val(orgName);
    $('#selectOrgTree_editStaff').toggle();
}

function findTreeSelectNodes(treeId) {
    var treObj = $.fn.zTree.getZTreeObj(treeId);
    return treObj.getSelectedNodes();
}

function findTreeCheckNodes(treeId) {
    var treObj = $.fn.zTree.getZTreeObj(treeId);
    return treObj.getCheckedNodes();
}

function onDropStaffNode(event, treeId, treeNodes, targetNode, moveType, isCopy) {
    if (moveType == null) {
        return false;
    }
    if (isCopy) {
        return false;
    }
    if (!isCopy) {
        reIndexStaff(treeId, treeNodes, targetNode);
    }
}

function findStaffNodesInOrgNode(orgNode) {
    if (!orgNode.isParent) {
        return null;
    }
    var childrenNodes = orgNode.children;
    var staffNodes = new Array();
    childrenNodes.forEach(function (node) {
        if (node.type == "staff") {
            staffNodes.push(node);
        }
    });
    return staffNodes;
}


function validateStaffUsername(usernameInputId) {
    var value = $("#" + usernameInputId).val().trim();
    if (validateNull(value)) {
        renderInputField(false, usernameInputId, "账号不能为空！");
        return false;
    } else if (!isAccount(value)) {
        renderInputField(false, usernameInputId, "账号只能为英文字母、数字、下划线、点号.，以英文字母开头");
        return false;
    }
    restoreInputField(usernameInputId);
    return true;
}


function validateStaffName(nameInputId) {
    var value = $("#" + nameInputId).val().trim();
    if (validateNull(value)) {
        renderInputField(false, nameInputId, "用户姓名不能为空！");
        return false;
    } else if (!validName(value)) {
        renderInputField(false, nameInputId, "用户姓名不合法");
        return false;
    }
    restoreInputField(nameInputId);
    return true;
}

function validateStaffPosition(positionInputId) {
    var value = $("#" + positionInputId).val().trim();
    if (validateNull(value)) {
        renderInputField(false, positionInputId, "职位不能为空！");
        return false;
    } else if (!validName(value)) {
        renderInputField(false, positionInputId, "职位名不合法");
        return false;
    }
    restoreInputField(positionInputId);
    return true;
}

function validateStaffEmail(emailinputId) {
    var value = $("#" + emailinputId).val().trim();
    if (!validateNull(value) && !isMail(value)) {
        renderInputField(false, emailinputId, "邮箱格式不正确！");
        return false;
    }
    restoreInputField(emailinputId);
    return true;
}
function validateStaffNumber(numberInputId) {
    var value = $("#" + numberInputId).val().trim();
    if (!validateNull(value) && !isStaffNumber(value)) {
        renderInputField(false, numberInputId, "员工编号格式不正确！");
        return false;
    }
    restoreInputField(numberInputId);
    return true;
}
function validateExtensionNumber(extensionNumber) {
    var value = $("#" + extensionNumber).val().trim();
    if (validateNull(value)) {
        renderInputField(false, extensionNumber, "分机号不能为空！");
        return false;
    } else if (!isNumber(value)) {
        renderInputField(false, extensionNumber, "分机号格式不正确！");
        return false;
    }
    restoreInputField(extensionNumber);
    return true;
}

function validatePhoneMac(phoneMac) {
    var numberLetterPattern = /^[A-Za-z0-9]+$/;
    var value = $("#" + phoneMac).val().trim();
    if (validateNull(value)) {
        renderInputField(false, phoneMac, "Mac不能为空！");
        return false;
    } else if (!numberLetterPattern.test(value)) {
        renderInputField(false, phoneMac, "Mac格式不正确！");
        return false;
    }
    restoreInputField(phoneMac);
    return true;
}
function validatePhoneIP(phoneIp) {
    var ipPattern = /((?:(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d)))\.){3}(?:25[0-5]|2[0-4]\d|((1\d{2})|([1-9]?\d))))/;
    var value = $("#" + phoneIp).val().trim();
    if (!validateNull(value) && !ipPattern.test(value)) {
        renderInputField(false, phoneIp, "IP地址格式不正确！");
        return false;
    }
    restoreInputField(phoneIp);
    return true;
}
function validateStaffWorkPhone(workPhoneInputId) {
    var value = $("#" + workPhoneInputId).val();
    if (!validateNull(value) && !isNumber(value)) {
        renderInputField(false, workPhoneInputId, "话机号码只能为数字！");
        return false;
    }
    restoreInputField(workPhoneInputId);
    return true;
}

function validateStaffOrg(type) {
    if (validateNull($("#orgId_" + type + "Staff").val().trim())) {
        renderInputField(false, "org_" + type, "不能为空！");
        return false;
    }
    restoreInputField("org_" + type);
    return true;
}

function validateStaffMobilePhone(mobilePhoneinputId) {
    var value = $("#" + mobilePhoneinputId).val();
    if (!validateNull(value) && !isNumber(value)) {
        renderInputField(false, mobilePhoneinputId, "移动电话只能为数字！");
        return false;
    }
    restoreInputField(mobilePhoneinputId);
    return true;
}

function getMobilePhoneListValue(inputIdPre) {
    var mobilePhones = new Array();
    $("input[id^='" + inputIdPre + "']").each(function () {
        var inputVal = $(this).val();
        if (!validateNull(inputVal)) {
            mobilePhones.push(inputVal);
        }
    });
    return mobilePhones;
}
function isStaffNumber(value) {
    return /^[A-Za-z0-9]+$/.test(value)
}
function listPhoneInput(inputDivId, addButtonId, inputIdPre) {
    var maxInputs = 3;
    var inputsWrapper = $("#" + inputDivId);
    var addButton = $("#" + addButtonId);
    var x = $("input[id^=" + inputIdPre + "]").length;
    $(addButton).click(function (e) {
        if (x < maxInputs) {
            $(inputsWrapper).append(mobilePhoneInputTemplet(inputIdPre, x));
            x++;
        }
        return false;
    });
    $(inputsWrapper).on("click", "." + inputIdPre + "_remove", function (e) {
        if (x > 1) {
            $(this).parent('div').parent('div').remove();
            x--;
        }
        return false;
    })
}
function validateWhenSave(type) {
    if ($("#isSelectExtensionSetting_" + type).prop('checked')) {
        if (!validateExtensionNumber("extensionNumber_" + type)) {
            return false;
        }
    }

    if ($("#isSelectPhoneSetting_" + type).prop('checked')) {
        if (!validatePhoneIP("phoneIP_" + type) || !validatePhoneMac("phoneMAC_" + type)) {
            return false;
        }
    }
    return true;
}
function validateExtension(type) {
    var settingId = '#isSelectExtensionSetting_' + type;
    var extensionNumberId = "extensionNumber_" + type;
    if ($(settingId).prop('checked')) {
        $("#" + extensionNumberId).blur(function () {
            validateExtensionNumber(extensionNumberId);
        });
    }
    $(settingId).on('click', function () {
        if ($(this).prop('checked')) {
            $("#" + extensionNumberId).blur(function () {
                validateExtensionNumber(extensionNumberId);
            });
        } else {
            restoreInputField(extensionNumberId);
        }
    });
}

function validatePhone(type) {
    var settingId = '#isSelectPhoneSetting_' + type;
    var phoneMacId = "phoneMAC_" + type;
    var phoneIPId = "phoneIP_" + type;
    if ($(settingId).prop('checked')) {
        $('#' + phoneMacId).blur(function () {
            validatePhoneMac(phoneMacId);
        });
        $('#' + phoneIPId).blur(function () {
            validatePhoneIP(phoneIPId);
        });
    }
    $(settingId).on('click', function () {
        if ($(this).prop('checked')) {
            $('#' + phoneMacId).blur(function () {
                validatePhoneMac(phoneMacId);
            });
            $('#' + phoneIPId).blur(function () {
                validatePhoneIP(phoneIPId);
            });
        } else {
            restoreInputField(phoneMacId);
            restoreInputField(phoneIPId);
        }
    });
}
function mobilePhoneInputTemplet(inputIdPrex, inputIndex) {
    return "<div class='form-group' id='div" + inputIdPrex.substr(0, 1).toLocaleUpperCase() + inputIdPrex.slice(1) + inputIndex + "'>" +
        "<div class='col-sm-1'></div><div class='col-sm-5'>" +
        "<span>" +
        "<input class='form-control' id='" + inputIdPrex + inputIndex + "' maxlength='20' name='mobilePhone' onblur=\"validateStaffMobilePhone('" + inputIdPrex + inputIndex + "')\">" +
        "</span>" +
        "</div>" +
        "<div class='col-sm-1 row'>" +
        "<label class='pull-right btn " + inputIdPrex + "_remove'><i class='icon-minus icon-large icon-minus-phone'/></label>" +
        "</div><label id=\"error" + inputIdPrex.substr(0, 1).toLocaleUpperCase() + inputIdPrex.slice(1) + inputIndex + "\" class=\"control-label\"></label>" +
        "</div>";
}


function changeStaffStatus(status, staffId) {
    $.ajax({
        url: createUrl("/staff/" + status),
        type: "post",
        async: false,
        data: {staffId: staffId},
        dataType: "json",
        traditional: true,
        success: function (msg) {
            if (msg.success) {
                alertPromptMsgDlg(msg.message, 1, reload);
            } else {
                alertPromptMsgDlg(msg.message, 2);
            }
        }
    });
}
var avatar_file;
function staffAvatarFileUpload(avatarId, previewId, submitUrl) {
    $(avatarId).fileupload({
        url: submitUrl,
        dataType: 'json',
        autoUpload: false,
        acceptFileTypes: /^image\/(gif|jpe?g|png)$/,
        maxFileSize: 2 * 1 * 100,
        previewMaxWidth: 100,
        previewMaxHeight: 100,
        previewCrop: true
    }).on('fileuploadadd', function (e, data) {
        avatar_file = data.files[0];
        $(avatarId).data(data);
    }).on('fileuploadprocessdone', function (e, data) {
        var index = data.index;
        var file = data.files[index];
        var node = $(previewId);
        if (file.preview) {
            node.html(file.preview)
        }
    }).on('fileuploadsubmit', function (e, data) {
    }).on('fileuploadalways', function (e, data) {
        alertPromptMsgDlg(data.result.message, 1, reload);
    });
}

// for multi org
//function getOrgIds(orgNodes) {
//    var orgMapping = new Array();
//    var orgLength = orgNodes.length;
//    for (var i = 0; i < orgLength; i++) {
//        orgMapping.push(orgNodes[i].originalId);
//    }
//    return orgMapping;
//}
//$("#selectOrgTree_createStaff").mouseleave(function () {
//    $(this).hide();
//    var nodes = findTreeCheckNodes("selectOrgTree_createStaff");
//    var selectNodeStr = getNodesName(nodes);
//    $("#org_create").val(selectNodeStr);
//    validateStaffOrg("org_create");
//});