/**
 /////////////////////////////////////////Edit///////////////////////////////////////////////////////////
 * */
var settingMenuEdit = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "-1_project"
        }
    },
    callback: {
        onClick: onClickMenuEdit,
        onNodeCreated: zTreeOnNodeCreated
    },
    view: {
        showIcon: showIcon
    }
};
function readyEditMenu() {
    $("#menuName_edit").blur(function () {
        validateMenuName("menuName_edit");
    });
    $("#menuUrl_edit").blur(function () {
        validateMenuUrl("menuUrl_edit");
    });
}
function forwardToEditMenu(menuId) {
    var url = createUrl("/menu/forwardToEdit/" + menuId);
    //获取添加org的页面内容
    $.get(url, function (data) {
        $('#divEditContent').html(data);
    });
}


function showModuleTreeForMenuEdit() {
    $("#treeTradeMenuEdit").toggle();
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
            $.fn.zTree.init($("#treeTradeMenuEdit"), settingMenuEdit, treeNodes);
        }
    });
};


function onClickMenuEdit(e, treeId, treeNode) {
    var menu = getSelectedMenuNode(treeId);
    var menuId = menu.menuId;
    var menuName = menu.menuName;
    $("#moduleId_menuEditForm").val(menuId);
    $("#moduleName_menuEditForm").val(menuName);
    $('#treeTradeMenuEdit').toggle();
}


function editMenu() {
    var menuName = $("#menuName_edit").val();
    var menuDescription = $("#menuDescription_edit").val();
    var menuUrl = $("#menuUrl_edit").val();
    var menuIcon = 'icon_user'; //todo
    var moduleId = $('#moduleId_menuEditForm').val(); //todo
    if (!validateMenu("menuName_edit", "menuUrl_edit"))return;

    var params = {
        "id": $('#menuId_edit').val(),
        "name": menuName,
        "description": menuDescription,
        "url": menuUrl,
        "icon": menuIcon,
        "moduleId": moduleId
    };
    ajax('post', params, createUrl("/menu/edit"),
        function (data) {
            $('#modalPopup').modal('hide');
            alertPromptMsgDlg(data.message, 1, refreshMenuIndex);
        },
        function (data) {
            alertPromptMsgDlg(data.message, 3)
        }
    );
}
