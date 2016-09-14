$("label[id^='deleteRoleLevel_']").click(function () {
    var id = $(this).attr("id").replace("deleteRoleLevel_", "");
    alertConfirmationMsgDlg("确定要删除此角色级别?", deleteRoleLevel, id);
});

function deleteRoleLevel(id) {
    ajax('post', null, createUrl("/rolelevel/delete/" + id),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshRoleLevelIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}