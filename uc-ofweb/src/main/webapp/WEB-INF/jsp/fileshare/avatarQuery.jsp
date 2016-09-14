<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
    <!--头像查询-->
    <title>
        <spring:message code="fileshare.title.avatar.query"/>
    </title>
</head>
<body>
<%@include file="common.jsp"%>
<div class="container">
    <form class="form-horizontal" role="form">
        <!--查询条件-->
        <label class="form-label"><spring:message code="fileshare.common.label.query"/></label>
        <fieldset>
            <div class="form-group">
                <!--用户名-->
                <label class="col-sm-2 control-label" for="userName"><spring:message code="fileshare.label.avatar.username"/></label>
                <div class="col-sm-4">
                    <input class="form-control" id="userName" name="userName" type="text" placeholder=""/>
                </div>
                <!--是否使用中-->
                <label class="col-sm-2 control-label" for="isUsing"><spring:message code="fileshare.label.avatar.isusing"/></label>
                <div class="col-sm-4">
                    <!-- 1-是 0 否 -->
                    <select id="isUsing" name="isUsing" class="form-control">
                        <option value=""><spring:message code="fileshare.label.avatar.sel"/></option>
                        <option value="1"><spring:message code="fileshare.label.avatar.yes"/></option>
                        <option value="0"><spring:message code="fileshare.label.avatar.no"/></option>
                    </select>
                </div>
                <div class="col-sm-12" style="text-align:right;">
                    <!--查询-->
                    <button id="queryBtn" type="button" class="btn btn-primary btn-sm">
                        <spring:message code="fileshare.btn.avatar.query"/>
                    </button>
                </div>
            </div>
        </fieldset>
    </form>
    <div id="toolbar" class="fixed-table-toolbar" style="text-align:left">
        <!--批量删除-->
        <button id="remove_selected" class="btn btn-primary removePatch" disabled>
            <spring:message code="fileshare.btn.avatar.batchdel"/>
        </button>
    </div>
    <!--  头像列表-->
    <table id="avatarListTable" data-toggle="avatarTable">
    </table>
</div>
<script>
    var oTable = null;
    var $remove = $('#remove_selected');
    var $table = $('#avatarListTable');
    $(function () {
        //初始化头像列表
        oTable = new TableInit();
        oTable.Init();

        $table.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function () {
            var selectRows = $table.bootstrapTable('getSelections');
            var batchBtnDisabled=!selectRows.length;
            for (var i=0; i<selectRows.length; i++) {
                if (selectRows[i].isUsing) {
                    batchBtnDisabled=true;
                    break;
                }
            }
            $remove.prop('disabled', batchBtnDisabled);
        });
        $remove.click(function () {
            var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
                return row._id;
            });
            deleteAvatars(ids.toString());
        });

        $("#queryBtn").bind("click", function(){
            $('#avatarListTable').bootstrapTable("refresh");
        });
    });

    // 获取头像列表
    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            var queryUrl = '<%=request.getContextPath()%>/fileshare/query/getAvatars';
            this.sessionTableObj = $('#avatarListTable').bootstrapTable({
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
                pageList: [3, 6, 50, 100],    //可供选择的每页的行数（*）
                strictSearch: false,
                clickToSelect: true,        //是否启用点击选中行
                height: 400,            //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                cardView: false,          //是否显示详细视图
                detailView: false,          //是否显示父子表
                showColumns : false,
                columns: [{
                    field: 'avatarcheck',
                    checkbox : true
                }, {
                    field: '_id',
                    visible : false
                }, {
                    field: 'userName',
                    title: '<spring:message code="fileshare.label.avatar.cardid"/>'
                }, {
                    field: 'sendDate',
                    title: '<spring:message code="fileshare.label.avatar.senddate"/>'
                }, {
                    field: 'email',
                    title: '<spring:message code="fileshare.label.avatar.email"/>'
                }, {
                    field: 'mobilePhones',
                    title: '<spring:message code="fileshare.label.avatar.mobile"/>'
                }, {
                    field: 'receiveFileSize',
                    title: '<spring:message code="fileshare.label.avatar.recesize"/>'
                }, {
                    field: 'isUsing',
                    title: '<spring:message code="fileshare.label.avatar.isusing"/>',
                    formatter:function(value,row,index){
                        var isUsingLabel = "";
                        if (row.isUsing == '1') {
                            isUsingLabel = '<spring:message code="fileshare.label.avatar.yes"/>';
                        } else if (row.isUsing == '0') {
                            isUsingLabel = '<spring:message code="fileshare.label.avatar.no"/>';
                        }
                        return isUsingLabel;
                    }
                }, {
                    field: 'avatarUrl',
                    title: '<spring:message code="fileshare.label.avatar"/>',
                    formatter:function(value,row,index){
                        var img = "<img src='"+row.avatarUrl+"' />";
                        return img;
                    }
                }, {
                    field: 'digest',
                    title: '<spring:message code="fileshare.common.label.action"/>',
                    formatter:function(value,row,index){
                        if (!row.isUsing) {
                            return '<button class="btn btn-primary deleteAvatar"><spring:message code="fileshare.common.btn.del"/></button>';
                        } else {
                            return '<button disabled class="btn btn-primary deleteAvatar"><spring:message code="fileshare.common.btn.del"/></button>';
                        }
                    },
                    events: 'operateEvents'
                }]
            });
        };
        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {
                pageSize : params.pageSize,
                pageNumber : params.pageNumber,
                sortOrder : params.sortOrder,
                userName : $("#userName").val(),
                isUsing : $("#isUsing").val()
            };
            return temp;
        };
        return oTableInit;
    };

    window.operateEvents = {
        'click .deleteAvatar': function (e, value, row, index) {
            deleteAvatars(row._id);
        }
    };
    // 批量删除
    function deleteAvatars(ids) {
        $.ajax({
            type: "post",
            data: ids,
            contentType: 'application/json',
            url: '<%=request.getContextPath()%>/fileshare/query/deleteAvatar',
            success: function (data) {
                if (data == '1') {
                    alert('<spring:message code="fileshare.common.msg.delsuccess"/>');
                    $('#avatarListTable').bootstrapTable("refresh");
                    $('#remove_selected').prop('disabled', true);
                } else if (data == '0') {
                    alert('<spring:message code="fileshare.common.msg.delfail"/>');
                }
            }
        });
    }

    // 触发回车查询
    $('.form-control').keypress(function(e) {
        if (e.keyCode == 13) {
            $("#queryBtn").trigger("click");
            e.preventDefault();
        }
    });
</script>
</body>
</html>