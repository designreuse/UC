/**
 /////////////////////////////////////////Delete///////////////////////////////////////////////////////////
 * */
function confirmDeleteModule() {
    alertConfirmationMsgDlg("确定要删除此模块?", deleteModule, $('#currentModuleId').val());
};

function deleteModule(id) {
    ajax('post', null, createUrl("/module/delete/" + id),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshModuleIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}