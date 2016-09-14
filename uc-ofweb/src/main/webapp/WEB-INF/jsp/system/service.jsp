<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isELIgnored="false"%>
<%
    response.setHeader("Pragma", "No-Cache");
    response.setHeader("Cache-Control", "No-Cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/common.css">
</head>
<body>
<%@include file="../fileshare/common.jsp"%>
<div id="content_middle">
    <%@ include file="../common/navigation.jsp" %>
</div>
<div  id="content_right">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <!-- 系统服务 -->
                <div class="div-title-bar">
                    <spring:message code="system.service.titlename"/>
                    <hr style="margin-top: 10px;"/>
                </div>
                <span class="block  input-icon input-icon-right input-max-width">
                        <input class="form-control  input-uc-yealink" id="defaultName" name="defaultName"
                               title="<spring:message code="system.service.search"/>" placeholder="<spring:message code="system.service.search"/>"
                               value="" autocomplete="off"/>
                        <i class="icon-search btn" id="sessionBtnSearch" onclick="search()"></i>
                    </span>
                <!--  服务列表-->
                <br>
                <table id="serviceListTable" data-toggle="serviceTable">
                </table>
            </div>
        </div>
    </div>
    <!--服务操作滚动条  -->
    <div class="modal fade" id="progressBarModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true" style="top:200px" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content" id="progressBarContent" style="height: 70px">
                <div class="modal-body" style="height: 30px">
                    <div class="progress">
                        <div class="progress-bar" role="progressbar" aria-valuenow="60"
                             aria-valuemin="0" aria-valuemax="100" style="width: 20%;">
                        </div>
                    </div>
                </div>
                <div id="progressBarFooter" class="modal-footer" style="text-align:center;color:forestgreen">
                    <spring:message code="system.service.opting"/>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var oTable = null;
    var $table = $('#serviceListTable');
    $(function () {
        setCurrentPageNavigation("systemManageFirstNav","systemSidebar","");
        //初始化服务列表
        oTable = new TableInit();
        oTable.Init();
    });

    // 获取服务列表
    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            var queryUrl = '<%=request.getContextPath()%>/system/service/get';
            this.sessionTableObj = $('#serviceListTable').bootstrapTable({
                url: queryUrl,
                method: 'get',
                striped: true, //是否显示行间隔色
                cache: false,    //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,          //是否显示分页（*）
                sortable: false,           //是否启用排序
                sortOrder: "desc",          //排序方式
                queryParams: oTableInit.queryParams, //传递参数（*）
                sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,            //初始化加载第一页，默认第一页
                pageSize: 10,            //每页的记录行数（*）
                pageList: [10, 20, 50, 100],    //可供选择的每页的行数（*）
                strictSearch: false,
                clickToSelect: true,        //是否启用点击选中行
                height: 400,            //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                cardView: false,          //是否显示详细视图
                detailView: false,          //是否显示父子表
                showColumns : false,
                columns: [{
                    field: 'serviceCheck',
                    checkbox : true
                }, {
                    field: '_id',
                    visible : false
                }, {
                    field: 'serviceName',
                    title: '<spring:message code="system.service.name"/>'
                }, {
                    field: 'host',
                    visible : false
                }, {
                    field: 'port',
                    visible : false
                }, {
                    field: 'flag',
                    visible : false
                }, {
                    field: 'dir',
                    visible : false
                }, {
                    field: 'status',
                    title: '<spring:message code="system.service.status"/>',
                    formatter:function(value,row,index){
                        if (value == '0') {
                            return '<spring:message code="system.service.noservice"/>';
                        } else if (value == '1') {
                            return '<spring:message code="system.service.yesservice"/>';
                        }
                    }
                }, {
                    field: 'operation',
                    width: '222',
                    title: '<spring:message code="fileshare.common.label.action"/>',
                    formatter:function(value,row,index){
                        var img = "";
                        var param = '"'+row.flag+'","'+row.dir+'","'+row.host+'","'+row.port+'"';
                        if (row.status == '0') {
                            img = "<img onclick='startService("+param+")' src='<%=request.getContextPath()%>/images/service/startYL.png' height='38' width='38' title='<spring:message code="system.service.start"/>' />";
                            img=img+"&nbsp;&nbsp;&nbsp;<img onclick='refreshStatus()' src='<%=request.getContextPath()%>/images/service/refreshYL.png' height='38' width='38' title='<spring:message code="system.service.refresh"/>'/>";
                        } else if (row.status == '1') {
                            img=img+"<img onclick='restartService("+param+")' src='<%=request.getContextPath()%>/images/service/restartYL.png' height='38' width='38' title='<spring:message code="system.service.restart"/>'/>";
                            img=img+"&nbsp;&nbsp;&nbsp;<img onclick='stopService("+param+")' src='<%=request.getContextPath()%>/images/service/stopYL.png' height='38' width='38' title='<spring:message code="system.service.stop"/>'/>";
                        }
                        return img;
                    }
                }]
            });
        };
        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {
                pageSize : params.pageSize,
                pageNumber : params.pageNumber,
                sortOrder : params.sortOrder,
                defaultName : encodeURI($("#defaultName").val())
            };
            return temp;
        };
        return oTableInit;
    };

    function startService(flag, dir, host, port) {
        bootbox.confirm('<spring:message code="system.service.confirmstart"/>', function(msg){
            if(msg) {
                operateService(flag, dir, host, port, 1);
            }
        });
    }

    function restartService(flag, dir, host, port) {
        bootbox.confirm('<spring:message code="system.service.confirmrestart"/>', function(msg){
            if(msg) {
                operateService(flag, dir, host, port, 2);
            }
        });
    }

    function stopService(flag, dir, host, port) {
        bootbox.confirm('<spring:message code="system.service.confirmstop"/>', function(msg){
            if(msg) {
                operateService(flag, dir, host, port, 3);
            }
        });
    }

    function operateService(flag, dir, host, port, opt) {
        var url = '<%=request.getContextPath()%>/system/service/operate';
        var param = "flag="+flag+"&dir="+dir+"&opt="+opt+"&host="+host+"&port="+port;
        $('.progress-bar').css('width','20%');
        $("#progressBarModal").modal('show');
        //假进度条
        var count=12;
        var i=1;
        var progressTotal=20;
        var barCalc = setInterval(function(){
            if (i<=count-7) {
                progressTotal = progressTotal+10;
            } else if (i<=count-4) {
                progressTotal = progressTotal+5;
            } else if (i<=count) {
                progressTotal = progressTotal+3;
            }
            $('.progress-bar').css('width',progressTotal+'%');
            i++;
        }, 1500);
        $.get(url, param, function(data){
            $("#progressBarModal").modal('hide');
            clearInterval(barCalc);
            alert(data);
            refreshStatus();
        });
    }

    function search() {
        $('#serviceListTable').bootstrapTable("refresh");
    }

    function refreshStatus() {
        search();
    }

    // 触发回车查询
    $('.form-control').keypress(function(e) {
        if (e.keyCode == 13) {
            $('#serviceListTable').bootstrapTable("refresh");
            e.preventDefault();
        }
    });

</script>
</body>
</html>