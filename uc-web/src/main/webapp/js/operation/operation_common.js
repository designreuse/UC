/**
 /////////////////////////////////////////Common-Module///////////////////////////////////////////////////////////
 **/
function refreshOperationIndex() {
    window.location.href = createUrl("/operation/index");
}

function validOperationName(val) {
    var re = /^([\u4E00-\u9FA5]|\w)*$/;
    return re.test(val);
}


function validateOperationName(nameId) {
    var nameObject = $("#" + nameId);
    var operationName = nameObject.val();
    if (validateNull(operationName)) {
        nameObject.focus();
        renderInputField(false, nameId, "操作名称不能为空");
        return false;
    } else if (!validOperationName(operationName)) {
        nameObject.focus();
        renderInputField(false, nameId, "操作名称不合法");
        return false;
    } else if (!validateNull(operationName) && $.trim(operationName).length > 64) {
        nameObject.focus();
        renderInputField(false, "OperationName_create", "操作名称长度不能超过64位");
        return false;
    } else {
        renderInputField(true, nameId, "");
        return true;
    }
}
function validateOperation(nameId, codeIdId) {
    if (!validateOperationName(nameId))return false;
    if (!validateOperationCode(codeIdId))return false;
    return true;
}
function validateOperationCode(codeIdId) {
    var codeIdObject = $("#" + codeIdId);
    var operationCode = codeIdObject.val();
    if (validateNull(operationCode)) {
        codeIdObject.focus();
        renderInputField(false, codeIdId, "操作代码不能为空");
        return false;
    } else if (!validateNull(operationCode) && !validateCode(operationCode)) {
        codeIdObject.focus();
        renderInputField(false, codeIdId, "操作代码格式不正确");
        return false;
    } else {
        renderInputField(true, codeIdId, "");
        return true;
    }
}