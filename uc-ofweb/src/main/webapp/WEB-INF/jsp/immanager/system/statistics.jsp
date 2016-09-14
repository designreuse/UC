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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap-daterangepicker-master/daterangepicker.css">
    <style type="text/css">
        .breadcrumb > li + li:before {
            color: #CCCCCC;
            content:"               ";
            padding: 0 5px;
        }
        .breadcrumb > li.active > a {
            color: #30b383;
        }
        .breadcrumb {
            padding: 8px 15px;
           margin-bottom: 10px;
            list-style: none;
            background-color: #FFFFFF;
            border-radius: 0px;
        }

    </style>

</head>

<body>
<div id="content_middle">
    <%@ include file="../../common/navigation.jsp" %>
</div>
<div  id="content_right">
<div class="container-fluid">
    <div class="div-title-bar">
        服务器信息和状态
        <hr/>
    </div>
    <form class="form-horizontal" role="form" >
        <div class="form-group">
            <div class="panel-body">
                <i class="glyphicon glyphicon-calendar fa fa-calendar" ></i>
                <label>系统时间：</label><label id="systemDate"></label>  &nbsp; &nbsp; | &nbsp;&nbsp;
                <label>运行时间：</label><label id="runTime"></label>
                <label class="pull-right">版本号：V1.0.0.0</label>
            </div>
        </div>
    <div class="form-group panel panel-default" style="margin-left: 1px; margin-right: 1px;">
        <div class="panel-body">
            <div id="divContent" class="col-sm-12">
                <div class=" clearfix">
                    <div class="pull-left">
                        <div class="div-title-form-group" >
                          <label style="font-size: 15px;font-weight: 600"> 服务器性能</label>
                        </div>
                    </div>
                    <div class="pull-right">
                        <div class="div-title-form-group" id="dateChoseSearch" >
                            <span class="block input-icon input-icon-right" >
                                <input class="form-control datepicker" style="width:350px;"
                                       autocomplete="off" id="dateInput"/>
                                <i class="btn glyphicon glyphicon-calendar fa fa-calendar" ></i>
                            </span>
                        </div>
                    </div>
                    <div class="pull-right">
                        <div class="div-title-form-group">
                            <ol  class="breadcrumb" id="dateChoose">
                            <li class="active" id="today"><a style="cursor:pointer">今天</a></li>
                            <li><a id="yesterday" style="cursor:pointer">昨天</a></li>
                            <li><a id="lastweek" style="cursor:pointer">最近7天</a></li>
                            <li><a id="lastMouth" style="cursor:pointer">最近30天</a></li>
                        </ol >
                        </div>
                    </div>
                </div>
                </div>
            </div>
            <div id='cpuAndMomeryDiv' style="height: 500px;width: 1550px;"></div>
        </div>


    </form>
</div>
</div>
<input type="hidden" id="projectContext" value="${pageContext.request.contextPath}">
<input type="hidden" id="startTime">
<input type="hidden" id="endTime">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/echarts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap-daterangepicker-master/moment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap-daterangepicker-master/daterangepicker.js"></script>

<script type="text/javascript">
    getServerRunTime();
    getSystemDate();
    var chart = echarts.init(document.getElementById('cpuAndMomeryDiv'));
    var cpu = [];
    var memory = [];
    var xaxis = [];
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

    $(function() {
        setCurrentPageNavigation("systemManageFirstNav","","statisticsSidebar");
        drawOpenfireMonitorChart("",xaxis,cpu,memory,chart);
        var startTime = moment().startOf('day');
        var endTime =  moment();
        getDatas(startTime.format('YYYY-MM-DD HH:mm:ss'),endTime.format('YYYY-MM-DD HH:mm:ss'));

        //初始化日期控件
        $("#dateChoseSearch").daterangepicker({
            arrows:true,
            locale:locale,
            maxDate : moment(), //最大时间
            dateLimit : {
                days : 30
            }, //起止时间的最大间隔
            showDropdowns : true,
            showWeekNumbers : false, //是否显示第几周
            timePicker : true, //是否显示小时和分钟
            timePickerIncrement : 1, //时间的增量，单位为分钟
            timePicker12Hour : false, //是否使用12小时制来显示时间
            buttonClasses : [ 'btn btn-default' ],
            applyClass : 'btn-small btn-primary blue',
            cancelClass : 'btn-small',
            format : 'YYYY-MM-DD', //控件中from和to 显示的日期格式
        }, function(start, end, label) {//格式化日期显示框并赋值
            $('#dateInput').attr("value",start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));
        });

        $("#dateChoseSearch").on('apply.daterangepicker',function(ev, picker){
            $("#startTime").val(picker.startDate.format('YYYY-MM-DD HH:mm:ss'));
            $("#endTime").val(picker.endDate.format('YYYY-MM-DD HH:mm:ss'));
            $("#dateInput").attr("value",picker.startDate.format('YYYY-MM-DD HH:mm:ss')+"-"+picker.endDate.format('YYYY-MM-DD HH:mm:ss'));
            getDatas(picker.startDate.format('YYYY-MM-DD HH:mm:ss'),picker.endDate.format('YYYY-MM-DD HH:mm:ss'));
        });

        $("#today").click(function () {
            var start = moment().startOf('day');
            getDatas(start.format('YYYY-MM-DD HH:mm:ss'),moment().format('YYYY-MM-DD HH:mm:ss'));
        });
        $("#yesterday").click(function () {
            var start = moment().subtract('days', 1).startOf('day');
            var end = moment().subtract('days', 1).endOf('day');
            getDatas(start.format('YYYY-MM-DD HH:mm:ss'),end.format('YYYY-MM-DD HH:mm:ss'));
        });
        $("#lastweek").click(function () {
            var start = moment().subtract(6, 'days');
            var end = moment();
            getDatas(start.format('YYYY-MM-DD HH:mm:ss'),end.format('YYYY-MM-DD HH:mm:ss'));
        });
        $("#lastMouth").click(function () {
            var start = moment().subtract(29, 'days');
            var end = moment();
            getDatas(start.format('YYYY-MM-DD HH:mm:ss'),end.format('YYYY-MM-DD HH:mm:ss'));
        });

        $("#dateChoose li").click(function(){
            $("#dateChoose li").each(function () {
                $(this).removeClass("active");
            });
            $(this).addClass("active");
        })
    });


    function drawOpenfireMonitorChart(text,xaxis,series1,series2,allPackageChart){
        option = {
            title : {
                text: text,
                subtext:""
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['cpu','memory']
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    name : "时间",
                    type : 'category',
                    data : xaxis
                }
            ],
            yAxis : [
                {
                    name : "使用率（%）",
                    type : 'value'
                }
            ],
            dataZoom: [
                {   // 这个dataZoom组件，默认控制x轴。
                    type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                    start: 0,      // 左边在 10% 的位置。
                    end: 100         // 右边在 60% 的位置。
                },
                {   // 这个dataZoom组件，也控制x轴。
                    type: 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
                    start: 0,      // 左边在 10% 的位置。
                    end: 100         // 右边在 60% 的位置。
                }
            ],
            series : [
                {
                    name:'cpu',
                    type:'line',
                    data:series1,
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'},
                            {type : 'min', name: '最小值'}
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: '平均值'}
                        ]
                    }
                },
                {
                    name:'memory',
                    type:'line',
                    data:series2,
                    markPoint : {
                        data : [
                            {name : '最大值',type : 'max'},
                            {name : '最小值',type : 'min' }
                        ]
                    },
                    markLine : {
                        data : [
                            {type : 'average', name : '平均值'}
                        ]
                    }
                }
            ],
            color:[
                '#30b383','#f6d675'
            ]
        };
        allPackageChart.setOption(option);
    }

    function getDatas(startTime,endTime){
        var options = chart.getOption();
        var url=$("#projectContext").val()+"/system/infos";
        var param={
            startTime:startTime,
            endTime:endTime
        }
        $.ajax({
            url: url,
            cache: false,
            dataType: "json",
            type: "get",
            data:param,
            async: false,
            error: function (xmlHttpRequest, error) {
                chart.showLoading();
            },
            success: function (result) {
                if(result.ret>=0 && result.data){
                    cpu = [];
                    memory=[];
                    xaxis = [];
                    temp=result.data;
                    var size = temp.length;
                    for(var i=0;i<size;i++){
                        var cpuRate=String(Math.round(temp[i].cpuRate*100*100)/100);
                        var memoryRate = String(Math.round(temp[i].memoryRate*100)/100);
                        cpu.push(cpuRate);
                        memory.push(memoryRate);
                        xaxis.push(new Date(temp[i].time).Format("yyyy-MM-dd hh:mm:ss"));
                    }
                    options.series[0].data=cpu;
                    options.series[1].data=memory;
                    options.xAxis[0].data=xaxis;
                    chart.setOption(options);

                }
            }
        });
    }
    /**
     * 获取服务器运行时间
     */
    function getServerRunTime(){
        var url=$("#projectContext").val()+"/server/properties";
        $.ajax({
            url: url,
            cache: false,
            dataType: "json",
            type: "get",
            async: false,
            error: function (xmlHttpRequest, error) {

            },
            success: function (result) {
                if (result.ret >= 0 && result.data) {
                    var temp = result.data;
                    $("#runTime").text(temp.uptimeDisplay);
                }
            }
        });
    }

    function getSystemDate(){

        var url=$("#projectContext").val()+"/locale/get_locale";
        $.ajax({
            url: url,
            cache: false,
            dataType: "json",
            type: "get",
            async: false,
            error: function (xmlHttpRequest, error) {
                alert(error);
            },
            success: function (result) {
                if (result.ret >= 0 && result.data) {
                    $("#systemDate").text(result.data.systemTime);
                }
            }
        });
    }

</script>
</body>
</html>
