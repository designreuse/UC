/**
 /////////////////////////////////////////Delete///////////////////////////////////////////////////////////
 * */
function confirmDeleteResource() {
    alertConfirmationMsgDlg("确定要删除此菜单?", deleteResource, $('#currentResourceId').val());
};

function deleteResource(id) {
    ajax('post', null, createUrl("/resource/delete/" + id),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshResourceIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}