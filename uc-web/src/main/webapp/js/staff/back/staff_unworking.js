function readyUnworkList() {
    setCurrentPageNavigation("headerDatacenter", "sysLeftUnwork");
    $("li[id^='select_']").click(function () {
        $("#status_select").val($(this).val());
        searchUnworkList();
    });
    $("#btnSearch").click(function () {
        searchUnworkList();
    });
    searchInput("searchKey", null, searchUnworkList);

    $("label[id^='unlock_']").click(function () {
        var id = $(this).attr("id").replace("unlock_", "");
        changeStaffStatus('unlock',id);
    });
    $("label[id^='delete_']").click(function () {
        var id = $(this).attr("id").replace("delete_", "");
        deleteStaff([id]);
    });
    $("label[id^='revert_']").click(function () {
        var id = $(this).attr("id").replace("revert_", "");
        revertStaff(id);
    });
    $("label[id^='recycle_']").click(function () {
        var id = $(this).attr("id").replace("recycle_", "");
        changeStaffStatus('recycle',id);
    });
}

function searchUnworkList() {
    var statusSelected = $("#status_select").val();
    var searchKey = $("#searchKey").val();
    var requestURL = createUrl("/staff/search");
    requestURL = setUrlParam(requestURL, "selectedStatus", statusSelected);
    requestURL = setUrlParam(requestURL, "searchKey", searchKey);
    window.location.href = requestURL;
}

function revertStaff(staffId) {
    $.ajax({
        url: createUrl("/staff/revert"),
        type: "post",
        data: {
            staffIds: [staffId]
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