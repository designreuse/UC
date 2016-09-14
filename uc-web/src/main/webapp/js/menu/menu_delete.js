/**
 /////////////////////////////////////////Delete///////////////////////////////////////////////////////////
 * */
function confirmDeleteMenu() {
    alertConfirmationMsgDlg("确定要删除此菜单?", deleteMenu, $('#currentMenuId').val());
};

function deleteMenu(id) {
    ajax('post', null, createUrl("/menu/delete/" + id),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshMenuIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}