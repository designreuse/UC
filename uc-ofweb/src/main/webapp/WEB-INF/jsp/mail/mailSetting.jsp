<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/common.css">
</head>
<body>
<%@include file="../fileshare/common.jsp"%>
<div id="content_middle">
    <%@ include file="../common/navigation.jsp" %>
</div>
<div  id="content_right">
    <div class="container-fluid">
        <div class="row">
            <div  class="col-sm-12">
                <%--<!-- 邮箱设置 -->--%>
                <div class="div-title-bar">
                    <spring:message code="mail.title.mailsetting"/>
                    <hr style="margin-top: 10px;"/>
                </div>
                <div class="col-sm-7">
                    <form id="mailSettingForm" action="<%=request.getContextPath()%>/mailSetting/save"
                          method="post" class="form-horizontal" role="form">
                            <input type="hidden" name="_id" id="_id" value="${_id}"/>
                            <div class="form-group">
                                <!-- 邮件服务器 -->
                                <label for="mailServerHost"><spring:message code="mail.label.mailserver"/></label>
                                <div>
                                    <input class="form-control  input-uc-yealink"id="mailServerHost" name="mailServerHost" value="${host}" type="text"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <!-- 发送人邮件地址 -->
                                <label class="control-label" for="fromAddress"><spring:message code="mail.label.mailsender"/></label>
                                <div>
                                    <input class="form-control  input-uc-yealink" id="fromAddress" name="fromAddress" value="${fromAddress}" type="text" />
                                </div>
                            </div>
                            <div class="form-group">
                                <!-- SMTP用户名 -->
                                <label class="control-label" for="userName"><spring:message code="mail.label.smtpusername"/></label>
                                <div>
                                    <input class="form-control  input-uc-yealink" id="userName" name="userName" value="${userName}" type="text" />
                                </div>
                            </div>
                            <div class="form-group">
                                <!-- SMTP密码 -->
                                <label class="control-label" for="passWord"><spring:message code="mail.label.smtpassword"/></label>
                                <div>
                                    <input class="form-control  input-uc-yealink" id="passWord" name="passWord" value="${passWord}" type="password" />
                                </div>
                            </div>
                            <div class="form-group">
                                <!-- 端口 -->
                                <label class="control-label" for="mailServerPort"><spring:message code="mail.label.port"/></label>
                                <div>
                                    <input class="form-control  input-uc-yealink" id="mailServerPort" name="mailServerPort" value="${port}" type="text" />
                                </div>
                            </div>
                            <div class="form-group">
                                <!-- 此服务器要求安全连接 -->
                                <div>
                                    <input type="checkbox" id="isSecure" name="isSecure" class="regular-checkbox" value="true" <c:if test="${'true' eq isSecure}">checked</c:if>>
                                    <label for="isSecure"></label>&nbsp;<spring:message code="mail.label.serversecurity"/>
                                    &nbsp;<select name="secure" class="input-uc-yealink" id="secure" value="${secure}">
                                    <option value="2" <c:if test="${'2' eq secure}">selected</c:if>>TLS</option>
                                    <option value="1" <c:if test="${'1' eq secure}">selected</c:if>>SSL</option>
                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="pull-left">
                                    <!-- 测试邮箱设置 -->
                                    <input id="testMailBtn" type="button" onclick="popupTestModal()" class="btn btn-yealink btn-default" value='<spring:message code="mail.btn.testmail"/>'/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <!-- 保存 -->
                                    <input id="saveBtn" name="saveBtn" type="submit" class="btn btn-yealink btn-default" value='<spring:message code="fileshare.btn.maintain.save"/>'/>
                                </div>
                            </div>
                    </form>
                </div>
            </div>
        </div>
</div>
<!-- 邮件测试 模态框（Modal） -->
<div class="modal fade" id="testMailModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" style="top:200px">
    <div class="modal-dialog" style="width: 500px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" style="text-align:center" id="myModalLabel"><spring:message code="mail.btn.testmail"/></h4>
            </div>
            <div class="modal-body">
                <form id="testMailForm">
                    <div id="sendMailDiv" class="madal-content-padding-left">
                        <!-- 发送邮箱： -->
                        <label class="control-label" for="toAddress">
                            <spring:message code="mail.label.send.mail"/>
                        </label>
                        <input id="toAddress" class="input-uc-yealink" style="width:80%;" name="toAddress" type="text" />
                        <div id="errorMsgDiv" style="color:#a94442;text-align:center">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer" style="text-align:center">
                <button type="button" class="btn btn-yealink btn-default" onclick="startTestSendMail()">
                    <spring:message code="mail.btn.confirm"/>
                </button>&nbsp;&nbsp;&nbsp;
                <button type="button" class="btn btn-default btn-default" data-dismiss="modal">
                    <spring:message code="mail.btn.cancel"/>
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<!--测试邮件滚动条  -->
<div class="modal fade" id="progressBarModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" style="top:200px;" data-backdrop="static">
    <div class="modal-dialog" style="width: 500px">
        <div class="modal-content" id="progressBarContent" style="height: 120px">
            <!-- 发送邮件测试中...-->
            <div id="progressBarFooter" class="modal-header" style="text-align:center;color:#30B585">
                <spring:message code="mail.msg.mail.testing"/>
            </div>
            <div class="modal-body" style="height: 50px">
                <div class="progress">
                    <div class="progress-bar" role="progressbar" aria-valuenow="60"
                         aria-valuemin="0" aria-valuemax="100" style="width: 20%;">
                    </div>
                </div>
                <!-- 邮件发送测试成功!-->
                <div id="sendMailTestSuccess" style="text-align:center;font-size:20px;display:none">
                    <spring:message code="mail.msg.mail.sendsuccess"/>
                </div>
                <!-- 邮件发送测试失败!-->
                <div id="sendMailTestFail" style="text-align:center;font-size:20px;display:none">
                    <spring:message code="mail.msg.mail.sendfail"/>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-validate/jquery.validate.min.js"></script>
<script>
    $(function () {
        setCurrentPageNavigation("systemManageFirstNav","mailSidebar","");

        MyValidator.init();

        var code="${code}";
        if (code=='') {
            <%--code = "${param.code}";--%>
        }
        var errorMsg="${errorMsg}";
        if (code=='0') {
            bootbox.alert('<spring:message code="fileshare.msg.maintan.savesucce"/>');
        } else if (code=='-1') {
            bootbox.alert(errorMsg);
        }

        $('#toAddress').keypress(function(e){
            if(e.keyCode==13){
                e.preventDefault();
            }});
    });

    // 弹出测试邮件弹出框
    function popupTestModal() {
        if ($('.form-horizontal').validate().form()) {
            $("#testMailModal").modal('show');
        }
    }
    // 测试邮件
    function startTestSendMail() {
        var validate = $("#testMailForm").validate({
            rules: {
                toAddress : {
                    required : true,
                    email : true
                }
            },
            messages:{
                toAddress : {
                    required : '<spring:message code="mail.msg.mail.required"/>',
                    email : '<spring:message code="mail.msg.mail.invalid"/>'
                }
            },
            highlight : function(element) {
                $(element).closest('#sendMailDiv').addClass('has-error');
            },

            success : function(label) {
                label.closest('#sendMailDiv').removeClass('has-error');
                label.remove();
            },

            errorPlacement : function(error, element) {
                $('#errorMsgDiv').append(error);
            }
        }).form();
        if (validate) {
            //测试发送邮件
            var mailServerHost=$('#mailServerHost').val();
            var fromAddress=$('#fromAddress').val();
            var userName=$('#userName').val();
            var passWord=$('#passWord').val();
            var mailServerPort=$('#mailServerPort').val();
            var isSecure=$('#isSecure').is(':checked');
            var secure=$('#secure').val();
            var toAddress=$('#toAddress').val();

            var mailDataStr='{\"mailServerHost\":\"'+mailServerHost+'\",'
                    + '\"fromAddress\":\"'+fromAddress+'\",'
                    + '\"userName\":\"'+userName+'\",'
                    + '\"passWord\":\"'+passWord+'\",'
                    + '\"mailServerPort\":"'+mailServerPort+'\",'
                    + '\"isSecure\":\"'+isSecure+'\",'
                    + '\"secure\":\"'+secure+'\",'
                    + '\"toAddress\":\"'+toAddress+'\"}';
            // 隐藏测试邮件窗口 显示滚动条窗口
            $("#testMailModal").modal('hide');
            $('.progress-bar').css('width','20%');
            $("#progressBarModal").modal('show');
            $(".progress").css("display","");
            $("#progressBarFooter").css("display","");
            $("#sendMailTestSuccess").css("display","none");
            $("#sendMailTestFail").css("display","none");
            setTimeout(function(){
                //假进度条
                var count=12;
                var i=1;
                var progressTotal=20;
                var barCalc = setInterval(function(){
                    if (i<=count-7) {
                        progressTotal = progressTotal+10;
                    } else if (i<=count-4) {
                        progressTotal = progressTotal+5;
                    } else if (i<=count) {
                        progressTotal = progressTotal+3;
                    }
                    $('.progress-bar').css('width',progressTotal+'%');
                    i++;
                }, 1500);
                $.ajax({
                    type: "post",
                    data: mailDataStr,
                    contentType: 'application/json',
                    url: '<%=request.getContextPath()%>/mailSetting/test',
                    success: function (data) {
                        $(".progress").css("display","none");
                        $("#progressBarFooter").css("display","none");
                        clearInterval(barCalc);
                        if (data=="1") {
                            $("#sendMailTestSuccess").css("display","");
                        } else {
                            $("#sendMailTestFail").css("display","");
                        }
                        setTimeout(function(){
                            $("#progressBarModal").modal('hide');
                        },2000);

                    }
                });
            },500);
        }
    }

    var MyValidator = function() {
        var handleSubmit = function() {
            $('.form-horizontal').validate({
                errorElement : 'span',
                errorClass : 'help-block',
                focusInvalid : false,
                rules : {
                    mailServerHost : {
                        required : true
                    },
                    fromAddress : {
                        required : true,
                        email : true
                    },
                    userName : {
                        required : true
                    },
                    passWord : {
                        required : true
                    },
                    mailServerPort : {
                        required : true,
                        digits : true,
                        range:[1,65535]
                    }
                },
                messages : {
                    mailServerHost : {
                        required : '<spring:message code="mail.msg.server.required"/>'
                    },
                    fromAddress : {
                        required : '<spring:message code="mail.msg.sender.required"/>',
                        email : '<spring:message code="mail.msg.sender.invalid"/>'
                    },
                    userName : {
                        required : '<spring:message code="mail.msg.username.required"/>'
                    },
                    passWord : {
                        required : '<spring:message code="mail.msg.password.required"/>'
                    },
                    mailServerPort : {
                        required : '<spring:message code="mail.msg.port.required"/>',
                        digits : '<spring:message code="mail.msg.port.digit"/>',
                        range : '<spring:message code="mail.msg.port.range"/>'
                    }
                },

                highlight : function(element) {
                    $(element).closest('.form-group').addClass('has-error');
                },

                success : function(label) {
                    label.closest('.form-group').removeClass('has-error');
                    label.remove();
                },

                errorPlacement : function(error, element) {
                    element.parent('div').append(error);
                },

                submitHandler : function(form) {
                    form.submit();
                }
            });

            $('.form-horizontal input').keypress(function(e) {
                if (e.which == 13) {
                    if ($('.form-horizontal').validate().form()) {
                        $('.form-horizontal').submit();
                    }
                    return false;
                }
            });
        }
        return {
            init : function() {
                handleSubmit();
            }
        };
    }();
</script>
</body>
</html>