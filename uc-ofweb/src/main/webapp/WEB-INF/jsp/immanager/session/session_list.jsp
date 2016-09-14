<%--
  Created by IntelliJ IDEA.
  User: chenkl
  Date: 2016/8/3
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap-select/css/bootstrap-select.css">
</head>
<body>
<div id="content_middle">
    <%@ include file="../../common/sessionNavigation.jsp" %>
</div>
<div  id="content_right">
<div class="container-fluid">
    <div class="row">
        <div id="divContent" class="col-sm-12  panel">
            <div class="panel-heading">
                <div class="div-title-bar">
                    客户端会话（<label id="sessionCount"></label>）
                </div>
                <hr/>
            </div>
            <div class="div-title-bar clearfix">
                <div class="pull-left col-sm-3">
                    <div id="orgStaffRightContentDiv">
    						<span class="block  input-icon input-icon-right">
    							<input class="form-control " id="search_org" maxlength="128"
                                       title="请输入关键字搜索" placeholder="请输入关键字搜索"
                                       value="" autocomplete="off"/>
								<i class="icon-search btn" id="sessionBtnSearch"></i>
							</span>

                        <div class="select_content col-sm-11" style="overflow:auto;height:150px; font-size: 14px;" id="resultDiv">
                            <ul id="resultUL">
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="pull-right" style="padding-right: 15px;">
                    <div id="statusChoseSearch" >
                        <select id="statusSelect" class="selectpicker show-tick form-control" multiple data-live-search="false">
                            <option value="pc-online">PC在线</option>
                            <option value="mobile-online">手机在线</option>
                            <option value="pc-not_disturb">PC正忙</option>
                            <option value="mobile-not_disturb">手机正忙</option>
                            <option value="pc-away">PC离开</option>
                            <option value="mobile-away">手机离开</option>
                            <option value="pc-extended">PC电话中</option>
                            <option value="mobile-extended">手机电话中</option>
                        </select>
                    </div>
                </div>
            </div>

            <div style="margin-bottom: 70px;padding-right: 15px;padding-left: 15px;" id="divTitleBarHidden" hidden="hidden">
                <div  class="col-sm-12" style="background-color:#ddd;padding-bottom: 5px;">
                    <div class=" pull-left col-sm-1" style="padding-top: 8px;padding-bottom: 8px;">
                        <input type="checkbox" id="checkboxAll" /> 全选
                    </div>
                    <div class ="pull-left col-sm-2" style="padding-top: 10px;">
                        <label id="batchDeleteLable">
                            <a> <i class="icon-trash icon-large"></i>     批量删除 </a>
                        </label>
                    </div>
                </div>
            </div>
            <div class="div-list-table div-list-table-layout" style="padding-left: 15px;padding-right: 15px;">
                <table class="table fixed-table table-hover table-condensed">
                    <thead>
                    <tr>
                    <th width="3%" class="th-color"></th>
                    <th width="3%" class="th-color"></th>
                    <th  width="10%" class="th-color">账号</th>
                    <th  width="10%" class="th-color">姓名</th>
                    <th  width="20%" class="th-color">所属部门</th>
                    <th  width="15%" colspan="2" class="th-color">在线状态</th>
                    <th  width="15%" class="th-color">客户端版本</th>
                    <th width="15%" class="th-color">登录时间</th>
                    <th class="th-color"><spring:message code="session.details.operation"/></th>
                    <th width="1.8%" class="th-color"></th>
                    </tr>
                    </thead>
                </table>
                <div class="div-scroll-tabel-body">
                    <table  class="table fixed-table table-hover table-condensed">
                        <tbody id="sessionListBody">
                        </tbody>
                    </table>
                </div>
                <div id="sessionDiv" class="clearfix pull-right" style="padding-top: 15px;"></div>
            </div>

        </div>
    </div>
</div>
</div>
<div style="display: none">
    <ul id="staffOrgTreeForSearch"></ul>
</div>
<input type="hidden" id="staffIdsHidden"/>
<input type="hidden" id="total"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/artTemplate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap-select/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/tree_common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/session/session.js"></script>
<script  type="text/javascript">

    $(function() {
        initSessionIndex();
    });


</script>

<script id="sessionTemp" type="text/html">
    <%@include file="session_list.template"%>
</script>
<script id="sessionPageModelTemp" type="text/html">
    <%@include file="../../common/pager.template"%>
</script>
</body>
</html>
