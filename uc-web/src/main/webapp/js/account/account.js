/* A JavaScript implementation of the RSA Data Security,Inc. MD5 Message Digest Algorithm,as defined in RFC 1321.*/
function MDH(num) {
    var c = "0123456789abcdef";
    str = "";
    for (j = 0; j <= 3; j++)str += c.charAt((num >> (j * 8 + 4)) & 0x0F) + c.charAt((num >> (j * 8)) & 0x0F);
    return str
}
function MD(str) {
    nblk = ((str.length + 8) >> 6) + 1;
    blks = new Array(nblk * 16);
    for (i = 0; i < nblk * 16; i++)blks[i] = 0;
    for (i = 0; i < str.length; i++)blks[i >> 2] |= str.charCodeAt(i) << ((i % 4) * 8);
    blks[i >> 2] |= 0x80 << ((i % 4) * 8);
    blks[nblk * 16 - 2] = str.length * 8;
    return blks
}
function MDA(x, y) {
    var lsw = (x & 0xFFFF) + (y & 0xFFFF);
    var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
    return (msw << 16) | (lsw & 0xFFFF)
}
function rol(num, cnt) {
    return (num << cnt) | (num >>> (32 - cnt))
}
function cmn(q, a, b, x, s, t) {
    return MDA(rol(MDA(MDA(a, q), MDA(x, t)), s), b)
}
function ff(a, b, c, d, x, s, t) {
    return cmn((b & c) | ((~b) & d), a, b, x, s, t)
}
function gg(a, b, c, d, x, s, t) {
    return cmn((b & d) | (c & (~d)), a, b, x, s, t)
}
function hh(a, b, c, d, x, s, t) {
    return cmn(b ^ c ^ d, a, b, x, s, t)
}
function ii(a, b, c, d, x, s, t) {
    return cmn(c ^ (b | (~d)), a, b, x, s, t)
}
function MD5(str) {
    x = MD(str + '');
    var a = 1732584193;
    var b = -271733879;
    var c = -1732584194;
    var d = 271733878;
    for (i = 0; i < x.length; i += 16) {
        var olda = a;
        var oldb = b;
        var oldc = c;
        var oldd = d;
        a = ff(a, b, c, d, x[i + 0], 7, -680876936);
        d = ff(d, a, b, c, x[i + 1], 12, -389564586);
        c = ff(c, d, a, b, x[i + 2], 17, 606105819);
        b = ff(b, c, d, a, x[i + 3], 22, -1044525330);
        a = ff(a, b, c, d, x[i + 4], 7, -176418897);
        d = ff(d, a, b, c, x[i + 5], 12, 1200080426);
        c = ff(c, d, a, b, x[i + 6], 17, -1473231341);
        b = ff(b, c, d, a, x[i + 7], 22, -45705983);
        a = ff(a, b, c, d, x[i + 8], 7, 1770035416);
        d = ff(d, a, b, c, x[i + 9], 12, -1958414417);
        c = ff(c, d, a, b, x[i + 10], 17, -42063);
        b = ff(b, c, d, a, x[i + 11], 22, -1990404162);
        a = ff(a, b, c, d, x[i + 12], 7, 1804603682);
        d = ff(d, a, b, c, x[i + 13], 12, -40341101);
        c = ff(c, d, a, b, x[i + 14], 17, -1502002290);
        b = ff(b, c, d, a, x[i + 15], 22, 1236535329);
        a = gg(a, b, c, d, x[i + 1], 5, -165796510);
        d = gg(d, a, b, c, x[i + 6], 9, -1069501632);
        c = gg(c, d, a, b, x[i + 11], 14, 643717713);
        b = gg(b, c, d, a, x[i + 0], 20, -373897302);
        a = gg(a, b, c, d, x[i + 5], 5, -701558691);
        d = gg(d, a, b, c, x[i + 10], 9, 38016083);
        c = gg(c, d, a, b, x[i + 15], 14, -660478335);
        b = gg(b, c, d, a, x[i + 4], 20, -405537848);
        a = gg(a, b, c, d, x[i + 9], 5, 568446438);
        d = gg(d, a, b, c, x[i + 14], 9, -1019803690);
        c = gg(c, d, a, b, x[i + 3], 14, -187363961);
        b = gg(b, c, d, a, x[i + 8], 20, 1163531501);
        a = gg(a, b, c, d, x[i + 13], 5, -1444681467);
        d = gg(d, a, b, c, x[i + 2], 9, -51403784);
        c = gg(c, d, a, b, x[i + 7], 14, 1735328473);
        b = gg(b, c, d, a, x[i + 12], 20, -1926607734);
        a = hh(a, b, c, d, x[i + 5], 4, -378558);
        d = hh(d, a, b, c, x[i + 8], 11, -2022574463);
        c = hh(c, d, a, b, x[i + 11], 16, 1839030562);
        b = hh(b, c, d, a, x[i + 14], 23, -35309556);
        a = hh(a, b, c, d, x[i + 1], 4, -1530992060);
        d = hh(d, a, b, c, x[i + 4], 11, 1272893353);
        c = hh(c, d, a, b, x[i + 7], 16, -155497632);
        b = hh(b, c, d, a, x[i + 10], 23, -1094730640);
        a = hh(a, b, c, d, x[i + 13], 4, 681279174);
        d = hh(d, a, b, c, x[i + 0], 11, -358537222);
        c = hh(c, d, a, b, x[i + 3], 16, -722521979);
        b = hh(b, c, d, a, x[i + 6], 23, 76029189);
        a = hh(a, b, c, d, x[i + 9], 4, -640364487);
        d = hh(d, a, b, c, x[i + 12], 11, -421815835);
        c = hh(c, d, a, b, x[i + 15], 16, 530742520);
        b = hh(b, c, d, a, x[i + 2], 23, -995338651);
        a = ii(a, b, c, d, x[i + 0], 6, -198630844);
        d = ii(d, a, b, c, x[i + 7], 10, 1126891415);
        c = ii(c, d, a, b, x[i + 14], 15, -1416354905);
        b = ii(b, c, d, a, x[i + 5], 21, -57434055);
        a = ii(a, b, c, d, x[i + 12], 6, 1700485571);
        d = ii(d, a, b, c, x[i + 3], 10, -1894986606);
        c = ii(c, d, a, b, x[i + 10], 15, -1051523);
        b = ii(b, c, d, a, x[i + 1], 21, -2054922799);
        a = ii(a, b, c, d, x[i + 8], 6, 1873313359);
        d = ii(d, a, b, c, x[i + 15], 10, -30611744);
        c = ii(c, d, a, b, x[i + 6], 15, -1560198380);
        b = ii(b, c, d, a, x[i + 13], 21, 1309151649);
        a = ii(a, b, c, d, x[i + 4], 6, -145523070);
        d = ii(d, a, b, c, x[i + 11], 10, -1120210379);
        c = ii(c, d, a, b, x[i + 2], 15, 718787259);
        b = ii(b, c, d, a, x[i + 9], 21, -343485551);
        a = MDA(a, olda);
        b = MDA(b, oldb);
        c = MDA(c, oldc);
        d = MDA(d, oldd);
    }
    return MDH(a) + MDH(b) + MDH(c) + MDH(d)
}

/**
 *    修饰页绑定个人信息、密码修改绑定
 */
function accountBind() {
    $("#accountInfo").click(function () {
        //showProgress();
        $.ajax({
            url: $("#projectContext").val() + "/account/show",
            type: "get",
            async: true,
            success: function (data) {
                $('#divHeaderContent').html(data);
                $('#modelPersonal').modal('show');
                //hideProgressBar();
            },
            error: function () {
                alertPromptErrorMsgDlg();
                //hideProgressBar();
            }
        });
    });
}

/**
 * 忘记密码申请初始化
 */
function readyForgotPwd() {
    $('#email').focus();

    $("#email").blur(function () {
        validateMail("email");
    });


    $("#forgotSub").click(function () {
        forgotPwd();
        return false;
    });

    bindCaptchaInput();
}

/**
 * 忘记密码申请提交
 */
function forgotPwd() {
    showProgress();
    if (!validateMail("email")) {
        hideProgressBar();
        $("#email").focus();
        return false;
    }

    if (!checkCaptcha()) {
        hideProgressBar();
        $("#captcha").focus();
        return false;
    }

    var params = {
        mail: $.trim($("#email").val()),
        captcha: $("#captcha").val()
    }
    $.ajax({
        url: setUrlCsrfTokenParameter($("#projectContext").val() + "/account/password/submitResetApply"),
        type: "post",
        async: true,
        data: params,
        dataType: "json",
        success: function (result) {
            hideProgressBar();
            alertPromptMsgDlg(result.message, 0, refresh);
        },
        error: function () {
            hideProgressBar();
            alertPromptErrorMsgDlg();
        }
    });
}

/**
 * 刷新忘记密码申请页面
 */
function refresh() {
    window.location = $("#projectContext").val() + "/account/password/forwardToForgot";

}

/**
 * 校验邮箱格式
 * @param 邮箱组件的id
 * @returns boolean
 */
function validateMail(inputId) {
    var value = $.trim($("#" + inputId).val());
    if (!validateNull(value)) {
        if (!isMail(value)) {
            renderInputField(false, inputId, Lang("account_tips_invalid_email"));
            return false;
        } else {
            restoreInputField(inputId);
            return true;
        }
    } else {
        renderInputField(false, inputId, Lang("account_tips_fill_email"));
        return false;
    }
}

/**
 * 忘记密码之后的重置密码
 */
function readyResetPwdAfterForgot() {
    $("#newPassword").focus();

    passwordFieldLockTips("newPassword", Lang("system_common_tips_capslock"));

    $("#submitResetPwdBtn").click(function () {
        resetPwdAfterForgot();
        return;
    });

    $("#repeatPassword").blur(function () {
        validateRepeatPwd("newPassword", "repeatPassword");
    });

}

function passwordFieldLockTips(pwdId, msg) {
    $("#" + pwdId).keypress(function (event) {
        if (checkCapsLock(event)) {
            restoreInputField(pwdId);
            var newFileldId = pwdId.substr(0, 1).toLocaleUpperCase() + pwdId.slice(1);
            $("#div" + newFileldId).addClass("has-error");
            $("#error" + newFileldId).text(msg)
            $("#" + pwdId).attr("isCapsLock", "1");
        } else {
            if ($("#" + pwdId).attr("isCapsLock") == "1") {
                $("#" + pwdId).removeAttr("isCapsLock");
                restoreInputField(pwdId);
            }
        }
    });

    $("#" + pwdId).blur(function () {
        if ($("#" + pwdId).attr("isCapsLock") == "1") {
            $("#" + pwdId).removeAttr("isCapsLock");
            restoreInputField(pwdId);
        }
        validateNewPwd("newPassword", "repeatPassword");
    });
}

/**
 * 忘记密码之后的重置密码提交
 */
function resetPwdAfterForgot() {
    if (!validateNewPwd("newPassword", "repeatPassword")) {
        $("#newPassword").focus();
        return;
    }
    if (!validateRepeatPwd("newPassword", "repeatPassword")) {
        $("#repeatPassword").focus();
        return;
    }

    var params = {
        newPassword: MD5($.trim($("#newPassword").val())),
        activeCode: $("#activeCode").val()
    }

    $.ajax({
        url: setUrlCsrfTokenParameter($("#basePath").val() + "/account/password/reset"),
        type: "post",
        async: false,
        data: params,
        dataType: "json",
        success: function (result) {
            var type = (result.success) ? 1 : 3;
            alertPromptMsgDlg(result.message, type, function () {
                if (result.success) {
                    window.location = getContextPath() + '/';
                }
            });
        },
        error: function () {
            alertPromptErrorMsgDlg();
        }
    });
}

/**
 * 个人信息显示初始化
 */
function readyAccountInfo() {
    $("#toInfoBtn, #toEditBtn").click(function () {
        changeStatus();
    });

    $("#toPwdBtn").click(function () {
        showEditPersonalPwd();
    });

    readyEditInfo();
}

/**
 * 个人信息切换编辑和显示状态
 */
function changeStatus() {
    changeVisible(".form-label-must-field");
    changeVisible("div[class$='div-edit']");
    changeVisible("label[id^='errorAccount']");
    changeVisible("div[class$='div-info']");
    changeVisible("#detailOpDiv");
    changeVisible("#editOpDiv");
    changeVisible("#divCurrentPsw");
    changeVisible("#divNewPsw");
    changeVisible("#divRepeatPsw");

    $("input[id^='account']").each(function () {
        restoreInputField($(this).attr("id"));
    });

    if (!$("#editOpDiv").attr("hidden")) {
        $("input[id^=account][disabled!='disabled']:first").focus();
    }
}

/**
 * 修改元素的可见性
 * @param dom dom元素
 */
function changeVisible(dom) {
    $(dom).attr("hidden", !$(dom).attr("hidden"));
}

/**
 * 显示修改个人密码（登录者密码修改）
 */
function showEditPersonalPwd() {
    var url = $("#projectContext").val() + "/account/password/forwardToEdit";
    $.get(url, function (data) {
        $('#divHeaderContent').html(data);
    });
}

/**
 * 用户信息编辑页准备
 */
function readyEditInfo() {
    $("#accountUsername").blur(function () {
        validateUsername();
    });
    $("#accountFullName").blur(function () {
        validateName("accountFullName", "full_name")
    });
    $("#accountEnterprise").blur(function () {
        validateName("accountEnterprise", "company")
    });
    $("#saveInfoBtn").click(function () {
        saveInfo();
    });
}

/**
 * 个人信息编辑保存
 */
function saveInfo() { //2
    console.log("Run saveInfo")
    if (!validateUsername()) {
        $("#accountUsername").focus();
        return;
    }
    if (!validateName("accountFullName", "full_name")) {
        $("#accountFullName").focus();
        return;
    }
    if (!validateName("accountEnterprise", "company")) {
        $("#accountEnterprise").focus();
        return;
    }
   /* if (!validateMail("accountMail")) {
        $("#accountMail").focus();
        return;
    }*/
    var currentPsw = $("#currentPsw").val();
    var newPsw = $("#newPsw").val();
    var repeatPsw = $("#repeatPsw").val();

    var needModifyPassword = !validateNull(currentPsw);
    console.log("needModifyPassword=" + needModifyPassword)
    if (needModifyPassword && !validateCurrentPwd("currentPsw")) {
        $("#currentPsw").focus();
        return false;
    }

    if (needModifyPassword && !validateNull(newPsw) && !validateNull(repeatPsw) && !validateNewPwd("newPsw", "repeatPsw")) {
        $("#newPsw").focus();
        return false;
    }
    if (needModifyPassword && !validateNull(newPsw) && !validateNull(repeatPsw) && !validateRepeatPwd("newPsw", "repeatPsw")) {
        $("#repeatPsw").focus();
        return false;
    }
    var params;
    if (needModifyPassword) {
        params = {
            "username": $.trim($("#accountUsername").val()),
            "name": $.trim($("#accountFullName").val()),
            //"accountMail": $.trim($("#accountMail").val()),
            "enterpriseName": $.trim($("#accountEnterprise").val()),
            "newPassword": MD5(newPsw),
            "oldPassword": MD5(currentPsw)
        };
    } else {
        params = {
            "username": $.trim($("#accountUsername").val()),
            "name": $.trim($("#accountFullName").val()),
            //"accountMail": $.trim($("#accountMail").val()),
            "enterpriseName": $.trim($("#accountEnterprise").val())
        };
    }


    $.ajax({
        url: setUrlCsrfTokenParameter($("#projectContext").val() + "/account/edit"),
        type: "post",
        async: true,
        data: params,
        dataType: "json",
        success: function (result) {
            $("#modelPersonal").modal('hide');
            if (result.success) {
                alertPromptMsgDlg(result.message, 1, logout);
            } else {
                alertPromptMsgDlg(result.message, 3, null);
            }
        },
        error: function () {
            $("#modelPersonal").modal('hide');
            alertPromptErrorMsgDlg();
        }
    });
}

/**
 * 个人密码修改页面准备，绑定输入框、按钮
 */
function readyFormPsw() {
    $("#currentPsw").focus();

    bindModifyPswEnter("currentPsw");
    bindModifyPswEnter("newPsw");
    bindModifyPswEnter("repeatPsw");

    passwordFieldLockTips("currentPsw", Lang("system_common_tips_capslock"));
    passwordFieldLockTips("newPsw", Lang("system_common_tips_capslock"));
    passwordFieldLockTips("repeatPsw", Lang("system_common_tips_capslock"));
    $("#modifyPswBtn").click(function () {
        modifyPsw();
    });
    $("#currentPsw").blur(function () {
        validateCurrentPwd("currentPsw");
    });
    $("#newPsw").blur(function () {
        validateNewPwd("newPsw", "repeatPsw");
    });
    $("#repeatPsw").blur(function () {
        validateRepeatPwd("newPsw", "repeatPsw");
    });
}

/**
 * 绑定修改密码页的回车事件
 * @param inputId 输入框id
 */
function bindModifyPswEnter(inputId) {
    $('#' + inputId).bind('keypress', function (event) {
        var keycode = event.keyCode ? event.keyCode : event.which;
        if (keycode == 13) {
            modifyPsw();
            return false;
        }
    });
}

/**
 * 校验重复密码输入框
 * @param newPwdId 新密码输入框id
 * @param repeatPwdId 重复密码输入框id
 * @returns boolean
 * 重复密码是否为空、是否与输入的新密码相等
 */
function validateRepeatPwd(newPwdId, repeatPwdId) {
    var value = $("#" + repeatPwdId).val();
    var newPswValue = $("#" + newPwdId).val();
    if (!validateNewPwd(newPwdId, repeatPwdId)) {
        return false;
    }
    if (validateNull(value)) {
        renderInputField(false, repeatPwdId, Lang("account_tips_password_fill_repeatPwd"));
        return false;
    } else if (value != newPswValue) {
        renderInputField(false, repeatPwdId, Lang("account_tips_password_not_consistent"));
        return false;
    } else {
        renderInputField(true, repeatPwdId, "");
        return true;
    }
}

/**
 * 校验新密码输入框
 * @param newPwdId 新密码输入框id
 * @param repeatPwdId 重复密码输入框id，新密码不正确后要清除重复密码输入框的渲染
 * @returns boolean
 * 新密码是否为空、至少8位、至少一个字母数字
 */
function validateNewPwd(newPwdId, repeatPwdId) {
    var value = $("#" + newPwdId).val();
    var repeatValue = $("#" + repeatPwdId).val();
    if (validateNull(value)) {
        renderInputField(false, newPwdId, Lang("account_tips_password_fill_newPwd"));
        restoreInputField(repeatPwdId);
        return false;
    } else if (value.length < 8) {
        renderInputField(false, newPwdId, Lang("account_tips_password_8_characters_min"));
        restoreInputField(repeatPwdId);
        return false;
    } else if (!isPassword(newPwdId)) {
        restoreInputField(repeatPwdId);
        return false;
    } else if (!validateNull(repeatValue) && value != repeatValue) {
        renderInputField(false, repeatPwdId, Lang("account_tips_password_not_consistent"));
        return false;
    } else {
        renderInputField(true, newPwdId, "");
        restoreInputField(repeatPwdId);
        return true;
    }
}

/**
 * 检验当前密码输入框
 * @param id 当前密码输入框id
 * @returns {Boolean} 当前密码是否为空、至少8位
 */
function validateCurrentPwd(id) {
    var value = $("#" + id).val();
    if (validateNull(value)) {
        renderInputField(false, id, Lang("account_tips_password_fill_currentPwd"));
        return false;
    } else if (value.length < 8) {
        renderInputField(false, id, Lang("account_tips_password_8_characters_min"));
        return false;
    } else {
        restoreInputField(id);
        return true;
    }
}

/**
 * 修改密码
 * @returns {Boolean}
 */
function modifyPsw() {
    var username = $("#modifyPwdUsername").val();
    var currentPsw = $("#currentPsw").val();
    var newPsw = $("#newPsw").val();

    if (!validateCurrentPwd("currentPsw")) {
        $("#currentPsw").focus();
        return false;
    }

    if (!validateNewPwd("newPsw", "repeatPsw")) {
        $("#newPsw").focus();
        return false;
    }
    if (!validateRepeatPwd("newPsw", "repeatPsw")) {
        $("#repeatPsw").focus();
        return false;
    }

    var params = {
        "oldPassword": MD5(currentPsw),
        "newPassword": MD5(newPsw)
    };

    $.ajax({
        url: setUrlCsrfTokenParameter($("#projectContext").val() + "/account/password/edit"),
        type: "post",
        async: true,
        data: params,
        dataType: "json",
        success: function (result) {
            $("#modelPersonal").modal('hide');
            if (result.success) {
                alertPromptMsgDlg(result.message, 1, logout);
            } else {
                alertPromptMsgDlg(result.message, 3);
            }
        },
        error: function () {
            $("#modelPersonal").modal('hide');
            alertPromptErrorMsgDlg();
        }
    });
}

/**
 * 校验用户名
 * @param inputId
 */
function validateUsername() {
    var value = $.trim($("#accountUsername").val());
    if (validateNull(value)) {
        renderInputField(false, "accountUsername", Lang("account_tips_fill_username"));
        return false;
    } else if (!isAccount(value)) {
        renderInputField(false, "accountUsername", Lang("account_tips_fmt_username"));
        return false;
    } else {
        renderInputField(true, "accountUsername", "");
        return true;
    }
}

/**
 * 判断字符串是否符合账号的规则
 *
 * @param str
 * @returns
 */
function isAccount(str) {
    var pattern = /^[0-9a-zA-Z-_@\.]*$/;
    return pattern.test(str);
}

/**
 * 判断密码复杂性
 * @param str 字符串
 * @returns {Boolean}
 */
function isPassword(pwdId) {
    var pwdStr = $("#" + pwdId).val();
    var numLength;
    var chrLength;

    if (/[0-9]/.test(pwdStr)) {
        numLength = pwdStr.match(/[0-9]/g).length;
    } else {
        renderInputField(false, pwdId, Lang("system_common_tips_pwd_number"));
        return false;
    }
    if (/[a-zA-Z]/.test(pwdStr)) {
        chrLength = pwdStr.match(/[a-zA-Z]/g).length;
    } else {
        renderInputField(false, pwdId, Lang("system_common_tips_pwd_word"));
        return false;
    }
    return true;
}

/**
 * 校验名称
 * @param id 元素id
 * @param messageId 错误提示代码
 * @returns {Boolean} 是否校验通过
 */
function validateName(id, messageId) {
    var name = $.trim($("#" + id).val());
    if (!validateNull(name)) {
        renderInputField(true, id, "");
        return true;
    } else {
        renderInputField(false, id, Lang("account_tips_fill_" + messageId));
        return false;
    }
}

/**
 * 判断名称是否合法
 * @param str 名称字符串
 * @returns boolean
 */
function isName(str) {
    var pattern = /^[^~`!！@#$%\^&\*()_+=\{\}\[\]:;|\\,.?/\<\>"'"-]*$/;
    return pattern.test(str);
}

function initAccountBind(){
    $("#bind-mobile").click(forwardToBindMobile);

    $("#bind-email").click(forwardToBindEmail);
}

function forwardToBindEmail(){
    $("#bindModel").modal({backdrop: 'static', keyboard: false, remote: createUrl("/account/forwardToBindEmail/")});
}

function forwardToBindMobile(){
    $("#bindModel").modal({backdrop: 'static', keyboard: false, remote: createUrl("/account/forwardToBindMobile/")});
}

function initBindEmail(){
    $("#bindEmail").blur(function(){
        validateMail("bindEmail");
    });

    $("#toBindEmail").click(function(){
        bindEmail();
    });
}

function bindEmail() {
    if(!validateMail("bindEmail")){
        return false;
    }
    $.ajax({
        url: createUrl("/account/bindEmail"),
        type: "post",
        data: {email : $.trim($("#bindEmail").val())},
        dataType: "json",
        success: function (result) {
            if(result.success){
                alertPromptMsgDlg(result.message, 1);
                $("#bindModel").modal("hide");
            }else{
                renderInputField(false, "bindEmail", result.message);
            }
        },
        error: function () {
            alertPromptErrorMsgDlg();
        }
    });
}