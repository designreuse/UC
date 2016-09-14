<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>导入</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/3rdLibrary/jquery-ui/themes/jquery-ui.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/css/jquery.fileupload.css">
</head>
<body>
<div class="content-right-up">
    <div>
        批量导入
    </div>
</div>
<div class="container-fluid">
    <div class="row">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <div class="col-sm-4"><span class="pull-left">请按照模板的要求准备导入数据</span></div>
            </div>
            <div class="form-group">
                <div class="col-sm-6">
                    <a href="${pageContext.request.contextPath}/staff/downloadImportTemplate" class="btn btn-default">
                        <span class="hidden-xs">模板下载</span>
                    </a>
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <form class="form-horizontal" role="form">
            <div class="form-group">
                <div class="col-sm-2" style="width:204px">
                    <span class="pull-left">请选择需要导入的Excel文件</span>
                </div>
                <div class="col-sm-4" style="font-size: smaller;margin-top: 3px;color:#B1A9A9">
                    注意：文件后缀名必须为：xls（即Excel格式），导入数据最大条数不能超过5000条
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-5">
                    <div class="form-control">
                        <div tabindex="500" class=" btn-file"><i class="glyphicon glyphicon-folder-open"></i>&nbsp;<span class="hidden-xs" id="importFileName">选择文件</span>
                            <input id="importFile" type="file" name="importStaffFile" multiple="multiple" accept=".xls">
                        </div>
                    </div>
                </div>
                <div class="col-sm-1">
                    <button tabindex="500" type="button" id="uploadBtn" style="margin-left: -128px;" class="btn btn btn-success ">
                        <span class="hidden-xs">上传</span></button>
                </div>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="col-sm-5">
            <div id="importError">

            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-5">
            <!-- The global progress bar -->
            <div id="progress" class="progress">
                <div class="progress-bar progress-bar-success"></div>
            </div>
            <!-- The container for the uploaded files -->
            <div id="files" class="files"></div>
        </div>
    </div>
    <div class="row">
        <div class="modal fade" id="importResultModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true">
            <div class="modal-content" id="importResultModalContent"/>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/jquery-file-upload/js/jquery.fileupload.all.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/staff/staff_import.js"></script>

<script>
    $(function () {
        readyImportStaff();
    })
</script>
</body>
</html>