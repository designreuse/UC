<%--
  Created by IntelliJ IDEA.
  User: pzy
  Date: 2016/8/3
  Time: 22:59
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
    <!-- 文件共享统计 -->
    <title>
        <spring:message code="fileshare.title.stat.stat"/>
    </title>
</head>
<body>
<link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<div class="container">
    <fieldset>
        <div class="form-group">
            <!-- 发送时间 -->
            <label class="col-sm-2 control-label" for="beginSendDate"><spring:message code="fileshare.label.stat.senddate"/></label>
            <div class="col-sm-4">
                <input class="form-control form_datetime" value="${beginDate}" readonly id="beginSendDate" name="beginSendDate" type="text" />
            </div>
            <!-- 至 -->
            <label class="col-sm-2 control-label" for="endSendDate"><spring:message code="fileshare.label.stat.to"/></label>
            <div class="col-sm-4">
                <input class="form-control form_datetime" value="${nowDate}" readonly id="endSendDate" name="endSendDate" type="text" />
            </div>
        </div>
        <div class="form-group">
            <!-- 文件类别 -->
            <label class="col-sm-2 control-label" for="fileType"><spring:message code="fileshare.label.stat.filetype"/></label>
            <div class="col-sm-4">
                <select id="fileType" name="fileType" class="form-control">
                    <option value=""><spring:message code="fileshare.label.avatar.sel"/></option>
                    <option value="online"><spring:message code="fileshare.label.stat.onlinefile"/></option>
                    <option value="offline"><spring:message code="fileshare.label.stat.offlinefile"/></option>
                    <option value="group"><spring:message code="fileshare.label.stat.groupfile"/></option>
                    <option value="chat"><spring:message code="fileshare.label.stat.msgfile"/></option>
                    <option value="avatar"><spring:message code="fileshare.label.stat.avatarfile"/></option>
                </select>
            </div>
            <div class="col-sm-4">
                <!-- 当天 本周 本月 -->
                <button type="button" id="curDayBtn" class="btn btn-primary btn-sm"><spring:message code="fileshare.label.stat.curdate"/></button>
                <button type="button" id="curWeekBtn" class="btn btn-primary btn-sm"><spring:message code="fileshare.label.stat.curweek"/></button>
                <button type="button" id="curMonthBtn" class="btn btn-primary btn-sm"><spring:message code="fileshare.label.stat.curmonth"/></button>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-10" style="text-align:right;">
                <!-- 统计 -->
                <button id="queryBtn" type="button" class="btn btn-success btn-sm">
                    <spring:message code="fileshare.btn.stat.stat"/>
                </button>
            </div>
        </div>
    </fieldset>
    <!-- 文件共享统计-->
    <div id="fileshareStatInfo" style="height:400px"></div>

    <!-- echart -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/echart/echarts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/echart/chart/pie.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/echart/chart/funnel.js"></script>
</div>
<script>
    $(function () {
        $(".form_datetime").datetimepicker({
            format: "yyyy-mm-dd",
            minView: "month"
        });
        // 初始化图
        drawServerStatChart();
        $("#queryBtn").bind("click", function(){
            serverStatChart.clear();
            drawServerStatChart();
        });

        $("#curDayBtn").bind("click", function(){
            var nowDay = formatDate(new Date());
            $("#beginSendDate").val(nowDay);
            $("#endSendDate").val(nowDay);
        });
        $("#curWeekBtn").bind("click", function(){
            $("#beginSendDate").val(getWeekDate(0));
            $("#endSendDate").val(getWeekDate(1));
        });
        $("#curMonthBtn").bind("click", function(){
            $("#beginSendDate").val(getMonthDate(0));
            $("#endSendDate").val(getMonthDate(1));
        });
    });
    <!-- 文件共享统计图表 -->
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
                'echarts/chart/pie',
                'echarts/chart/funnel'
            ],
            initEcChart
    )
    var serverStatChart;
    function initEcChart(ec) {
        // 初始化echarts图表
        serverStatChart = ec.init(document.getElementById('fileshareStatInfo'));
    }

    // 画图
    function drawServerStatChart() {
        serverStatChart.showLoading({
            text: '<spring:message code="fileshare.msg.fileserver.loading"/>'
        });
        // 同步执行
        $.ajaxSettings.async = false;
        // 加载数据
        var queryUrl = '<%=request.getContextPath()%>'+'/fileshare/stat/stat';
        var data = {};
        data["beginSendDate"] = $("#beginSendDate").val();
        data["endSendDate"] = $("#endSendDate").val();
        data["fileType"] = $("#fileType").val();

        var statResult = [];
        $.getJSON(queryUrl, data, function (json) {
            statResult = json;
        });
        var option = {
            title : {
                text: '<spring:message code="fileshare.title.stat.stat"/>',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series : [
                {
                    name:'<spring:message code="fileshare.label.stat.filetransfer"/>',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
//                        {value:335, name:'直接访问'},
//                        {value:310, name:'邮件营销'},
//                        {value:234, name:'联盟广告'},
//                        {value:135, name:'视频广告'},
//                        {value:1548, name:'搜索引擎'}
                    ]
                }
            ]
        };
        option.series[0].data=[];
        option.series[0].data = statResult;
        serverStatChart.setOption(option);
        serverStatChart.hideLoading();
    }
    //格式化日期：yyyy-MM-dd
    function formatDate(date) {
        var myyear = date.getFullYear();
        var mymonth = date.getMonth()+1;
        var myweekday = date.getDate();

        if(mymonth < 10){
            mymonth = "0" + mymonth;
        }
        if(myweekday < 10){
            myweekday = "0" + myweekday;
        }
        return (myyear+"-"+mymonth + "-" + myweekday);
    }
    /**
     * 获得本周的开始结束日期
     * @param flag 0取开始 1取结束
     */
    function getWeekDate(flag) {
        var now = new Date();                    //当前日期
        var nowDayOfWeek = now.getDay();         //今天本周的第几天
        var nowDay = now.getDate();              //当前日
        var nowMonth = now.getMonth();           //当前月
        var nowYear = now.getYear();             //当前年
        nowYear += (nowYear < 2000) ? 1900 : 0;  //

        var days;
        if (flag == 0) {
            days = nowDay - nowDayOfWeek;
        } else if (flag == 1) {
            days = nowDay + (6 - nowDayOfWeek);
        }
        var weekDate = new Date(nowYear, nowMonth, days);
        return formatDate(weekDate);
    }
    /**
     * 获得本月的开始日期
     * @param flag 0取开始 1取结束
     */
    function getMonthDate(flag){
        var now = new Date();                    //当前日期
        var nowMonth = now.getMonth();           //当前月
        var nowYear = now.getYear();             //当前年
        nowYear += (nowYear < 2000) ? 1900 : 0;  //

        var mongths;
        if (flag == 0) {
            mongths = 1;
        } else if (flag == 1) {
            mongths = getMonthDays(nowYear, nowMonth);
        }
        var monthDate = new Date(nowYear, nowMonth, mongths);
        return formatDate(monthDate);
    }

    //获得某月的天数
    function getMonthDays(nowYear, myMonth){
        var monthStartDate = new Date(nowYear, myMonth, 1);
        var monthEndDate = new Date(nowYear, myMonth + 1, 1);
        var days = (monthEndDate - monthStartDate)/(1000*60*60*24);
        return days;
    }

</script>
</body>
</html>