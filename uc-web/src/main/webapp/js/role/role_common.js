/**
 /////////////////////////////////////////Common-Module///////////////////////////////////////////////////////////
 **/
function refreshRoleIndex() {
    window.location.href = createUrl("/role/index");
}

function validName(val) {
    var re = /^([\u4E00-\u9FA5]|\w)*$/;
    return re.test(val);
}


function validateRoleName(nameId) {
    var nameObject = $("#" + nameId);
    var roleName = nameObject.val();
    if (validateNull(roleName)) {
        nameObject.focus();
        renderInputField(false, nameId, "角色名称不能为空");
        return false;
    } else if (!validName(roleName)) {
        nameObject.focus();
        renderInputField(false, nameId, "角色名称不合法");
        return false;
    } else if (!validateNull(roleName) && $.trim(roleName).length > 64) {
        nameObject.focus();
        renderInputField(false, nameId, "角色名称长度不能超过64位");
        return false;
    } else {
        renderInputField(true, nameId, "");
    }
}
function validateRole(nameId) {
    validateRoleName(nameId);
    return true;
}
