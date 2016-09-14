/**
 * 提示窗口的宽度
 */
var promptDlgWidth = "400px";
/**
 * 提示窗口的高度
 */
var promptDlgHeight = "150px";

function changeLang(selectedLanguage){
    var context = $("#basePath").val();
    if (context == undefined) {
        context = $("#projectContext").val();
    }
    $.ajax({
        url : context+"/system/locale/change",
        type : "post",
        data: {language: selectedLanguage},
        async : false,
        dataType : "json",
        success : function(msg) {
            redirectTo(window.location.href);
        }
    });
}
/**
 * 验证字符串是否为空
 *
 * @param name
 * @returns true:空
 */
function validateNull(name) {
    if (name == null || $.trim(name) == "" || name.length == 0) {
        return true;
    } else {
        return false;
    }
}

/**
 * 判断给定的是否为数字
 *
 * @param {}
 *            num 参数
 * @returns {} true:是数字
 */
function isNumber(num) {
    var pattern = /^[0-9]*$/;
    return pattern.test(num);
}

/**
 * 判断给定是否为电话号码,号码允许数字、+、.、(、)、-
 * @param num 参数
 * @returns true,是电话号码
 */
function isPhone(num) {
    var pattern = /^[0-9\+\-.\(\)]*$/;
    return pattern.test(num);
}

/**
 * 初始化密码输入框，当打开大写锁定时，将会在对应的pwdDivId上显示提示语句
 *
 * @param pwdDivId
 *            密码框所在的Div Id
 * @param pwdId
 *            密码输入框的元素Id
 * @return
 */
function initPasswordField(pwdDivId, pwdId) {
    $("#" + pwdId).keypress(function (event) {
        if (checkCapsLock(event)) {
            renderInputField(false, pwdId, Lang("system_common_tips_capslock"));
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
        }
    });
}

/**
 * 检测Caps Lock键是否打开
 *
 * @param e
 */
function checkCapsLock(e) {
    var valueCapsLock = e.keyCode ? e.keyCode : e.which; // 判断Caps
    // Lock键是否按下
    var isShift = e.shiftKey || (e.keyCode == 16) || false; // shift键是否按住
    if ((valueCapsLock >= 65 && valueCapsLock <= 90) && !isShift
        || (valueCapsLock >= 97 && valueCapsLock <= 122) && isShift) {
        return true;
    } else {
        return false;
    }
}

/**
 * 检测Caps Lock键是否打开
 *
 * @param e
 */
function checkCapsLock(e, id) {
    var valueCapsLock = e.keyCode ? e.keyCode : e.which; // 判断Caps
    // Lock键是否按下
    var isShift = e.shiftKey || (e.keyCode == 16) || false; // shift键是否按住
    if ((valueCapsLock >= 65 && valueCapsLock <= 90) && !isShift
        || (valueCapsLock >= 97 && valueCapsLock <= 122) && isShift) {
        $("#" + id).html(Lang("system_common_tips_capslock"));
        return true;
    } else {
        $("#" + id).html("");
        return false;
    }
}

/**
 * 弹出消息提示框
 *
 * @param message
 *            提示的消息
 * @param type
 *            提示消息的类型, 1:success, 2:warn, 3:error。默认为0
 * @param callbackFunction
 *            回调函数的名称，不需要以字符串的形式传递
 *
 */
function alertPromptMsgDlg(message, type, callbackFunction) {
    $('#promptModalBody').html("<i id='icon-class' class=' icon-large'/>" + message);
    clearRenderPromptMsgDlg();
    if (type == 1) {
        $('#icon-class').addClass("icon-ok-sign custom-success");
    } else if (type == 2) {
        $('#icon-class').addClass("icon-exclamation-sign custom-warn");
    } else if (type == 3) {
        $('#icon-class').addClass(" icon-remove-sign custom-failed");
    }
    $('#promptModal').on(
        'shown.bs.modal',
        function () {
            $("#promptModalOkBtn").click(
                function () {
                    if (callbackFunction != null
                        && callbackFunction != ''
                        && callbackFunction != undefined) {
                        callbackFunction();
                    }
                });
        });
    $('#promptModal').modal('show');
}

/**
 * 系统级服务器错误提示调用的弹窗方法
 */
function alertPromptErrorMsgDlg() {
    hideProgressBar();
    alertPromptMsgDlg(Lang("system_common_error"), 3);
}

/**
 * 清除弹窗的渲染样式
 */
function clearRenderPromptMsgDlg() {
    $('#promptModalBody').removeClass("alert");
    $('#promptModalBody').removeClass("alert-success");
    $('#promptModalBody').removeClass("alert-warning");
    $('#promptModalBody').removeClass("alert-danger");
    $('#promptModalBody').removeClass("alert-info");
}

/**
 * 弹出消息提示框
 *
 * @param message
 *            提示的消息
 * @param type
 *            提示消息的类型, 0:info, 1:success, 2:warn, 3:error。默认为0
 * @param callbackFunction
 *            回调函数的名称，不需要以字符串的形式传递
 *
 */
function alertPromptMsgDlgForDetail(message, width, height, type) {
    $('#promptModalBody').html(message);
    if (type == 1) {
        $('#promptModalBody').addClass("alert alert-success");
    } else if (type == 2) {
        $('#promptModalBody').addClass("alert alert-warning");
    } else if (type == 3) {
        $('#promptModalBody').addClass("alert alert-danger");
    } else {
        $('#promptModalBody').addClass("alert alert-info");
    }
    $('#promptModal .modal-dialog').css('width', width);
    $('#promptModalBody').css('height', height);
    $('#promptModal').modal('show');
}

/**
 * 弹出确认消息对话框，如果点击确认则执行回调函数 调用示例： alertConfirmationMsgDlg("是否删除第三方服务："+name,
 * deleteThirdServer, number, name);
 *
 * @param message
 *            显示的提示语
 * @param callbackFunction
 *            回调函数的名称，不需要以字符串的形式传递
 * @param callbackFunctionParam...
 *            回调函数的参数（根据需要任意追加）
 */
function alertConfirmationMsgDlg() {

    // 获取传入参数
    var args = arguments;
    if (args && args.length < 2) {
        return;
    }

    // 将参数数组转化为Array类型的数组
    var arrArgs = new Array();
    for (var i = 0; i < args.length; i++) {
        arrArgs[i] = args[i];
    }

    var message = arrArgs.shift();
    var callbackFunction = arrArgs.shift();

    $('#confirmModalBody').html(message);
    $("#confirmModalOkBtn").one('click', function () {
        if (arrArgs.length >= 1) {
            callbackFunction.apply(this, arrArgs);
        } else {
            callbackFunction();
        }
    });
    $('#confirmModal').on('hide.bs.modal', function () {
        $('#confirmModalOkBtn').unbind();
    });
    $('#confirmModal').modal('show');
}
/**
 * 设置公共确认弹窗的确认按钮的颜色，当colorId==0时，表示红色，即危险操作，
 *  当colorId==1时表示绿色，表示可以撤销的操作。
 *  例：在调用alertConfirmationMsgDlg()方法前加入setConfirmationButtonColor(1),
 *  表示进行删除操作，或者撤销，重置密码等操作
 * @param colorId
 */
function setConfirmationButtonColor(colorId) {
    $("#confirmModalOkBtn").removeClass("btn-danger");
    $("#confirmModalOkBtn").removeClass("btn-success");
    if (colorId == 0) {
        $("#confirmModalOkBtn").addClass("btn-danger");
    } else if (colorId == 1) {
        $("#confirmModalOkBtn").addClass("btn-success");
    }
}
/**
 * 批量操作,如批量删除，批量发送邮件，批量重置密码等，先提交是否确认批量操作，确定后提交后台.
 *
 * @param actionUrl
 *            删除提交的URL地址，例如/context/audit/delete
 * @param message
 *           提示的消息
 */
function batchOperation(actionUrl, message) {
    var selectedItemIds = new Array();
    // 把选中行的checkBox值放入到selectedItemIds里
    $("input[name='rowCheckbox']:checked").each(function () {
        selectedItemIds.push($(this).val());
    });
    if (selectedItemIds.length == 0) {
        alertPromptMsgDlg(Lang("system_common_please_select_a_recored"), 2);
        return;
    }
    alertConfirmationMsgDlg(message, confirmOperateSelectListData, actionUrl,
        selectedItemIds);
}

/**
 * 批量删除操作，先提交是否确认删除，确定后提交后台.
 *
 * @param actionUrl
 *            删除提交的URL地址，例如/context/audit/delete
 */
function batchDel(actionUrl) {
    var selectedItemIds = new Array();
    // 把选中行的checkBox值放入到selectedItemIds里
    $("input[name='rowCheckbox']:checked").each(function () {
        selectedItemIds.push($(this).val());
    });
    if (selectedItemIds.length == 0) {
        alertPromptMsgDlg(Lang("system_common_prompt_please_select_a_recored"), 2);
        return;
    }
    var message = Lang("system_common_confirm_delete_the_records");
    alertConfirmationMsgDlg(message, confirmDelSelectedListData, actionUrl,
        selectedItemIds);
}
/**
 * 批量进行某些操作，不过带有进度条,并且是异步的.
 * @param actionUrl
 * @param selectedItemIds
 */
function confirmOperateSelectListData(actionUrl, selectedItemIds, callbackfunction) {
    showProgress();
    var ids = "";
    for (var i = 0; i < selectedItemIds.length; i++) {
        if (i == selectedItemIds.length - 1) {
            ids += selectedItemIds[i];
        } else {
            ids = ids + selectedItemIds[i] + ",";
        }
    }
    var param = {
        ids: ids
    };
    $('#confirmModal').modal('hide');
    $.ajax({
        url: actionUrl,
        type: "post",
        async: true,
        data: param,
        dataType: "json",
        success: function (msg) {
            hideProgressBar();
            if (msg.ret >= 0){
                if (callbackfunction == null || callbackfunction == undefined) {
                    alertPromptMsgDlg(msg.msg, 1, reload);
                } else {
                    alertPromptMsgDlg(msg.msg, 1, callbackfunction);
                }
            }else {
                alertPromptMsgDlg(msg.msg, 3);
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alertPromptErrorMsgDlg();
        }
    });
    var isAllSelected = document.getElementById("checkboxAll");
    if (isAllSelected != null || isAllSelected != undefined) {
        isAllSelected.checked = false;
        $("input[name='rowCheckbox']").removeAttr("checked");
    }
}

/**
 * 确定删除所选择的列表数据，删除成功后会刷新页面.
 *
 * @param actionUrl
 *            删除提交的URL地址，例如/context/audit/delete
 * @param selectedItemIds
 *            所选择的数据项的Id，采用数组形式，如["12","11"]
 */
function confirmDelSelectedListData(actionUrl, selectedItemIds) {
    $.ajax({
        url: actionUrl,
        type: "post",
        async: false,
        data: JSON.stringify(selectedItemIds),
        dataType: "json",
        contentType: "application/json",
        success: function (msg) {
            var result = msg.message;
            if (msg.success) {
                alertPromptMsgDlg(msg.message, 1, reload);
            } else {
                alertPromptMsgDlg(msg.message, 3);
            }
        }
    });
    $("input[name='rowCheckbox']").removeAttr("checked");
}

function reload() {
    window.location.reload();
}

function redirectTo(url) {
    window.location.href = url;
}
/**
 * 项目中所有的表格列表页面公用脚本，包括表格的分页、动态显示等。 启动页面加载项，对表格进行效果处理
 *
 * @returns {}
 */
$(document).ready(function () {
    handleTableRowEffect();

    $("#pageGo").click(function () {
        var pageNum = $.trim($("#pageNumInput").val());
        clickPageNumHref(pageNum);
    });
});

/**
 * 处理表格行的效果，包括hover、click等效果
 *
 * @returns {}
 */
function handleTableRowEffect() {
    $(".table tr").each(function () {
        $(this).click(function () {
            $(".table tr").each(function () {
                $(this).removeClass("active");
            });
            $(this).addClass("active");
        });
    });
    addTableSortEffect();
}

/**
 * 对使用data-grid的表格添加表头点击排序的处理逻辑
 *
 * @author yjz
 */
function addTableSortEffect() {
    // 取出table样式下的所有th
    var dtSelector = ".table th";
    var thList = $(dtSelector);
    // 取出隐藏域的值 orderbyField,orderbyType隐藏域设置在pageModel里面
    var sortField = $("#orderbyField").val();
    var sortType = $("#orderbyType").val();
    // 判断排序字段是否为空，不为空根据升降序，设置标题后面的排序图标
    if (sortField != '') {
        if (sortType == "desc") {
            var currentHtml = $("#" + sortField).html();
            if (currentHtml != undefined
                && currentHtml.indexOf("glyphicon glyphicon-arrow-down") <= 0) {
                currentHtml = currentHtml
                + "&nbsp;<span class='header-order-icon glyphicon glyphicon-arrow-down'></span>";
            }
            $("#" + sortField).html(currentHtml);
            sortType = "asc";// 降序点击后变升序
        } else {
            var currentHtml = $("#" + sortField).html();
            if (currentHtml != undefined
                && currentHtml.indexOf("glyphicon glyphicon-arrow-up") <= 0) {
                currentHtml = currentHtml
                + "&nbsp;<span class='header-order-icon glyphicon glyphicon-arrow-up'></span>";
            }
            $("#" + sortField).html(currentHtml);
            sortType = "desc";// 升序点击后变降序
        }
    }
    // 设置每个th点击排序逻辑
    thList.each(function () {
        $(this).click(function () {
            // 只有有设置id的th才进行排序，id以排序字段命名
            if ($(this).attr("id")) {
                $("#orderbyField").val($(this).attr("id"));
                $("#orderbyType").val(sortType);
                var url = window.location.href;
                var byField = $("#orderbyField").val()
                url = setUrlParam(url, "orderbyField", byField);
                window.location.href = url;
            }
        });
    });
}


/**
 * 设置当前的显示页面导航，例如当前在项目，则显示项目的导航.<br/>
 * 对于头部的导航通过查找id为navigation下的所有li元素，移除active类，然后再对指定的headerNavId添加active类.<br/>
 * 对于左侧的导航通过查找id以collapse开头的所有div下的ul li元素，移除active类，然后再对指定的leftNavId添加active类.
 *
 * @author lbt
 * @since 2014-10-15
 * @param headerNavId
 *            头部的导航Id
 * @param leftNavId
 *            左侧的导航Id，如果没有则不需要传递
 */
function setCurrentPageNavigation(headerNavId, leftNavId) {
    $("#navigation li").each(function () {
        $(this).removeClass("active");
    });
    $("#" + headerNavId).addClass("active");

    if (leftNavId != null && leftNavId != '' && leftNavId != undefined) {
        $("div[id^='collapse'] ul li").each(function () {
            $(this).removeClass("active");
        });
        $("#" + leftNavId).addClass("active");
        $("div[id^='collapse']").each(function () {
            $(this).removeClass("in");
        });
        $("#" + leftNavId).parents("div").addClass("in");
    }
}

/**
 * 点击页码的链接，请求新页面
 *
 * @param {}
 *            pageNum
 * @returns {}
 */

function clickPageNumHref(pageNum) {
    // 取出隐藏域的值 orderbyField,orderbyType隐藏域设置在pageModel里面
    var sortField = $("#orderbyField").val();
    var sortType = $("#orderbyType").val();
    var url = window.location.href;
    // 去掉页码的参数，使用传递进来的值
    if (url.indexOf("pageNum") > -1) {
        url = url.substring(0, url.indexOf("pageNum") - 1);
    }

    var validPageNum = getValidPageNum("" + pageNum);

    url = setUrlParam(url, "pageNum", validPageNum);
    url = setUrlParam(url, "orderbyField", $("#orderbyField").val());
    url = setUrlParam(url, "orderbyType", sortType);
    location.href = url;
}

/**
 * 输入页码请求新页面，回车之后生效
 *
 * @param {}
 *            event 键盘事件
 * @returns {}
 */

function inputPageNum(event) {
    var keycode = event.keyCode ? event.keyCode : event.which;
    if (keycode == 13) {
        var pageNum = $.trim($("#pageNumInput").val());
        clickPageNumHref(pageNum);
        return false;
    }
}

/**
 * 检测页码,获取有效的页码
 *
 * @param {}
 *            strPageNum 页码，以string的方式传递进来
 * @returns {} 有效的pageNum
 */

function getValidPageNum(strPageNum) {
    var patrn = /^[0-9]*$/;
    if (strPageNum == "" || strPageNum == null) {
        return 1;
    }

    if (!patrn.exec(strPageNum)) {
        return 1;
    }

    var pageNum = parseInt(strPageNum);
    var totalPage = parseInt($("#totalPage").val());

    if (pageNum < 1) {
        return 1;
    }

    if (pageNum > totalPage) {
        return totalPage;
    }

    return pageNum;
}

/**
 * 显示进度条加载进度条 进度条div已经在header.jsp中定义
 *
 * @param prompt
 *            提示语的参数，如果未定义则使用默认的(请稍等...)作为提示语
 */
function showProgress(prompt) {
    if (prompt == "" || prompt == undefined) {
        prompt = Lang("system_common_please_wait");
    }
    $("#progressBarTitle").text(prompt);
    $("#modalProgressBar").modal('show');
}

/**
 * 列表中选中所有checkBox.
 * 如果Id为checkboxAll的checkbox选中时，所有name为rowCheckbox的Checkbox都会被选中，否则不选中.
 */
function selectAllListCheckBox() {
    var isAllSelected = document.getElementById("checkboxAll");
    var arrRecIds = document.getElementsByName("rowCheckbox");

    for (var i = 0; i < arrRecIds.length; i++) {
        if (isAllSelected.checked) {
            arrRecIds[i].checked = true;
        } else {
            arrRecIds[i].checked = false;
        }
    }
}

/**
 * 隐藏进度条
 */
function hideProgressBar() {
    $("#modalProgressBar").modal('hide');
    $(".btn-loading").button('reset');
}

/**
 * 构造搜索框，输入框的title属性作为默认的显示提示语
 *
 * @author lbt
 * @since 2014-5-30
 * @param searchInputId
 *            搜索框的Id
 * @param validSearchFuncName
 *            搜索框的输入字符校验函数名称，不以字符串的方式传递<br/>
 *            通常在输入过程中会进行限制，在keypress时会回调该方法，方法可接收参数event<br/>
 *            如果方法通过校验需要返回true,否则无法显示输入值。<br/>
 *            如果不需要字符校验，则传递null即可<br/>
 *            例如校验输入函数为valid(event),则只需要传递为valid<br/>
 * @param searchFuncName
 *            搜索的函数名称，不以字符串的方式传递<br/>
 *            在keydown时按下回车键后会回调该方法。<br/>
 *            例如搜索函数为search()，则只需要传递search
 */
function searchInput(searchInputId, validSearchFuncName, searchFuncName) {
    $("#" + searchInputId).keydown(function (event) {
        var keycode = event.keyCode ? event.keyCode : event.which;
        // enter
        if (keycode == 13 && searchFuncName != null && searchFuncName != undefined && !validateNull($("#" + searchInputId).val())) {
            searchFuncName();
            // 该语句用于解决IE9下回车后会执行第1个按钮事件
            return false;
        }
    }).keypress(function (event) {
        if (validSearchFuncName != null && validSearchFuncName != undefined) {
            return validSearchFuncName(event);
        }
    });
}

// email的判断。
function isMail(mail) {
    if (validateNull(mail)) {
        return false;
    }
    return (new RegExp(
        /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)
        .test(mail));
}

/**
 * 为URL添加CSRFToken参数，csrfToken参数直接添加在URL的第一个参数中.
 *
 * @param url
 *            例如/dms/user/save-add
 * @returns /dms/user/save-add?csrfToken=xxxxx
 */
function setUrlCsrfTokenParameter(url) {
    var csrfToken = $("#csrfToken").val();
    return url + "?csrfToken=" + csrfToken;
}

/**
 * 设置或者获取url参数.<br/> 如果没有value值，则直接返回url <br/> 如果url已存在该参数名称和值，则替换值<br/>
 * 如果不存在，则根据是否有?添加参数
 *
 * @author liyl
 * @since 2014-11-3
 *
 * @param {}
 *            url 要设置的url，例如：/rdmp/project/list
 * @param {}
 *            name 参数名称,例如：title
 * @param {}
 *            value 参数值，例如：需求
 * @return {} /rdmp/project/list?title=需求
 *
 */
function setUrlParam(url, name, value) {
    if (getUrlParam(url, name) == "") {
        url = deleteUrlParam(url, name);
    }
    var reg = new RegExp("(\\\?|&)" + name + "=([^&]*)(&|$)", "i");
    var m = url.match(reg);
    // 如果值为空，就去掉该参数项
    if (value == null || value == undefined || value == '') {
        url = deleteUrlParam(url, name);
        return url;
    }
    if (m) {
        return (url.replace(reg, function ($0, $1, $2) {
            return ($0.replace($2, escape(value)));
        }));
    } else {
        if (url.indexOf('?') == -1) {
            return (url + '?' + name + '=' + escape(value));
        } else {
            return (url + '&' + name + '=' + escape(value));
        }
    }
}


function deleteUrlParam(url, name) {
    // 重写
    name += "=";// 防止查找到 value 为title的
    var idxOfName = url.indexOf(name);
    if (idxOfName == -1) {
        return url;
    }
    var cart = url.charAt(idxOfName - 1);// 查看该参数是在url的什么位置
    // 以name的参数为起始位置，截断参数字符串
    var tmpUrlParams = url.substring(idxOfName, url.length);
    var baseUrl = url.substring(0, idxOfName);
    // 查找该字符串的第一个& 也可能为空
    var idxOfAmp = tmpUrlParams.indexOf("&");
    if (idxOfAmp == -1) {
        // 未找到&，即要删除的参数为最后一个
        return baseUrl.substring(0, baseUrl.length - 1);
    }
    // 找到了&
    // 如果该参数的前一个字符为 ?
    tmpUrlParams = tmpUrlParams
        .substring(idxOfAmp + 1, tmpUrlParams.length + 1);
    return baseUrl + tmpUrlParams;
}

function getUrlParam(url, name) {
    var reg = new RegExp("(\\\?|&)" + name + "=([^&]*)(&|$)", "i");
    var m = url.match(reg);
    if (m) {
        return m[2];
    }
    return "";
}

/**
 * 渲染表单输入框错误域的效果.</br> 命名规则：</br> 错误域所在的div 以div+错误域的ID</br> 错误域外层的span
 * 以span+错误域的ID</br> 错误提示标签icon 以icon+错误域的ID</br> 错误信息lable 以error+错误域的ID</br>
 *
 * @param isSuccess
 *            渲染状态 success时为true； error时为false
 * @param fieldId
 *            错误域的Id，例如projectTitle
 * @param errorMsg
 *            错误的提示信息，例如:名称不能为空
 */
function renderInputField(isSuccess, fieldId, errorMsg) {
    var newFileldId = fieldId.substr(0, 1).toLocaleUpperCase()
        + fieldId.slice(1);
    if (isSuccess) {
        $("#div" + newFileldId).removeClass("has-error");
        //$("#div" + newFileldId).addClass("has-success");
        //$("#span" + newFileldId).addClass("block input-icon input-icon-right");
        $("#icon" + newFileldId).removeClass("icon-warning-sign icon-large");
        //$("#icon" + newFileldId).addClass("icon-ok-sign icon-large");
        $("#error" + newFileldId).html("&nbsp; ");
    } else {
        $("#div" + newFileldId).removeClass("has-success");
        $("#div" + newFileldId).addClass("has-error");
        $("#span" + newFileldId).addClass("block input-icon input-icon-right");
        $("#icon" + newFileldId).removeClass("icon-ok-sign icon-large");
        $("#icon" + newFileldId).addClass("icon-warning-sign icon-large");
        $("#error" + newFileldId).text(errorMsg);
    }
}
/**
 * 还原表单输入框的效果.</br> 命名规则：</br> 错误域所在的div 以div+错误域的ID</br> 错误域外层的span
 * 以span+错误域的ID</br> 错误提示标签icon 以icon+错误域的ID</br> 错误信息lable 以error+错误域的ID</br>
 *
 * @param fieldId
 *            错误域的Id，例如projectTitle
 */
function restoreInputField(fieldId) {
    var newFileldId = fieldId.substr(0, 1).toLocaleUpperCase()
        + fieldId.slice(1);
    $("#div" + newFileldId).removeClass("has-error");
    $("#div" + newFileldId).removeClass("has-success");
    $("#icon" + newFileldId).removeClass("icon-warning-sign icon-large");
    $("#icon" + newFileldId).removeClass("icon-ok-sign icon-large");
    $("#error" + newFileldId).html("&nbsp;");
}


/**
 * 初始化文件上传的组件.
 *
 * @param belongToType
 *            所属的类型，1：企业，2：License
 * @param belongToId
 *            所属的目标对象Id，如果初始化则传递0
 * @param okBtnId
 *            确定按钮的Id，用于上传过程中会控制状态
 */
function readyFileUpload(belongToType, belongToId, okBtnId) {
    $('#uploadFileProgress').hide();
    var isIE = IEVersion() == 8 || IEVersion() == 9;
    $("#fileupload")
        .fileupload(
        {
            url: $("#projectContext").val()
            + "/attachment/upload",
            formData: {
                // 所属类型1：企业
                "belongToType": belongToType,
                "belongToId": belongToId
            },
            dataType: 'json',
            autoUpload: false,
            done: function (e, data) {
                if (okBtnId != null && okBtnId != undefined && okBtnId != '') {
                    $("#" + okBtnId).removeAttr("disabled");
                }
                if (data.result.files == undefined) {
                    alertPromptMsgDlg(Lang("system_common_check_file_format_size"), 3);
                } else {
                    var downloadReqUrl = $("#projectContext").val()
                        + "/attachment/download";
                    $
                        .each(
                        data.result.files,
                        function (index, file) {
                            var filePath = ""
                                + file.uploadFile;
                            var html = "<tr id='"
                                + file.uploadFile
                                + "' name='" + escape(file.name)
                                + "'>"
                                + "<td width='60%;'>"
                                + escape(file.name)
                                + "</td>"
                                + "<td width='25%;'>"
                                + byteFormat(file.size)
                                + "</td>"
                                + "<td>"
                                + "<button type='button' class='btn btn-default btn-xm' "
                                + "onclick=\"confirmDeleteFile($(this),"
                                + file.size + ");\">"
                                + "<i class='icon-remove'></i></button>"
                                + "</td>" + "</tr>";
                            $("#files").append(html);

                            // set current total file size
                            var totalFileSize = parseInt($("#totalFileSize").val());
                            totalFileSize += file.size;
                            $("#totalFileSize").val(totalFileSize)
                        });
                }
                $('#uploadFileProgress').hide();
                if (isIE) {
                    hideProgressBar();
                }
            },
            add: function (e, data) {
                var originalFieSize = 0;
                var needSubmit = true;
                $.each(data.originalFiles,
                    function (index, file) {
                        // valid file name suffix
                        var fileName = file.name + "";
                        var dotIndex = fileName.lastIndexOf(".");
                        var fileStandTyp = new Array(".jpg", ".jpeg", ".png", ".bmp", ".gif");
                        var suffix = fileName.substring(dotIndex, fileName.length);
                        if (fileName.length > 256) {
                            alertPromptMsgDlg(Lang("attachment_invalid_file_name_too_long"), 3);
                            needSubmit = false;
                            return;
                        }
                        if (fileName.length != 0) {
                            if ($.inArray(suffix.toLowerCase(), fileStandTyp) == -1) {
                                alertPromptMsgDlg(Lang("please_choose_correct_file"), 3);
                                needSubmit = false;
                                return;
                            }
                        }

                        // single file 5M
                        var singleFileSize = file.size;
                        if (file.size > 5242880) {
                            alertPromptMsgDlg(Lang("system_common_file_can_not_over_5"), 3);
                            needSubmit = false;
                            return;
                        }

                        originalFieSize += singleFileSize;
                    });

                if (!needSubmit) {
                    $('#uploadFileProgress').hide();
                    if (isIE) {
                        hideProgressBar();
                    }
                    return;
                }
                // total file size 10M
                var totalFileSize = parseInt($("#totalFileSize").val());
                totalFileSize += originalFieSize;
                if (totalFileSize > 10485760) {
                    alertPromptMsgDlg(Lang("system_common_file_size_limit")
                    + byteFormat(10485760), 3);
                    return;
                }
                if (isIE) {
                    showProgress();
                }
                data.submit();
            },
            progressall: function (e, data) {
                if (okBtnId != null && okBtnId != undefined && okBtnId != '') {
                    $("#" + okBtnId).attr("disabled", "disabled");
                }
                $('#uploadFileProgress').show();
                var progress = parseInt(data.loaded * 100
                / data.total, 10);
                $('#uploadFileProgressBar').css('width',
                    progress + '%');
            }
        });
}

/**
 * 将文件大小转换成合适的单位显示出来，原本file.length得到的是B，后面根据实际大小，换算成KB或者MB，保留小数点后2位.
 * @author tyf
 * @since 2014-12-15
 *
 * @param fileSize 文件的大小，例如11236
 *
 * @return 实际显示的内容，例如1.23 KB
 */
function byteFormat(fileSize) {
    if (fileSize < 1024) {
        return fileSize + " B";
    } else {
        var kSize = Math.round(fileSize / 1024 * 100) / 100;
        if (kSize < 1024) {
            return kSize + " KB";
        } else {
            var mSize = Math.round(fileSize / 1024 / 1024 * 100) / 100;
            return mSize + " MB";
        }
    }
}

/**
 * 用于存储被点击的删除文件按钮.
 */
var deleteFileBtn;

/**
 * 确认是否需要删除文件.
 *
 * @param button
 */
function confirmDeleteFile(button, fileSize) {
    deleteFileBtn = button;
    var fileName = deleteFileBtn.parent().parent().attr("name");
    setConfirmationButtonColor(0);
    alertConfirmationMsgDlg(Lang("system_common_confirm_delete_file") + escape(fileName), deleteFile, fileSize);
}

/**
 * 确定删除文件
 */
function deleteFile(fileSize) {
    var filePath = deleteFileBtn.parent().parent().attr("id");
    requestDeleteAttachement(deleteFileBtn, filePath.replace(/\\/g, "\\"));
    deleteFileBtn = null;
    var totalFileSize = parseInt($("#totalFileSize").val()) - parseInt(fileSize);
    $("#totalFileSize").val(totalFileSize);
}


/**
 * 请求删除附件.
 *
 * @param button
 *            当前的按钮对象
 * @param path
 *            附件在服务器上的路径
 */
function requestDeleteAttachement(button, path) {
    var params = {
        "path": encodeURIComponent(path)
    };
    var flag;
    $.ajax({
        url: $("#projectContext").val() + "/attachment/delete",
        type: "post",
        async: true,
        data: params,
        dataType: 'json',
        success: function (msg) {
            flag = msg;
            if (flag.success) {
                $(button).parent().parent().remove();
                alertPromptMsgDlg(Lang("system_common_delete_success"), 1);
            } else {
                alertPromptMsgDlg(Lang("system_common_delete_failed"), 3);
            }
        }
    });
}

/**
 * 将特殊字符转译
 *
 * @param str
 * @returns
 */
function escape(str) {

    if (validateNull(str)) {
        return str;
    }

    var newString = "" + str;

    newString = newString.replace(new RegExp(/&/g), "&amp;");
    newString = newString.replace(new RegExp(/</g), "&lt;");
    newString = newString.replace(new RegExp(/>/g), "&gt;");
    newString = newString.replace(new RegExp(/\"/g), "&quot;");
    newString = newString.replace(new RegExp(/\'/g), "&apos;");

    return newString;
}

function decode(str) {

    if (validateNull(str)) {
        return str;
    }

    var newString = "" + str;

    newString = newString.replace(/&amp;/g,'&' );
    newString = newString.replace( /&lt;/g,'<');
    newString = newString.replace(/&gt;/g,'>' );
    newString = newString.replace( /&quot;/g,'\"');
    newString = newString.replace(/&apos;/g,'\'' );

    return newString;
}

/**
 * 将特殊字符转化成16进制，以便后台能够读取。通常用在get请求，url的参数内容带有特殊字符的情况下使用。
 * 目前用于将url作为参数传递。
 */
function URLencode(sStr) {
    return escape(sStr).replace(/\+/g, "%2B").replace(/\"/g, '%22').replace(
        /\'/g, '%27').replace(/\//g, '%2F').replace(/&/g, '%26').replace(
        /=/g, '%3D');
}

function URLdecode(sStr) {
    return decode(sStr).replace(/%2B/g,'\+').replace(/%22/g,'\"').replace(
        /%27/g,'\'' ).replace( /%2F/g,'\/').replace(/%26/g,'&').replace(
        /%3D/g,'=');
}

/**
 * 对验证码输入框进行绑定，在初始化页面时调用.</br>
 * 默认验证码输入框id：captcha</br>
 * 默认验证码图片id：img-captcha</br>
 * 默认创建验证码请求uri：/createCaptcha</br>
 * 默认校验验证码请求uri：/checkCaptcha</br>
 * <li>加载验证码图片</li>
 * <li>绑定验证码输入框焦点丢失效果</li>
 * <li>绑定验证码输入框keyup事件</li>
 * @param captchaId 验证码输入框的id
 * @param contextPath 系统基本路径
 * @param messageId 验证码提示组件id,可为空
 *
 */
function bindCaptchaInput(messageId) {
    var captchaId = "captcha";
    var captchaImgId = "img-captcha";
    var createCaptchaUri = "/createCaptcha";
    document.getElementById(captchaImgId).src = getContextPath() + createCaptchaUri;

    $("#" + captchaId).blur(function () {
        if (!validateNull($("#" + captchaId).val())) {
            checkCaptcha(messageId);
        }
    });
    $("#" + captchaId).keyup(function (event) {
        var keycode = event.keyCode ? event.keyCode : event.which;
        if (keycode != 13) {//屏蔽回车事件，防止回车登陆时再次进行验证码校验而导致错误
            $("#" + captchaId).attr("checked", false);
            var length = $("#" + captchaId).val().length;
            if (length < 4) {
                restoreInputField(captchaId);
                setMessage(messageId, "");
            } else if (length == 4) {
                submitCaptcha(messageId);
            } else {
                renderInputField(false, captchaId, Lang("system_common_tips_captcha_error"));
                setMessage(messageId, Lang("system_common_tips_captcha_error"));
            }
        }
    });
}

/**
 * 校验验证码<br>
 * jsp中定义验证码的id为captcha
 * @param messageId 验证码提示组件id,可为空
 * @returns {Boolean} true:验证码正确
 */
function checkCaptcha(messageId) {
    var captchaId = "captcha";
    var captcha = $.trim($("#" + captchaId).val());
    if (validateNull(captcha)) {
        renderInputField(false, captchaId, Lang("system_common_tips_fill_captcha"));
        $("#" + captchaId).attr("checked", true);
        setMessage(messageId, Lang("system_common_tips_fill_captcha"));
        return false;
    }
    if (captcha.length != 4 && captcha.length != 0) {
        renderInputField(false, captchaId, Lang("system_common_tips_captcha_error"));
        $("#" + captchaId).attr("checked", true);
        setMessage(messageId, Lang("system_common_tips_captcha_error"))
        return false;
    }
    if (captcha.length == 4) {
        return submitCaptcha(messageId);
    }
}

/**
 * 到后台验证验证码
 * @param captchaId 验证码输入框的id
 * @param contextPath 系统基本路径
 * @param messageId 验证码提示组件id,可为空
 * @returns {Boolean} true:验证码正确
 */
function submitCaptcha(messageId) {
    var captchaId = "captcha";
    var checkCaptchaUri = "/checkCaptcha";
    if ($("#" + captchaId).attr("checked")) {
        var newFileldId = captchaId.substr(0, 1).toLocaleUpperCase()
            + captchaId.slice(1);
        return $("#div" + newFileldId).attr("class").indexOf("has-error") == -1;
    }
    var result = false;
    var inputCaptcha = $("#" + captchaId).val();
    var checkUrl = getContextPath() + checkCaptchaUri + "?inputCaptcha=" + $.trim(inputCaptcha);
    $.ajax({
        url: checkUrl,
        type: "post",
        async: false,
        dataType: "json",
        success: function (msg) {
            $("#" + captchaId).attr("checked", true);
            renderInputField(msg.success, captchaId, msg.message);
            setMessage(messageId, msg.message);
            result = msg.success;
        },
        error: function () {
            alertPromptMsgDlg(Lang("system_common_captcha_check_failed"), 3);
        }
    });
    return result;
}

function setMessage(messageId, message) {
    if (!validateNull(messageId)) {
        $("#" + messageId).text(message);
    }
}

/**
 * 重新加载验证码
 * @param imgId 验证码图片id
 * @param inputId 验证码输入框id
 * @param contextPath 系统基本路径
 */
function reloadCaptcha() {
    var captchaId = "captcha";
    var captchaImgId = "img-captcha";
    var createCaptchaUri = "/createCaptcha";
    document.getElementById(captchaImgId).src = getContextPath() + createCaptchaUri
    + "?nocache=" + new Date().getTime();
    $("#" + captchaId).val("");
    $("#" + captchaId).attr("checked", false);
}

/**
 * 获取绝对路径
 * 目前jsp中绝对路径赋值给id为projectContext或basePath
 * @returns 系统绝对路径
 */
function getContextPath() {
    var contextPath = $("#projectContext").val();
    if (validateNull(contextPath)) {
        contextPath = $("#basePath").val();
    }
    if (validateNull(contextPath)) {
        contextPath = "";
    }
    return contextPath;
}

/**
 * 拦截当前页面上的所有ajax操作，
 * 当页面上有ajax在执行的时候，将当前页面上所有具有提交功能的按钮禁用（样式为btn-success的按钮），
 * 前提：所有ajax请求都是用jquery的$.ajax发起的，而非原生的XHR；
 * 当所有ajax结束以后，将所有按钮重新启用。
 */
function ajaxControll() {
    //前提：所有ajax请求都是用jquery的$.ajax发起的，而非原生的XHR；
    var ajaxBack = $.ajax;
    var ajaxCount = 0;
    var doCount = 0;
    var allAjaxDone = function () {
        $(".btn-success").removeAttr("disabled");
    } //一行代码，就可以知道所有ajax请求什么时候结束
    //由于get/post/getJSON等，最后还是调用到ajax，因此只要改ajax函数即可
    $.ajax = function (setting) {
        ajaxCount++;
        $(".btn-success").attr("disabled", "disabled");
        var complete = setting.complete;
        setting.complete = function () {
            if ($.isFunction(complete)) {
                complete.apply(setting.context, arguments);
            }
            ajaxCount--;
            if (ajaxCount == 0 && $.isFunction(allAjaxDone)) {
                allAjaxDone();
            }
        }

        ajaxBack(setting);
    }
}

/**
 * 返回上一级
 */
function backToHistory() {


    if (IEVersion() == 8) {
        history.go(-1);
    } else {
        self.location = document.referrer;
    }
}

/**
 * 判断ie浏览器版本，只判断是否是ie6、ie7、ie8,ie9
 * @returns 6、7、8,9，ie版本；0，非ie6、7、8,9
 */
function IEVersion() {
    if (window.ActiveXObject) {
        var browser = navigator.appName;
        var version = navigator.appVersion.split(";");
        var trimVersion = version[1].replace(/[ ]/g, "");
        if (browser == "Microsoft Internet Explorer") {
            switch (trimVersion) {
                case "MSIE6.0":
                    return 6;
                case "MSIE7.0":
                    return 7;
                case "MSIE8.0":
                    return 8;
                case "MSIE9.0":
                    return 9;
                default:
                    return 0;
            }
        }
    }
    return 0;
}


/**
 * 设置日期选择器的开始时间
 * @param divId 日期选择器组件id
 * @param limitData 限制的开始时间，默认为2016年开始
 */
function setDateTimePickerStart(divId, limitData) {
    if (validateNull(limitData)) {
        limitData = '2016-01-01 00:00:00';
    }
    $('#' + divId).datetimepicker('setStartDate', limitData);
}

/**
 * 设置日期选择器的结束时间
 * @param divId 日期选择器组件id
 * @param limitData 限制的结束时间，默认为当前时间
 */
function setDateTimePickerEnd(divId, limitData) {
    if (validateNull(limitData)) {
        var year = (new Date()).getUTCFullYear() + 20;
        limitData = year + "-12-31 23:59:59";
    }
    $('#' + divId).datetimepicker('setEndDate', limitData);
}


/**
 * Create absolute url
 * @param uri
 * @return url
 */
function createUrl(uri) {
    return $("#projectContext").val() + uri;
}

function Map() {
    /** 存放键的数组(遍历用到) */
    /** 存放数据 */
    this.data = new Object();

    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function (key, value) {
        this.data[key] = value;
    };

    /**
     * 获取某键对应的值
     * @param {String} key
     * @return {Object} value
     */
    this.get = function (key) {
        return this.data[key];
    };

    /**
     * 删除一个键值对
     * @param {String} key
     */
    this.remove = function (key) {
        delete this.data[key];
    };
}


/**
 * 截取过长的字符串，将大写字母转换成**后进行计算长度
 *
 * @param {}
 *            str 原始的字符串
 * @param {}
 *            minLength 显示的最小长度
 * @param {}
 *            maxLength 显示的最大长度
 * @returns {}
 */
function cutOutTooLongString(str, minLength, maxLength) {
    var text = $.trim(str);
    var tempLength = text.replace(/[A-Z]/g, "**").length;
    var realLength = text.length;
    var rCount = maxLength - (tempLength - realLength);
    var displayText = str;
    if (tempLength > realLength && maxLength > rCount && rCount > minLength
        && tempLength > maxLength) {
        displayText = text.substring(0, rCount) + "...";
    } else if (tempLength > realLength && maxLength > rCount
        && tempLength > maxLength) {
        displayText = text.substring(0, minLength) + "...";
    } else if (tempLength == realLength && tempLength > maxLength) {
        displayText = text.substring(0, maxLength) + "...";
    }
    return displayText;
}
/**
 * 判断字符串是否符合账号的规则
 * 英文字母、数字、下划线、点号（.），要求以英文字母开头
 * @param str
 * @returns
 */
function isAccount(str) {
    var pattern = /^[a-zA-z]+([0-9a-zA-Z_\.])*$/;
    return pattern.test(str);
}

function isDate(str) {
    return !/Invalid|NaN/.test(new Date(str).toString())
}

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 获取页码数组
 * @param pageNo
 * @param totalPage
 * @returns {Array}
 */
function getPageNoArray(pageNo,totalPage){
    var b=pageNo-2;
    if(b<1) b=1;
    if(b+5>totalPage){
        b=totalPage-5;
        if(b<1) b=1;
    }
    var pages=new Array();
    for(var i=b;i<(b+5)&&i<=totalPage;i++){
        pages.push(i);
    }
    return pages;
}
/**
 * 拼接页码模板数据
 * @param result
 * @returns {{pageModel: (result.attrs|{pageSize, pageNo, total}), totalPages: number, pageArray: Array}}
 */
function executePageModel(result) {
    var totalPages=Math.ceil(result.attrs.total/result.attrs.pageSize);
    var pageArray = getPageNoArray(result.attrs.pageNo, totalPages);
    return {"pageModel": result.attrs,"totalPages": totalPages, "pageArray": pageArray};
}



