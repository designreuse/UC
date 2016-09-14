/**
 * 异步树设置
 */
var settingForInit = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pid",
            rootPId: "0_org"
        }
    }
};

/**
 * 定义树节点
 */
function NodeWithUser(id, pid, name, isParent, type, gender, pinyin, pinyinAlia, mail, originalId, originalPid) {
    this.id = id;
    this.pid = pid;
    this.originalId = originalId;
    this.originalPid = originalPid;
    this.pidNotChangedWhenDrag = pid;
    this.gender = gender;
    this.pinyin = pinyin;
    this.pinyinAlia = pinyinAlia;
    this.fullname = name;
    this.mail = mail;
    this.open = true;
    this.childOuter = false;
    this.dropInner = true;
    this.dropRoot = true;
    if (name.length != null && name.length > 13) {
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
 * 加载树
 */
function showOrgStaffTreeNodesForSearch() {
    var treeNodes = new Array();
    $.ajax({
        url: $("#projectContext").val()+"/warning/showOrgStaffTreeNodes",
        type: "get",
        async: false,
        dataType: "json",
        success: function (response) {
            if ((!response.success) && response.success == false) {
                return;
            }
            $.each(response.data, function (i, item) {
                treeNodes.push(new NodeWithUser(item.id + "_" + item.type, item.pid + "_" + item.parentType,
                    item.name, item.isParent,
                    item.type, item.gender, item.pinyin, item.pinyinAlia, item.mail,
                    item.id, item.pid
                ));
                $.fn.zTree.init($("#staffOrgTreeForSearch"), settingForInit, treeNodes);
            });
        }
    });
}
var currentPageNo = 1;
//初始化
function initSessionIndex(){
    //选定导航栏
    setCurrentPageNavigation("sessionFirstNav","sessionSidebar","");
    //初始化状态选择栏
    $('#statusSelect').selectpicker({
        'selectedText': 'cat',
        'actionsBox':false,
        'width':'200px'
    });
    //默认全部选择
    $('#statusSelect').selectpicker('selectAll');
    //加载组织结构树
    showOrgStaffTreeNodesForSearch();
    //给搜索框添加按键事件
    $("#orgStaffRightContentDiv").on('keyup', "#search_org", function (e) {
        e = e || window.event;
        //不是回车，模糊匹配
        if (e.keyCode != 13) {
            getNodesByFuzzy(e, "resultUL", "resultDiv", "search_org", $("#divSearch").width() - 2, "", "staffOrgTreeForSearch", searchFilterWithMail);
        }
    });

    $("#orgStaffRightContentDiv").on('keydown', "#search_org",function (e) {
        e = e || window.event;
        //回车
        if (e.keyCode == 13) {
            var nodeId = $("#resultUL > li.selectLi").attr('nodeId');
            var nodeName = $("#resultUL > li.selectLi").text();
            if(nodeId==undefined){
                searchStaffSession( $("#search_org").val());
            }else {
                $("#search_org").val(nodeName);
                searchStaffSession(nodeId);
            }
            return false;
        }
    });
    //状态选择后触发查询事件
    $('#statusSelect').on('changed.bs.select', function (e) {
        goPage(1);
    });
    //默认加载第一页
    goPage(1);
    //详情事件
    $("#sessionListBody").on('click',"label[id^='detail_']",function(){
        console.log("into detail");
        var detailId = $(this).attr("id");
        var jid=detailId.substring(detailId.indexOf("_") + 1);
        var url=$("#projectContext").val()+"/session/detail";
        url = setUrlParam(url, "jid", jid);
        var preUrl = window.location.href;
        url = setUrlParam(url, "preUrl", URLencode(preUrl));
        window.location.href=url;
    });
    //关闭事件
    $("#sessionListBody ").on("click","label[id^='close_']",function(){
        var detailId = $(this).attr("id");
        var jid=detailId.substring(detailId.indexOf("_") + 1);
        var url=$("#projectContext").val()+"/session/close_session";
        url = setUrlParam(url, "jid", jid);
        closeSession(url);
    });

    //监听分页每页页码变化事件
    $("#auditDiv").on('change',"select[id='pageSizeSelect']",function(){
        var pageSize = $("#pageSizeSelect").val();
        if(pageSize*currentPageNo >$("#total").val()){
            goPage(1);
        }else {
            goPage(currentPageNo);
        }
    });

    //监听分页第几页变化事件
    $("#auditDiv").on('change',"select[id='pageNoSelect']",function(){
        goPage($("#pageNoSelect").val());
    });

//批量删除操作
    $("#batchDeleteLable").on('click',function(){
        var url=$("#projectContext").val()+"/session/close_session";
        var message=Lang("system_common_confirm_revoke_records");
        setConfirmationButtonColor(0);
        batchOperation(url,message);
    });
//全选按钮没勾选时隐藏批量操作栏
    $("#checkboxAll").click(function() {
        selectAllListCheckBox();
        var countChecked = getSelectRecordsCount();
        if(countChecked==0){
            $("#divTitleBarHidden").removeAttr("hidden");
            $("#divTitleBarHidden").attr("hidden","hidden");
        }else {
            $("#divTitleBarHidden").removeAttr("hidden");
        }
    });

//监听每一个单选框事件，选中后，批量操作栏显示，没有选中后批量操作栏隐藏
    $("#sessionListBody").on("change","input[name='rowCheckbox']",function(){
        var arrRecIds = document.getElementsByName("rowCheckbox");
        var countChecked = getSelectRecordsCount();
        if (countChecked == arrRecIds.length){
            document.getElementById("checkboxAll").checked = true;
        } else {
            document.getElementById("checkboxAll").checked = false;
        }
        if(countChecked==0){
            $("#divTitleBarHidden").removeAttr("hidden");
            $("#divTitleBarHidden").attr("hidden","hidden");
        }else {
            $("#divTitleBarHidden").removeAttr("hidden");
        }
    });
}
/**
 * 获取选中项的个数.
 * @returns
 */
function getSelectRecordsCount(){
    var selectedItemIds = new Array();
    // 把选中行的checkBox值放入到selectedItemIds里
    $("input[name='rowCheckbox']:checked").each(function() {
        selectedItemIds.push($(this).val());
    });
    return selectedItemIds.length;
}
/**
 * 查询部门下用户的id,并通过id获取用户的session
 * @param nodeId
 */
function searchStaffSession(nodeId) {
    if(nodeId==""){
        goPage(1);
        return;
    }else if(nodeId.indexOf("_")==-1){
        setEmptemyResult();
        return;
    }
    var staffIds="";
    var treeObj = $.fn.zTree.getZTreeObj("staffOrgTreeForSearch");
    var id = nodeId.substring(0, nodeId.indexOf("_"));
    if (nodeId.indexOf("_staff") != -1)
    {
        staffIds = id;
    } else {
        var node = treeObj.getNodesByParam("id", nodeId, null);
        if(node.length==0){
            setEmptemyResult();
        }
        var nodes = node[0].children;
        for (var i = 0, l = nodes.length; i < l; i++) {
            var temp = nodes[i].id.substring(0, nodes[i].id.indexOf("_"));
            if (nodes[i].isParent == false) {
                staffIds = staffIds + temp + ",";
            }
        }
        staffIds = staffIds.substring(0,staffIds.length-1);
    }
    if(staffIds!=""){
        $("#staffIdsHidden").val(staffIds);
        goPage(1);
    }else{
        setEmptemyResult();
    }
    $("#resultDiv").hide();
}

function closeSession(url){
    showProgress();
    var id=url.substring(url.indexOf("?")+1,url.length);
    url = url.substring(0,url.indexOf("?"));
    var ids={
        ids:id
    }
    $.ajax({
        url: url,
        cache: false,
        dataType: "json",
        data:ids,
        type: "post",
        async: false,
        error: function (xmlHttpRequest, error) {
            hideProgressBar();
        },
        success: function (result) {
            if (result.ret >= 0) {
                hideProgressBar();
                alertPromptMsgDlg(result.msg,1,reload);
            }else {
                hideProgressBar();
                alertPromptMsgDlg(result.msg,3);
            }
        }
    });
}

function getPageNoArray(pageNo,totalPage){
    var b=pageNo-2;
    if(b<1) b=1;
    if(b+5>totalPage){
        b=totalPage-5;
        if(b<1) b=1;
    }
    var pages=new Array();
    for(var i=b;i<(b+5)&&i<=totalPage;i++){
        pages.push(i);
    }
    return pages;
}

function goPage(pageNum){
    var pageSize = $("#pageSizeSelect").val();
    if(pageSize==undefined){
        pageSize=10;
    }
    var status="";
    var value=$("#statusSelect").val();
    for(index in value){
        status+=value[index]+",";
    }
    status = status.substring(0,status.length-1);
    var url = $("#projectContext").val()+"/session/get_list";
    var param={
        pageNo:pageNum,
        pageSize:pageSize,
        staffIds:$("#staffIdsHidden").val(),
        status:status
    };
    $.ajax({
        url: url,
        cache: false,
        data:param,
        dataType: "json",
        type: "get",
        async: false,
        error: function (xmlHttpRequest, error) {

        },
        success: function (result) {
            if (result.ret >= 0 && result.data) {
                var pageModelRet = executePageModel(result);
                result=getOrgNameList(result);
                var html = template('sessionTemp', result);
                document.getElementById('sessionListBody').innerHTML=html;
                document.getElementById('sessionDiv').innerHTML=template('sessionPageModelTemp', pageModelRet);
                currentPageNo = result.attrs.pageNo;
                $("#pageSizeSelect").val(result.attrs.pageSize);
                $("#sessionCount").text(result.attrs.total);
                $("#total").val(result.attrs.total);
            }
            //清除上一次查询的条件
            $("#staffIdsHidden").val("");
        }
    });
}
/**
 * 拼接父部门名称
 * @param result
 * @returns {*}
 */
function getOrgNameList(result){
    var treeObj = $.fn.zTree.getZTreeObj("staffOrgTreeForSearch");
    var newResult = result;
    for(index in result.data){
        var temp=result.data[index].staffId;
        var node = treeObj.getNodesByParam("id", temp+"_staff", null);
        var newData = newResult.data[index];
        newData.orgName = node[0].getParentNode().name;
        newData.created =new Date(result.data[index].created).Format("yyyy-MM-dd hh:mm:ss");
        for(var i=1;i<node.length;i++){
            newData.orgName =newData.orgName+";"+ node[i].getParentNode().name;
        }
    }
    return newResult;
}

function inputPageNumEvent(event,totalPage) {
    var keycode = event.keyCode ? event.keyCode : event.which;
    if (keycode == 13) {
        var pageNum = $.trim($("#pageNumInput").val());
        if(pageNum>totalPage){
            pageNum=totalPage;
        }
        goPage(pageNum);
    }
}

/**
 *构造空的列表
 **/

function setEmptemyResult(){
    var result={data:[],attrs:{pageSize:10,pageNo:1,total:0}};
    var pageModelRet = executePageModel(result);
    var html = template('sessionTemp', result);
    document.getElementById('sessionListBody').innerHTML=html;
    document.getElementById('sessionDiv').innerHTML=template('sessionPageModelTemp', pageModelRet);
    currentPageNo = result.attrs.pageNo;
    $("#pageSizeSelect").val(result.attrs.pageSize);
}

