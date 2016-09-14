function forwardToCreateOperation() {
    var url = createUrl("/operation/forwardToCreate/");
    $.get(url, function (data) {
        $('#modelContent').html(data);
    });
}

function readyCreateOperation() {
    $("#operationName_create").blur(function () {
        validateOperationName("operationName_create");
    });
    $("#operationCode_create").blur(function () {
        validateOperationCode("operationCode_create");
    });

}

function validateCode(value) {
    var reg = new RegExp("(\\w)+");
    if (!reg.test($.trim(value))) {
        return false;
    }
    return true;
};

function createOperation() {
    var operationName = $("#operationName_create").val();
    var operationDescription = $("#operationDescription_create").val();
    var operationCode = $("#operationCode_create").val();
    if (!validateOperation("operationName_create", "operationCode_create"))return;

    var params = {
        "name": operationName,
        "description": operationDescription,
        "code": operationCode
    };
    ajax('post', params, createUrl("/operation/create"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshOperationIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}
