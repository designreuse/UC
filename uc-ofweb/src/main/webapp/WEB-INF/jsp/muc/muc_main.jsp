<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="mucroom.title.name"/></title>
    <link rel="stylesheet" href=${pageContext.request.contextPath}/3rdLibrary/ztree/zTreeStyle/zTreeStyle.css>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/muc/muc_manage.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/artTemplate.js"></script>

    <script type="text/javascript">
        var roomTypeNames=new Array();
        roomTypeNames[0]='<spring:message code="muc.roomtype.group"/>';
        roomTypeNames[1]='<spring:message code="muc.roomtype.privateroom"/>';
        roomTypeNames[2]='<spring:message code="muc.roomtype.publicroom"/>';
        var pageSize=15;
        $(function(){
            goPage(1);
            $("#btnSearch").click(function(){goPage(1);});
            $("#room_type_list li[id^='select_']").click(function(){
                $("#room_type_select").val($(this).val());
                goPage(1);
            });
            $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
            $('.tree li.parent_li > span').on('click', function (e) {
                var children = $(this).parent('li.parent_li').find(' > ul > li');
                if (children.is(":visible")) {
                    children.hide('fast');
                    $(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
                } else {
                    children.show('fast');
                    $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
                }
                e.stopPropagation();
            });
//            readyCreateStaff();
        });
        function updateEllipsis(container,text) {
            if (container.scrollWidth > container.offsetWidth) {
                //此处重要，利用container的实际宽度和显示宽度的比例计算出文字显示的个数，然后，截取出来并在末位加上...
                var len = container.offsetWidth / container.scrollWidth * text.length;
                setText(label, text.substring(0, Math.floor(len) - 1) + "...");
                label.title = text;
            } else {
                if (label.title != "") {
                    setText(label, label.title);
                    label.title = "";
                }
            }
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
        function generateNavi(attrs){
            var navi='<div class="pull-left page-total-records">\
                总共记录：'+attrs.total
                    +'</div>';
            navi+='<div class="pull-right pagination-number-div">跳转到\
                <input type="text" onkeydown="return inputPageNum(event);" value="1" name="pageNumInput" id="pageNumInput" maxlength="10" class="input-page-number">页\
                <button id="pageGo" class="btn pagination-go-btn btn-sm" type="button">Go</button>\
                </div>\n<div class="pull-right">\
                <ul class="pagination">';
            var totalPages=Math.ceil(attrs.total/attrs.pageSize);
            var pageArray=getPageNoArray(attrs.pageNo,totalPages);
            if(attrs.pageNo>1){
                navi+='<li><a onclick="goPage(1);" href="javascript:void(0);"><span title="首页">«</span></a></li>\n';
                navi+='<li><a onclick="goPage('+(attrs.pageNo-1)+');" href="javascript:void(0);"><span title="上一页">&lt;</span></a></li>\n';
            }
            for(var i=0;i<pageArray.length;i++){
                if(pageArray[i]==attrs.pageNo){
                    navi+='<li class="active"><a>'+pageArray[i]+'</a></li>\n';
                }
                else{
                    navi+='<li><a href="javascript:void(0);" onclick="goPage('+pageArray[i]+')">'+pageArray[i]+'</a></li>\n';
                }
            }
            if(attrs.pageNo<totalPages){
                navi+='<li><a onclick="goPage('+(attrs.pageNo+1)+');" href="javascript:void(0);"><span title="下一页">&gt;</span></a></li>\n';
                navi+='<li><a onclick="goPage('+totalPages+');" href="javascript:void(0);"><span title="尾页">»</span></a></li>\n';

            }
            navi+='</ul></div>\n';
            return navi;
        }
        function deleteMucRoom(roomJid) {
            var uri = "mucRoom?roomJid="+roomJid;
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
                        reload();
                    }else {
                        alert("删除失败，请稍后重试...");
                    }
                }
            })
        }
        //修改群组信息：修改群组名称与群公告
        function editRoom(roomJid) {
            var caption = $("#confirmModal input[name='caption_edit']").val();
            var desc = $("#confirmModal input[name='desc_edit']").val();
            var uri = "mucRoom?roomJid="+roomJid;
            uri += "&caption="+caption;
            uri += "&desc="+desc;
            alert(uri);
            $.ajax({
                url:uri,
                cache:false,
                method:"PUT",
                dataType:"json",
                //contentType:"application/json",
                error:function(xmlHttpRequest,error){
                    alert(error);
                },
                success:function(result){
                    if (result.ret>=0){
                        alert("修改成功");
                        reload();
                    }else {
                        alert("修改失败，请稍后重试...");
                    }
                }
            })
        }


        function goPage(pageNo){
            var uri="mucRoom?pageSize="+pageSize+"&pageNo="+pageNo;
            var keyword=$("#keyword").val();
            var roomType=$("#room_type_select").val();
            if(keyword !=""){
                uri+="&keyword="+keyword;
            }
            if(roomType !=""){
                uri+="&roomType="+roomType;
            }
            $.ajax({
                url:uri,
                cache:false,
                method:"GET",
                dataType:"json",
                error:function(xmlHttpRequest,error){
                    alert(error);
                },
                success:function(result){
                    if(result.mucRoomViews){
                        $("#mucList tr").remove();
                        var mucContent="";
                        for(var i=0;i<pageSize;i++){
                            if(i<result.mucRoomViews.length){
                                var room=result.mucRoomViews[i];
                                //table-striped-tr-odd table-striped-tr-even
                                mucContent+='<tr><td><label title="'+room.roomJid+'">'
                                        +room.roomJid
                                        +'</label></td><td>\
                                <label title="'+room.roomNaturalName+'">'
                                        +room.roomNaturalName
                                        +'</label></td><td>';
                                if(room.roomType==1){
                                    if(room.isPublic){
                                        mucContent+=roomTypeNames[2];
                                    }
                                    else{
                                        mucContent+=roomTypeNames[1];
                                    }
                                }
                                else{
                                    mucContent+=roomTypeNames[0];
                                }
                                mucContent+='</td>\n<td><label title="'+room.desc+'">'
                                        +room.desc
                                        +'</label></td><td>';
                                var date=new Date();
                                date.setTime(room.creationDate);
                                mucContent+=date.toLocaleString()
                                    +'</td>\
                            <td>\
                                <div class="row td-div-operation">\
                                    <label class="table-icon-label" data-id="'+room.roomJid+'" >\
                                        <i class="icon-pencil icon-large" title="编辑"></i>\
                                    </label>\
                                    <label class="table-icon-label" data-id="'+room.roomJid+'">\
                                        <i class="icon-trash icon-large" title="删除"></i>\
                                    </label>\
                                    <label name="'+room.roomNaturalName+'" class="table-icon-label" data-id="'+room.roomJid+'">\
                                        <i class="icon-cogs icon-large" title="成员管理"></i>\
                                    </label>\
                                </div></td></tr>\n';
                            }
                            else{
                                mucContent+='<tr><td></td><td></td><td></td><td></td><td></td>\n';
                            }
                        }
                        $("#mucList").append(mucContent);
                        $("#mucList tr:even").addClass("table-striped-tr-even");
                        $("#mucList tr:odd").addClass("table-striped-tr-odd")
                    }
                    $("#naviDiv").children().remove();
                    $("#naviDiv").append(generateNavi(result.pager));
                    $("#mucList i").click(function(){
                        var roomJid = $(this.parentNode).attr("data-id");
                        var roomNode = roomJid.substring(0,roomJid.indexOf("@"));
                        //修改群信息
                        if ($(this).attr("class") == "icon-pencil icon-large"){
                            $("#roomNode").html(roomNode);
                            var confirmModalBodyStr = $("#divEditRoomTemplate").html();
                            alertConfirmationMsgDlg(confirmModalBodyStr,editRoom,roomJid);
                        }
                        //删除群组
                        if ($(this).attr("class") == "icon-trash icon-large"){
                            alertConfirmationMsgDlg("确认删除该群组吗？",deleteMucRoom,roomJid);
                        }
                        //成员管理
                        if ($(this).attr("class") == "icon-cogs icon-large"){
                            readyMemberManage(roomJid);
                        }
                    });
                }
            });
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div id="divContent" class="col-sm-12 div-content-main">
            <div class="div-title-bar clearfix">
                <div class="form-inline pull-right">
                    <div class="btn-group">
                        <button class="btn btn-primary dropdown-toggle btn-sm" data-toggle="dropdown"
                                id="room_type_select" value="">
                            所有
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" role="menu" id="room_type_list">
                            <li id="select_all" value=""><a href="#">所有</a></li>
                            <li id="select_locked" value="1"><a href="#">用户群</a></li>
                            <li id="select_recycled" value="0"><a href="#">讨论组</a></li>
                        </ul>
                    </div>
                    <div class="form-group">
    						<span class="block  input-icon input-icon-right">
    							<input class="form-control" id="keyword" maxlength="128"
                                       title="请输入关键字搜索" placeholder="请输入关键字搜索"
                                       value="" autocomplete="off"/>
								<i class="icon-search btn" id="btnSearch"></i>
							</span>
                    </div>
                </div>
            </div>

            <div class="div-list-table div-list-table-layout">
                <input type="hidden" value="${status }" id = "status"/>
                <table class="table fixed-table table-hover table-condensed">
                    <thead>
                    <tr>
                        <th class="th-first list-sort-cursor">群组编号</th>
                        <th>群组名称</th>
                        <th>群组类型</th>
                        <th>公告内容</th>
                        <th>创建时间</th>
                        <th width="10%">操作</th>
                    </tr>
                    </thead>
                    <tbody id="mucList">
                    </tbody>
                </table>
            </div>
            <div id="naviDiv" class="clearfix">
            </div>
        </div>
    </div>

    <div id="divEditRoomTemplate" style="display: none">
        <table>
            <tbody>
            <tr>
                <td>群组编号：</td>
                <td id="roomNode"></td>
            </tr>
            <tr>
                <td>群组名称：</td>
                <td >
                    <input id="caption_edit" name="caption_edit" class="form-control" placeholder="请输入群名称" title="请输入群名称" maxlength="32">
                </td>
            </tr>
            <tr>
                <td>群组公告：</td>
                <td >
                    <input id="desc_edit" name="desc_edit" class="form-control" type="text" placeholder="请输入群公告" title="请输入群公告" maxlength="500">
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- 群组成员管理窗口 -->
    <div class="modal fade" id="popMemberMng" role="dialog" aria-hidden="true">
        <div class="modal-dialog" style="width:800px; height:500px;">
            <div class="modal-content">
                <div class="container-fluid div-nav-left-page">
                    <div class="col-sm-12 div-title-height-fix  div-content-main">
                        <div class="panel-body panel-body-fix">
                            <form class="form-horizontal" role="form" autocomplete="off">
                                <div class="form-group" id="divRoomId">
                                    <label class="control-label col-sm-3">群组编号</label>
                                    <div class="col-sm-6">
                                         <span>
                                            <label class="form-control" id="labelRoomId">群组编号</label>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group" id="divRoomType">
                                    <label class="control-label col-sm-3">群组类型</label>
                                    <div class="col-sm-6">
                                         <span>
                                            <label class="form-control" id="labelRoomType">群组类型</label>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group" id="divRoomName">
                                    <label class="control-label col-sm-3">群组名称</label>
                                    <div class="col-sm-6">
                                        <span id="spanRoomName">
                                            <label class="form-control" id="labelRoomName">群组名称</label>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group" id="divAddRoomMember">
                                    <label class="control-label col-sm-3">添加成员</label>
                                    <div class="col-sm-6">
                                        <div class="input-group">
                                            <input type="text" class="form-control" id="selectedMember" readonly="readonly">
                                            <div class="input-group-btn">
                                                <button type="button" id="orgDropdownBtn_add" class="btn btn-default dropdown-toggle">
                                                    选择<span class="caret"></span>
                                                </button>
                                                <ul id="treeTrade_create" class="ztree dropdown-menu dropdown-menu-right"
                                                         style="height: 280px; width:250px; overflow:auto;">
                                                </ul>
                                            </div>
                                            <div style="margin-left: 3px; width: 90px">
                                            <select id="selectedRole" class="form-control">
                                                <option value="owner">群主</option>
                                                <option value="admin">管理员</option>
                                                <option value="member" selected>群员</option>
                                            </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-3">成员列表</label>
                                    <div class="col-sm-6" style="align-content: center; height: 250px">
                                        <table class="table fixed-table table-hover table-condensed" >
                                            <thead>
                                            <tr>
                                                <th >成员</th>
                                                <th style="width: 30%">角色</th>
                                                <th style="width: 30%">操作</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td colspan="3" >
                                                        <div style="overflow: auto;height: 200px">
                                                            <table class="table fixed-table table-hover table-condensed">
                                                                <tbody id="memberList" >
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </form>
                            <%-- 窗口的底部按钮 --%>
                            <div class="modal-footer" style="text-align: center">
                                <button type="button" class="btn btn-primary" id="saveMemberBtn"><spring:message code="system.common.button.ok" text="system.common.button.ok"/></button>
                                <a class="btn btn-default" href="${pageContext.request.contextPath}/muc/index"><spring:message code="system.common.button.cancel"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 群组成员修改窗口 -->
    <div class="modal fade" id="popMemberModify" role="dialog" aria-hidden="true">
        <div class="modal-dialog" style="width:800px; height:500px;">
            <div class="modal-content">
                <div class="container-fluid div-nav-left-page">
                    <div class="col-sm-12 div-title-height-fix  div-content-main">
                        <div class="panel-body panel-body-fix">
                            <form class="form-horizontal" role="form" autocomplete="off">
                                <div class="form-group">
                                    <label class="control-label col-sm-3">群组名称</label>
                                    <div class="col-sm-6">
                                        <span>
                                            <label class="form-control" id="roomNameForModify">群组名称</label>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-3">成员名称</label>
                                    <div class="col-sm-6">
                                        <span>
                                            <label class="form-control" id="memberNameForModify">成员名称</label>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-sm-3">成员角色</label>
                                    <div class="col-sm-6">
                                        <select id="modifyMemberRole" class="form-control">
                                            <option value="owner">群主</option>
                                            <option value="admin">管理员</option>
                                            <option value="member" selected>群员</option>
                                        </select>
                                    </div>
                                </div>
                            </form>
                            <%-- 窗口的底部按钮 --%>
                            <div class="modal-footer" style="text-align: center">
                                <button type="button" class="btn btn-primary" id="saveMemberModifyBtn"><spring:message code="system.common.button.ok" text="system.common.button.ok"/></button>
                                <button type="button" class="btn btn-default"
                                        data-dismiss="modal" id="cancelMemberModifyBtn"><spring:message code="system.common.button.cancel" text="system.common.button.cancel"/></button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script id="showMembers" type="text/html">
    <%@include file="member_list.template"%>
</script>

</body>
</html>
