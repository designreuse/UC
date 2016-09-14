/**
 ///////////////////////////////////////// Create ///////////////////////////////////////////////////////////
 **/

function forwardToCreateRole() {
    var url = createUrl("/role/forwardToCreate/");
    $.get(url, function (data) {
        $('#modelContent').html(data);
    });
}

function readyCreateRole() {
    $("#roleName_create").blur(function () {
        validateRoleName("roleName_create");
    });
}

function createRole() {
    var roleName = $("#roleName_create").val();
    var roleDescription = $("#roleDescription_create").val();
    var roleLevel = $("#roleLevelId_create").val();
    if (!validateRole("roleName_create"))return;

    var params = {
        "name": roleName,
        "description": roleDescription,
        "roleLevelId": roleLevel
    };
    ajax('post', params, createUrl("/role/create"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshRoleIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}
