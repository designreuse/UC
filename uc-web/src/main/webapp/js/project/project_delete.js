
//////////////////////////////////////////////Delete//////////////////////////////////////////////////////

$("label[id^='deleteProject_']").click(function () {
    var id = $(this).attr("id").replace("deleteProject_", "");
    alertConfirmationMsgDlg("确定要删除此项目?", deleteProject, id);
});

function deleteProject(id) {
    ajax('post', null, createUrl("/project/delete/" + id),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshProjectIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}