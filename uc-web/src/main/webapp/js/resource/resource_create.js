/**
 ///////////////////////////////////////// Create///////////////////////////////////////////////////////////
 **/
var settingResourceCreate = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "-1_project"
        }
    },
    callback: {
        onClick: onClickResourceCreate,
        onNodeCreated: zTreeOnNodeCreated
    },
    view: {
        showIcon: showIcon
    }
};

function onClickResourceCreate(e, treeId, treeNode) {
    var resource = createResourceNode(treeId);
    var resourceId = resource.resourceId;
    var resourceName = resource.resourceName;
    $("#moduleId_resourceCreateForm").val(resourceId);
    $("#moduleName_resourceCreateForm").val(resourceName);
    $('#treeTradeResourceCreate').toggle();
}


function forwardToCreateResource() {
    var url = createUrl("/resource/forwardToCreate/");
    $.get(url, function (data) {
        $('#modelContent').html(data);
    });
}

function validateResourceName(resourceName) {
    if (validateNull(resourceName)) {
        $("#resourceName_create").focus();
        renderInputField(false, "resourceName_create", "资源名称不能为空");
        return false;
    } else if (!validResourceName(resourceName)) {
        $("#resourceName_create").focus();
        renderInputField(false, "resourceName_create", "资源名称不合法");
        return false;
    } else if (!validateNull(resourceName) && $.trim(resourceName).length > 64) {
        $("#resourceName_create").focus();
        renderInputField(false, "resourceName_create", "资源名称长度不能超过64位");
        return false;
    } else {
        renderInputField(true, "resourceName_create", "");
    }
}
function validateResource(resourceName, resourceCode) {
    validateResourceName(resourceName);
    validateResourceCode(resourceCode);
    return true;
}
function validateResourceCode(resourceCode) {
    if (validateNull(resourceCode)) {
        $("#resourceCode_create").focus();
        renderInputField(false, "resourceCode_create", "资源代码不能为空");
        return false;
    } else {
        renderInputField(true, "resourceCode_create", "");
    }
}
function readyCreateResource() {
    $("#resourceName_create").blur(function () {
        var resourceName = $("#resourceName_create").val();
        validateResourceName(resourceName);
    });
    $("#resourceCode_create").blur(function () {
        var resourceCode = $("#resourceCode_create").val();
        validateResourceCode(resourceCode);
    });
}


function createResource() {
    var resourceName = $("#resourceName_create").val();
    var resourceDescription = $("#resourceDescription_create").val();
    var resourceCode = $("#resourceCode_create").val();
    var moduleId = $('#moduleId_resourceCreateForm').val(); //todo
    var projectId = $('#projectId').val();
    if (!validateResource("resourceName_create", "resourceCode_create"))return;

    var params = {
        "name": resourceName,
        "description": resourceDescription,
        "code": resourceCode,
        "moduleId": moduleId,
        "projectId": projectId
    };
    ajax('post', params, createUrl("/resource/create"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshResourceIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}

function showModuleTreeForResourceCreate() {
    $("#treeTradeResourceCreate").toggle();
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/resource/showTreeNodes?projectId=" + $('#projectId').val()),
        type: "get",
        async: false,
        dataType: "json",
        success: function (response) {
            $.each(response.data, function (i, item) {
                if (item.type == 'module')
                    createResourceTreeNode(item, treeNodes);
            });
            $.fn.zTree.init($("#treeTradeResourceCreate"), settingResourceCreate, treeNodes);
        }
    });
};
