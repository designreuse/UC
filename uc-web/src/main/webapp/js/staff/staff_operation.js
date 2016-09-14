var content_right = $("#orgManagementRightContent");

$(content_right).on("click", "a[id^='lock_']", function () {
    var id = $(this).attr("id").replace("lock_", "");
    alertConfirmationMsgDlgDetail("锁定员工", "锁定后，禁用该员工终端登录权限", "锁定", changeStaffStatus, 'lock', id);
});
$(content_right).on("click", "a[id^='unlock_']", function () {
    var id = $(this).attr("id").replace("unlock_", "");
    alertConfirmationMsgDlgDetail("解锁员工", "解锁后，该员工终端可登录。", "解锁", changeStaffStatus, 'unlock', id);
});
$(content_right).on("click", "a[id^='recycle_']", function () {
    var id = $(this).attr("id").replace("recycle_", "");
    alertConfirmationMsgDlgDetail("禁用员工", "禁用后，该员工放入禁用员工列表，所有数据不可见。", "禁用", changeStaffStatus, 'recycle', id);
});

$(content_right).on("click", "a[id^='revert_']", function () {
    var id = $(this).attr("id").replace("revert_", "");
    alertConfirmationMsgDlgDetail("还原员工", "还原后，进入用户信息编辑页确认信息是否正确。", "还原", revertStaff, id);
});
$(content_right).on("click", "a[id^='delete_']", function () {
    var id = $(this).attr("id").replace("delete_", "");
    alertConfirmationMsgDlgDetail("删除员工", "删除后，员工所有数据会丢失，不可恢复。", "删除", deleteStaff, [id]);
});
$(content_right).on("click", "a[id^='mail_']", function () {
    var id = $(this).attr("id").replace("mail_", "");
    alertConfirmationMsgDlgDetail("发送账号", "通过邮箱发送，该员工将接收到IM/话机的信息。", "发送", mailStaff, [id]);
});

$("#orgManagementRightContent a[id^='more_']").popover('show');
$(content_right).on("click", "#checkAllStaff", function () {
    $(content_right).find("input[id^='staffCheck_']").each(function () {
        $(this).prop('checked', !$(this).prop('checked'));
    });
    if ($(this).prop('checked')) {
        $(content_right).find('#batchOperationDiv').show();
    } else {
        $(content_right).find('#batchOperationDiv').hide();
    }
});
$(content_right).on('click', "input[id^='staffCheck_']", function () {
    if ($(this).prop('checked')) {
        $(content_right).find('#batchOperationDiv').show();
    }
});

$(content_right).on('click', "#closeBatchOperation", function () {
    $(content_right).find('#batchOperationDiv').hide();
});
$(content_right).on("click", "#batchDeleteStaff", function () {
    var staffIds = new Array();
    $(content_right).find("input[id^='staffCheck_']").each(function () {
        if ($(this).prop('checked')) {
            staffIds.push($(this).attr("id").replace("staffCheck_", ""));
        }
    });
    alertConfirmationMsgDlgDetail("批量删除", "删除后，员工所有数据会丢失，不可恢复。", "删除", deleteStaff, staffIds);
});
$(content_right).on("click", "#batchMailStaff", function () {
    var staffIds = new Array();
    $(content_right).find("input[id^='staffCheck_']").each(function () {
        if ($(this).prop('checked')) {
            staffIds.push($(this).attr("id").replace("staffCheck_", ""));
        }
    });
    alertConfirmationMsgDlg("确认要批量发送邮件?", mailStaff, staffIds);
});

function deleteStaff(staffIds) {
    $.ajax({
        url: createUrl("/staff/delete"),
        type: "post",
        data: {
            staffIds: staffIds
        },
        dataType: "json",
        traditional: true,
        success: function (msg) {
            if (msg.success) {
                alertPromptMsgDlg(msg.message, 1, reload);
            } else {
                alertPromptMsgDlg(msg.message, 3);
            }
        }
    });
}
function mailStaff(staffIds) {
    $.ajax({
        url: createUrl("/staff/batchMail"),
        type: "post",
        data: {
            staffIds: staffIds
        },
        dataType: "json",
        traditional: true,
        success: function (msg) {
            if (msg.success) {
                alertPromptMsgDlg(msg.message, 1);
            } else {
                alertPromptMsgDlg(msg.message, 3);
            }
        }
    });
}

function revertStaff(id) {
    $.ajax({
        url: createUrl("/staff/revert"),
        type: "post",
        async: false,
        data: {staffIds: [id]},
        dataType: "json",
        traditional: true,
        success: function (msg) {
            if (msg.success) {
                $.ajax({
                    url: createUrl("/staff/forwardToEdit/" + id),
                    type: "get",
                    async: true,
                    success: function (data) {
                        $('#orgManagementRightContent').html(data);
                    }
                });
            } else {
                alertPromptMsgDlg(msg.message, 2);
            }
        }
    });
}