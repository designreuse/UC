
//////////////////////////////////////////////Delete//////////////////////////////////////////////////////

$("label[id^='deleteOperation_']").click(function () {
    var id = $(this).attr("id").replace("deleteOperation_", "");
    alertConfirmationMsgDlg("确定要删除此操作?", deleteOperation, id);
});

function deleteOperation(id) {
    ajax('post', null, createUrl("/operation/delete/" + id),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshOperationIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}