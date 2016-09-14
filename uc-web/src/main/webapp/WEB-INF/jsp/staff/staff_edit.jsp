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
        编辑用户
    </div>
</div>
<div class="container-fluid content-right-down" id="staffBaseConfig_edit">
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
            <input hidden id="staffId_edit" value="${staffDetail.staff._id}">
            <input hidden id="currentOrgs" value="${currentOrgs}">

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
            <div class="form-group" id="divUsername_edit">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
					<span id="spanUsername_edit">
						<input class="form-control" id="username_edit" value="<c:out value='${staffDetail.ucAccount.username }'/>"
                               readonly>
					</span>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">密码</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divPassword_edit">
                <div class="col-sm-1"></div>
                <div class="col-sm-3">
					<span id="spanPassword_edit">
						<input class="form-control" id="password_edit" value="<c:out value='${staffDetail.ucAccount.plainPassword }'/>" maxlength="32"
                               title="请输入密码"
                               placeholder="请输入密码" readonly>
					</span>
                </div>
                <div class="col-sm-1">
                    <button type="button" id="password_edit_reset" class="btn btn-success dropdown-toggle">
                        重置
                    </button>
                </div>

                <label id="errorPassword_edit" class="control-label"></label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">PIN码</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divPinCode_edit">
                <div class="col-sm-1"></div>
                <div class="col-sm-3">
					<span id="spanPinCode_edit">
						<input class="form-control" id="pinCode_edit" value="<c:out value='${staffDetail.ucAccount.pinCode }'/>" maxlength="32"
                               title="请输入PIN码"
                               placeholder="请输入PIN码" readonly>
					</span>
                </div>
                <div class="col-sm-1">
                    <button type="button" id="pinCode_edit_reset" class="btn btn-success dropdown-toggle">
                        重置
                    </button>
                </div>

                <label id="errorPinCode_edit" class="control-label"></label>
            </div>
            <div class="form-group">
                <label class="col-sm-2 form-title-label">个人信息</label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">员工头像</span></div>
            </div>
            <div class="form-group" id="divAvatar_edit">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
                    <span class="btn fileinput-button">
                        <span id="preview_edit">
							<c:choose>
                                <c:when test="${staffDetail.staff.avatar == null}">
                                    <img width="100" height="100" src="/images/staff/staff_default.png">
                                </c:when>
                                <c:otherwise>
                                    <img width="100" height="100" src="${fileServiceUrl}/avatar?id=${staffDetail.staff.avatar}&x=100&y=100">
                                </c:otherwise>
                            </c:choose>
                        </span>
                        <input class="form-control" id="avatar_edit" name="avatar"
                               type="file" accept=".png,.jpg,.jpeg"/>
                    </span>
                    <span>单击图片上传，仅支持1024*768大小的jpg，png图片</span>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">员工姓名</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divName_edit">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
					<span id="spanName_edit">
						<input class="form-control" id="name_edit" value="${staffDetail.staff.name }" maxlength="64"
                               title="请输入员工姓名"
                               placeholder="请输入员工姓名">
					</span>
                </div>
                <label id="errorName_edit" class="control-label"></label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">性别</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
                    <label class="checkbox-inline row">
                        <input type="radio" name="gender_edit" value="1"
                               <c:if test="${staffDetail.staff.gender == 1}">checked</c:if>>男
                    </label>
                    <label class="checkbox-inline">
                        <input type="radio" name="gender_edit" value="0"
                               <c:if test="${staffDetail.staff.gender == 0}">checked</c:if>>女
                    </label>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">手机</span></div>
            </div>
            <div id="inputList_edit">
                <div class="form-group" id="divMobilePhone_edit">
                    <div class="col-sm-1"></div>
                    <div class="col-sm-5">
					<span id="spanMobilePhone_edit1">
						<input class="form-control" id="mobilePhone_edit" maxlength="20" value="${staffDetail.staff.mobilePhones[0]}">
					</span>
                    </div>
                    <div class="col-sm-1 row">
                        <label class="pull-right btn" id="addMobilePhone_edit"><i class="icon-plus icon-large"/></label>
                    </div>
                    <label id="errorMobilePhone_edit" class="control-label"></label>
                </div>
                <c:forEach items="${staffDetail.staff.mobilePhones}" var="mobilePhone" varStatus="rownum" begin="1">
                    <c:set var="count" value="${rownum.count}"/>
                    <div class="form-group">
                        <div class="col-sm-1"></div>
                        <div class="col-sm-5">
							<span>
								<input class="form-control" id="mobilePhone_edit${count}" maxlength="20" value="${mobilePhone }">
							</span>
                        </div>
                        <div class="col-sm-1 row">
                            <label class="pull-right btn mobilePhone_edit_remove">
                                <i class="icon-minus icon-large icon-minus-phone"/>
                            </label>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">邮箱</span></div>
            </div>
            <div class="form-group" id="divEmail_edit">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
					<span id="spanEmail_edit">
						<input class="form-control" id="email_edit" value="<c:out value='${staffDetail.staff.email}'/>" maxlength="128"
                               title="请输入电子邮件"
                               placeholder="请输入电子邮件">
					</span>
                </div>
                <label id="errorEmail_edit" class="control-label"></label>
            </div>

            <div class="form-group">
                <label class="col-sm-2 form-title-label">部门信息</label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">所属部门</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divOrg_edit">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
                    <div class="input-group">
                        <input type="text" class="form-control" id="orgName_editStaff" readonly="readonly">
                        <input type="text" class="hide" id="orgId_editStaff" value="${parentOrgId}">

                        <div class="input-group-btn">
                            <button type="button" id="orgDropdownBtn_edit" class="btn btn-default dropdown-toggle">
                                选择<span class="caret"></span>
                            </button>
                            <ul id="selectOrgTree_editStaff" class="ztree dropdown-menu dropdown-menu-right showIcon"
                                style="height: 300px; overflow:auto;">
                            </ul>
                        </div>
                    </div>
                </div>
                <label id="errorOrg_edit" class="control-label"></label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">职位</span><span style="color: red">*</span></div>
            </div>
            <div class="form-group" id="divPosition_edit">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
					<span id="spanPosition_edit">
						<input class="form-control" id="position_edit" value="<c:out value='${staffDetail.staff.position}'/>" maxlength="128"
                               title="请输入职位" placeholder="请输入职位">
					</span>
                </div>
                <label id="errorPosition_edit" class="control-label"></label>
            </div>
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-4"><span class="pull-left">员工编号</span></div>
            </div>
            <div class="form-group" id="divNumber_edit">
                <div class="col-sm-1"></div>
                <div class="col-sm-6">
                    <input class="form-control" id="number_edit" value="<c:out value='${staffDetail.staff.number}'/>" maxlength="128"
                           title="请输入员工编号" placeholder="请输入员工编号">
                </div>
                <label id="errorNumber_edit" class="control-label"></label>
            </div>
        </form>
    </div>
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

<div class="container-fluid content-right-down" id="staffExtensionPhoneConfig_edit" hidden="true">
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
        <div class="row" style="margin-left:7px">
            <div class="row" style="margin-left:22px;margin-top:30px">

                <form class="form-horizontal" role="form">
                    <div id="divConfigExtension">
                        <div class="form-group">
                            <div class="col-sm-1">
                                <input id="isSelectExtensionSetting_edit" name="isSelectExtensionSetting" type="checkbox" <c:if test="${staffDetail.extension.status}">checked</c:if>>
                                <label class="form-title-label">配置分机</label>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-4"><span class="pull-left">分机号</span><span style="color: red">*</span></div>
                        </div>
                        <div class="form-group" id="divExtensionNumber_edit">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-6">

                                <input class="form-control" id="extensionNumber_edit" value="<c:out value='${staffDetail.extension.number}'/>"
                                       <c:if test="${staffDetail.extension.number ne null}">readonly</c:if>
                                        >
                            </div>
                            <label id="errorExtensionNumber_edit" class="control-label"></label>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-4"><span class="pull-left">密码</span><span style="color: red">*</span></div>
                        </div>
                        <div class="form-group" id="divExtensionPassword_edit">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-3">
                            <span id="spanExtensionPassword_edit">
                                <input class="form-control" id="extensionPassword_edit" value="<c:out value='${staffDetail.extension.password}'/>" maxlength="32" title="请输入密码" placeholder="请输入密码"
                                       readonly="">
                            </span>
                            </div>
                            <c:if test="${staffDetail.extension.password ne null}">
                                <div class="col-sm-1">
                                    <button type="button" id="extension_password_edit_reset" class="btn btn-success dropdown-toggle">
                                        重置
                                    </button>
                                </div>
                            </c:if>
                            <label id="errorExtensionPassword_edit" class="control-label"></label>
                        </div>
                        <div class="form-group" id="divExtensionPinCode_edit">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-3">
                            <span id="spanExtensionPinCode_edit">
                                <input class="form-control" id="extensionPinCode_edit" value="<c:out value='${staffDetail.extension.pinCode}'/>" maxlength="32"
                                       readonly>
                            </span>
                            </div>
                            <c:if test="${staffDetail.extension.pinCode ne null}">
                                <div class="col-sm-1">
                                    <button type="button" id="extensionPinCode_edit_reset" class="btn btn-success dropdown-toggle">
                                        重置
                                    </button>
                                </div>
                            </c:if>

                            <label id="errorExtensionPinCode_edit" class="control-label"></label>
                        </div>
                    </div>
                    <div id="divConfigPhone">
                        <div class="form-group">
                            <div class="col-sm-1">
                                <input id="isSelectPhoneSetting_edit" name="isSelectPhoneSetting" type="checkbox" <c:if test="${staffDetail.phone.status}">checked</c:if>>
                                <label>配置话机</label>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-4"><span class="pull-left">MAC</span><span style="color: red">*</span></div>
                        </div>
                        <div class="form-group" id="divPhoneMAC_edit">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-6">
					<span id="spanPhoneMAC_edit">
						<input class="form-control" id="phoneMAC_edit" value="<c:out value='${staffDetail.phone.mac}'/>" maxlength="128" title="请输入MAC地址" placeholder="请输入MAC地址">
					</span>
                            </div>
                            <label id="errorPhoneMAC_edit" class="control-label"></label>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-4"><span class="pull-left">IP</span></div>
                        </div>
                        <div class="form-group" id="divPhoneIP_edit">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-6">
					<span id="spanPhoneIP_edit">
						<input class="form-control" id="phoneIP_edit" value="<c:out value='${staffDetail.phone.ip}'/>" maxlength="128" title="请输入话机IP" placeholder="请输入话机IP">
					</span>
                            </div>
                            <label id="errorPhoneIP_edit" class="control-label"></label>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-4"><span class="pull-left">型号</span></div>
                        </div>
                        <div class="form-group" id="divPhoneModel_edit">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-6">
                                <select class="form-control" id="phoneModel_edit">
                                    <option value="-1">请选择</option>
                                    <option value="1" <c:if test="${staffDetail.phone.model eq 1}">selected</c:if>>T767</option>
                                    <option value="2" <c:if test="${staffDetail.phone.model eq 2}">selected</c:if>>T49</option>
                                </select>
                            </div>
                            <label id="errorPhoneModel_edit" class="control-label"></label>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-4"><span class="pull-left">配置模板：</span></div>
                        </div>
                        <div class="form-group" id="divPhoneSettingTemplate_edit">
                            <div class="col-sm-1"></div>
                            <div class="col-sm-6">
                                <select class="form-control" id="phoneSettingTemplate_edit">
                                    <option value="-1">请选择</option>
                                    <option value="1" <c:if test="${staffDetail.phone.settingTemplate eq 1}">selected</c:if>>全局模板</option>
                                    <option value="2" <c:if test="${staffDetail.phone.settingTemplate eq 2}">selected</c:if>>T49模板</option>
                                    <option value="3" <c:if test="${staffDetail.phone.settingTemplate eq 3}">selected</c:if>>无人机模板</option>
                                </select>
                            </div>
                            <label id="errorPhoneSettingTemplate_edit" class="control-label"></label>
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

                            <button type="button" class="btn btn-success" id="updateStaffBtn">
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
            readyEditStaff();

        });
    </script>
</body>
</html>
