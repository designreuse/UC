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
    <!-- 文件服务器管理 -->
    <title>
        <spring:message code="fileshare.title.fileserver.manager"/>
    </title>
</head>
<body>
<%@include file="common.jsp"%>
<div class="container">
    <!-- 文件服务器列表 -->
    <label class="form-label"><spring:message code="fileshare.label.fileserver.list"/></label>
    <table id="fileServerTable" data-toggle="serverTable"
           data-url="<%=request.getContextPath()%>/fileshare/manager/getFileServerList"
           data-click-to-select="true"
           data-row-style="rowStyle"
           data-query-params="queryParams"
           data-pagination="false"
           data-search="false"
           data-height="150">
        <thead>
        <tr>
            <th data-field="jid">JID</th> <!-- JID -->
            <th data-field="host"><spring:message code="fileshare.label.fileserver.host"/></th> <!-- 主机地址 -->
            <th data-field="port"><spring:message code="fileshare.label.fileserver.port"/></th> <!-- 端口 -->
            <th data-field="cpuCombined"><spring:message code="fileshare.label.fileserver.cpurate"/></th> <!-- cpu使用率 -->
            <th data-field="serverMemRate"><spring:message code="fileshare.label.fileserver.memrate"/></th> <!-- 内存使用率 -->
            <th data-field="diskIoRate"><spring:message code="fileshare.label.fileserver.diskiorate"/></th> <!-- 磁盘IO使用率 -->
            <th data-field="diskUsage"><spring:message code="fileshare.label.fileserver.diskrate"/></th> <!-- 磁盘使用率 -->
            <th data-field="diskUseInfo"><spring:message code="fileshare.label.fileserver.diskusedinfo"/></th> <!-- 磁盘使用情况(可用/全部) -->
            <th data-field="connNum"><spring:message code="fileshare.label.fileserver.curconnnum"/></th> <!-- 当前连接数 -->
            <!-- 资源监视 -->
            <th data-field="queryNowState" data-formatter="nowStateFormatter" data-events="queryNowStateEvents">
                <spring:message code="fileshare.label.fileserver.resoucemonitor"/>
            </th>
        </tr>
        </thead>
    </table>

    <!-- 服务器状态-->
    <div id="serverStatInfo" style="height:300px"></div>
    <!-- echart -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/echart/echarts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/echart/chart/line.js"></script>

    <!-- 会话列表-->
    <label class="form-label"><spring:message code="fileshare.label.fileserver.sessionlist"/></label>
    <table id="sessionListTable" data-toggle="sessionTable">
    </table>
</div>
<script>
    function rowStyle(row, index) {return {};}
    function queryParams() {return {};}
    function nowStateFormatter(value, row, index) {
        return [
            '<button class="btn btn-xs resend"><spring:message code="fileshare.btn.fileserver.get"/></button>'
        ].join('');
    }
    // 查看实时资源监测
    window.queryNowStateEvents = {
        'click .resend': function (e, value, row, index) {
            var jid = row.jid;
            var queryUrl = "<%=request.getContextPath()%>/fileshare/manager/initTime?jid="+jid;
            var iTop = (window.screen.availHeight-400)/2; //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-600)/2; //获得窗口的水平位置;
            var windowVariables="width=800,height=400,resizable=no,scrollbars=yes,status=no,toolbar=no,menubar=no,location=no";
            windowVariables = windowVariables+",top="+iTop+",left="+iLeft;
            window.open(queryUrl,"fileServerMonitor", windowVariables);
        }
    };
    var $fileServerTable = $('#fileServerTable').bootstrapTable({
        onClickRow: function (item, $element) {
            var jid = item.jid;
            var queryUrl = '<%=request.getContextPath()%>/fileshare/manager/getSessionList?jid='+jid;
            // 刷新会话列表
            oTable.sessionTableObj.bootstrapTable("changeUrlAndRresh",queryUrl);
            // 刷新服务器状态列表
            drawServerStatChart(jid);
            curSelectJid = jid;
            return false;
        }
    });

    //当前选择的jid
    var curSelectJid = "";

    var oTable = null;
    $(function () {
        //初始化会话列表
        oTable = new TableInit();
        oTable.Init("");
        //初始化服务器状态图表
        drawServerStatChart("");
        // 定时刷新
        setInterval(function() {
            $('#fileServerTable').bootstrapTable("refresh");
            if (curSelectJid != "") {
                // 刷新会话列表
                oTable.sessionTableObj.bootstrapTable("refresh");
            }
        }, 10000);
        setInterval(function() {
            if (curSelectJid != "") {
                // 刷新服务器状态列表
                drawServerStatChart(curSelectJid);
            }
        }, 60000*5);
    });

    // 获取会话列表
    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function (jid) {
            var queryUrl = '<%=request.getContextPath()%>/fileshare/manager/getSessionList?jid='+jid;
            this.sessionTableObj = $('#sessionListTable').bootstrapTable({
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
                strictSearch: true,
                clickToSelect: true,        //是否启用点击选中行
                height: 300,            //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id",           //每一行的唯一标识，一般为主键列
                cardView: false,          //是否显示详细视图
                detailView: false,          //是否显示父子表
                columns: [{
                    field: 'id',
                    title: 'ID'
                }, {
                    field: 'senddate',
                    title: '<spring:message code="fileshare.label.fileserver.senddate"/>'
                }, {
                    field: 'clientSocketAddress',
                    title: '<spring:message code="fileshare.label.fileserver.clientaddress"/>'
                }, {
                    field: 'filename',
                    title: '<spring:message code="fileshare.label.fileserver.filename"/>'
                }, {
                    field: 'size',
                    title: '<spring:message code="fileshare.label.fileserver.size"/>'
                }, {
                    field: 'sender',
                    title: '<spring:message code="fileshare.label.fileserver.senduser"/>'
                }, {
                    field: 'receiver',
                    title: '<spring:message code="fileshare.label.fileserver.receuser"/>'

                }, {
                    field: 'digest',
                    visible: false
                }, {
                    field: 'operation',
                    title: '<spring:message code="fileshare.common.label.action"/>',
                    formatter:function(value,row,index){
                        return '<button class="btn btn-primary closesession"><spring:message code="fileshare.common.btn.close"/></button>';
                    },
                    events: 'operateEvents'
                }]
            });
        };
        //得到查询的参数
        oTableInit.queryParams = function (params) {
            return params;
        };
        return oTableInit;
    };

    window.operateEvents = {
        'click .closesession': function (e, value, row, index) {
            if (confirm('<spring:message code="fileshare.msg.fileserver.confirmclose"/>')) {
                $.ajax({
                    type: "post",
                    data: JSON.stringify(row),
                    contentType: 'application/json',
                    url: '<%=request.getContextPath()%>/fileshare/manager/closeSession?jid='+curSelectJid,
                    success: function (data) {
                        if (data="1") {
                            alert('<spring:message code="fileshare.msg.fileserver.closesuccess"/>');
                            $('#sessionListTable').bootstrapTable('refresh');
                        } else {
                            alert('<spring:message code="fileshare.msg.fileserver.closefail"/>');
                        }
                    }
                });
            }
        }
    };

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
                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
            ],
            initEcChart
    )
    var serverStatChart;
    function initEcChart(ec) {
        // 初始化echarts图表
        serverStatChart = ec.init(document.getElementById('serverStatInfo'));
    }
    function drawServerStatChart(jid) {
        serverStatChart.showLoading({
            text: '<spring:message code="fileshare.msg.fileserver.loading"/>'
        });
        // 同步执行
        $.ajaxSettings.async = false;
        // 加载数据
        var queryUrl = '<%=request.getContextPath()%>/fileshare/manager/getServerStatInfo?jid='+jid;
        var values=[];
        $.getJSON(queryUrl, function (json) {
            values = json;
        });
        var option = {
            title : {
                text: jid+':<spring:message code="fileshare.title.fileserver.statechange"/>',
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
                    data : ['最近1分钟',
                        '5分钟',
                        '10分钟',
                        '15分钟',
                        '20分钟',
                        '25分钟',
                        '30分钟',
                        '35分钟',
                        '40分钟',
                        '45分钟',
                        '50分钟',
                        '55分钟',
                        '60分钟']
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
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: '<spring:message code="fileshare.common.label.avgnum"/>'}
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
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: '<spring:message code="fileshare.common.label.avgnum"/>'}
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
                    },
                    markLine : {
                        data : [
                            {type : 'average', name: '<spring:message code="fileshare.common.label.avgnum"/>'}
                        ]
                    }
                }
            ]
        };
        // 为echarts对象加载数据
        option.series[0].data = values[0].data;
        option.series[1].data = values[1].data;
        option.series[2].data = values[2].data;
        serverStatChart.setOption(option);
        serverStatChart.hideLoading();
    }
</script>
</body>
</html>