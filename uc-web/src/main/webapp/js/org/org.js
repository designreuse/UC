///**
// /////////////////////////////////////////Common-Module///////////////////////////////////////////////////////////
// **/
///**
// * 异步树设置
// */
//var settingForInit = {
//    data: {
//        simpleData: {
//            enable: true,
//            idKey: "id",
//            pIdKey: "pid",
//            rootPId: "0_org"
//        }
//    },
//    edit: {
//        drag: {
//            autoExpandTrigger: true,
//            prev: dropPrev,
//            inner: dropInner,
//            next: dropNext
//        },
//        enable: true,
//        showRemoveBtn: false,
//        showRenameBtn: false
//    },
//    callback: {
//        onClick: onClickTreeNode,
//        beforeDrag: beforeDrag,
//        onDrop: onDropNode
//    },
//    view: {
//        showIcon: hideIconForRootAndUser
//    }
//};
//function hideIconForRootAndUser(treeId, treeNode) {
//    return (treeNode.pid == "0_org" || treeNode.type == "staff");
//};
///**
// * 定义树节点
// */
//function Node(id, pid, name, isParent, type, pinyin, pinyinAlia) {
//    this.id = id;
//    this.pid = pid;
//    this.pinyin = pinyin;
//    this.pinyinAlia = pinyinAlia;
//    this.fullname = name;
//
//    if (name.length > 25) {
//        this.name = name.substring(0, 25) + "...";
//    } else {
//        this.name = name
//    }
//    this.isParent = isParent;
//    this.type = type;
//
//    if (this.type == "staff") {
//        this.iconSkin = "sIcon";
//    }
//
//    if (this.pid == "0_org") {
//        this.iconSkin = "pIcon";
//    }
//}
//
///**
// * 定义树节点
// */
//function StaffNode(id, pid, name, isParent, type, gender, pinyin, pinyinAlia, mail) {
//    this.id = id;
//    this.pid = pid;
//    this.gender = gender;
//    this.pinyin = pinyin;
//    this.pinyinAlia = pinyinAlia;
//    this.fullname = name;
//    this.mail = mail;
//    this.open = true;
//    this.childOuter = false;
//    this.dropInner = true;
//    this.dropRoot = true;
//    if (name.length > 25) {
//        this.name = name.substring(0, 25) + "...";
//    } else {
//        this.name = name
//    }
//    this.isParent = isParent;
//    this.type = type;
//
//    if (this.type == "staff") {
//        if (this.gender == "0") {
//            this.iconSkin = "sIconWomen";
//        } else {
//            this.iconSkin = "sIconMen";
//        }
//    }
//
//    if (this.pid == "0_org") {
//        this.iconSkin = "pIcon";
//    }
//}
//
//
//function OrgNode(id, pid, name, isParent, type) {
//    this.id = id;
//    this.pid = pid;
//    this.name = name;
//    this.isParent = isParent;
//    this.type = type;
//
//    if (this.type == "user") {
//        this.iconSkin = "sIcon";
//    }
//
//    if (this.pid == "0_org") {
//        this.iconSkin = "pIcon";
//    }
//}
//
///**
// * 显示组织结构树
// */
//function showTreeNodesForInit() {
//    var treeNodes = new Array();
//    $.ajax({
//        url: createUrl("/org/showTreeNodes"),
//        type: "post",
//        async: true,
//        dataType: "json",
//        success: function (data) {
//            if ((!data.success) && data.success == false) {
//                alertPromptMsgDlg(data.message, 2, null);
//                return;
//            }
//            $.each(data, function (i, item) {
//                treeNodes.push(new StaffNode(item.id, item.pid, item.name, item.isParent, item.type, item.gender, item.pinyin, item.pinyinAlia, item.mail
//                ));
//                $.fn.zTree.init($("#tree"), settingForInit, treeNodes);
//            });
//
//            //默认展开第一个节点
//            var treeObj = $.fn.zTree.getZTreeObj("tree");
//            if (null != treeObj) {
//                var treenode = treeObj.getNodesByFilter(function (node) {
//                    return node.level == 0
//                });
//                treeObj.expandNode(treenode[0], true, true, true);
//                treeObj.selectNode(treenode[0]);
//                treeObj.setting.callback.onClick(null, treenode[0].id, treenode[0]);
//            } else {
//                //如果树不存在，弹出创建组织结构窗口
//                $("#btnCreateOrg").click();
//            }
//        }
//    });
//}
//
///**
// * 关键字搜索结点
// * @param even (用于识别按键)
// * @param resultULId（用于存放创建的搜索结果的li）
// * @param resultDivId（搜索结果的外层div）
// * @param searchInputId（搜索输入框）
// * @param width（搜索结果的框宽）
// * @param type（节点类型，用于过滤用户、组织、资源、角色）
// * @param treeId（树放置的ulId）
// * @returns {Boolean}
// */
//function getNodesByFuzzy(e, resultULId, resultDivId, searchInputId, width, type, treeId) {
//    e = e || window.event;
//    //下移
//    if (e.keyCode == 40) {
//        //获取当前高亮行的序列号
//        var index = $("#" + resultULId + " > li.selectLi").index();
//        $("#" + resultULId + " > li.selectLi").removeClass('selectLi');
//        //计算下一个高亮行序列号
//        index = index + 1;
//        if (index > $("#" + resultULId + " > li").length - 1) {
//            index = 0;
//        }
//        $("#" + resultULId + " > li").eq(index).addClass('selectLi');
//        return false;
//    }
//
//    //上移
//    if (e.keyCode == 38) {
//        //获取当前高亮行的序列号
//        var index = $("#" + resultULId + " > li.selectLi").index();
//        $("#" + resultULId + " > li.selectLi").removeClass('selectLi');
//        index = index - 1;
//        if (index < 0) {
//            index = $("#" + resultULId + " > li").length - 1;
//        }
//        $("#" + resultULId + " > li").eq(index).addClass('selectLi');
//        return false;
//    }
//
//    var value = $("#" + searchInputId).val();
//    var ul = $("#" + resultULId);
//    var resultDiv = $("#" + resultDivId)
//    ul.html("");
//    if (value != "") {
//        var append = "";
//        var treeObj = $.fn.zTree.getZTreeObj(treeId);
//        var seaNodes = treeObj.getNodesByFilter(searchFilter, false, null, value);
//        for (j = 0; j < seaNodes.length; j++) {
//
//            if ((type != "" && type == seaNodes[j].type) || type == "") {
//                append += "<li nodeId='" + seaNodes[j].id + "' " +
//                "id='" + j + "' " +
//                "onclick=\"focusNodeByIdAndName('" + seaNodes[j].id + "', '" + treeId + "')\">" + escape(seaNodes[j].name) + "</li>";
//            }
//        }
//        ul.append(append);
//        $("#" + resultULId + " > li:first").addClass('selectLi');
//        resultDiv.show();
//    } else {
//        resultDiv.hide();
//    }
//}
//
///**
// * 节点搜索过滤器
// * @param node
// * @param key
// * @returns {Boolean}
// */
//function searchFilter(node, key) {
//    if (validateNull(key)) {
//        return false;
//    }
//
//    key = $.trim(key).toLocaleLowerCase();
//
//    if (!validateNull(node.fullname)) {
//        if (node.fullname.toLocaleLowerCase().indexOf(key) != -1) {
//            return true;
//        }
//    }
//
//    if (!validateNull(node.pinyin)) {
//        if (node.pinyin.toLocaleLowerCase().indexOf(key) != -1) {
//            return true;
//        }
//    }
//
//    if (!validateNull(node.pinyinAlia)) {
//        if (node.pinyinAlia.toLocaleLowerCase().indexOf(key) != -1) {
//            return true;
//        }
//    }
//    if (!validateNull(node.mail)) {
//        if (node.mail.toLocaleLowerCase().indexOf(key) != -1) {
//            return true;
//        }
//    }
//    return false;
//}
//
//
///**
// * 通过节点id找到指定节点，并调用它的onclick事件
// * @param id
// * @param treeId
// */
//function focusNodeByIdAndName(id, treeId) {
//    var treeObj = $.fn.zTree.getZTreeObj(treeId);
//    var treenode = treeObj.getNodesByParam('id', id);
//
//    treeObj.selectNode(treenode[0]);
//    treeObj.setting.callback.onClick(null, treenode[0].id, treenode[0]);
//}
//
//
///**
// * 点击树节点
// * @param e
// * @param treeId
// * @param treeNode
// */
//function onClickTreeNode(e, treeId, treeNode) {
//    $("#search_org").val("");
//    $("#resultUL").html("");
//    $("#resultDiv").hide();
//
//    //屏蔽点击组织结构节点时，添加部门和用户的按钮根据节点类型启用/不启用
//    if (treeNode.type == "staff") {
//        forwardToEditUser(treeNode.id.replace("_staff", ""));
//        $("#btnResetPassWord").show();
//        $("#btnReleaseOrgMapping").show();
//    } else {
//        forwardToEditOrg(treeNode.id.replace("_org", ""));
//        $("#btnResetPassWord").hide();
//        $("#btnReleaseOrgMapping").hide();
//    }
//}
//
//function refreshOrgIndex() {
//    window.location.href = createUrl("/org/index");
//}
//
//function logout() {
//    window.location.href = createUrl("/logout");
//}
//
//function validName(val) {
//    var re = /^([\u4E00-\u9FA5]|\w)*$/;
//    return re.test(val);
//}
//
//
///**
// * 组织加载前准备工作
// */
//function readyOrgIndex() {
//    setCurrentPageNavigation("headerOrg", "sysLeftNavOrg");
//    //显示树
//    showTreeNodesForInit();
//
//    $("#search_org").keyup(function (e) {
//        getNodesByFuzzy(e, "resultUL", "resultDiv", "search_org", $("#divSearch").width() - 2, "", "tree");
//    });
//
//    $("#search_org").keydown(function (e) {
//        e = e || window.event;
//        //回车
//        if (e.keyCode == 13) {
//            var nodeId = $("#resultUL > li.selectLi").attr('nodeId');
//            var nodeName = $("#resultUL > li.selectLi").text();
//
//            focusNodeByIdAndName(nodeId, "tree");
//            return false;
//        }
//    });
//
//}
///**
// ///////////////////////////////////////// Create-Module ///////////////////////////////////////////////////////////
// **/
//
//var settingOrgCreate = {
//    data: {
//        simpleData: {
//            enable: true,
//            idKey: "id",
//            pIdKey: "pid",
//            rootPId: "0_org"
//        }
//    },
//    callback: {
//        onClick: onClickOrgCreate,
//        onNodeCreated: zTreeOnNodeCreated
//    },
//    view: {
//        showIcon: hideIconForRootAndUser
//    }
//};
//
//function onClickOrgCreate(e, treeId, treeNode) {
//    $().dropdown('toggle');
//    var zTree = $.fn.zTree.getZTreeObj(treeId), nodes = zTree
//        .getSelectedNodes(), orgName = "";
//    var orgId = "";
//    nodes.sort(function compare(a, b) {
//        return a.id - b.id;
//    });
//    for (var i = 0; i < nodes.length; i++) {
//        orgId += nodes[i].id + ",";
//        orgName += nodes[i].name + ",";
//    }
//    if (orgId.length > 0)
//        orgId = orgId.substring(0, orgId.length - 1);
//    if (orgName.length > 0)
//        orgName = orgName.substring(0, orgName.length - 1);
//    $("#orgId_orgCreateForm").val(orgId);
//    $("#orgName_orgAddForm").val(orgName);
//    $('#treeTradeOrgCreate').toggle();
//}
//
///**
// * 显示单选树
// */
//function showTreeNodes(divTreeId, setting) {
//    $(divTreeId).toggle();
//    var treeNodes = new Array();
//    $.ajax({
//        url: createUrl("/org/showTreeNodes"),
//        type: "post",
//        async: false,
//        dataType: "json",
//        success: function (data) {
//            $.each(data, function (i, item) {
//                var name = item.name;
//                if (name.length > 25) {
//                    name = name.substring(0, 25) + "...";
//                }
//                var node = new OrgNode(item.id, item.pid, name, item.isParent, item.type);
//                if (item.type == 'org') {
//                    treeNodes.push(node);
//                }
//            });
//            $.fn.zTree.init(divTreeId, setting, treeNodes);
//        }
//    });
//};
//
//
//function zTreeOnNodeCreated(event, treeId, treeNode) {
//    var treeObj = $.fn.zTree.getZTreeObj(treeId);
//    if (treeNode.level == 0) {
//        treeObj.expandNode(treeNode, true, true, true);
//    }
//    if (!treeNode.isParent) {
//        treeNode.iconSkin = "lIcon";
//        treeObj.updateNode(treeNode);
//    }
//};
//
//function showOrgTreeForOrgCreate() {
//    showTreeNodes($("#treeTradeOrgCreate"), settingOrgCreate);
//}
//function forwardToCreateOrg() {
//    var treeObj = $.fn.zTree.getZTreeObj("tree");
//    var nodes = null;
//    if (null != treeObj) {
//        nodes = treeObj.getSelectedNodes();
//        if (nodes.length == 0) {
//            alertPromptMsgDlg("请选择组织树上节点", 2, null);
//            $('#modalPopup').modal('hide');
//            return;
//        }
//    }
//    var orgId = 0;
//    if (nodes != null) {
//        if (nodes.length != 0) {
//            if (nodes[0].type != "org")
//                orgId = nodes[0].pid.replace("_org", "");
//            else
//                orgId = nodes[0].id.replace("_org", "");
//        }
//    }
//
//    var url = createUrl("/org/forwardToCreate/" + orgId);
//    //获取添加org的页面内容
//    $.get(url, function (data) {
//        $('#modelContent').html(data);
//    });
//}
//
//function readyCreateOrg() {
//    $("#orgName_create").blur(function () {
//        var value = $("#orgName_create").val().trim();
//        if (validateNull(value)) {
//            renderInputField(false, "orgName_create", "部门名称不能为空");
//        } else if (!validName(value)) {
//            renderInputField(false, "orgName_create", "部门名称不合法");
//        } else {
//            renderInputField(true, "orgName_create", "");
//        }
//    });
//
//    $("#index_create").blur(function () {
//        var value = $("#index_create").val();
//        if (!validateNull(value)) {
//            if (!isNumber(value)) {
//                renderInputField(false, "index_create", "显示顺序只能为数字！");
//            } else {
//                renderInputField(true, "index_create", "");
//            }
//        } else {
//            restoreInputField("index_create");
//        }
//    });
//}
//
//function createOrg() {
//    var treeObj = $.fn.zTree.getZTreeObj("tree");
//    var parentOrgId = $("#orgId_orgCreateForm").val();
//
//    if (null != treeObj) {
//        var nodes = treeObj.getSelectedNodes();
//        if (nodes.length == 0) {
//            alertPromptMsgDlg("请选择组织树上节点", 2, null);
//            $('#modalPopup').modal('hide');
//            return;
//        }
//    }
//
//    var orgName = $("#orgName_create").val();
//    var index = $("#index_create").val();
//    var mail = $("#mail_create").val();
//    var isExtAssistance = $("#isExtAssistance_create").prop("checked");
//    if (validateNull(orgName)) {
//        $("#orgName_create").focus();
//        renderInputField(false, "OrgName_create", "部门名称不能为空");
//    } else if (!validName(orgName)) {
//        $("#orgName_create").focus();
//        renderInputField(false, "OrgName_create", "部门名称不合法");
//    } else if (!validateNull(orgName) && $.trim(orgName).length > 64) {
//        $("#orgName_create").focus();
//        renderInputField(false, "OrgName_create", "部门名称长度不能超过64位");
//    } else if (!validateNull(index) && !isNumber(index)) {
//        $("#index_create").focus();
//        renderInputField(false, "Index_create", "显示顺序只能为数字！");
//    } else if (!validateNull(mail) && !isMail(mail)) {
//        $("#mail_create").focus();
//        renderInputField(false, "Mail_create", "邮箱格式不正确！");
//    } else {
//        var params = {
//            "name": orgName,
//            "parentId": parentOrgId.replace("_org", ""),
//            "index": index,
//            "mail": mail,
//            "isExtAssistance": isExtAssistance
//        };
//        $.ajax({
//            url: createUrl("/org/create"),
//            type: "post",
//            async: false,
//            dataType: "json",
//            data: params,
//            success: function (msg) {
//                if (msg.success) {
//                    $('#modalPopup').modal('hide');
//                    alertPromptMsgDlg(msg.message, 2, refreshOrgIndex);
//                } else {
//                    alertPromptMsgDlg(msg.message, 2);
//                }
//            }
//        });
//    }
//}
//
///**
// /////////////////////////////////////////Edit-Module///////////////////////////////////////////////////////////
// * */
//
//function forwardToEditOrg(orgId) {
//    var url = createUrl("/org/forwardToEdit/" + orgId);
//    //获取添加org的页面内容
//    $.get(url, function (data) {
//        $('#divEditContent').html(data);
//    });
//}
//function readyEditOrg() {
//    $("#orgName_edit").blur(function () {
//        var value = $("#orgName_edit").val().trim();
//        if (validateNull(value)) {
//            renderInputField(false, "orgName_edit", "部门名称不能为空");
//        } else {
//            renderInputField(true, "orgName_edit", "");
//        }
//    });
//
//    $("#index_edit").blur(function () {
//        var value = $("#index_edit").val();
//        if (!validateNull(value)) {
//            if (!isNumber(value)) {
//                renderInputField(false, "index_edit", "显示顺序只能为数字！");
//            } else {
//                renderInputField(true, "index_edit", "");
//            }
//        } else {
//            restoreInputField("index_edit");
//        }
//    });
//}
//
//function editOrg() {
//    var addOrgStaffTitleItems = new Array();
//    $("tr[name='titleRow']").each(function (index) {
//        if ($(this).parent().attr('id') != 'addTitleTbody') {
//            var title = $(this).find("input[name=title]").val();
//            var staffId = $(this).find("select[name=staff]").val();
//            addOrgStaffTitleItems.push({"title": title, "staffId": new Number(staffId)});
//        }
//    });
//
//    var orgName = $("#orgName_edit").val();
//    var index = $("#index_edit").val();
//    var mail = $("#mail_edit").val();
//
//    if (validateNull(orgName)) {
//        $("#orgName_edit").focus();
//        renderInputField(false, "orgName_edit", "部门名称不能为空");
//    } else if (!validateNull(orgName) && $.trim(orgName).length > 64) {
//        $("#orgName_edit").focus();
//        renderInputField(false, "orgName_edit", "部门名称长度不能超过64位");
//    } else if (!validateNull(index) && !isNumber(index)) {
//        $("#index_edit").focus();
//        renderInputField(false, "index_edit", "显示顺序只能为数字！");
//    } else if (!validateNull(mail) && !isMail(mail)) {
//        $("#mail_edit").focus();
//        renderInputField(false, "mail_edit", "邮箱格式不正确！");
//    } else {
//        var params = {
//            orgId: $("#orgId_edit").val(),
//            name: orgName,
//            index: index,
//            mail: mail,
//            isExtAssistance: $("#isExtAssistance_edit").prop("checked"),
//            editOrgRequestItemList: addOrgStaffTitleItems
//        };
//        $.ajax({
//            url: createUrl("/org/edit"),
//            type: "post",
//            async: false,
//            dataType: "json",
//            data: JSON.stringify(params),
//            contentType: "application/json; charset=utf-8",
//            success: function (msg) {
//                if (msg.success) {
//                    alertPromptMsgDlg(msg.message, 1, refreshOrgIndex);
//                } else {
//                    alertPromptMsgDlg(msg.message, 2);
//                }
//            }
//        });
//    }
//}
//
//function titleAdd() {
//    var $objectAfter = $("#orgTitleList");
//    $($("#addTitleTbody").html()).appendTo($objectAfter);
//}
//
//function titleDelete(valueDOM) {
//    $(valueDOM).parent().parent().parent().remove();
//}
//
///***
// /////////////////////////////////////////Delete-Module///////////////////////////////////////////////////////////
// * */
//
//function forwardToDelete() {
//    $("#popUserOrOrgDelete").modal({backdrop: 'static', keyboard: false, remote: createUrl("/org/forwardToDelete")});
//    $('#popUserOrOrgMove .modal-dialog').css('width', '560px');
//    $('#popUserOrOrgMove .modal-dialog').css('height', '600px');
//}
//
//
//function readyDelete() {
//    $("#search_org_delete").keyup(function (even) {
//        getNodesByFuzzy(even, "resultUL_delete", "resultDiv_delete", "search_org_delete", $("#divSearch_delete").width() - 3, "org", "treeOrgForDelete");
//    });
//
//    $("#search_org_delete").keydown(function (e) {
//        e = e || window.event;
//        //回车
//        if (e.keyCode == 13) {
//            var nodeId = $("#resultUL_delete > li.selectLi").attr('nodeId');
//            var nodeName = $("#resultUL_delete > li.selectLi").text();
//
//            focusNodeByIdAndName(nodeId, "treeOrgForDelete");
//            return false;
//        }
//    });
//
//    var treeNodes = new Array();
//    var deleteSetting = {
//        check: {
//            enable: true
//        },
//        data: {
//            simpleData: {
//                enable: true,
//                idKey: "id",
//                pIdKey: "pid",
//                rootPId: "0_org"
//            }
//        },
//        callback: {
//            onCheck: onCheckDelete,
//            onClick: onClickTreeNodeForDelete
//        },
//        view: {
//            showIcon: hideIconForMove
//        }
//    };
//    $.ajax({
//        url: createUrl("/org/showTreeNodes"),
//        type: "post",
//        async: true,
//        dataType: "json",
//        success: function (data) {
//            $.each(data, function (i, item) {
//                if (item.type == 'org') {
//                    treeNodes.push(new Node(item.id, item.pid, item.name, item.isParent, item.type, item.pinyin, item.pinyinAlia));
//                }
//            });
//            $.fn.zTree.init($("#treeOrgForDelete"), deleteSetting, treeNodes).setting.check.chkboxType = {"Y": "", "N": ""};
//            //默认展开第一个节点
//            var treeObj = $.fn.zTree.getZTreeObj("treeOrgForDelete");
//            if (null != treeObj) {
//                var treenode = treeObj.getNodesByFilter(function (node) {
//                    return node.level == 0
//                });
//                treeObj.expandNode(treenode[0], true, true, true);
//            }
//        }
//    });
//
//    //模态窗口隐藏
//    $('#popUserOrOrgDelete').on('hide.bs.modal', function () {
//        $(this).removeData("bs.modal");
//    });
//}
//
//function onClickTreeNodeForDelete(event, treeId, treeNode) {
//    $("#search_org_delete").val("");
//    $("#resultDiv_delete").hide();
//    $("#resultUL_delete").html("");
//}
//
//function onCheckDelete(e, treeId, treeNode) {
//    if (treeNode.checked) {
//        deleteIdMap.put(treeNode.id, treeNode.id.replace("_org", ""));
//    } else {
//        deleteIdMap.remove(treeNode.id);
//    }
//}
//
//function deleteOrg() {
//    var treeObj = $.fn.zTree.getZTreeObj("treeOrgForDelete");
//    var checkNodes = treeObj.getCheckedNodes(true);
//    var orgIds = new Array();
//    $.each(checkNodes, function (i, node) {
//        if (node) {
//            orgIds.push(new Number(node.id.replace("_org", "")));
//        }
//    });
//    $.ajax({
//        url: createUrl("/org/delete"),
//        type: "post",
//        data: {orgIds: orgIds},
//        traditional: true,
//        dataType: "json",
//        success: function (msg) {
//            if (msg.success) {
//                alertPromptMsgDlg(msg.message, 1, refreshOrgIndex);
//            } else {
//                alertPromptMsgDlg(msg.message, 2, null);
//            }
//        }
//    });
//}
//
///***
// * ///////////////////////////////////// Move-Module //////////////////////////////////////////////////////////////
// * */
//
//function readyMove() {
//    $("#search_org_move").keyup(function (even) {
//        getNodesByFuzzy(even, "resultUL_move", "resultDiv_move", "search_org_move", $("#divSearch_move").width() - 3, "org", "treeOrgForMove");
//    });
//
//    $("#search_org_move").keydown(function (e) {
//        e = e || window.event;
//        //回车
//        if (e.keyCode == 13) {
//            var nodeId = $("#resultUL_move > li.selectLi").attr('nodeId');
//            var nodeName = $("#resultUL_move > li.selectLi").text();
//
//            focusNodeByIdAndName(nodeId, "treeOrgForMove");
//            return false;
//        }
//    });
//
//    var params = {
//        "moveOrgId": $("#move_org_id").val()
//    };
//
//    var treeNodes = new Array();
//    var moveSetting = {
//        data: {
//            simpleData: {
//                enable: true,
//                idKey: "id",
//                pIdKey: "pid",
//                rootPId: "0_org"
//            }
//        },
//        callback: {
//            onClick: onClickTreeNodeForMove
//        },
//        view: {
//            showIcon: hideIconForMove
//        }
//    };
//    $.ajax({
//        url: createUrl("/org/showTreeNodesForMove"),
//        type: "post",
//        async: true,
//        data: params,
//        dataType: "json",
//        success: function (data) {
//            $.each(data, function (i, item) {
//                treeNodes.push(new Node(item.id, item.pid, item.name, item.isParent, item.type, item.pinyin, item.pinyinAlia));
//            });
//            $.fn.zTree.init($("#treeOrgForMove"), moveSetting, treeNodes);
//
//            //默认展开第一个节点
//            var treeObj = $.fn.zTree.getZTreeObj("treeOrgForMove");
//            if (null != treeObj) {
//                var treenode = treeObj.getNodesByFilter(function (node) {
//                    return node.level == 0
//                });
//                treeObj.expandNode(treenode[0], true, true, true);
//            }
//        }
//    });
//    //模态窗口隐藏
//    $('#popUserOrOrgMove').on('hide.bs.modal', function () {
//        $(this).removeData("bs.modal");
//    });
//}
//
//function onClickTreeNodeForMove(event, treeId, treeNode) {
//    $("#move_target").attr("value", treeNode.id.replace("_org", ""));
//    $("#search_org_move").val("");
//    $("#resultDiv_move").hide();
//    $("#resultUL_move").html("");
//}
//function forwardToMove() {
//    var treeObj = $.fn.zTree.getZTreeObj("tree");
//    var nodes = treeObj.getSelectedNodes();
//    var treeNode = nodes[0];
//
//    if (treeNode.pid == "0_org") {
//        alertPromptMsgDlg("根节点部门不允许移动！", 2);
//        return;
//    }
//
//    if (treeNode.type == "staff") {
//        forwardToMoveStaff(treeNode.id.replace("_staff", ""));
//    } else {
//        forwardToMoveOrg(treeNode.id.replace("_org", ""));
//    }
//}
//function forwardToMoveOrg(orgId) {
//    $("#popUserOrOrgMove").modal({backdrop: 'static', keyboard: false, remote: createUrl("/org/forwardToMove/" + orgId)});
//    $('#popUserOrOrgMove .modal-dialog').css('width', '560px');
//    $('#popUserOrOrgMove .modal-dialog').css('height', '600px');
//}
//
//function hideIconForMove(treeId, treeNode) {
//    return treeNode.pid == "0_org";
//};
//
//function submitMove() {
//    var treeObj = $.fn.zTree.getZTreeObj("treeOrgForMove");
//    var nodes = treeObj.getSelectedNodes();
//    var treeNode = nodes[0];
//
//    if (treeNode == undefined) {
//        alertPromptMsgDlg("请选择要移动到的部门！", 2, null);
//        return;
//    }
//
//    if ($("#move_type").val() == "org") {
//        submitMoveOrg(treeNode.id.replace("_org", ""));
//    }
//}
//
//function submitMoveOrg(targetOrgId) {
//    var orgId = $("#move_org_id").val();
//    var params = {
//        "orgId": orgId,
//        "targetOrgId": targetOrgId
//    };
//
//    $.ajax({
//        url: createUrl("/org/move"),
//        type: "post",
//        data: params,
//        dataType: "json",
//        success: function (msg) {
//            if (msg.success) {
//                alertPromptMsgDlg(msg.message, 1, refreshOrgIndex);
//            } else {
//                alertFailMsgDlgForMove(msg.message);
//            }
//        }
//    });
//}
//function alertFailMsgDlgForMove(message) {
//    $('#promptModalBody_MoveOrg').html("<i id='icon-class' class=' icon-large'/>" + message);
//    $('#promptModalBody_MoveOrg').removeClass("alert").removeClass("alert-success").removeClass("alert-warning")
//        .removeClass("alert-danger").removeClass("alert-info");
//    $('#icon-class').addClass("icon-exclamation-sign custom-warn");
//    $('#promptModal_MoveOrg').on(
//        'shown.bs.modal',
//        function () {
//            $('#promptModalContinueBtn_MoveOrg').on(
//                'click', function () {
//                    $('#closePromtBtn').trigger('click');
//                });
//            $('#promptModalCancelBtn_MoveOrg').on(
//                'click', function () {
//                    $('#popUserOrOrgMove').find('.close').trigger('click');
//                });
//        }).modal('show');
//}
///**
// /////////////////////////////////////////ReIndex-Module///////////////////////////////////////////////////////////
// * */
//function dropPrev(treeId, nodes, targetNode) {
//    var pNode = targetNode.getParentNode();
//    if (pNode && pNode.dropInner === false) {
//        return false;
//    } else {
//        for (var i = 0, l = curDragNodes.length; i < l; i++) {
//            var curPNode = curDragNodes[i].getParentNode();
//            if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
//                return false;
//            }
//        }
//    }
//    return true;
//}
//function dropInner(treeId, nodes, targetNode) {
//    if (targetNode && targetNode.dropInner === false) {
//        return false;
//    } else {
//        for (var i = 0, l = curDragNodes.length; i < l; i++) {
//            if (!targetNode && curDragNodes[i].dropRoot === false) {
//                return false;
//            } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
//                return false;
//            }
//        }
//    }
//    return true;
//}
//function dropNext(treeId, nodes, targetNode) {
//    var pNode = targetNode.getParentNode();
//    if (pNode && pNode.dropInner === false) {
//        return false;
//    } else {
//        for (var i = 0, l = curDragNodes.length; i < l; i++) {
//            var curPNode = curDragNodes[i].getParentNode();
//            if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
//                return false;
//            }
//        }
//    }
//    return true;
//}
//
//var curDragNodes;
//function beforeDrag(treeId, treeNodes) {
//    for (var i = 0, l = treeNodes.length; i < l; i++) {
//        if (treeNodes[i].drag === false) {
//            curDragNodes = null;
//            return false;
//        } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
//            curDragNodes = null;
//            return false;
//        }
//    }
//    curDragNodes = treeNodes;
//    return true;
//}
//
//function onDropNode(event, treeId, treeNodes, targetNode, moveType, isCopy) {
//    if (treeNodes[0].type == 'staff') {
//        onDropStaffNode(event, treeId, treeNodes, targetNode, moveType, isCopy);
//    }
//    if (treeNodes[0].type == 'org') {
//        reIndexOrg(event, treeId, treeNodes, targetNode, moveType, isCopy);
//    }
//}
//
//function reIndexOrg(event, treeId, treeNodes, targetNode, moveType, isCopy) {
//    if (!targetNode)return;
//    var pid = treeNodes[0].pid;
//
//    function filter(node) {
//        return (node.type == 'org' && node.pid == pid);
//    }
//
//    var treeObj = $.fn.zTree.getZTreeObj("tree");
//    var subOrgNodes = treeObj.getNodesByFilter(filter); // 查找节点集合
//    var reIndexOrgRequestItemList = new Array();
//    $.each(subOrgNodes, function (i, node) {
//        if (node) {
//            reIndexOrgRequestItemList.push({orgId: new Number(node.id.replace("_org", "")), index: i + 1});
//        }
//    });
//    $.ajax({
//        url: createUrl("/org/reIndex"),
//        type: "post",
//        async: true,
//        dataType: "json",
//        traditional: true,
//        data: JSON.stringify({parentOrgId: pid.replace("_org", ""), reIndexOrgRequestItemList: reIndexOrgRequestItemList}),
//        contentType: "application/json; charset=utf-8",
//        success: function (data) {
//            if (data.success) {
//            } else {
//                alertPromptMsgDlg(data.message, 2);
//            }
//        }
//    });
//}
