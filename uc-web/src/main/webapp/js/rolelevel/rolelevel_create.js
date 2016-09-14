function forwardToCreateRoleLevel() {
    var url = createUrl("/rolelevel/forwardToCreate/");
    $.get(url, function (data) {
        $('#modelContent').html(data);
    });
}

function readyCreateRoleLevel() {
    $("#roleLevelName_create").blur(function () {
        validateRoleLevelName("roleLevelName_create");
    });
    $("#roleLevelCode_create").blur(function () {
        validateRoleLevelCode("roleLevelCode_create");
    });
}

function validateCode(value) {
    var reg = new RegExp("(\\w)+");
    if (!reg.test($.trim(value))) {
        return false;
    }
    return true;
};

function createRoleLevel() {
    var roleLevelName = $("#roleLevelName_create").val();
    var roleLevelDescription = $("#roleLevelDescription_create").val();
    var roleLevelCode = $("#roleLevelCode_create").val();
    var roleLevelType = $("#roleLevelType_create").val();
    var roleLevelPriority = $("#roleLevelPriority_create").val();
    if (!validateRoleLevel("roleLevelName_create", "roleLevelCode_create"))return;

    var params = {
        "name": roleLevelName,
        "description": roleLevelDescription,
        "code": roleLevelCode,
        "type": roleLevelType,
        "priority": roleLevelPriority
    };
    ajax('post', params, createUrl("/rolelevel/create"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshRoleLevelIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}
