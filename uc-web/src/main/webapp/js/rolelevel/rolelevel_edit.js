$("label[id^='editRoleLevel_']").click(function () {
    var id = $(this).attr("id").replace("editRoleLevel_", "");
    $.ajax({
        url: createUrl("/rolelevel/forwardToEdit/" + id),
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


function readyEditRoleLevel() {
    $("#roleLevelName_edit").blur(function () {
        validateRoleLevelName("roleLevelName_edit");
    });
    $("#roleLevelCode_edit").blur(function () {
        validateRoleLevelCode("roleLevelCode_edit");
    });
}
function editRoleLevel() {
    var roleLevelName = $("#roleLevelName_edit").val();
    var roleLevelDescription = $("#roleLevelDescription_edit").val();
    var roleLevelCode = $("#roleLevelCode_edit").val();
    var roleLevelType = $("#roleLevelType_edit").val();
    var roleLevelPriority = $("#roleLevelPriority_edit").val();
    if (!validateRoleLevel("roleLevelName_edit", "roleLevelCode_edit"))return;

    var params = {
        "id": $('#roleLevelId').val(),
        "name": roleLevelName,
        "description": roleLevelDescription,
        "code": roleLevelCode,
        "type": roleLevelType,
        "priority": roleLevelPriority
    };
    ajax('post', params, createUrl("/rolelevel/edit"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshRoleLevelIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}