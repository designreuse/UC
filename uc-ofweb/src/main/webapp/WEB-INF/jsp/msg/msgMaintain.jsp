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
    <!-- 消息属性设置 -->
    <title>
        <spring:message code="message.title.maintain.msg"/>
    </title>
</head>
<body>
<div class="container">
    <form id="fileshareForm" action="<%=request.getContextPath()%>/msg/maintain/save" method="post" class="form-horizontal" role="form">
        <fieldset>
            <!-- 属性设置 -->
            <legend><spring:message code="message.label.maintain.setting"/></legend>
            <div class="form-group">
                <!-- 撤回消息有效时间 -->
                <label class="col-sm-2 control-label" for="revertMsgValidTimes"><spring:message code="message.label.maintain.revertmsgvalidtime"/></label>
                <div class="col-sm-4">
                    <input class="form-control" id="revertMsgValidTimes" name="revertMsgValidTimes" type="text" value="${revertMsgValidTimes}"/>
                </div>
                <!-- 秒 -->
                <label><spring:message code="message.label.maintain.second"/></label>
            </div>
            <div class="form-group">
                <!-- 消息存储时间 -->
                <label class="col-sm-2 control-label" for="msgSaveDays"><spring:message code="message.label.maintain.msgsavedays"/></label>
                <div class="col-sm-4">
                    <input class="form-control" id="msgSaveDays" name="msgSaveDays" type="text" value="${msgSaveDays}"/>
                </div>
                <!-- 天 -->
                <label><spring:message code="message.label.maintain.day"/></label>
            </div>
            <div class="form-group">
                <!-- 最近联系人保存最大个数 -->
                <label class="col-sm-2 control-label" for="recentMaxNumbers"><spring:message code="message.label.maintain.recentmaxnums"/></label>
                <div class="col-sm-4">
                    <input class="form-control" id="recentMaxNumbers" name="recentMaxNumbers" type="text" value="${recentMaxNumbers}"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-offset-2 col-md-10">
                    <!-- 保存 -->
                    <input id="saveBtn" name="saveBtn" type="submit" class="btn btn-primary btn-sm" value='<spring:message code="fileshare.btn.maintain.save"/>'>
                </div>
            </div>
        </fieldset>
    </form>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-validate/jquery.validate.min.js"></script>
<script>
    $(function () {
        MyValidator.init();

        var code="${ret}";
        var errorMsg="${msg}";
        if (code=="200") {
            alert('<spring:message code="fileshare.msg.maintan.savesucce"/>');
        } else if (code=="-2") {
            alert(errorMsg);
        }
    });

    var MyValidator = function() {
        var handleSubmit = function() {
            $('.form-horizontal').validate({
                errorElement : 'span',
                errorClass : 'help-block',
                focusInvalid : false,
                rules : {
                    revertMsgValidTimes : {
                        required : true,
                        digits : true,
                        range:[1,120]
                    },
                    msgSaveDays : {
                        required : true,
                        digits : true,
                        range:[30,3000]
                    },
                    recentMaxNumbers : {
                        required : true,
                        digits : true,
                        range:[5,1000]
                    }
                },
                messages : {
                    revertMsgValidTimes : {
                        required : '<spring:message code="message.msg.maintain.revertmsgvalid.required"/>',
                        digits : '<spring:message code="message.msg.maintain.revertmsgvalid.digit"/>',
                        range : '<spring:message code="message.msg.maintain.revertmsgvalid.range"/>'
                    },
                    msgSaveDays : {
                        required : '<spring:message code="message.msg.maintain.msgsavedays.required"/>',
                        digits : '<spring:message code="message.msg.maintain.msgsavedays.digit"/>',
                        range : '<spring:message code="message.msg.maintain.msgsavedays.range"/>'
                    },
                    recentMaxNumbers : {
                        required : '<spring:message code="message.msg.maintain.recentmaxnums.required"/>',
                        digits : '<spring:message code="message.msg.maintain.recentmaxnums.digit"/>',
                        range : '<spring:message code="message.msg.maintain.recentmaxnums.range"/>'
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