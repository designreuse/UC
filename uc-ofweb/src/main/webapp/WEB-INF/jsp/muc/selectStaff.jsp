<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<HTML>
<HEAD>
	<TITLE>选择参与人</TITLE>
    <link rel="stylesheet" href=${pageContext.request.contextPath}/3rdLibrary/ztree/zTreeStyle/zTreeStyle.css>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/ztree/jquery.ztree.all-3.5.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/im-web.css">
	<style>
    .container{
        width:500px;
        height:400px;
    }

	.content{
        float:left;
        border:1px solid red;
        width:49%;
        height:81%;
        overflow: auto;
    }
    .header{
        float:left;
        border:1px solid red;
        width:49%;
    }
    .footer{
        clear: both;
        text-align: center
    }
    .ul-none{
     list-style: none
    }
    .search{
        width:220px;
    }
	</style>
</HEAD>

<BODY>
<div class="container">
    <div>
        <div class="header">
            <input class="search" type="text" id="searchKey" style="width: 100%"/>
            <div style="position:absolute;display:none;z-index: 1;" id="recordStaff">
                <select class="search" id="selectStaff" multiple size="5">
                </select>
            </div>
        </div>
        <div class="header">
            已选择：
            <span style="margin-right: 1px">
                <input type="button" value="删除" id="del"/>
                <input type="button" value="清空" id="clear"/>
            </span>
        </div>
    </div>
	<div class="content" style="position:relative;">
		<div>
        	<ul id="staffTree" class="ztree"></ul>
        </div>
	</div>
	<div class="content">
		<div>
            <select id="selectedStaff" multiple size="16" style="width:100%;">
            </select>
		</div>
	</div>
    <div class="footer">
        <input type="button" value="确认" id="confirm"/>
    </div>
</div>
<script type="text/javascript">
    var setting = {
        view: {
            selectedMulti: false
        },
        check: {
            enable: true,
            autoCheckTrigger:false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid",
            }
        }
    };

    var zNodes =[
        { id:1, pId:0, name:"随意勾选 1",age:23, open:true},
        { id:11, pId:1, name:"随意勾选 1-1",age:23},
        { id:12, pId:1, name:"随意勾选  1-2",age:23,open:true},
        { id:121, pId:12, name:"随意勾选 1-2-1",age:23},
        { id:13, pId:1, name:"随意勾选 1-3",age:23},
        { id:2, pId:0, name:"随意勾选 2",age:23,open:true},
        { id:21, pId:2, name:"随意勾选 2-1",age:23},
        { id:22, pId:2, name:"随意勾选 2-2",age:23,open:true},
        { id:221, pId:22, name:"随意勾选 2-2-1",age:23},
        { id:222, pId:22, name:"随意勾选 2-2-2",age:23},
        { id:223, pId:22, name:"随意勾选 2-2-3",age:23},
        { id:224, pId:22, name:"随意勾选 2-2-4",age:23},
        { id:11, pId:2, name:"随意勾选 1-1",age:23}
    ];
    //主键ID
    var keyId = "id";
    var keyName = "name";
    var keyTreeId = "staffTree"

    function zTreeOnCheck(event, treeId, treeNode){
        console.log(treeNode.id);
        //触发相同ID的
        loopDealNodesKeyId(treeId, treeNode);
        //勾选或取消
        showCheckNodes(treeId, treeNode);
        return true;
    }

    //显示已选中列表
    function showCheckNodes(treeId){
        $("#selectedStaff").empty();
        var nodes = getRoots(treeId);
        $(nodes).each(function(index, element){
            addNodes(element);
        })
    }

    //增加所有节点
    function addNodes(treeNode){
        if(treeNode.isParent){
            if(treeNode.check_Child_State == 2){
                addNode(treeNode);
            }else{
                var childNodes = treeNode.children;
                $(childNodes).each(function(index, element){
                    addNodes(element);
                })
            }
        }else {
            if(treeNode.checked){
                addNode(treeNode);
            }
        }
    };

    //获取根节点
    function getRoots(treeId) {
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        //返回根节点集合
        var nodes = zTree.getNodesByFilter(function (node) { return node.level == 0 });
        return nodes
    }
    //是否列表中已存在
    function isSelected(treeNode){
        var isSelected = false;
        var zTree = $.fn.zTree.getZTreeObj(keyTreeId);
        var nodes = zTree.getNodesByParam(keyId,treeNode[keyId],null);
        $(nodes).each(function(index, element){
            if(element.tId != treeNode.tId){
                if(element.checked){
                    //其它节点的父节点已全选中
                    if(element.getParentNode().check_Child_State ==2){
                        isSelected = true;
                    }else {
                        if(isHaveAdd(element)){//如果其它节点为子节点，如果已加到列表中，就不用加了
                            isSelected = true;
                        }
                    }
                }
            }
        })
        return isSelected;
    }

    //增加节点
    function addNode(treeNode){
        if(!isSelected(treeNode)){
            var opt="<option value='"+treeNode[keyId]+"'>"+treeNode[keyName]+"</option>";
            $("#selectedStaff").append(opt);

        }
    }

    //是否右边有相同的节点
    function isHaveAdd(treeNode){
        var isHaveAdd = false;
        var nodes = $("#selectedStaff option");
        $(nodes).each(function(index, element){
            if($(element).val() == treeNode[keyId]){
                isHaveAdd = true;
            }
        })
        return isHaveAdd;
    }


    //循环处理相同keyId
    function loopDealNodesKeyId(treeId, treeNode){
        if(treeNode.isParent){
            var childNodes = treeNode.children;
            $(childNodes).each(function(index, element){
                loopDealNodesKeyId(treeId,element);
            })
        }else{
            dealNodesKeyId(treeId,treeNode,treeNode.checked);
        }
    }
    //处理相同的keyId
    function dealNodesKeyId(treeId,treeNode,checked){
        var zTree = $.fn.zTree.getZTreeObj(treeId);
        var nodes = zTree.getNodesByParam(keyId,treeNode[keyId],null);
        $(nodes).each(function(index, element){
            if(checked){//选中处理
                if(!element.checked){//没选中的选中
                    zTree.checkNode(element, true, true,false);
                }
            }else{//取消处理
                if(element.checked){//选中的取消
                    zTree.checkNode(element, false, true,false);//不触发onCheck事件
                }
            }

        })
    }
    //查询关键字
    function searchNode(keyVal){
        var zTree = $.fn.zTree.getZTreeObj(keyTreeId);
        $("#selectStaff").empty();
        var nodeList = zTree.getNodesByParamFuzzy(keyName, keyVal);
        $(nodeList).each(function(index,element){
            var keyIdVal=element[keyId];
            var name=element[keyName];
            //判断是否已加入相同的keyID
            var isExist = false;
            $("#selectStaff option").each(function(){
                var optVal = $(this).val();
                if(keyIdVal == optVal){
                    isExist = true;
                }
            })
            //不存在则加入
            if(!isExist){
                var opt="<option value='"+keyIdVal+"'>"+name+"</option>";
                $("#selectStaff").append(opt);
            }
        });
        if(keyVal==""){
            $("#recordStaff").hide();
        }else{
            $("#recordStaff").show();
        }
    }
    $(document).ready(function(){
        //生成树
        loadTree();
        //关键字查询
        $("#searchKey").keyup(function(){
            var keyVal = $(this).val();
            searchNode(keyVal);
        });
        //失去焦点
        $(document).click(function (e) {
            var curId = $(e.target).attr("id");
            if(curId != "selectStaff"){
                $("#recordStaff").hide();
            }
        });
        //模糊查找，勾选
        $("#selectStaff").change(function(){
            var zTree = $.fn.zTree.getZTreeObj(keyTreeId);
            $("#searchKey").val("");
            var keyIdVal = $(this).val()[0];//返回的是数组
            var nodes = zTree.getNodesByParam(keyId,keyIdVal,null);
            $(nodes).each(function(index, element){
                zTree.checkNode(element, true, true,true);
            })
        })
        //删除操作
        $("#del").click(function(){
            var zTree = $.fn.zTree.getZTreeObj("staffTree");
            var opts = $("#selectedStaff option:selected");
            $(opts).each(function(index, element){
                var keyIdVal = $(this).val();
                var nodes = zTree.getNodesByParam(keyId,keyIdVal,null);
                var nodeFirst = nodes[0];
                zTree.checkNode(nodeFirst, false, true,true);
            })
        })
        //清空操作
        $("#clear").click(function(){
            $("#selectedStaff").empty();
            var zTree = $.fn.zTree.getZTreeObj(keyTreeId);
            zTree.checkAllNodes(false);
        })
        $("#confirm").click(function(){
            var zTree = $.fn.zTree.getZTreeObj(keyTreeId);
            var nodes = zTree.getCheckedNodes(true);
            //已选择树
            var checkedTreeId="";
            var relStaffId="";
            var relStaffName="";
            $(nodes).each(function () {
                if(!this.isParent && this.type == "staff"){
                    checkedTreeId = checkedTreeId +  this.tId + ";";
                    if(relStaffId.indexOf(this[keyId]) == -1){//去重
                        relStaffId = relStaffId +  this[keyId] + ";";
                        relStaffName = relStaffName +  this[keyName] + ";";
                    }

                }
            })
            //检验
            if(relStaffId== ""){
                alert("选择必须包含人员！");
                return false;
            }
            //已选择列表
            var selectedOriStaffId="";
            var selectedOriStaffName="";
            var opts = $("#selectedStaff option");
            $(opts).each(function(index, element){
                var OriStaffId = $(this).val();
                selectedOriStaffId = selectedOriStaffId + OriStaffId + ";";
                var OriStaffName = $(this).text();
                selectedOriStaffName = selectedOriStaffName + OriStaffName + ";";
            })
            window.opener.setRelStaffInfo(relStaffId,relStaffName,checkedTreeId,selectedOriStaffId,selectedOriStaffName)
            self.close();
        })
        //初始化化树
        initStaffTree();
    });

    function initStaffTree(){
        var url = decodeURI(document.URL,"utf8");
        var paramStr = url.substr(url.indexOf("?")+1)
        var param = paramStr.split("&");
        var checkedTreeId;
        var selectedOriStaffId;
        var selectedOriStaffName;
        var keyVal;
        //处理参与：选择树（tId）|已选择列表（id|name）
        $(param).each(function(){
            keyVal = this.split("=");
            if(keyVal[0]=="checkedTreeId"){
                checkedTreeId = keyVal[1];
            }else if(keyVal[0] == "selectedOriStaffId"){
                selectedOriStaffId = keyVal[1];
            }else{
                selectedOriStaffName =  keyVal[1];
            }
        })
        if(checkedTreeId != ""){
            //树形选中
            var checkedTreeIdList = checkedTreeId.substring(0,checkedTreeId.length -1).split(";");
            $(checkedTreeIdList).each(function(){
                var zTree = $.fn.zTree.getZTreeObj(keyTreeId);
                var node = zTree.getNodeByTId(this);
                zTree.checkNode(node, true, true,false);
            })
            //已选择列表
            var selectedOriStaffIdList = selectedOriStaffId.substring(0,selectedOriStaffId.length -1).split(";");
            var selectedOriStaffNameList = selectedOriStaffName.substring(0,selectedOriStaffName.length -1).split(";");
            $(selectedOriStaffIdList).each(function(index, element){
                var opt="<option value='"+selectedOriStaffIdList[index]+"'>"+selectedOriStaffNameList[index]+"</option>";
                $("#selectedStaff").append(opt);
            })
        }
    };
    function loadTree() {
        var treeNodes = new Array();
        $.ajax({
            async: false,
            type: "GET",
            dataType: "json",
            url: "/createTreeNodes", //请求的action路径
            error: function() {//请求失败处理函数
                alert('显示失败！');
            },
            success: function(data) { //请求成功后处理函数。此处的data是JSON对象
                jQuery.each(data, function(i, item) {
                    treeNodes.push(new NodeWithUser(item.id, item.pid, item.name, item.isParent, item.type, item.gender, item.pinyin, item.pinyinAlia, item.mail));
                });
                $.fn.zTree.init($("#staffTree"), setting, treeNodes);
            }
        });
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
        this.name = name;
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

   /*     if (this.type == "staff") {
            if (this.gender == "0") {
                this.iconSkin = "sIconWomen";
            } else {
                this.iconSkin = "sIconMen";
            }
        }

        if (this.pid == "0_org") {
            this.iconSkin = "pIcon";
        }*/
    }

</script>
</BODY>
</HTML>