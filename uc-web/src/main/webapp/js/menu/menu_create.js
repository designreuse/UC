/**
 ///////////////////////////////////////// Create///////////////////////////////////////////////////////////
 **/
var settingMenuCreate = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "-1_project"
        }
    },
    callback: {
        onClick: onClickMenuCreate,
        onNodeCreated: zTreeOnNodeCreated
    },
    view: {
        showIcon: showIcon
    }
};

function onClickMenuCreate(e, treeId, treeNode) {
    var menu = getSelectedMenuNode(treeId);
    var menuId = menu.menuId;
    var menuName = menu.menuName;
    $("#moduleId_menuCreateForm").val(menuId);
    $("#moduleName_menuCreateForm").val(menuName);
    $('#treeTradeMenuCreate').toggle();
}


function forwardToCreateMenu() {
    var url = createUrl("/menu/forwardToCreate/");
    $.get(url, function (data) {
        $('#modelContent').html(data);
    });
}

function validateMenu(menuName, menuUrl) {
    validateMenuName(menuName);
    validateMenuUrl(menuUrl);
    return true;
}
function readyCreateMenu() {
    $("#menuName_create").blur(function () {
        validateMenuName("menuName_create");
    });
    $("#menuUrl_create").blur(function () {
        validateMenuUrl("menuUrl_create");
    });
}


function createMenu() {
    var menuName = $("#menuName_create").val();
    var menuDescription = $("#menuDescription_create").val();
    var menuUrl = $("#menuUrl_create").val();
    var menuIcon = 'icon_user'; //todo
    var moduleId = $('#moduleId_menuCreateForm').val(); //todo
    var projectId = $('#projectId').val();
    if (!validateMenu("menuName_create", "menuUrl_create"))return;

    var params = {
        "name": menuName,
        "description": menuDescription,
        "url": menuUrl,
        "icon": menuIcon,
        "moduleId": moduleId,
        "projectId": projectId
    };
    ajax('post', params, createUrl("/menu/create"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshMenuIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}

function showModuleTreeForMenuCreate() {
    $("#treeTradeMenuCreate").toggle();
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/menu/showTreeNodes?projectId=" + $('#projectId').val()),
        type: "get",
        async: false,
        dataType: "json",
        success: function (response) {
            $.each(response.data, function (i, item) {
                if (item.type == 'module') {
                    treeNodes.push(createMenuNode(item));
                }
            });
            $.fn.zTree.init($("#treeTradeMenuCreate"), settingMenuCreate, treeNodes);
        }
    });
};
