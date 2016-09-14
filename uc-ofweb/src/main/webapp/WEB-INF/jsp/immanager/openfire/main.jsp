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
    <title>system monitor</title>
</head>
<body>
<div class="container-fluid">
    <form class="form-horizontal" role="form" >

    <div class="form-group" style="padding-left:100px; ">
        <div class="panel-body">
            <div id='allPackageDiv' style="height: 400px;width: 100%;padding-right: 100px;padding-bottom: 50px;"></div>
            <div id='iqPackageDiv' style="height: 400px;width: 100%;padding-right: 100px;padding-bottom: 50px;"></div>
            <div id='presencePackageDiv' style="height: 400px;width: 100%;padding-right: 100px;padding-bottom: 50px;"></div>
            <div id='messagePackageDiv' style="height: 400px;width: 100%;padding-right: 100px;"></div>
        </div>
    </div>


    </form>
</div>
<input type="hidden" id="projectContext" value="${pageContext.request.contextPath}">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/echarts.min.js"></script>
<script type="text/javascript">
    var allPackageChart = echarts.init(document.getElementById('allPackageDiv'));
    var iqPackageChart = echarts.init(document.getElementById('iqPackageDiv'));
    var presencePackageChart = echarts.init(document.getElementById('presencePackageDiv'));
    var messagePackageChart = echarts.init(document.getElementById('messagePackageDiv'));
    var send = [];
    var receive = [];
    var xaxis = [];
    var iqSend=[];
    var iqReceive = [];
    var presenceSend=[];
    var presenceReceive=[];
    var messageSend=[];
    var messageReceive=[];
    $(function() {
        drawOpenfireMonitorChart("最近24小时发包情况发送接收统计",xaxis,send,receive,allPackageChart);
        drawOpenfireMonitorChart("最近24小时IQ包情况发送接收统计",xaxis,iqSend,iqReceive,iqPackageChart);
        drawOpenfireMonitorChart("最近24小时Presence包情况发送接收统计",xaxis,presenceSend,presenceReceive,presencePackageChart);
        drawOpenfireMonitorChart("最近24小时Message包情况发送接收统计",xaxis,messageSend,messageReceive,messagePackageChart);
        var date = new Date();
        var startTime = (date.getTime()-24*60*60*1000);
        var endTime = date.getTime();
        getDatas(startTime,endTime);
    });

    function drawOpenfireMonitorChart(text,xaxis,series1,series2,allPackageChart){
        option = {
            title : {
                text: text,
                subtext: '一分钟统计一次'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['发送量','接收量']
            },
            toolbox: {
                show : true,
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
                    type : 'category',
                    data : xaxis
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            dataZoom: [
                {   // 这个dataZoom组件，默认控制x轴。
                    type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                    start: 10,      // 左边在 10% 的位置。
                    end: 60         // 右边在 60% 的位置。
                },
                {   // 这个dataZoom组件，也控制x轴。
                    type: 'inside', // 这个 dataZoom 组件是 inside 型 dataZoom 组件
                    start: 10,      // 左边在 10% 的位置。
                    end: 60         // 右边在 60% 的位置。
                }
            ],
            series : [
                {
                    name:'发送量',
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
                    name:'接收量',
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
            ]
        };
        allPackageChart.setOption(option);
    }

    function getDatas(startTime,endTime){
        var allOptions = allPackageChart.getOption();
        var iqOptions = iqPackageChart.getOption();
        var presenceOptions = presencePackageChart.getOption();
        var messageOptions= messagePackageChart.getOption();
        var url=$("#projectContext").val()+"/of-monitor/monitor?startTime="+startTime+"&endTime="+endTime;

        $.ajax({
            url: url,
            cache: false,
            dataType: "json",
            type: "get",
            async: false,
            error: function (xmlHttpRequest, error) {
                allPackageChart.showLoading();
                iqPackageChart.showLoading();
                presencePackageChart.showLoading();
                messagePackageChart.showLoading();
            },
            success: function (result) {
                if(result.ret>=0 && result.data){
                    send = [];
                    receive=[];
                    xaxis = [];
                    iqSend=[];
                    iqReceive = [];
                    presenceSend=[];
                    presenceReceive=[];
                    messageSend=[];
                    messageReceive=[];
                    temp=result.data;
                    var size = temp.length;
                    for(var i=0;i<size;i++){
                        send.push(temp[i].sendAll);
                        receive.push(temp[i].receiveAll);
                        iqSend.push(temp[i].sendIq);
                        iqReceive.push(temp[i].receiveIq);
                        presenceSend.push(temp[i].sendPresence);
                        presenceReceive.push(temp[i].receivePresence);
                        messageSend.push(temp[i].sendMessage);
                        messageReceive.push(temp[i].receiveMessage);
                        xaxis.push(new Date(temp[i].recordTime).toLocaleTimeString().replace(/^\D*/,''));
                    }
                    allOptions.series[0].data=send;
                    allOptions.series[1].data=receive;
                    allOptions.xAxis[0].data=xaxis;
                    iqOptions.series[0].data=iqSend;
                    iqOptions.series[1].data=iqReceive;
                    iqOptions.xAxis[0].data=xaxis;
                    presenceOptions.series[0].data=presenceSend;
                    presenceOptions.series[1].data=presenceReceive;
                    presenceOptions.xAxis[0].data=xaxis;
                    messageOptions.series[0].data=messageSend;
                    messageOptions.series[1].data=messageReceive;
                    messageOptions.xAxis[0].data=xaxis;
                    allPackageChart.setOption(allOptions);
                    iqPackageChart.setOption(iqOptions);
                    presencePackageChart.setOption(presenceOptions);
                    messagePackageChart.setOption(messageOptions);

                }
            }
        });
    }

</script>
</body>
</html>
