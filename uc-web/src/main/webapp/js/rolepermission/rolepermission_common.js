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
    this.type = type; // type = menu/module/menu
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


function searchInit(id) {
    $("#" + id).keyup(function (e) {
        getNodesByFuzzy(e, "resultUL", "resultDiv", id, $("#divSearch").width() - 2, "", "treeForRolePermission",searchFilter);
    });
    $("#" + id).keydown(function (e) {
        e = e || window.event;
        //回车
        if (e.keyCode == 13) {
            var nodeId = $("#resultUL > li.selectLi").attr('nodeId');
            var nodeName = $("#resultUL > li.selectLi").text();

            focusNodeByIdAndName(nodeId, "treeForRolePermission");
            return false;
        }
    });
}

function createPermissionNode(item) {
    return new Node(item.id, item.pid, item.name, item.pinyin, item.pinyinAlia, item.type, item.parentType);
}
