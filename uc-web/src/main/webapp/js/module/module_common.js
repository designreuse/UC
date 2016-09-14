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
    this.type = type; // type = module/module/module
    if (name.length > 25) {
        this.name = name.substring(0, 25) + "...";
    } else {
        this.name = name
    }
    if (this.type == 'project') {
        this.iconSkin = "pIcon";
    }
}

function refreshModuleIndex() {
    window.location.href = createUrl("/module/index?projectId=" + $('#projectId').val());
}

function validModuleName(val) {
    var re = /^([\u4E00-\u9FA5]|\w)*$/;
    return re.test(val);
}


function getSelectModuleNode(treeId) {
    $().dropdown('toggle');
    var zTree = $.fn.zTree.getZTreeObj(treeId), nodes = zTree
        .getSelectedNodes();
    var moduleId = "", moduleName = "";
    nodes.sort(function compare(a, b) {
        return a.originalId - b.originalId;
    });
    for (var i = 0; i < nodes.length; i++) {
        moduleId += nodes[i].originalId + ",";
        moduleName += nodes[i].name + ",";
    }
    if (moduleId.length > 0)
        moduleId = moduleId.substring(0, moduleId.length - 1);
    if (moduleName.length > 0)
        moduleName = moduleName.substring(0, moduleName.length - 1);
    return {moduleId: moduleId, moduleName: moduleName};
}
function showIcon(treeId, treeNode) {
    return (treeNode.type == 'project');
};


function validateModuleName(nameId) {
    var moduleObject = $('#' + nameId);
    var moduleName = moduleObject.val();
    if (validateNull(moduleName)) {
        moduleObject.focus();
        renderInputField(false, nameId, "模块名称不能为空");
        return false;
    } else if (!validModuleName(moduleName)) {
        moduleObject.focus();
        renderInputField(false, nameId, "模块名称不合法");
        return false;
    } else if (!validateNull(moduleName) && $.trim(moduleName).length > 64) {
        moduleObject.focus();
        renderInputField(false, nameId, "模块名称长度不能超过64位");
        return false;
    } else {
        renderInputField(true, nameId, "");
    }
}


function createModuleNode(item) {
    var name = item.name;
    if (name.length > 25) {
        name = name.substring(0, 25) + "...";
    }
    return new Node(item.id, item.pid, name, item.pinyin, item.pinyinAlia, item.type, item.parentType);
}