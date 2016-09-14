///////////////////////////////////////////////Edit//////////////////////////////////////////////////////

$("label[id^='editOperation_']").click(function () {
    var id = $(this).attr("id").replace("editOperation_", "");
    $.ajax({
        url: createUrl("/operation/forwardToEdit/" + id),
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


function readyEditOperation() {
    $("#operationName_edit").blur(function () {
        validateOperationName("operationName_edit");
    });
    $("#operationCode_edit").blur(function () {
        validateOperationCode("operationCode_edit");
    });

}
function editOperation() {
    var operationName = $("#operationName_edit").val();
    var operationDescription = $("#operationDescription_edit").val();
    var operationCode = $("#operationCode_edit").val();
    if (!validateOperation("operationName_edit", "operationCode_edit"))return;

    var params = {
        "id": $('#operationId').val(),
        "name": operationName,
        "description": operationDescription,
        "code": operationCode
    };
    ajax('post', params, createUrl("/operation/edit"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshOperationIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}