/**
 /////////////////////////////////////////Common-Module///////////////////////////////////////////////////////////
 **/
function Node(id, pid, name, pinyin, pinyinAlia, type, parentType) {
    this.id = id + "_" + type;
    this.pid = pid + "_" + parentType;
    this.originalId = id;
    this.originalPid = pid;
    this.pinyin = pinyin;
    this.pinyinAlia = pinyinAlia;
    this.fullname = name;
    this.type = type;
    this.parentType = parentType;
    if (name.length > 25) {
        this.name = name.substring(0, 25) + "...";
    } else {
        this.name = name
    }
    if (this.type == 'operation') {
        this.iconSkin = "sIcon";
    } else if (this.type == 'project') {
        this.iconSkin = "pIcon";
    }
}

function refreshResourceIndex() {
    window.location.href = createUrl("/resourceoperation/index?projectId=" + $('#projectId').val());
}

function createResourceOperationTreeNode(item, treeNodes) {
    var name = item.name;
    if (name.length > 25) {
        name = name.substring(0, 25) + "...";
    }
    var node = new Node(item.id, item.pid, name, item.pinyin, item.pinyinAlia, item.type, item.parentType)
    treeNodes.push(node);
}