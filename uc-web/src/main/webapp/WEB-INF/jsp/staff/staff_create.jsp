<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tag" uri="http://www.yealinkuc.com/tag" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/css/jquery.fileupload.css">
</head>
<body>
<div class="content-right-up">
    <div>
        新增用户
    </div>
</div>
<div class="container-fluid content-right-down" id="staffBaseConfig_create">
    <div class="row" style="margin-left:7px">
        <div class="form-group">
            <div class="col-sm-10">
                <div class="row">
                    <div class="col-sm-5">
                        <div style="color: #fff;background-color: #36bb89;border-color: #4cae4c;width: 100%;height: 36px;border-radius: 4px;">
                            <label style="margin-left:108px;margin-top:5px">01基本信息</label>
                        </div>
                    </div>
                    <div class="col-sm-1">
                        <div style="margin-top: 9px;margin-left: 12px;">
                            <img src="${pageContext.request.contextPath}/images/staff/left-arrow.png">
                        </div>
                    </div>

                    <div class="col-sm-5">
                        <div style="color: #fff;background-color: #B5B5B5;border-color: #4cae4c;width: 100%;height: 36px;border-radius: 4px;">
                            <label style="margin-left:108px;margin-top:5px">02分配分机/话机</label>
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </div>
    <div class="row" style="margin-left:22px;margin-top:30px">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <label class="col-sm-1 form-title-label">账户信息</label>

                <div class="col-sm-4 staff-account-tip">
                    该账号可访问yealink所有终端软件，如IM、话机
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">用户名</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divUsername_create">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
					<span id="spanUsername_create">
						<input class="form-control" id="username_create"/>
					</span>
                </div>
                <label id="errorUsername_create" class="control-label"></label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">密码</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divPassword_create">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
					<span id="spanPassword_create">
						<input class="form-control" id="password_create" maxlength="32"
                               title="请输入密码"
                               placeholder="请输入密码" readonly>
					</span>
                </div>
                <label id="errorPassword_create" class="control-label"></label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">PIN码</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divPinCode_create">
                <div class="col-sm-1"></div>
                <div class="col-sm-3">
					<span id="spanPinCode_create">
						<input class="form-control" id="pinCode_create" maxlength="32"
                               title="请输入PIN码"
                               placeholder="请输入PIN码" readonly>
					</span>
                </div>
                <label id="errorPinCode_create" class="control-label"></label>
            </div>
            <div class="form-group">
                <label class="col-sm-2 form-title-label">个人信息</label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">员工头像</span></div>
            </div>
            <div class="form-group" id="divAvatar_create">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
                    <span class="btn fileinput-button">
                        <span id="preview_create">
                            <img width="100" height="100" src="${pageContext.request.contextPath}/images/staff/staff_default.png">
                        </span>
                        <input class="form-control" id="avatar_create" name="avatar"
                               type="file" accept=".png,.jpg,.jpeg"/>
                    </span>
                    <span>单击图片上传，仅支持1024*768大小的jpg，png图片</span>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">员工姓名</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divName_create">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
					<span id="spanName_create">
						<input class="form-control" id="name_create" maxlength="64"
                               title="请输入员工姓名"
                               placeholder="请输入员工姓名">
					</span>
                </div>
                <label id="errorName_create" class="control-label"></label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">性别</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
                    <label class="checkbox-inline row">
                        <input type="radio" name="gender_create" value="1" checked>男
                    </label>
                    <label class="checkbox-inline">
                        <input type="radio" name="gender_create" value="0">女
                    </label>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">手机</span></div>
            </div>
            <div id="inputList_create">
                <div class="form-group" id="divMobilePhone_create">
                    <div class="col-sm-1"></div>
                    <div class="col-sm-5">
					<span id="spanMobilePhone_create1">
						<input class="form-control" id="mobilePhone_create" maxlength="20" name="mobilePhone">
					</span>
                    </div>
                    <div class="col-sm-1 row">
                        <label class="pull-right btn" id="addMobilePhone_create"><i class="icon-plus icon-large"/></label>
                    </div>
                    <label id="errorMobilePhone_create" class="control-label"></label>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">邮箱</span></div>
            </div>
            <div class="form-group" id="divEmail_create">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
					<span id="spanEmail_create">
						<input class="form-control" id="email_create" maxlength="128"
                               title="请输入电子邮件"
                               placeholder="请输入电子邮件">
					</span>
                </div>
                <label id="errorEmail_create" class="control-label"></label>
            </div>

            <div class="form-group">
                <label class="col-sm-2 form-title-label">部门信息</label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">所属部门</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divOrg_create">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
                    <div class="input-group">
                        <input type="text" class="form-control" id="orgName_createStaff" readonly="readonly">
                        <input type="text" class="hide" id="orgId_createStaff">

                        <div class="input-group-btn">
                            <button type="button" id="orgDropdownBtn_create" class="btn btn-default dropdown-toggle">
                                选择<span class="caret"></span>
                            </button>
                            <ul id="selectOrgTree_createStaff" class="ztree dropdown-menu dropdown-menu-right showIcon"
                                style="height: 300px; overflow:auto;">
                            </ul>
                        </div>
                    </div>
                </div>
                <label id="errorOrg_create" class="control-label"></label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">职位</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divPosition_create">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
					<span id="spanPosition_create">
						<input class="form-control" id="position_create" maxlength="128"
                               title="请输入职位" placeholder="请输入职位">
					</span>
                </div>
                <label id="errorPosition_create" class="control-label"></label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">员工编号</span></div>
            </div>
            <div class="form-group" id="divNumber_create">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
                    <input class="form-control" id="number_create" maxlength="128"
                           title="请输入员工编号" placeholder="请输入员工编号">
                </div>
                <label id="errorNumber_create" class="control-label"></label>
            </div>
        </form>
        <div class="row" style="margin-left:22px;">

            <div class="col-sm-5">
            </div>
            <div class="col-sm-2">
                <button type="button" class="btn btn-success" id="nextStepBtn">
                    下一步
                </button>
            </div>
            <div class="col-sm-5">
            </div>
        </div>
    </div>
</div>
<div class="container-fluid content-right-down" id="staffExtensionPhoneConfig_create" hidden="true">
    <div class="row" style="margin-left:7px">
        <div class="form-group">
            <div class="col-sm-10">
                <div class="row">
                    <div class="col-sm-5">
                        <div style="color: #fff;background-color: #36bb89;border-color: #4cae4c;width: 100%;height: 36px;border-radius: 4px;">
                            <label style="margin-left:108px;margin-top:5px">01基本信息</label>
                        </div>
                    </div>
                    <div class="col-sm-1">
                        <div style="margin-top: 9px;margin-left: 24px;">
                            <img src="${pageContext.request.contextPath}/images/staff/left-arrow.png">
                        </div>
                    </div>

                    <div class="col-sm-5">
                        <div style="color: #fff;background-color: #B5B5B5;border-color: #4cae4c;width: 100%;height: 36px;border-radius: 4px;">
                            <label style="margin-left:108px;margin-top:5px">02分配分机/话机</label>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <div class="row" style="margin-left:7px">
        <div class="row" style="margin-left:22px;margin-top:30px">

            <form class="form-horizontal" role="form">
                <div id="divConfigExtension">
                    <div class="form-group">
                        <div class="col-sm-1">
                            <input id="isSelectExtensionSetting_create" name="isSelectExtensionSetting" type="checkbox" checked>
                            <label>配置分机</label>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-4"><span class="pull-left">分机号</span><span style="color: red">*</span></div>
                    </div>
                    <div class="form-group" id="divExtensionNumber_create">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-6">
                            <span id="spanExtensionNumber_create">
                                <input class="form-control" id="extensionNumber_create">
                            </span>
                        </div>
                        <label id="errorExtensionNumber_create" class="control-label"></label>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-4"><span class="pull-left">密码</span><span style="color: red">*</span></div>
                    </div>
                    <div class="form-group" id="divExtensionPassword_create">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-3">
                            <span id="spanExtensionPassword_create">
                                <input class="form-control" id="extensionPassword_create" maxlength="32" title="请输入密码" placeholder="请输入密码" readonly="">
                            </span>
                        </div>
                        <label id="errorExtensionPassword_create" class="control-label"></label>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-4"><span class="pull-left">PIN码</span><span style="color: red">*</span></div>
                    </div>
                    <div class="form-group" id="divExtensionPinCode_create">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-3">
                                <span id="spanExtensionPinCode_create">
                                    <input class="form-control" id="extensionPinCode_create" maxlength="32"
                                           readonly>
                                </span>
                        </div>
                        <label id="errorExtensionPinCode_create" class="control-label"></label>
                    </div>

                </div>
                <div id="divConfigPhone">
                    <div class="form-group">
                        <input id="isSelectPhoneSetting_create" name="isSelectPhoneSetting" type="checkbox" checked>
                        <label>配置话机</label>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-4"><span class="pull-left">MAC</span><span style="color: red">*</span></div>
                    </div>
                    <div class="form-group" id="divPhoneMAC_create">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-6">
					<span id="spanPhoneMAC_create">
						<input class="form-control" id="phoneMAC_create" maxlength="128" title="请输入MAC地址" placeholder="请输入MAC地址">
					</span>
                        </div>
                        <label id="errorPhoneMAC_create" class="control-label"></label>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-4"><span class="pull-left">IP</span></div>
                    </div>
                    <div class="form-group" id="divPhoneIP_create">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-6">
					<span id="spanPhoneIP_create">
						<input class="form-control" id="phoneIP_create" maxlength="128" title="请输入话机IP" placeholder="请输入话机IP">
					</span>
                        </div>
                        <label id="errorPhoneIP_create" class="control-label"></label>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-4"><span class="pull-left">型号</span></div>
                    </div>
                    <div class="form-group" id="divPhoneModel_create">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-6">
                            <select class="form-control" id="phoneModel_create">
                                <option  value="-1">请选择</option>
                                <option value="1">T767</option>
                                <option value="2">T49</option>
                            </select>
                        </div>
                        <label id="errorPhoneModel_create" class="control-label"></label>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-4"><span class="pull-left">配置模板：</span></div>
                    </div>
                    <div class="form-group" id="divPhoneSettingTemplate_create">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-6">
                            <select class="form-control" id="phoneSettingTemplate_create">
                                <option  value="-1">请选择</option>
                                <option value="1">全局模板</option>
                                <option value="2">T49模板</option>
                                <option value="3">无人机模板</option>
                            </select>
                        </div>
                        <label id="errorPhoneSettingTemplate_create" class="control-label"></label>
                    </div>
                </div>
            </form>
        </div>
        <div class="row" style="margin-left:22px;">

            <div class="col-sm-3">
            </div>
            <div class="col-sm-4">
                <div class="row">
                    <div class="col-sm-3">
                        <button type="button" class="btn btn-success" id="prevStepBtn">
                            上一步
                        </button>
                    </div>
                    <div class="col-sm-2">
                        <div class="row">
                        </div>

                        <button type="button" class="btn btn-success" id="saveStaffBtn">
                            保存
                        </button>
                    </div>
                    <div class="col-sm-4">
                        <button type="button" class="btn btn-success" id="saveThenMailBtn">
                            保存并发送
                        </button>
                    </div>
                    <div class="col-sm-3">
                        <button type="button" class="btn btn-success" id="cancelBtn">
                            取消
                        </button>
                    </div>
                </div>

            </div>
            <div class="col-sm-5">

            </div>

        </div>

    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/vendor/canvas-to-blob.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/vendor/load-image.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/jquery.fileupload-image.js"></script>
<script>
    $(function () {
        readyCreateStaff();

    });
</script>
</body>
</html>
