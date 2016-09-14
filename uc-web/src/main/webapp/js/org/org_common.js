function extractedOrgNode(treeId) {
    $().dropdown('toggle');
    var zTree = $.fn.zTree.getZTreeObj(treeId), nodes = zTree.getSelectedNodes(), orgName = "";
    var orgId = "";
    nodes.sort(function compare(a, b) {
        return a.originalId - b.originalId;
    });
    for (var i = 0; i < nodes.length; i++) {
        orgId += nodes[i].originalId + ",";
        orgName += nodes[i].name + ",";
    }
    if (orgId.length > 0)
        orgId = orgId.substring(0, orgId.length - 1);
    if (orgName.length > 0)
        orgName = orgName.substring(0, orgName.length - 1);
    return {orgName: orgName, orgId: orgId};
}

function onClickOrgCreate(e, treeId, treeNode) {
    var __ret = extractedOrgNode(treeId);
    var orgName = __ret.orgName;
    var orgId = __ret.orgId;
    $("#orgId_orgCreateForm").val(orgId);
    $("#orgName_orgAddForm").val(orgName);
    $('#selectOrgTree_createOrg').toggle();
}
function onClickOrgEdit(e, treeId, treeNode) {
    var __ret = extractedOrgNode(treeId);
    var orgName = __ret.orgName;
    var orgId = __ret.orgId;
    $("#orgId_editOrg").val(orgId);
    $("#orgName_editOrg").val(orgName);
    $('#selectOrgTree_editOrg').toggle();
}
/**
 * 显示单选树
 */
function showTreeNodes(divTreeId, setting) {
    var treeNodes = new Array();
    $.ajax({
        url: createUrl("/org/showTreeNodesForIndex"),
        type: "post",
        async: false,
        dataType: "json",
        success: function (response) {
            $.each(response.data, function (i, item) {
                if (item.type == 'org') {
                    var name = item.name;
                    if (name.length > 25) {
                        name = name.substring(0, 25) + "...";
                    }
                    treeNodes.push(new OrgNode(item.id + "_" + item.type, item.pid + "_" + item.parentType,
                        name, item.isParent, item.type, item.pinyin, item.pinyinAlia, item.mail, item.id, item.pid
                    ));
                }
            });
            $.fn.zTree.init(divTreeId, setting, treeNodes);
        }
    });
};



function zTreeOnNodeCreated(event, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    if (treeNode.level == 0) {
        treeObj.expandNode(treeNode, true, true, true);
    }
    if (!treeNode.isParent) {
        treeNode.iconSkin = "lIcon";
        treeObj.updateNode(treeNode);
    }
}
function refreshOrgIndex() {
    window.location.href = createUrl("/org/index");
}

function logout() {
    window.location.href = createUrl("/logout");
}
