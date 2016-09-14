function refreshRoleLevelIndex() {
    window.location.href = createUrl("/rolelevel/index");
}

function validRoleLevelName(val) {
    var re = /^([\u4E00-\u9FA5]|\w)*$/;
    return re.test(val);
}


function validateRoleLevelName(nameId) {
    var nameObject = $("#" + nameId);
    var roleLevelName = nameObject.val();
    if (validateNull(roleLevelName)) {
        nameObject.focus();
        renderInputField(false, nameId, "角色级别名称不能为空");
        return false;
    } else if (!validRoleLevelName(roleLevelName)) {
        nameObject.focus();
        renderInputField(false, nameId, "角色级别名称不合法");
        return false;
    } else if (!validateNull(roleLevelName) && $.trim(roleLevelName).length > 64) {
        nameObject.focus();
        renderInputField(false, "RoleLevelName_create", "角色级别名称长度不能超过64位");
        return false;
    } else {
        renderInputField(true, nameId, "");
        return true;
    }
}
function validateRoleLevel(nameId, codeIdId) {
    if (!validateRoleLevelName(nameId))return false;
    if (!validateRoleLevelCode(codeIdId))return false;
    return true;
}
function validateRoleLevelCode(codeIdId) {
    var codeIdObject = $("#" + codeIdId);
    var roleLevelCode = codeIdObject.val();
    if (validateNull(roleLevelCode)) {
        codeIdObject.focus();
        renderInputField(false, codeIdId, "角色级别代码不能为空");
        return false;
    } else if (!validateNull(roleLevelCode) && !validateCode(roleLevelCode)) {
        codeIdObject.focus();
        renderInputField(false, codeIdId, "角色级别代码格式不正确");
        return false;
    } else {
        renderInputField(true, codeIdId, "");
        return true;
    }
}