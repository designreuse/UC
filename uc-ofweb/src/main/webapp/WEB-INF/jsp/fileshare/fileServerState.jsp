<%--
  Created by IntelliJ IDEA.
  User: yl1179
  Date: 2016/8/3
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
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
    <!-- 文件服务器实时监测 -->
    <title>
        <spring:message code="fileshare.title.fileserver.timemonitor"/>
    </title>
    <!-- css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/style.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap/css/bootstrap-theme.min.css"/>
    <!-- js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
</head>
<body>
<div class="container">
    <!-- 服务器状态-->
    <div id="serverStatInfo" style="height:400px"></div>
    <!-- echart -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/echart/echarts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/echart/chart/line.js"></script>
</div>
<script>
    var curSelectJid="";
    $(function () {
        curSelectJid = '${param.jid}';
        // 初始化数据
        for (var i=0; i<cpuArr.length; i++) {
            cpuArr[i] = 0;
        }
        for (var i=0; i<memArr.length; i++) {
            memArr[i] = 0;
        }
        for (var i=0; i<ioArr.length; i++) {
            ioArr[i] = 0;
        }
        //初始化服务器状态图表
        drawServerStatChart(curSelectJid);
        // 定时刷新
        setInterval(function() {
            drawServerStatChart(curSelectJid);
        }, 1000);
    });
    <!-- 服务器状态图表 -->
    // 路径配置
    require.config({
        paths: {
            echarts: '../../../3rdLibrary/echart/dist'
        }
    });
    // 使用
    require(
            [
                'echarts',
                'echarts/chart/line',
            ],
            initEcChart
    )
    var serverStatChart;
    function initEcChart(ec) {
        // 初始化echarts图表
        serverStatChart = ec.init(document.getElementById('serverStatInfo'));
    }

    // cpu 内存 磁盘IO数据
    var cpuArr = new Array(60);
    var memArr = new Array(60);
    var ioArr = new Array(60);
    function drawServerStatChart(jid) {
        serverStatChart.showLoading({
            text: '<spring:message code="fileshare.msg.fileserver.loading"/>'
        });
        // 同步执行
        $.ajaxSettings.async = false;
        // 加载数据
        var queryUrl = '<%=request.getContextPath()%>/fileshare/manager/getFileServerTimeInfo?jid='+jid;
        $.getJSON(queryUrl, function (json) {
            cpuArr.shift();
            cpuArr.push(json.cpuRate);

            memArr.shift();
            memArr.push(json.memRate);

            ioArr.shift();
            ioArr.push(json.ioRate);
        });
        var option = {
            title : {
                text: '<spring:message code="fileshare.title.fileserver.statemonitor"/>',
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['<spring:message code="fileshare.label.fileserver.cpurate"/>','<spring:message code="fileshare.label.fileserver.memrate"/>','<spring:message code="fileshare.label.fileserver.diskiorate"/>']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : ['60秒','','','','','','','','','','','','','','','','','','','','','','','','','',
                            '','','','','','','','','','','','','','','','','','','','','','','','',
                            '','','','','','','','','','']
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLabel : {
                        formatter: '{value} %'
                    },
                    splitNumber:10,
                    min : 0,
                    max : 100
                }
            ],
            series : [
                {
                    name:'<spring:message code="fileshare.label.fileserver.cpurate"/>',
                    type:'line',
                    data:[],
                    markPoint : {
                        data : [
                            {type : 'max', name: '<spring:message code="fileshare.common.label.maxnum"/>'},
                            {type : 'min', name: '<spring:message code="fileshare.common.label.minnum"/>'}
                        ]
                    }
                },
                {
                    name:'<spring:message code="fileshare.label.fileserver.memrate"/>',
                    type:'line',
                    data:[],
                    markPoint : {
                        data : [
                            {type : 'max', name: '<spring:message code="fileshare.common.label.maxnum"/>'},
                            {type : 'min', name: '<spring:message code="fileshare.common.label.minnum"/>'}
                        ]
                    }
                },
                {
                    name:'<spring:message code="fileshare.label.fileserver.diskiorate"/>',
                    type:'line',
                    data:[],
                    markPoint : {
                        data : [
                            {type : 'max', name: '<spring:message code="fileshare.common.label.maxnum"/>'},
                            {type : 'min', name: '<spring:message code="fileshare.common.label.minnum"/>'}
                        ]
                    }
                }
            ]
        };
        // 为echarts对象加载数据
        option.series[0].data = cpuArr;
        option.series[1].data = memArr;
        option.series[2].data = ioArr;
        serverStatChart.setOption(option);
        serverStatChart.hideLoading();
    }
</script>
</body>
</html>
