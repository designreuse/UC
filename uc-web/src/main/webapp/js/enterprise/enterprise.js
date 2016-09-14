/**
 * 企业信息编辑初始化
 */
function  readyEnterpriseEdit(){
	$("#enterpriseName_edit").blur(function() {
		var enterpriseName = $("#enterpriseName_edit").val().trim();
		if (validateNull(enterpriseName)) {
			renderInputField(false, "enterpriseName_edit", "企业名称不能为空！");
		} else {
			restoreInputField("enterpriseName_edit");
		}
	});

	$('#enterpriseLogo_edit').fileupload({
		url: createUrl("/enterprise/edit"),
		dataType: 'json',
		autoUpload: false,
		acceptFileTypes: /^image\/(jpe?g|png)$/,
		maxFileSize: 2 * 1 * 1,
		previewMaxWidth: 100,
		previewMaxHeight: 100,
		previewCrop: true
	}).on('fileuploadadd', function (e, data) {
		logo_file = data.files[0];
		$("#enterpriseLogo_edit").data(data);
	}).on('fileuploadprocessdone', function (e, data) {
		var index = data.index;
		var file = data.files[index];
		var node = $('#enterprise_logo_preview_edit');
		if (file.preview) {
			node.html(file.preview)
		}
	}).on('fileuploadsubmit', function (e, data) {
	}).on('fileuploadalways', function (e, data) {
		alertPromptMsgDlg(data.result.message, 1, reload);
	});

	$("#updateEnterpriseBtn").click(function(){
		updateEnterprise();
		return false;
	});
}

/**
 * 提交企业信息更新
 */
var logo_file;

function updateEnterprise(){
	var name = $("#enterpriseName_edit").val().trim();

	if (validateNull(name)) {
		renderInputField(false, "enterpriseName_edit", "企业名称不能为空！");
		return false;
	}

	var params = {
		id : $("#enterpriseId_edit").val(),
		name : name
	};
	var enterpriseLogoData = $("#enterpriseLogo_edit").data();
	if(logo_file != undefined && logo_file != null && enterpriseLogoData != undefined){
		enterpriseLogoData.formData = params;
		enterpriseLogoData.submit();
		return;
	}
}
