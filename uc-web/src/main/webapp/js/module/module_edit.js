/**
 /////////////////////////////////////////Edit///////////////////////////////////////////////////////////
 * */
var settingModuleEdit = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "-1_project"
        }
    },
    callback: {
        onClick: onClickModuleEdit,
        onNodeCreated: zTreeOnNodeCreated
    },
    view: {
        showIcon: showIcon
    }
};
function readyEditModule() {
    $("#moduleName_edit").blur(function () {
        validateModuleName("moduleName_edit");
    });
}
function forwardToEditModule(moduleId) {
    var url = createUrl("/module/forwardToEdit/" + moduleId);
    //获取添加org的页面内容
    $.get(url, function (data) {
        $('#divEditContent').html(data);
    });
}


function showModuleTreeForModuleEdit() {
    $("#treeTradeModuleEdit").toggle();
    var treeNodes = new Array();
    $.ajax({
        url: setUrlParam(setUrlParam("/module/showTreeNodesForEdit", "projectId", $('#projectId').val()), "moduleId", $('#currentModuleId').val()),
        type: "get",
        async: false,
        dataType: "json",
        success: function (response) {
            $.each(response.data, function (i, item) {
                treeNodes.push(createModuleNode(item));
            });
            $.fn.zTree.init($("#treeTradeModuleEdit"), settingModuleEdit, treeNodes);
        }
    });
};


function onClickModuleEdit(e, treeId, treeNode) {
    var module = getSelectModuleNode(treeId);
    var moduleId = module.moduleId;
    var moduleName = module.moduleName;
    $("#parentModuleId_moduleEditForm").val(moduleId);
    $("#parentModuleName_moduleEditForm").val(moduleName);
    $('#treeTradeModuleEdit').toggle();
}


function editModule() {
    var moduleName = $("#moduleName_edit").val();
    var moduleDescription = $("#moduleDescription_edit").val();
    var parentId = $('#parentModuleId_moduleEditForm').val(); //todo
    if (!validateModule("moduleName_edit"))return;

    var params = {
        "id": $('#moduleId_edit').val(),
        "name": moduleName,
        "description": moduleDescription,
        "parentId": parentId
    };
    ajax('post', params, createUrl("/module/edit"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshModuleIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}
