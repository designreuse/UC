/**
 * 关键字搜索结点
 * @param even (用于识别按键)
 * @param resultULId（用于存放创建的搜索结果的li）
 * @param resultDivId（搜索结果的外层div）
 * @param searchInputId（搜索输入框）
 * @param width（搜索结果的框宽）
 * @param type（节点类型，用于过滤用户、菜单、资源、角色）
 * @param treeId（树放置的ulId）
 * @returns {Boolean}
 */
function getNodesByFuzzy(e, resultULId, resultDivId, searchInputId, width, type, treeId, filter) {
    e = e || window.event;
    //下移
    if (e.keyCode == 40) {
        //获取当前高亮行的序列号
        var index = $("#" + resultULId + " > li.selectLi").index();
        $("#" + resultULId + " > li.selectLi").removeClass('selectLi');
        //计算下一个高亮行序列号
        index = index + 1;
        if (index > $("#" + resultULId + " > li").length - 1) {
            index = 0;
        }
        $("#" + resultULId + " > li").eq(index).addClass('selectLi');
        return false;
    }

    //上移
    if (e.keyCode == 38) {
        //获取当前高亮行的序列号
        var index = $("#" + resultULId + " > li.selectLi").index();
        $("#" + resultULId + " > li.selectLi").removeClass('selectLi');
        index = index - 1;
        if (index < 0) {
            index = $("#" + resultULId + " > li").length - 1;
        }
        $("#" + resultULId + " > li").eq(index).addClass('selectLi');
        return false;
    }

    var value = $("#" + searchInputId).val();
    var ul = $("#" + resultULId);
    var resultDiv = $("#" + resultDivId);
    ul.html("");
    if (value != "") {
        var append = "";
        var treeObj = $.fn.zTree.getZTreeObj(treeId);
        var seaNodes = treeObj.getNodesByFilter(filter, false, null, value);
        for (j = 0; j < seaNodes.length; j++) {

            if ((type != "" && type == seaNodes[j].type) || type == "") {
                append += "<li nodeId='" + seaNodes[j].id + "' " +
                "id='" + j + "' " +
                "onclick=\"searchStaffSession('"+ seaNodes[j].id +"');\">" + escape(seaNodes[j].name) + "</li>";
            }
        }
        ul.append(append);
        $("#" + resultULId + " > li:first").addClass('selectLi');
        resultDiv.show();
    } else {
        resultDiv.hide();
    }
}

/**
 * 节点搜索过滤器
 * @param node
 * @param key
 * @returns {Boolean}
 */
function searchFilter(node, key) {
    if (validateNull(key)) {
        return false;
    }
    key = $.trim(key).toLocaleLowerCase();

    if (!validateNull(node.fullname)) {
        if (node.fullname.toLocaleLowerCase().indexOf(key) != -1) {
            return true;
        }
    }

    if (!validateNull(node.pinyin)) {
        if (node.pinyin.toLocaleLowerCase().indexOf(key) != -1) {
            return true;
        }
    }

    if (!validateNull(node.pinyinAlia)) {
        if (node.pinyinAlia.toLocaleLowerCase().indexOf(key) != -1) {
            return true;
        }
    }
    return false;
}

/**
 * 节点搜索过滤器
 * @param node
 * @param key
 * @returns {Boolean}
 */
function searchFilterWithMail(node, key) {
    if (validateNull(key)) {
        return false;
    }

    key = $.trim(key).toLocaleLowerCase();

    if (!validateNull(node.fullname)) {
        if (node.fullname.toLocaleLowerCase().indexOf(key) != -1) {
            return true;
        }
    }

    if (!validateNull(node.pinyin)) {
        if (node.pinyin.toLocaleLowerCase().indexOf(key) != -1) {
            return true;
        }
    }

    if (!validateNull(node.pinyinAlia)) {
        if (node.pinyinAlia.toLocaleLowerCase().indexOf(key) != -1) {
            return true;
        }
    }
    if (!validateNull(node.mail)) {
        if (node.mail.toLocaleLowerCase().indexOf(key) != -1) {
            return true;
        }
    }
    return false;
}


/**
 * 通过节点id找到指定节点，并调用它的onclick事件
 * @param id
 * @param treeId
 */
function focusNodeByIdAndName(id, treeId) {
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    var treenode = treeObj.getNodesByParam('id', id);

    treeObj.selectNode(treenode[0]);
    treeObj.setting.callback.onClick(null, treenode[0].id, treenode[0]);
}

