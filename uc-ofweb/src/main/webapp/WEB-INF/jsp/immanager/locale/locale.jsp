<%--
  Created by IntelliJ IDEA.
  User: chenkl
  Date: 2016/8/8
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/my97DatePicker/skin/WdatePicker.css">
    <style type="text/css">

    </style>
</head>
<body>
<div id="content_middle">
    <%@ include file="../../common/navigation.jsp" %>
</div>
<div  id="content_right">
<div class="container-fluid">
    <div class="row">
        <div  class="col-sm-12">
            <div class="div-title-bar">
                   <spring:message code="locale.set.title" text="locale.set.title"/>
                <hr style="margin-top: 20px;"/>
            </div>
            <div class="col-sm-5" id="divForm">
                <form class="form-horizontal" role="form" autocomplete="off">
                    <div  class="form-group">
                        <label>
                            <spring:message code="locale.system.time" text="locale.system.time"/>
                        </label>
                        <input class="form-control col-sm-2" id="systemTime" tabindex="4" readonly="readonly">
                        </input>
                    </div>
                    <div  class="form-group" id="divTimeServerDomain">
                        <label for="timeServerDomain">
                            <spring:message code="locale.time.domain" text="locale.time.domain"/>
                        </label>

                        <input class="form-control" id="timeServerDomain" tabindex="4" value="time.windows.com">
                        </input>

                        <label id="errorTimeServerDomain" class="control-label"></label>

                    </div>
                    <div  class="form-group ">
                        <label>
                            <spring:message code="locale.language.choose" text="locale.language.choose"/>
                        </label>

                        <select class="form-control" id="languageSelect" tabindex="4" >

                                <option value="zh_CN">
                                    中文
                                </option>
                                <option value="en">
                                    English
                                 </option>

                        </select>
                    </div>
                    <div class="form-group">
                        <label >
                            <spring:message code="locale.timezone.choose" text="locale.timezone.choose"/>
                        </label>

                        <select class="form-control" id="timezonesSelect" tabindex="4" >

                        </select>

                    </div>
                    <div  class="form-group">
                        <label >
                            <spring:message code="locale.dst.choose" text="locale.dst.choose"/>
                        </label>

                        <select class="form-control" id="dst" tabindex="4" >

                            <option value="true">
                                启动
                            </option>
                            <option value="false">
                                禁止
                            </option>

                        </select>

                    </div>
                    <div  class="form-group">
                        <label>
                            时间获取方式
                        </label>
                        </div>
                    <div class="form-group">

                        <input type="radio"  name="1" id="isSync"/>
                        自动与Internet时间同步

                    </div>
                    <div  class="form-group">
                        <input type="radio" name="1" id="isSet"/>
                        手动设置时间
                    </div>

                    <div class="form-group" id="dateTimeChoseDiv" style="width: 50%" hidden="hidden">
                            <input class="form-control Wdate col-sm-4 " style="height: 35px;"
                                   autocomplete="off" id="dateTimeInput"/>
                    </div>

                    <div class="form-group col-sm-3" style="padding-top: 20px;padding-left: 0px;">
                        <button type="button" class="btn btn-success btn-loading btn-block" id="saveLocaleBtn" data-loading-text="<spring:message code="button.loading.text" text="button.loading.text"/>">
                            <spring:message code="system.common.button.save" text="system.common.button.save"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
    </div>
<div class="modal fade" id="setLinuxRootModal" tabindex="-1" role="dialog"
     aria-labelledby="linuxModalLabel" aria-hidden="true" style="top:200px">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" style="text-align:center" id="myModalLabel">系统权限验证</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form" autocomplete="off">
                    <div  class="form-group">
                        <label class="control-label col-sm-2">
                           用户名：
                        </label>
                        <div class="col-sm-6">
                            <input class="form-control" id="linuxUsername" tabindex="4">
                            </input>
                        </div>
                    </div>
                    <div  class="form-group">
                        <label class="control-label col-sm-2">
                            密码：
                        </label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="linuxPassword" tabindex="4">
                            </input>
                        </div>
                    </div>
                    <div class="form-group form-footer-div">
                        <div class="col-sm-offset-2 col-sm-6">
                            <div class="col-sm-6 row pull-left">
                                <button type="button" class="btn btn-success btn-loading btn-block" id="saveLinuxBtn" data-loading-text="<spring:message code="button.loading.text" text="button.loading.text"/>">
                                    <spring:message code="system.common.button.save" text="system.common.button.save"/>
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>

<input type="hidden" id="dateTime"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/artTemplate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/my97DatePicker/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/my97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">

    $(function(){
        setCurrentPageNavigation("systemManageFirstNav","","localeSidebar");
        getTimezones();
        getLocale();
        $("#saveLocaleBtn").click(function(){
            saveLocale();
        });
        $("#dateTimeChoseDiv").click(function(){
            WdatePicker({
                lang:'zh-cn',
                dateFmt:'yyyy-MM-dd HH:mm:ss'
            });
        });
        $("#saveLinuxBtn").click(function () {
            saveLinuxRootAuth();
        });
        $("#timeServerDomain").blur(function(){
            renderInputField(true, "timeServerDomain", "");
        });
        $("#divForm").on('change',"input[name='1']",function(){
            if($("#isSet").is(':checked')==true){
                $("#dateTimeChoseDiv").removeAttr("hidden");
            }else {
                $("#dateTimeChoseDiv").attr("hidden","hidden");
            }
        })

    });

    function getTimezones(){

        var url=$("#projectContext").val()+"/locale/get_timezones";
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
                    var html = template('timezonesTemp', result);
                    document.getElementById('timezonesSelect').innerHTML = html;
                }
            }
        });
    }

    function getLocale(){

        var  url=$("#projectContext").val()+"/locale/get_locale";
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
                    $("#systemTime").val(result.data.systemTime);
                    $("#languageSelect").val(result.data.local);
                    $("#timezonesSelect").val(result.data.timeZoneId);
                    $("#timeServerDomain").val(result.data.timeDomain);
                    $("#isSync").prop("checked",result.data.isSync);
                }else {
                    alertPromptMsgDlg(result.data.msg,3);
                }
            }
        });
    }

    function saveLocale(){
        var localeCode =  $("#languageSelect").val();
        var timezonesId = $("#timezonesSelect").val();
        if(localeCode==null || localeCode==''||localeCode==undefined){
            alertPromptMsgDlg("localeCode cant't null",3);
        }
        if(timezonesId==null || timezonesId==''||timezonesId==undefined){
            alertPromptMsgDlg("timezonesId cant't null",3);
        }
        var timeDomain = $("#timeServerDomain").val();
        var isSync =  $('#isSync').is(':checked');

        var isSet =  $('#isSet').is(':checked');
        var dateTime;
        if(isSet==true){
            dateTime = $("#dateTimeInput").val();
        }
        var url=$("#projectContext").val()+"/locale/save_locale";
        var dst=$("#dst").val();
        if(dst=="true"){
            dst=true;
        }else {
            dst=false;
        }
        if(isSync==true){
            if(timeDomain==''||timeDomain==null||timeDomain==undefined){
                renderInputField(false, "timeServerDomain", "自动同步此字段不能为空");
                return;
            }
        }
        var param={
            localeCode:localeCode,
            timezonesId:timezonesId,
            timeDomain:timeDomain,
            isSync:isSync,
            dateTime:dateTime,
            dst:dst
        };
        hideProgressBar();
        $("#progressBarTitle").text("请稍后");
        $("#modalProgressBar").modal('show');
        showProgress();
        $.ajax({
            url: url,
            cache: false,
            data:param,
            dataType: "json",
            type: "post",
            async: false,
            error: function (xmlHttpRequest, error) {
                hideProgressBar();
                alert(error);
            },
            success: function (result) {
                if (result.ret >= 0 ) {
                    hideProgressBar();
                    alertPromptMsgDlg(result.msg,1,tomain);
                }else {
                    hideProgressBar();
                    if(result.ret==-2){
                        hideSetLinuxPage();
                        alertPromptMsgDlg(result.msg,3,showSetLinuxPage);
                    }else {
                        alertPromptMsgDlg(result.msg,3);
                        hideSetLinuxPage();
                    }
                }
            }
        });
    }

    function showSetLinuxPage(){
        $("#setLinuxRootModal").modal('show');
    }
    function hideSetLinuxPage(){
        $("#setLinuxRootModal").modal('hide');
    }
    function saveLinuxRootAuth(){
        var root = $("#linuxUsername").val();
        var password=$("#linuxPassword").val();
        var url=$("#projectContext").val()+"/locale/save_linux_properties?root="+root+"&password="+password;
        $.ajax({
            url: url,
            cache: false,
            dataType: "json",
            type: "put",
            async: false,
            error: function (xmlHttpRequest, error) {
                alert(error);
            },
            success: function (result) {
                if (result.ret >= 0) {
                    hideSetLinuxPage();
                    alertPromptMsgDlg(result.msg, 1,hideSetLinuxPage);
                } else {
                    hideSetLinuxPage();
                    alertPromptMsgDlg(result.msg, 3);
                }
            }
        });
    }
    function tomain(){
        window.location.href=$("#projectContext").val()+"/locale/locale";
    }

</script>
<script id="timezonesTemp" type="text/html">
    {{each data as prop index}}
    <option value="{{prop[0]}}">
        {{prop[1]}}
    </option>
    {{/each}}
</script>
</body>
</html>
