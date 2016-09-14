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
    <!-- 文件共享维护 -->
    <title>
        <spring:message code="fileshare.title.maintain.fs"/>
    </title>
</head>
<body>
<div class="container">
    <form id="fileshareForm" action="<%=request.getContextPath()%>/fileshare/maintain/save" method="post" class="form-horizontal" role="form">
        <fieldset>
            <!-- 全局属性设置 -->
            <legend><spring:message code="fileshare.label.maintain.setting"/></legend>
            <div class="form-group">
                <!-- 离线文件存储时间 -->
                <label class="col-sm-2 control-label" for="offlineSaveDays"><spring:message code="fileshare.label.maintain.offlinesavedays"/></label>
                <div class="col-sm-4">
                    <input class="form-control" id="offlineSaveDays" name="offlineSaveDays" type="text" value="${offlineSaveDays}"/>
                </div>
                <!-- 天 -->
                <label><spring:message code="fileshare.label.maintain.day"/></label>
            </div>
            <div class="form-group">
                <!-- 上传文件最大值 -->
                <label class="col-sm-2 control-label" for="fileMaxSize"><spring:message code="fileshare.label.maintain.filemaxsize"/></label>
                <div class="col-sm-4">
                    <input class="form-control" id="fileMaxSize" name="fileMaxSize" type="text" value="${fileMaxSize}"/>
                </div>
                <!-- 字节 -->
                <label><spring:message code="fileshare.label.maintain.byte"/></label>
            </div>
            <div class="form-group">
                <!-- 头像文件格式限制 -->
                <label class="col-sm-2 control-label"><spring:message code="fileshare.label.maintain.avatarfileformat"/></label>
                <div class="col-sm-4">
                    <input type="checkbox" id="check-jpg" name="avatarFileTypes" value="jpg">jpg
                    &nbsp;
                    <input type="checkbox" id="check-bmp" name="avatarFileTypes" value="bmp">bmp
                    &nbsp;
                    <input type="checkbox" id="check-png" name="avatarFileTypes" value="png">png
                    &nbsp;
                    <input type="checkbox" id="check-gif" name="avatarFileTypes" value="gif">gif
                    &nbsp;
                    <input type="checkbox" id="check-tiff" name="avatarFileTypes" value="tiff">tiff
                </div>
                &nbsp;&nbsp;<span id="errorCheckBoxMsg"></span>
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
<!-- css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/icheck/skins/square/green.css" />
<!-- js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/icheck/js/icheck.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-validate/jquery.validate.min.js"></script>
<script>
    $(function () {
        MyValidator.init();

        $('input').iCheck({
            checkboxClass: 'icheckbox_square-green',
            increaseArea: '20%'
        });
        // 选中 移除checkbox验证信息状态
        $('input').on('ifChecked', function(event){
            $("#avatarFileTypes-error").closest('.form-group').removeClass('has-error');
            $("#avatarFileTypes-error").remove();
        });
        $('input').on('ifUnchecked', function(event){
            if($("#avatarFileTypes").length == 0) {

            }
        });

        var avatarFileTypes="${avatarFileTypes}";
        if (avatarFileTypes != '') {
            var avatarFileArr = avatarFileTypes.split(",");
            $.each(avatarFileArr, function(i,item){
                $('#check-'+item).iCheck('check');
            });
        }

        var code="${code}";
        var errorMsg="${errorMsg}";
        if (code=="1") {
            alert('<spring:message code="fileshare.msg.maintan.savesucce"/>');
        } else if (code=="0") {
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
                    offlineSaveDays : {
                        required : true,
                        digits : true,
                        range:[7,20]
                    },
                    fileMaxSize : {
                        required : true,
                        digits : true,
                        range:[1000,1000000000]
                    },
                    avatarFileTypes : {
                        required : true
                    }
                },
                messages : {
                    offlineSaveDays : {
                        required : '<spring:message code="fileshare.msg.maintan.offlinesaveday.required"/>',
                        digits : '<spring:message code="fileshare.msg.maintan.offlinesaveday.digit"/>',
                        range : '<spring:message code="fileshare.msg.maintan.offlinesaveday.range"/>'
                    },
                    fileMaxSize : {
                        required : '<spring:message code="fileshare.msg.maintan.filemaxsize.required"/>',
                        digits : '<spring:message code="fileshare.msg.maintan.filemaxsize.digit"/>',
                        range : '<spring:message code="fileshare.msg.maintan.filemaxsize.range"/>'
                    },
                    avatarFileTypes : {
                        required : '<spring:message code="fileshare.msg.maintan.filetype.required"/>'
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
                    if (element.is(":checkbox")) {
                        $("#errorCheckBoxMsg").append(error);
                    } else {
                        element.parent('div').append(error);
                    }
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