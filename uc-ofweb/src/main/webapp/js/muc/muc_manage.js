//获取树节点的名称，每个名称之间以“ ，”隔开
function getNodesName(nodes) {
    var length = nodes.length;
    var selectNodeStr = "";
    for (var i = 0; i < length; i++) {
        selectNodeStr += nodes[i].name + ",";
    }
    if (!validateNull(selectNodeStr)) {
        selectNodeStr = selectNodeStr.substr(0, selectNodeStr.length - 1);
    }
    return selectNodeStr;
}

/**
 * 获取树节点的ID
 * @param nodes
 * @returns {string}
 */
function getNodeId(nodes){
    var length = nodes.length;
    var selectNodeIdStr = "";
    for (var i = 0; i < length; i++) {
        var nodeId = nodes[i].id;
        if (nodeId.indexOf("_org")>0){
            var childrenNodes = nodes[i].children;
            if (childrenNodes && childrenNodes.length>0){
                selectNodeIdStr += getNodeId(childrenNodes);
                selectNodeIdStr +=",";
            }
        }else {
            selectNodeIdStr += nodes[i].id + ",";
        }
    }
    if (!validateNull(selectNodeIdStr)) {
        selectNodeIdStr = selectNodeIdStr.substr(0, selectNodeIdStr.length - 1);
    }
    return selectNodeIdStr;
}

/**
 * 获取被选中的树节点
 * @param treeId
 */
function findTreeCheckNodes(treeId) {
    var treObj = $.fn.zTree.getZTreeObj(treeId);
    return treObj.getCheckedNodes();
}

/**
 * 定义树节点
 */
function NodeWithUser(id, pid, name, isParent, type, gender, pinyin, pinyinAlia, mail) {
    this.id = id;
    this.pid = pid;
    this.gender = gender;
    this.pinyin = pinyin;
    this.pinyinAlia = pinyinAlia;
    this.fullname = name;
    this.mail = mail;
    this.open = true;
    this.childOuter = false;
    this.dropInner = true;
    this.dropRoot = true;
    if (name.length > 13) {
        this.name = name.substring(0, 13) + "...";
    } else {
        this.name = name
    }
    this.isParent = isParent;
    this.type = type;

    if (this.type == "staff") {
        if (this.gender == "0") {
            this.iconSkin = "sIconWomen";
        } else {
            this.iconSkin = "sIconMen";
        }
    }

    if (this.pid == "0_org") {
        this.iconSkin = "pIcon";
    }
}

/**
 * 点击树节点
 * @param e
 * @param treeId
 * @param treeNode
 */
function onClickTreeNode(e, treeId, treeNode) {
    //屏蔽点击组织结构节点时，添加部门和用户的按钮根据节点类型启用/不启用
    if (treeNode.type == "staff") {
    } else {
        forwardToEditOrg(treeNode.id.replace("_org", ""));
        $("#btnReleaseOrgMapping").hide();
    }
}

function zTreeOnNodeCreated(event, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    if (treeNode.level == 0) {
        treeObj.expandNode(treeNode, true, false, true);
    }
    if (treeNode.level >= 1){
        treeObj.expandNode(treeNode, false, true, true);
    }
};

/**
 * 显示单选树
 */
function showTreeNodesForCreate(divTreeId, setting) {
    $(divTreeId).toggle();
    var treeNodes = new Array();
    $.ajax({
        url: "createTreeNodes",
        method:"POST",
        async: false,
        dataType: "json",
        success: function (response) {
            $.each(response.data, function (i, item) {
                treeNodes.push(new NodeWithUser(item.id, item.pid, item.name, item.isParent, item.type, item.gender, item.pinyin, item.pinyinAlia, item.mail));
            });
            $.fn.zTree.init(divTreeId, setting, treeNodes);
        }
    });
};

/**
 * 显示多选组织结构树
 * @param treeId
 */
function showOrgTreeForStaff(treeId) {
    var orgSelectTreeSetting = {
        //设置复选框
        check: {
            enable: true,
            chkboxType: {"Y": "", "N": ""}
        },
        //设置树的数据模式：此为简单数据模式
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
                rootPId: "0_org"
            }
        },
        callback: {
            //onClick: onClickTreeNode,
            onNodeCreated: zTreeOnNodeCreated
        },
        view: {
            showIcon: false
        }
    };
    showTreeNodesForCreate($("#" + treeId), orgSelectTreeSetting);
}

//获取群信息
function queryRoomMembers(roomJid) {
    var uri = "mucMember?roomJid="+roomJid;
    var members = new Array();
    var room = null;
    $.ajax({
        url:uri,
        cache:false,
        method:"GET",
        dataType:"json",
        error:function(xmlHttpRequest,error){
            alert(error);
        },
        success:function(result){
            if (result.ret>=0 && result.data){
                var resultData = result.data;
                $("#labelRoomId").html(resultData.roomId);
                $("#labelRoomName").html(resultData.roomNaturalName);
                if(resultData.roomType == 0){
                    $("#labelRoomType").html("讨论组");
                }else {
                    $("#labelRoomType").html("私有群");
                }
                members = resultData.members;
                var length = members.length;
                var params = {
                    length : members.length,
                    members : members
                }
                var html = template('showMembers', params);
                document.getElementById('memberList').innerHTML = html;
                room = resultData;
            }else {
                alert("获取群组信息失败，请稍后重试...")
            }
        }
    })
    return room;
}

/**
 * 添加群成员
 */
function addMember(roomJid) {
    var params = {
        "roomJid" : roomJid,
        "role" : $("#selectedRole").val(),
        "membersId" : getNodeId(findTreeCheckNodes("treeTrade_create"))
    };
    alert(getNodeId(findTreeCheckNodes("treeTrade_create")));
    $.ajax({
        url: "mucMember",
        method: "POST",
        async: false,
        data: params,
        dataType: "json",
        traditional: true,
        success: function (result) {
            if (result.ret >=0 ) {
                alertPromptMsgDlg("保存成功！", 2, readyMemberManage(roomJid));
            } else {
                alertConfirmationMsgDlg("保存失败，请重试...",null,null);
            }
        }
    });
}

/**
 * 删除成员
 * @param roomJid
 * @param memberJid
 */
function deleteMember(roomJid, memberJid) {
    var uri = "mucMember?roomJid="+roomJid;
    uri += "&memberJid="+memberJid;
    $.ajax({
        url:uri,
        cache:false,
        method:"DELETE",
        dataType:"json",
        async:false,
        //contentType: 'application/json',
        error:function(xmlHttpRequest,error){
            alert(error);
        },
        success:function(result){
            if (result.ret>=0){
                readyMemberManage(roomJid);
            }else {
                alert("删除失败，请稍后重试...");
            }
        }
    })
}

function afterModify(roomJid){
    readyMemberManage(roomJid);
    $("#saveMemberModifyBtn").unbind('click');
    $("#popMemberModify").modal('hide');
}

/**
 * 修改成员角色
 * @param roomJid
 * @param memberJid
 */
function modifyMember(roomJid,memberJid) {
    var role = $("#modifyMemberRole").val();
    var uri = "mucMember?roomJid="+roomJid;
    uri += "&member="+memberJid;
    uri += "&role="+role;
    $.ajax({
        url:uri,
        cache:false,
        method:"PUT",
        dataType:"json",
        async:false,
        //contentType: 'application/json',
        error:function(xmlHttpRequest,error){
            alert(error);
        },
        success:function(result){
            if (result.ret>=0){
                alertPromptMsgDlg("修改成功", 2, afterModify(roomJid));
            }else {
                alert("修改失败，请稍后重试...");
            }
        }
    })
}

function showManage(roomJid) {
    //获取群组信息
    queryRoomMembers(roomJid);

    //显示组织架构树
    showOrgTreeForStaff("treeTrade_create");
    $("#treeTrade_create").hide();


    //显示成员管理窗口
    $("#popMemberMng").modal('show');
}

function readyMemberManage(roomJid) {
    $("#orgDropdownBtn_add").click(function () {
        $("#treeTrade_create").show();
    });
    //当鼠标离开组织树时：1.隐藏组织树；2.在显示框中显示所选用户
    $("#treeTrade_create").mouseleave(function () {
        $(this).hide();
        var nodes = findTreeCheckNodes("treeTrade_create");
        var selectNodeStr = getNodesName(nodes);
        $("#selectedMember").val(selectNodeStr);
    });
    showManage(roomJid);

    //点击操作图标
    $("#memberList i").click(function(){
        var memberJid = $(this.parentNode).attr("data-id");
        //修改成员权限
        if ($(this).attr("class") == "icon-cogs icon-large"){
            var roomName = $("#labelRoomName").html();
            var memberName = $(this.parentNode).attr("name");
            $("#roomNameForModify").html(roomName);
            $("#memberNameForModify").html(memberName);
            $("#saveMemberModifyBtn").click(function(){
                modifyMember(roomJid,memberJid);
                return false;
            })
            $("#popMemberModify").modal('show');
        }
        //删除成员
        if ($(this).attr("class") == "icon-trash icon-large"){
            var memberJid = $(this.parentNode).attr("data-id");
            alertConfirmationMsgDlg("确认删除该成员吗？",deleteMember,roomJid,memberJid);
        }
    })

    //点击保存按钮：1.调用添加成员函数；2.刷新成员列表
    $("#saveMemberBtn").click(function(){
        var checkedId = getNodeId(findTreeCheckNodes("treeTrade_create"));
        if (checkedId && checkedId!=""){
            addMember(roomJid);
        }else {
            reload();
        }
    });
}

