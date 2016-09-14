/**
 /////////////////////////////////////////Delete///////////////////////////////////////////////////////////
 * */
function confirmDeleteResource() {
    alertConfirmationMsgDlg("确定要删除此资源操作?", deleteResource, $('#currentResourceId').val(), $('#currentOperationId').val());
};

function deleteResource(resourceId, operationId) {
    ajax('post', null, setUrlParam(setUrlParam("/resourceoperation/delete", "resourceId", resourceId), "operationId", operationId),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshResourceIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}