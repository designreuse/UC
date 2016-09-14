function forwardToImportStaff() {
    $.get("/staff/forwardToImport",
        function(data){
            $("#orgManagementRightContent").html(data);
        });
}
function readyImportStaff() {
    $("#importFile").fileupload({
        url: createUrl("/staff/import"),
        autoUpload: false,
        dataType: "html",
        add: function (e, data) {
            $.each(data.originalFiles, function (index, file) {
                var ext = file.name.substr(file.name.lastIndexOf(".")).toLowerCase();
                if (ext != ".xls" && ext != ".xlsx") {
                    alertPromptMsgDlg("文件类型不正确，仅支持.xls或.xlsx文件类型", 2);
                    return false;
                }
                if (file.size > 10 * 1024 * 1024) {
                    alertPromptMsgDlg("上传文件过大，文件不能超过10M", 2);
                    return false;
                }
                $('#importFileName').html(file.name);
                $("#uploadBtn").on('click',function () {
                    data.submit();
                });
            });
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css( 'width', progress + '%');
        },
        always: function (e, data) {
            $('#importResultModalContent').html(data.result);
        }
    });
    //$('#importFile').fileupload({
    //    url: createUrl("/staff/import"),
    //    dataType: "html",
    //    autoUpload: false,
    //    acceptFileTypes: /(\.|\/)(xls|xlsx)$/i,
    //    maxFileSize: 999000
    //}).on('fileuploadadd', function (e, data) {
    //    $.each(data.files, function (index, file) {
    //        var ext = file.name.substr(file.name.lastIndexOf(".")).toLowerCase();
    //        if (ext != ".xls" && ext != ".xlsx") {
    //            alertPromptMsgDlg("文件类型不正确，仅支持.xls或.xlsx文件类型", 2);
    //            return false;
    //        }
    //        if (file.size > 10 * 1024 * 1024) {
    //            alertPromptMsgDlg("上传文件过大，文件不能超过10M", 2);
    //            return false;
    //        }
    //        $('#importFileName').html(file.name)
    //    });
    //    $("#uploadBtn").off('click').on('click',function () {
    //        alert('upload');
    //        data.submit();
    //    });
    //}).on('fileuploadprocessalways', function (e, data) {
    //    var index = data.index,
    //        file = data.files[index],
    //        node = $(data.context.children()[index]);
    //    if (file.preview) {
    //        node.prepend('<br>').prepend(file.preview);
    //    }
    //    if (file.error) {
    //        node.append('<br>').append($('<span class="text-danger"/>').text(file.error));
    //    }
    //    if (index + 1 === data.files.length) {
    //        data.context.find('button').text('上传').prop('disabled', !!data.files.error);
    //    }
    //}).on('fileuploadprogressall', function (e, data) {
    //    var progress = parseInt(data.loaded / data.total * 100, 10);
    //    $('#progress .progress-bar').css( 'width', progress + '%'
    //    );
    //}).on('fileuploadalways', function (e, data) {
    //    $('#importResultModalContent').html(data.result);
    //    $('#importResultModal').modal();
    //});

}