///////////////////////////////////////////////Edit//////////////////////////////////////////////////////

$("label[id^='editRole_']").click(function () {
    var id = $(this).attr("id").replace("editRole_", "");
    $.ajax({
        url: createUrl("/role/forwardToEdit/" + id),
        type: 'get',
        async: false,
        success: function (data) {
            if (data) {
                $('#modelContent').html(data);
                $('#modalPopup').modal('show');
                hideProgressBar();
            }
        },
        error: function () {
            alertPromptErrorMsgDlg();
            hideProgressBar();
        }
    });
})

function readyEditRole() {
    $("#roleName_edit").blur(function () {
        validateRoleName("roleName_edit");
    });
}


function editRole() {
    var roleName = $("#roleName_edit").val();
    var roleDescription = $("#roleDescription_edit").val();
    var roleLevel = $("#roleLevelId_edit").val();
    if (!validateRole("roleName_edit"))return;

    var params = {
        "id": $('#roleId').val(),
        "name": roleName,
        "description": roleDescription,
        "roleLevelId": roleLevel
    };
    ajax('post', params, createUrl("/role/edit"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshRoleIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}