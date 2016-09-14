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
<div id="content_right">
    <div class="container-fluid">
        <div class="row">
            <div  class="col-sm-12">
                <!-- 文件存储设置 -->
                <div class="div-title-bar">
                    <spring:message code="storage.file.setting"/>
                    <hr style="margin-top: 10px;"/>
                </div>
                <div class="col-sm-7">
                    <form id="fileStorageSettingForm" action="<%=request.getContextPath()%>/storage/file/save"
                          method="post" class="form-horizontal" role="form">
                        <!-- 服务器存储设置 -->
                        <div class="form-group">
                            <strong><spring:message code="storage.file.seversetting"/></strong>
                        </div>
                        <div class="form-group storage-padding-left">
                            <!-- 选择路径 -->
                            <label class="control-label" for="serverSavePath">
                                <label style="font-weight: 500">
                                    <spring:message code="storage.file.selectpath"/>&nbsp;&nbsp;&nbsp;
                                </label>
                                <label style="font-size:11px">
                                    <spring:message code="storage.file.selecttips"/>
                                </label>
                                <!-- 注：所有类型文件都存在此路径下面。-->
                            </label>
                            <div>
                                <input class="form-control  input-uc-yealink" id="serverSavePath" name="serverSavePath" value="${fileStorageSetting.serverSavePath}" type="text"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <!-- 离线文件 -->
                            <strong><spring:message code="storage.file.offline"/></strong>
                        </div>
                        <div class="form-group storage-padding-left">
                            <div>
                                <input type="checkbox" id="isDelOnlineFile" class="regular-checkbox" name="isDelOnlineFile" value="true" <c:if test="${fileStorageSetting.isDelOnlineFile}">checked</c:if>>
                                <label for="isDelOnlineFile"></label>&nbsp;<spring:message code="storage.file.autodel"/>&nbsp;<input style="width:20%" class="input-uc-yealink" id="offlineSaveDays" name="offlineSaveDays" value="${fileStorageSetting.offlineSaveDays}" type="text"/>
                                <spring:message code="storage.file.dayoffline"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <!-- 历史消息 -->
                            <strong><spring:message code="storage.file.hismsg"/></strong>
                        </div>
                        <div class="form-group storage-padding-left">
                            <div>
                                <input type="checkbox" id="isDelMsg" class="regular-checkbox" name="isDelMsg" value="true" <c:if test="${fileStorageSetting.isDelMsg}">checked</c:if>>
                                <label for="isDelMsg"></label>&nbsp;<spring:message code="storage.file.autodel"/>&nbsp;<input style="width:20%" class="input-uc-yealink" id="msgSaveDays" name="msgSaveDays" value="${fileStorageSetting.msgSaveDays}" type="text"/>
                                <spring:message code="storage.file.dayhismsg"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="storage-btn-padding-left">
                                <!-- 保存 -->
                                <input id="saveBtn" name="saveBtn" type="submit" class="btn btn-yealink btn-lg" value='<spring:message code="fileshare.btn.maintain.save"/>'/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
   </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-validate/jquery.validate.js"></script>
<script>
    $(function () {
        setCurrentPageNavigation("systemManageFirstNav","","fileStorageSidebar");

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

        // 增加自定义linux路径格式 校验
        $.validator.addMethod("checkLinuxPath",function(value,element,params){
            var checkLinuxPath = /^([\/][\w-]+)*$/;
            return this.optional(element)||(checkLinuxPath.test(value));
        },'<spring:message code="storage.file.pathinvalid"/>'); <!-- 请输入正确的路径!-->
        $.validator.addMethod("checkDelOffline",function(value,element,params){
            var isDelOffline = $("#isDelOnlineFile").is(":checked");
            if (isDelOffline) {
                var required = value.length > 0;
                if (!required) {
                    return false;
                }
                var isDigit =  /^\d+$/.test( value );
                if (!isDigit) {
                    return false;
                }
                var minCheck =  (value >= params);
                return minCheck;
            } else {
                return true;
            }
        },'<spring:message code="storage.file.offinvalid"/>'); <!-- 离线文件自动删除设置时间必须是大于0的整数!-->
        $.validator.addMethod("checkDelMsg",function(value,element,params){
            var isDelMsg = $("#isDelMsg").is(":checked");
            if (isDelMsg) {
                var required = value.length > 0;
                if (!required) {
                    return false;
                }
                var isDigit =  /^\d+$/.test( value );
                if (!isDigit) {
                    return false;
                }
                var minCheck =  (value >= params);
                return minCheck;
            } else {
                return true;
            }
        },'<spring:message code="storage.file.hisinvalid"/>');<!-- 历史消息自动删除设置时间必须是大于0的整数!-->
    });

    var MyValidator = function() {
        var handleSubmit = function() {
            $('.form-horizontal').validate({
                errorElement : 'span',
                errorClass : 'help-block',
                focusInvalid : false,
                rules : {
                    serverSavePath : {
                        required : true,
                        checkLinuxPath : true
                    },
                    offlineSaveDays : {
                        checkDelOffline : 1
                    },
                    msgSaveDays : {
                        checkDelMsg : 1
                    }
                },
                messages : {
                    serverSavePath : {
                        required : '<spring:message code="storage.file.pathrequired"/>',
                        checkLinuxPath : '<spring:message code="storage.file.serverpathinvalid"/>'
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