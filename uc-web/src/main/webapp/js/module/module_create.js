/**
 ///////////////////////////////////////// Create///////////////////////////////////////////////////////////
 **/
var settingModuleCreate = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "-1_project"
        }
    },
    callback: {
        onClick: onClickModuleCreate,
        onNodeCreated: zTreeOnNodeCreated
    },
    view: {
        showIcon: showIcon
    }
};

function onClickModuleCreate(e, treeId, treeNode) {
    var module = getSelectModuleNode(treeId);
    var moduleId = module.moduleId;
    var moduleName = module.moduleName;
    $("#moduleId_moduleCreateForm").val(moduleId);
    $("#moduleName_moduleCreateForm").val(moduleName);
    $('#treeTradeModuleCreate').toggle();
}


function forwardToCreateModule() {
    var url = createUrl("/module/forwardToCreate/");
    $.get(url, function (data) {
        $('#modelContent').html(data);
    });
}
function validateModule(nameID) {
    validateModuleName(nameID);
    return true;
}
function readyCreateModule() {
    $("#moduleName_create").blur(function () {
        validateModuleName("moduleName_create");
    });
}


function createModule() {
    var moduleName = $("#moduleName_create").val();
    var moduleDescription = $("#moduleDescription_create").val();
    var moduleIcon = 'icon_user'; //todo
    var parentId = $('#moduleId_moduleCreateForm').val(); //todo
    var projectId = $('#projectId').val();
    if (!validateModule("moduleName_create"))return;

    var params = {
        "name": moduleName,
        "description": moduleDescription,
        "icon": moduleIcon,
        "parentId": parentId,
        "projectId": projectId
    };
    ajax('post', params, createUrl("/module/create"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshModuleIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}

function showModuleTreeForModuleCreate() {
    $("#treeTradeModuleCreate").toggle();
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/module/showTreeNodes?projectId=" + $('#projectId').val()),
        type: "get",
        async: false,
        dataType: "json",
        success: function (response) {
            $.each(response.data, function (i, item) {
                var name = item.name;
                if (name.length > 25) {
                    name = name.substring(0, 25) + "...";
                }
                treeNodes.push(createModuleNode(item));
            });
            $.fn.zTree.init($("#treeTradeModuleCreate"), settingModuleCreate, treeNodes);
        }
    });
};
