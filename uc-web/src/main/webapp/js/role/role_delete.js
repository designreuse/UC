//////////////////////////////////////////////Delete//////////////////////////////////////////////////////

$("label[id^='deleteRole_']").click(function () {
    var id = $(this).attr("id").replace("deleteRole_", "");
    alertConfirmationMsgDlg("确定要删除此角色?", deleteRole, id);
});

function deleteRole(id) {
    ajax('post', null, createUrl("/role/delete/" + id),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshRoleIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}