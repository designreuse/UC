/**
 /////////////////////////////////////////Common-Module///////////////////////////////////////////////////////////
 **/
function refreshProjectIndex() {
    window.location.href = createUrl("/project/index");
}

function validProjectName(val) {
    var re = /^([\u4E00-\u9FA5]|\w)*$/;
    return re.test(val);
}


function validateProjectName(nameId) {
    var nameObject = $("#" + nameId);
    var projectName = nameObject.val();
    if (validateNull(projectName)) {
        nameObject.focus();
        renderInputField(false, nameId, "项目名称不能为空");
        return false;
    } else if (!validProjectName(projectName)) {
        nameObject.focus();
        renderInputField(false, nameId, "项目名称不合法");
        return false;
    } else if (!validateNull(projectName) && $.trim(projectName).length > 64) {
        nameObject.focus();
        renderInputField(false, "ProjectName_create", "项目名称长度不能超过64位");
        return false;
    } else {
        renderInputField(true, nameId, "");
        return true;
    }
}
function validateProject(nameId, urlId) {
    if (!validateProjectName(nameId))return false;
    if (!validateProjectUrl(urlId))return false;
    return true;
}
function validateProjectUrl(urlId) {
    var urlObject = $("#" + urlId);
    var projectUrl = urlObject.val();
    if (validateNull(projectUrl)) {
        urlObject.focus();
        renderInputField(false, urlId, "项目地址不能为空");
        return false;
    } else if (!validateNull(projectUrl) && !validateUrl(projectUrl)) {
        urlObject.focus();
        renderInputField(false, urlId, "项目地址格式不正确");
        return false;
    } else {
        renderInputField(true, urlId, "");
        return true;
    }
}