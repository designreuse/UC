<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        绑定邮箱
    </title>
</head>
<body>
<div class="modal-header custom-modal-header">
    <button type="button" class="close custom-close" data-dismiss="modal"
            aria-hidden="true">&times;</button>
    <h4 id="Title" class="modal-title custom-modal-title custom-title" >
        绑定邮箱
    </h4>
</div>
<div id="modelBody" class="modal-body col-sm-10 col-sm-offset-1">
    <div class="fade in active">
        <p>请输入您的邮箱，系统将会向你的邮箱发送一封包含绑定链接的邮件。</p>
        <form class="form-horizontal">
            <div class="form-group" id="divBindEmail">
                <div >
                    <input class="form-control" id="bindEmail" maxlength="64"
                       placeholder="请输入邮箱">
                </div>
                <label id="errorBindEmail" class="control-label"></label>
            </div>
        </form>
    </div>
</div>

<div class="modal-footer custom-modal-footer">
    <button type="button" class="btn btn-success btn-lg col-sm-10 col-sm-offset-1" id="toBindEmail">
        确定
    </button>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/chosen/chosen.jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/account/account.js"></script>
<script>
    $(document).ready(function () {
        initBindEmail();
    });
</script>
</body>
</html>
