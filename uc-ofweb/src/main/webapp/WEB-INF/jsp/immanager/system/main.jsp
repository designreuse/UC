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
            <div id='cpuGaugeDiv' style="height: 400px;width: 400px;" class="pull-left"></div>
            <div id='cpuDiv' style="height: 400px;width: 800px;padding-right: 100px;" class="pull-right"></div>
        </div>
    </div>
    <div class="form-group" style="padding-left:100px; ">
        <div class="panel-body">
            <div id='memoryGaugeDiv' style="height:400px;width:400px;"  class="pull-left"></div>
            <div id='memoryDiv' style="height: 400px;width: 800px;padding-right: 100px;" class="pull-right"></div>
        </div>
    </div>

    <div class="panel-body">
        <div class="form-group" style="padding-left: 100px;">
            <label style="width: 150px;"><spring:message code="system.monitor.total.memory"  text="system.monitor.total.memory"/></label> <input  id="totalMemory" readonly/>
            <label style="padding-left: 100px;width: 250px;"><spring:message code="system.monitor.used.memory" text="system.monitor.used.memory"/></label> <input  id="usedMemory" readonly/>
            </div>
        <div class="form-group" style="padding-left: 100px;">
            <label style="width: 150px;"><spring:message code="system.monitor.jvm.total.memory" text="system.monitor.jvm.total.memory"/></label> <input  id="jvmTotalMemory" readonly/>
            <label style="padding-left: 100px;width: 250px;"><spring:message code="system.monitor.jvm.used.memory" text="system.monitor.jvm.used.memory"/></label> <input  id="jvmUsedMemory" readonly/>
        </div>
        <div class="form-group" style="padding-left: 100px;">
            <label style="width: 150px;"><spring:message code="system.monitor.cpu.mhz" text="system.monitor.cpu.mhz"/></label> <input id="cpuMhz" readonly>
            <label style="padding-left: 100px;width: 250px;"><spring:message code="system.monitor.cpu.vender" text="system.monitor.cpu.vender"/></label> <input  id="cpuVender" readonly/>
        </div>
    </div>
    </form>
</div>
<input type="hidden" id="projectContext" value="${pageContext.request.contextPath}">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/system.js"></script>
<script type="text/javascript">
    $(function() {
        systemInit();
    });

</script>
</body>
</html>
