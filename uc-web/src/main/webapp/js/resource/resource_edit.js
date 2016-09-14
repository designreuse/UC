/**
 /////////////////////////////////////////Edit///////////////////////////////////////////////////////////
 * */
var settingResourceEdit = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "-1_project"
        }
    },
    callback: {
        onClick: onClickResourceEdit,
        onNodeCreated: zTreeOnNodeCreated
    },
    view: {
        showIcon: showIcon
    }
};
function readyEditResource() {
    $("#resourceName_edit").blur(function () {
        validateResourceName("resourceName_edit");
    });
    $("#resourceCode_edit").blur(function () {
        validateResourceCode("resourceCode_edit");
    });
}
function forwardToEditResource(resourceId) {
    var url = createUrl("/resource/forwardToEdit/" + resourceId);
    //获取添加org的页面内容
    $.get(url, function (data) {
        $('#divEditContent').html(data);
    });
}

function showModuleTreeForResourceEdit() {
    $("#treeTradeResourceEdit").toggle();
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/resource/showTreeNodes?projectId=" + $('#projectId').val()),
        type: "get",
        async: false,
        dataType: "json",
        success: function (response) {
            $.each(response.data, function (i, item) {
                if (item.type == 'module') {
                    createResourceTreeNode(item, treeNodes);
                }
            });
            $.fn.zTree.init($("#treeTradeResourceEdit"), settingResourceEdit, treeNodes);
        }
    });
};


function onClickResourceEdit(e, treeId, treeNode) {
    var resource = createResourceNode(treeId);
    var resourceId = resource.resourceId;
    var resourceName = resource.resourceName;
    $("#moduleId_resourceEditForm").val(resourceId);
    $("#moduleName_resourceEditForm").val(resourceName);
    $('#treeTradeResourceEdit').toggle();
}


function editResource() {
    var resourceName = $("#resourceName_edit").val();
    var resourceDescription = $("#resourceDescription_edit").val();
    var resourceCode = $("#resourceCode_edit").val();
    var moduleId = $('#moduleId_resourceEditForm').val(); //todo
    if (!validateResource(resourceName, resourceCode))return;

    var params = {
        "id": $('#resourceId_edit').val(),
        "name": resourceName,
        "description": resourceDescription,
        "code": resourceCode,
        "moduleId": moduleId
    };
    ajax('post', params, createUrl("/resource/edit"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshResourceIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}
