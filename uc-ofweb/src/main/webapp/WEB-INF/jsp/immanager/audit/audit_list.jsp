<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: chenkl
  Date: 2016/8/3
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap-daterangepicker-master/daterangepicker.css">
</head>
<body>
<div id="content_middle">
    <%@ include file="../../common/navigation.jsp" %>
</div>
<div  id="content_right">
<div class="container-fluid">
    <div class="row">
        <div class="div-list-table div-list-table-layout">
            <div id="divContent" class="col-sm-12">
                <div class="div-title-bar" style="text-align: left">
                    <label> 操作日志</label>
                    <hr/>
                </div>
                <div class="div-title-bar clearfix">
                    <div class="pull-left">
                        <div id="dateChoseSearch" >
    						<span class="block input-icon input-icon-right" >
    							<input class="form-control datepicker" style="width:350px;"
                                      autocomplete="off" id="dateInput"/>
                                <i class="btn glyphicon glyphicon-calendar fa fa-calendar" ></i>
							</span>
                        </div>
                    </div>
                    <div class="pull-right">
                        <div>
    						<span class="block  input-icon input-icon-right">
    							<input class="form-control" id="aditkeyword" maxlength="128"
                                       title="请输入关键字搜索" placeholder="请输入关键字搜索"
                                       value="" autocomplete="off"/>
								<i class="icon-search btn" id="auditBtnSearch"></i>
							</span>
                        </div>
                    </div>
                </div>

            <div class=" div-scroll-tabel-body">
                    <table  class="table">
                        <thead>
                        <tr>
                        <th width="5%" class="th-color"></th>
                        <th  width="10%" class="th-color">用户名</th>
                        <th  width="20%"  class="th-color">操作模块|菜单</th>
                        <th  width="10%" class="th-color">操作IP</th>
                        <th  width="15%" class="th-color">操作时间</th>
                        <th class="th-color">操作说明</th>
                        </tr>
                        </thead>
                        <tbody id="auditListBody">
                        </tbody>
                    </table>
                </div>
                <div id="auditDiv" class="clearfix pull-right" style="padding-top: 15px;"></div>
            </div>
        </div>
    </div>
</div>
</div>
<input type="hidden" id="startTime">
<input type="hidden" id="endTime">
<input type="hidden" id="total"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/template-debug.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap-daterangepicker-master/moment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap-daterangepicker-master/daterangepicker.js"></script>
<script  type="text/javascript">
    var currentPageNo = 1;
    $(function(){
        setCurrentPageNavigation("systemManageFirstNav","","auditSidebar");
        var locale = {
            "format": 'YYYY-MM-DD HH:mm:ss',
            "separator": "至",
            "applyLabel": "确定",
            "cancelLabel": "取消",
            "fromLabel": "起始时间",
            "toLabel": "结束时间'",
            "customRangeLabel": "自定义",
            "weekLabel": "W",
            "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
            "monthNames": ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            "firstDay": 1,
        };

        goPage(1);
        //初始化日期控件
        $("#dateChoseSearch").daterangepicker({
            arrows:true,
            locale:locale,
            maxDate : moment(), //最大时间
            //dateLimit : {
            //    days : 30
            //}, //起止时间的最大间隔
            showDropdowns : true,
            showWeekNumbers : false, //是否显示第几周
            timePicker : true, //是否显示小时和分钟
            timePickerIncrement : 60, //时间的增量，单位为分钟
            timePicker12Hour : false, //是否使用12小时制来显示时间
            ranges: {
                '今天': [moment().startOf('day'), moment()],
                '昨天': [moment().subtract('days', 1).startOf('day'),moment().subtract('days', 1).endOf('day')],
                '最近7天': [moment().subtract(6, 'days'), moment()],
                '最近30天': [moment().subtract(29, 'days'), moment()],
                '当月': [moment().startOf('month'), moment().endOf('month')],
                '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
            },
            opens : 'right', //日期选择框的弹出位置
            buttonClasses : [ 'btn btn-default' ],
            applyClass : 'btn-small btn-primary blue',
            cancelClass : 'btn-small',
            format : 'YYYY-MM-DD', //控件中from和to 显示的日期格式
            separator : ' to ',
        }, function(start, end, label) {//格式化日期显示框并赋值
            $('#dateInput').attr("value",start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));
        });

        //查询按钮监听事件
        $("#auditBtnSearch").click(function(){
            goPage(1);
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

        //选择时间后触发重新加载的方法
        $("#dateChoseSearch").on('apply.daterangepicker',function(ev, picker){
            $("#startTime").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
            $("#endTime").val(picker.endDate.format('YYYY-MM-DD HH:mm:ss'));
            $("#dateInput").attr("value",picker.startDate.format('YYYY-MM-DD HH:mm:ss')+"-"+picker.endDate.format('YYYY-MM-DD HH:mm:ss'));
            goPage(currentPageNo);
        });
        //搜索框绑定回车函数
        searchInput("aditkeyword", null,  searchFunction);

    });

    function searchFunction(){
        goPage(1);
    }


    function goPage(pageNum){
        var url = $("#projectContext").val()+"/audit/get_list";
        var pageSize = $("#pageSizeSelect").val();
        var param={
            pageNo:pageNum,
            pageSize:pageSize,
            searchKey:$("#aditkeyword").val(),
            startTime:$("#startTime").val(),
            endTime:$("#endTime").val()
        };
        $.ajax({
            url: url,
            cache: false,
            dataType: "json",
            data:param,
            type: "get",
            async: false,
            error: function (xmlHttpRequest, error) {

            },
            success: function (result) {
                if (result.ret >= 0 && result.data) {
                    var pageModelRet = executePageModel(result);
                    var html = template('auditTemp', result);
                    document.getElementById('auditListBody').innerHTML=html;
                    document.getElementById('auditDiv').innerHTML=template('auditPageModel', pageModelRet);
                    currentPageNo = result.attrs.pageNo;
                    $("#pageSizeSelect").val(result.attrs.pageSize);
                    $("#total").val(result.attrs.total);
                }
            }
        });
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

    function executePageModel(result) {
        var totalPages=Math.ceil(result.attrs.total/result.attrs.pageSize);
        var pageArray = getPageNoArray(result.attrs.pageNo, totalPages);
        return {"pageModel": result.attrs,"totalPages": totalPages, "pageArray": pageArray};
    }
</script>
<script id="auditPageModel" type="text/html">
    <%@include file="../../common/pager.template"%>
</script>

<script id="auditTemp" type="text/html">
  <%--  <%@include file=""%>--%>
    {{each data as prop index}}
    <tr class="
			{{if index % 2 ===0}}table-striped-tr-odd
			{{else}}table-striped-tr-even
			{{/if}}">
        <td width="5%" class="text-align-left"><label title="{{index+1}}">{{((attrs.pageNo-1)*attrs.pageSize)+index+1}}</label></td>
        <td width="10%" class="text-align-left"><label title="{{prop.operatorName}}">{{ prop.operatorName}}</label></td>
        <td width="20%" class="text-align-left">
            <label title="{{ prop.operation }}">
                {{ prop.operation }}
            </label>
        </td>
        <td width="10%" class="text-align-left">
        <label title="{{ prop.operatorIP}}">
            {{ prop.operatorIP}}
        </label>
        </td>
        <td width="15%" class="text-align-left">
            <label title="{{ prop.operationTime}}">
                {{ prop.operationTime}}
            </label>
        </td>
        <td class="text-align-left">
            <label title="{{ prop.resultDetail}}">
                {{ prop.resultDetail}}
            </label>
        </td>
    </tr>
    {{/each}}


</script>
</body>
</html>
