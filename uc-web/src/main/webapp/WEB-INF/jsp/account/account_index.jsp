<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>账号管理</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/3rdLibrary/font-awesome/css/font-awesome.min.css">
</head>
<body>
<input type="hidden" value="${projectId}" id="projectId">

<div class="container-fluid div-nav-left-page">
    <div class="row">
        <div class="col-sm-8 col-sm-offset-2">
            <div class="col-sm-1">
                <i class="icon-mobile-phone icon-4x"></i>
            </div>
            <div class="col-sm-11">
                <p>
                    <span>手机：${bindMobilePhone}</span>
                    <a href="#" id="bind-mobile">[绑定]</a>
                </p>
                <p class="bind-tips bind-tips-detial">· 绑定手机吧</p>
                <p class="bind-tips bind-tips-detial">· 请勿随意泄露手机号，以防不法分子利用造成个人损失</p>
            </div>

            <div class="col-sm-1">
                <i class="icon-envelope icon-2x"></i>
            </div>
            <div class="col-sm-11">
                <p>
                    <span>邮箱：${bindEmail}</span>
                    <a href="#" id="bind-email">[绑定]</a>
                </p>
                <p class="bind-tips bind-tips-detial">· 绑定邮箱吧</p>
                <p class="bind-tips bind-tips-detial">· 请勿随意泄露邮箱，以防不法分子利用造成个人损失</p>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="bindModel" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:450px; height:500px;">
        <div class="modal-content">
        </div>
    </div>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/account/account.js"></script>
<div id="script"></div>
<script>
    $(document).ready(function () {
        initAccountBind();
    });
</script>
</body>
</html>