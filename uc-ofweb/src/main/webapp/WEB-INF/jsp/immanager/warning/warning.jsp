<%--
  Created by IntelliJ IDEA.
  User: chenkl
  Date: 2016/7/28
  Time: 14:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/css/style.min.css">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/ztree/zTreeStyle/zTreeStyle.css">
    <style type="text/css">
        .col-left{
            padding-right:5px;
        }
        .col-right{
            padding-left:5px;
        }
        .tree-container{
            height:350px;
            border:1px solid #ddd;
            overflow: auto;
        }
        .list-container{
            height:350px;
            border:1px solid #ddd;
            overflow: auto;
        }
        .head-margin{
            margin-top:10px
        }
        .row-margin{
            margin-top:2px
        }
        .search-div{
            width:93%;
            position:absolute;
            z-index:1;
            display:none;
        }
        .list-container li.selectedItem {
            background-color:#FFE6B0;
            border:1px #FFB951 solid;
            opacity:0.8;
        }
        .th-color{
            background-color:#5fb0d5;
        }
        .td-color{
            background-color:#DFEFF7;
        }
        .td-padding-left{
            padding-left: 50px !important;
        }
    </style>
</head>

<body>
<div id="content_middle">
    <%@ include file="../../common/navigation.jsp" %>
</div>
<div  id="content_right">
    <div class="container-fluid">
   <div class="panel  col-sm-12" >
       <div class="panel-heading">
           <div class="panel-title">
               <spring:message code="warning.panel.title" text="warning.panel.title"/>
           </div>
       </div>
       <hr/>
       <div class="panel-body">
           <spring:message code="warning.panel.tip" text="warning.panel.tip"/>
           <div class="pull-right">
               <button id="setWarningBtn" class="btn btn-default"> <spring:message code="warning.button.set.user" text="warning.button.set.user"/></button>
           </div>
       </div>

       <div class="div-list-table-layout">
           <table class="table fixed-table table-condensed table-bordered">
               <thead >
                    <th width="60%" class="text-align-left td-padding-left th-color" style="height: 40px;">
                        <spring:message code="warning.table.th.content" /></th>
                    <th width="20%" class="text-align-center th-color">
                        <spring:message code="warning.table.th.email" /></th>
                    <th width="20%" class="text-align-center th-color">
                        <spring:message code="warning.table.th.im" /></th>
               </thead>
               <tbody>
                    <tr>
                        <td colspan="3" class="text-align-left td-color td-padding-left">
                          <label style="font-size: 14px;font-weight: 600"><spring:message code="warning.td.server"/></label>
                        </td>
                    </tr>
                    <tr>
                       <td colspan="3" class="table-striped-tr-even text-align-left td-padding-left">
                           <spring:message code="warning.td.hardware"/>
                       </td>
                    </tr>
                    <tr class="table-striped-tr-odd text-align-left">
                        <td width="60%" style="padding-left: 100px;" class="no-border"><spring:message code="warning.cpu.prefix"/>
                            <input type="text" id="cpuUsedRate"class="no-border"/><spring:message code="warning.cpu.suffix"/> </td>
                        <td width="20%"  class="text-align-center"> <input id="cpuIsEmail" type="checkbox"/> </td>
                        <td width="20%"  class="text-align-center"> <input id="cpuIsIM" type="checkbox"/> </td>
                    </tr>
                    <tr class="table-striped-tr-even text-align-left">
                        <td width="60%" style="padding-left: 100px;" class="no-border"><spring:message code="warning.memory.prefix"/>
                            <input type="text" id="memoryUsedRate" class="no-border"/><spring:message code="warning.memory.suffix"/> </td>
                        <td width="20%"  class="text-align-center"> <input id="memoryIsEmail" type="checkbox"/> </td>
                        <td width="20%"  class="text-align-center"> <input id="memoryIsIM" type="checkbox"/> </td>
                    </tr>
                    <tr class="table-striped-tr-odd text-align-left">
                        <td width="60%" style="padding-left: 100px;"class="no-border"><spring:message code="warning.hdd.prefix"/>
                            <input type="text" id="deskUsedRate" class="no-border"/><spring:message code="warning.hdd.suffix"/> </td>
                        <td width="20%" class="text-align-center"> <input id="deskIsEmail" type="checkbox"/> </td>
                        <td width="20%" class="text-align-center"> <input id="deskIsIM" type="checkbox"/> </td>
                    </tr>
                    <tr>
                        <td colspan="3" class="table-striped-tr-even text-align-left td-padding-left">
                            <spring:message code="warning.td.software"/>
                        </td>
                    </tr>
                    <tr class="table-striped-tr-odd text-align-left">
                        <td width="60%" style="padding-left: 100px;"><spring:message code="warning.td.new.version"/> </td>
                        <td width="20%"  class="text-align-center"> <input id="newSoftIsEmail" type="checkbox"/> </td>
                        <td width="20%"  class="text-align-center"> <input id="newSoftIsIM" type="checkbox"/> </td>
                    </tr>
                    <tr>
                        <td colspan="3" class="text-align-left td-padding-left td-color">
                            <label style="font-size: 14px;font-weight: 600"><spring:message code="warning.td.call"/> </label>
                        </td>
                    </tr>
                    <tr class="table-striped-tr-even text-align-left">
                        <td width="60%" style="padding-left: 100px;"><spring:message code="warning.td.repeaters.error"/> </td>
                        <td width="20%"  class="text-align-center"> <input  type="checkbox"/> </td>
                        <td width="20%"  class="text-align-center"> <input  type="checkbox"/> </td>
                    </tr>
                    <tr class="table-striped-tr-odd text-align-left">
                        <td width="60%" style="padding-left: 100px;"><spring:message code="warning.td.call.max"/></td>
                        <td width="20%" class="text-align-center"> <input  type="checkbox"/> </td>
                        <td width="20%" class="text-align-center"> <input  type="checkbox"/> </td>
                    </tr>
                    <tr class="table-striped-tr-even text-align-left">
                        <td width="60%" style="padding-left: 100px;"><spring:message code="warning.td.call.concurrence.max"/> </td>
                        <td width="20%"  class="text-align-center"> <input  type="checkbox"/> </td>
                        <td width="20%"  class="text-align-center"> <input  type="checkbox"/> </td>
                    </tr>
                    <tr>
                        <td colspan="3" class="text-align-left td-color td-padding-left">
                            <label style="font-size: 14px;font-weight: 600"><spring:message code="warning.td.serve"/></label>
                        </td>
                    </tr>
                    <tr class="table-striped-tr-even text-align-left">
                        <td width="60%" style="padding-left: 100px;" class="no-border"><spring:message code="warning.td.exception.prefix"/>
                            <input type="text" class="no-border"/><spring:message code="warning.td.exception.suffix"/> </td>
                        <td width="20%"  class="text-align-center"> <input  type="checkbox"/> </td>
                        <td width="20%"  class="text-align-center"> <input  type="checkbox"/> </td>
                    </tr>
                    <tr>
                        <td colspan="3" class="text-align-left td-color td-padding-left">
                            <label style="font-size: 14px;font-weight: 600"> <spring:message code="warning.td.safe"/> </label>
                        </td>
                    </tr>
                    <tr class="table-striped-tr-even text-align-left">
                        <td width="60%" style="padding-left: 100px;"><spring:message code="warning.td.blacklist"/> </td>
                        <td width="20%"  class="text-align-center"> <input  type="checkbox"/> </td>
                        <td width="20%"  class="text-align-center"> <input  type="checkbox"/> </td>
                    </tr>
               </tbody>
           </table>
       </div>
       <div class="pull-right">
           <button type="button" class="btn btn-primary btn-block btn-lg" id="saveWarningSetBtn"><spring:message code="system.common.button.save"/></button>
       </div>
   </div>

</div>
</div>
<!-- 选择通知对象 模态框（Modal） -->
<div class="modal fade" id="setWarningObjectModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" style="top:200px">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" style="text-align:center" id="myModalLabel"><spring:message code="warning.modal.title"/></h4>
            </div>
            <div class="modal-body">
                注：所有告警内容都会以邮件和站内信的方式通知管理员！
                <div  class="tab-content head-margin">
                    <div class="tab-pane fade in active" id="org">
                        <div class="row">

                            <%--<div class="col-sm-6 col-left">--%>
                                <%--<label style="padding-bottom: 10px;">选择通知对象</label>--%>
                                <%--<input class="form-control" type="text" placeholder="搜索过滤条件" id="org_search">--%>
                                <%--<div class="search-div" id="org_search_div">--%>
                                    <%--<select class="form-control" multiple id="org_search_list" size="6">--%>
                                    <%--</select>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="col-sm-6 col-right">--%>
                                <%--<div class="row panel panel-default" style="line-height:34px;">--%>

                                    <%--<div class="col-sm-6" ><spring:message code="warning.modal.choosen"/></div>--%>
                                    <%--<div class="col-sm-6">--%>
                                        <%--<button type="button" class="btn btn-default" id="org_del"><spring:message code="system.common.button.delete"/></button>--%>
                                        <%--<button type="button" class="btn btn-default" id="org_clear"><spring:message code="system.common.button.clean"/></button>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        </div>
                        <div class="row row-margin">
                            <div class="col-sm-6 col-left">
                                <label style="padding-bottom: 10px;">选择通知对象</label>
                                <input class="form-control" type="text" placeholder="搜索过滤条件" id="org_search">
                                <div class="search-div" id="org_search_div">
                                    <select class="form-control" multiple id="org_search_list" size="6">
                                    </select>
                                </div>
                                <div class="tree-container">
                                    <ul id="org_tree" class="ztree"></ul>
                                </div>
                            </div>
                            <div class="col-sm-6" style="padding-right: 15px;">
                            <div style="margin-top: 30px;padding-left: 0;padding-right: 0;border: 1px solid #ddd">
                                <div style="line-height:34px;">
                                    <div class="col-sm-5" ><spring:message code="warning.modal.choosen"/></div>
                                    <div class="col-sm-7" style="padding-left:80px; ">
                                        <label id="org_del" title="<spring:message code="system.common.button.delete"/>" style="cursor: pointer;padding-right: 20px;">
                                        <i class="icon-remove icon-large"></i>
                                        </label>
                                        <label id="org_clear" title="<spring:message code="system.common.button.clean"/>" style="cursor: pointer;">
                                            <i class="icon-trash icon-large"></i>
                                        </label>
                                    </div>
                                </div>
                                <div class="list-container">
                                    <ul class="list-unstyled" id="org_staff_List">
                                    </ul>
                                </div>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%--<div class="modal-footer">--%>
                <%--<button type="button" class="btn btn-primary" id="confirmSelectStaff"><spring:message code="system.common.button.ok"/> </button>--%>
                <%--<button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="system.common.button.cancel"/> </button>--%>
            <%--</div>--%>
            <div class="modal-footer  custom-modal-footer">
                <div class="col-sm-10 custom-col-sm-no-padding custom-div-padding-left">
                    <div class="col-sm-5 row pull-left">
                        <button type="button" class="btn  btn-block btn-primary btn-lg" id="confirmSelectStaff"
                                data-dismiss="modal"><spring:message code="system.common.button.ok" text="system.common.button.ok"/>
                        </button>
                    </div>
                    <div class="col-sm-5 row pull-right">
                        <button type="button" class="btn btn-default btn-block btn-lg"
                                data-dismiss="modal"><spring:message code="system.common.button.cancel" text="system.common.button.cancel"/>
                        </button>
                    </div>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<input type="hidden" id="selectIds"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/orgTree.js"></script>
<script type="text/javascript">

    //组织架构
    var orgStaff = new Staff("org","id","name","pid");
    $(function(){
        setCurrentPageNavigation("systemManageFirstNav","warningSidebar","");
        //获取之前设置的告警设置
        getWarningSet();
        //按组织框架
        orgStaff.initEvent();
        orgStaff.loadTree();
        //定义选择告警人事件，同时勾选中之前选择的人员，并在右边显示出来
        $("#setWarningBtn").click(function(){
            $("#setWarningObjectModal").modal("show");
            var treeObj = $.fn.zTree.getZTreeObj("org_tree");
            var selectedIds=$("#selectIds").val().split(",");
            for(index in selectedIds) {
                var idvalue = selectedIds[index] + "_staff";
                var nodes = treeObj.getNodesByParam("id", idvalue, null);
                for (var i = 0, l = nodes.length; i < l; i++) {
                    treeObj.checkNode(nodes[i], true, true);
                }
            }
            //展开根节点
            var rootNode=orgStaff.getRoots("org_tree");
            if (rootNode.length>0) {
                treeObj.expandNode(rootNode[0], true, false, true);
            }
            //显示已经勾选的用户
            orgStaff.showCheckNodes("org_tree");
        });

        //确认
        $("#confirmSelectStaff").click(function(){
            var staff= orgStaff;//当前员工对象
            var staffIdList=new Array();//员工Id
            var zTree = $.fn.zTree.getZTreeObj(staff.treeId);
            var nodes = zTree.getCheckedNodes(true);//所有已选中的员工节点
            $(nodes).each(function () {
                if(!this.isParent && this.type == staff.staffSuffix){
                    var id = this.id.substring(0,this.id.indexOf("_"));
                    if($.inArray(id,staffIdList)==-1){
                        staffIdList.push(id);
                    }
                }
            });
            $("#selectIds").val(staffIdList);
            $("#setWarningObjectModal").modal("hide");
        });
        $("#saveWarningSetBtn").click(function(){
            var ids = $("#selectIds").val();
            var cpuAlarmValue = $("#cpuUsedRate").val();
            var memoryAlarmValue = $("#memoryUsedRate").val();
            var cpuIsEmail = $("#cpuIsEmail").is(':checked');
            var cpuIsIM = $("#cpuIsIM").is(':checked');
            var memoryIsEmail = $("#memoryIsEmail").is(':checked');
            var memoryIsIM = $("#memoryIsIM").is(':checked');
            var params={
                userIds:ids,
                cpuAlarmValue:cpuAlarmValue,
                memoryAlarmValue:memoryAlarmValue,
                isEmailForCpu:cpuIsEmail,
                isIMForCpu:cpuIsIM,
                isEmailForMemory:memoryIsEmail,
                isIMForMemory:memoryIsIM
            };
            var url=$("#projectContext").val()+"/warning/save_properties";
            $.ajax({
                async: false,
                type: "post",
                dataType: "json",
                data:params,
                url:  url,
                success: function(result) {
                    if (result.ret >= 0 ) {
                        alertPromptMsgDlg("保存成功！",1,getWarningSet);
                    }else {
                        alertPromptMsgDlg(result.msg,3);
                    }

                },
                error: function() {
                    alert('Ajax Error!');
                }
            });
        });
    });

    function getWarningSet(){
        var url=$("#projectContext").val()+"/warning/get_properties";
        $.ajax({
            async: false,
            type: "get",
            dataType: "json",
            url:  url,
            success: function(result) {
                if (result.ret >= 0 ) {
                    if(result.data.isEmailForCpu==true){
                        $("#cpuIsEmail").prop("checked",true);
                    }
                    if(result.data.isIMForCpu==true){
                        $("#cpuIsIM").prop("checked",true);
                    }
                    if(result.data.isEmailForMemory==true){
                        $("#memoryIsEmail").prop("checked",true);
                    }
                    if(result.data.isIMForMemory==true){
                        $("#memoryIsIM").prop("checked",true);
                    }
                    $("#cpuUsedRate").val(result.data.cpuAlarmValue);
                    $("#memoryUsedRate").val(result.data.memoryAlarmValue);
                    $("#selectIds").val(result.data.userIds);
                }

            },
            error: function() {
                alert('Ajax Error!');
            }
        });
    }
</script>
</body>
</html>
