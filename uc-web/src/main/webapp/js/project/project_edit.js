///////////////////////////////////////////////Edit//////////////////////////////////////////////////////

$("label[id^='editProject_']").click(function () {
    var id = $(this).attr("id").replace("editProject_", "");
    $.ajax({
        url: createUrl("/project/forwardToEdit/" + id),
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


function readyEditProject() {
    $("#projectName_edit").blur(function () {
        validateProjectName("projectName_edit");
    });
    $("#projectUrl_edit").blur(function () {
        validateProjectUrl("projectUrl_edit");
    });

}
function editProject() {
    var projectName = $("#projectName_edit").val();
    var projectDescription = $("#projectDescription_edit").val();
    var projectUrl = $("#projectUrl_edit").val();
    if (!validateProject("projectName_edit", "projectUrl_edit"))return;

    var params = {
        "id": $('#projectId').val(),
        "name": projectName,
        "description": projectDescription,
        "url": projectUrl
    };
    ajax('post', params, createUrl("/project/edit"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshProjectIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}