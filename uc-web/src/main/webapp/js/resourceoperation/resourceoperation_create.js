/**
 ///////////////////////////////////////// Create///////////////////////////////////////////////////////////
 **/
var settingResourceOperationCreate = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "-1_project"
        }
    },
    callback: {
        onClick: onClickResourceOperationCreate,
        onNodeCreated: zTreeOnNodeCreated
    },
    view: {
        showIcon: showIcon
    }
};

function onClickResourceOperationCreate(e, treeId, treeNode) {
    if (treeNode.type != 'resource') {
        renderInputField(false, "resource_create", "请选择资源");
        return
    }else{
        restoreInputField("resource_create");
    }
    var resource = createResourceNode(treeId);
    var resourceId = resource.resourceId;
    var resourceName = resource.resourceName;
    $("#resourceId_resourceoperationCreateForm").val(resourceId);
    $("#resourceName_resourceoperationCreateForm").val(resourceName);
    $("#currentResourceCreateId").val(treeNode.originalId);
    $('#treeTradeResourceOperationCreate').toggle();
}


function forwardToCreateResource() {
    var url = createUrl("/resourceoperation/forwardToCreate/");
    $.get(url, function (data) {
        $('#modelContent').html(data);
    });
}

function createResource() {
    var resourceId = $("#currentResourceCreateId").val();
    var params = {
        "resourceId": resourceId,
        "operationId": $("#operationId_create").val()
    };
    ajax('post', params, createUrl("/resourceoperation/create"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshResourceIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}

function showResourceTree() {
    $("#treeTradeResourceOperationCreate").toggle();
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/resource/showTreeNodes?projectId=" + $('#projectId').val()),
        type: "get",
        async: false,
        dataType: "json",
        success: function (response) {
            $.each(response.data, function (i, item) {
                createResourceOperationTreeNode(item, treeNodes);
            });
            $.fn.zTree.init($("#treeTradeResourceOperationCreate"), settingResourceOperationCreate, treeNodes);
        }
    });
};
