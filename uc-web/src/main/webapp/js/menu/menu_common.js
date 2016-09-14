/**
 /////////////////////////////////////////Common-Module///////////////////////////////////////////////////////////
 **/
function Node(id, pid, name, pinyin, pinyinAlia, type, parentType) {
    this.id = id + "_" + type;
    this.pid = pid + "_" + parentType;
    this.originalId = id;
    this.pinyin = pinyin;
    this.pinyinAlia = pinyinAlia;
    this.fullname = name;
    this.type = type; // type = menu/module/resource
    this.parentType = parentType;
    if (name.length > 25) {
        this.name = name.substring(0, 25) + "...";
    } else {
        this.name = name
    }
    if (this.type == 'menu') {
        this.iconSkin = "sIcon";
    } else if (this.type == 'project') {
        this.iconSkin = "pIcon";
    }
}

function refreshMenuIndex() {
    window.location.href = createUrl("/menu/index?projectId=" + $('#projectId').val());
}

function validMenuName(val) {
    var re = /^([\u4E00-\u9FA5]|\w)*$/;
    return re.test(val);
}

function validateMenuName(nameId) {
    var menuObject = $('#' + nameId);
    var menuName = menuObject.val();
    if (validateNull(menuName)) {
        menuObject.focus();
        renderInputField(false, nameId, "菜单名称不能为空");
        return false;
    } else if (!validMenuName(menuName)) {
        menuObject.focus();
        renderInputField(false, nameId, "菜单名称不合法");
        return false;
    } else if (!validateNull(menuName) && $.trim(menuName).length > 64) {
        menuObject.focus();
        renderInputField(false, nameId, "菜单名称长度不能超过64位");
        return false;
    } else {
        renderInputField(true, nameId, "");
    }
}
function validateMenuUrl(urlId) {
    var menuObject = $('#' + urlId);
    if (validateNull(menuObject.val())) {
        menuObject.focus();
        renderInputField(false, urlId, "菜单链接不能为空");
        return false;
    } else {
        renderInputField(true, urlId, "");
    }
}


function createMenuNode(item) {
    var name = item.name;
    if (name.length > 25) {
        name = name.substring(0, 25) + "...";
    }
    return new Node(item.id, item.pid, name, item.pinyin, item.pinyinAlia, item.type, item.parentType);
}

function getSelectedMenuNode(treeId) {
    $().dropdown('toggle');
    var zTree = $.fn.zTree.getZTreeObj(treeId), nodes = zTree
        .getSelectedNodes();
    var menuId = "", menuName = "";
    nodes.sort(function compare(a, b) {
        return a.originalId - b.originalId;
    });
    for (var i = 0; i < nodes.length; i++) {
        menuId += nodes[i].originalId + ",";
        menuName += nodes[i].name + ",";
    }
    if (menuId.length > 0)
        menuId = menuId.substring(0, menuId.length - 1);
    if (menuName.length > 0)
        menuName = menuName.substring(0, menuName.length - 1);
    return {menuId: menuId, menuName: menuName};
}
