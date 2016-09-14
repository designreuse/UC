<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String contextPath = request.getContextPath();
%>
<script type="text/javascript">
    //容器上下文
    <%--var contextPath= "${pageContext.request.contextPath}";--%>
    <%--//刷新Session，在浏览器打开期间，不让其超时--%>
    <%--function freshSession(){--%>
        <%--//ajax请求--%>
        <%--$.get(contextPath+"/session/freshSession",function(data,status){--%>
            <%--if(status != "success"){--%>
                <%--alert("Service Error");--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>
    <%--//与服务器session超时时间相差10秒--%>
    <%--var interval = (${sessionScope.freshInterval}-10)*1000;--%>
    <%--//刷新Session--%>
    <%--var session_timer = window.setInterval(freshSession,interval);--%>
</script>
