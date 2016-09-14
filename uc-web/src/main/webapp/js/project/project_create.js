
/**
 ///////////////////////////////////////// Create ///////////////////////////////////////////////////////////
 **/

function forwardToCreateProject() {
    var url = createUrl("/project/forwardToCreate/");
    $.get(url, function (data) {
        $('#modelContent').html(data);
    });
}

function readyCreateProject() {
    $("#projectName_create").blur(function () {
        validateProjectName("projectName_create");
    });
    $("#projectUrl_create").blur(function () {
        validateProjectUrl("projectUrl_create");
    });

}

function validateUrl(value) {
    var reg = new RegExp("^(ht|f)tp(s?)\:\/\/");
    if (!reg.test($.trim(value))) {
        return false;
    }
    return true;
};

function createProject() {
    var projectName = $("#projectName_create").val();
    var projectDescription = $("#projectDescription_create").val();
    var projectUrl = $("#projectUrl_create").val();
    if (!validateProject("projectName_create", "projectUrl_create"))return;

    var params = {
        "name": projectName,
        "description": projectDescription,
        "url": projectUrl
    };
    ajax('post', params, createUrl("/project/create"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshProjectIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}
