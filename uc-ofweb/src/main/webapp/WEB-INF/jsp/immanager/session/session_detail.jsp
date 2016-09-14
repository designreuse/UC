<%--
  Created by IntelliJ IDEA.
  User: chenkl
  Date: 2016/8/4
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>

</head>
<body>
<div class="container-fluid">
 <div class="row">
    <div class="div-list-table div-list-table-layout" >
        <table class="table fixed-table table-hover table-condensed">
            <thead>
            <th colspan="2" style="text-align: left;">状态</th>
            </thead>
            <tbody id="sessionDetailBody">
            </tbody>
        </table>
    </div>
     <div class="modal-footer custom-button-margin custom-modal-footer" >
         <div class="col-sm-1">
             <button type="button" class="btn btn-default  btn-block btn-lg" id="backBtn" data-dismiss="modal" >
                 <spring:message code="system.common.button.back" text="system.common.button.back"/>
             </button>
         </div>
     </div>
     </div>
    </div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common/artTemplate.js"></script>
<script type="text/javascript">
    var preUri="";
    $(function(){
        initSessionDetail();
        $("#backBtn").click(function(){
            window.location.href=preUri;
        });
    });
    function initSessionDetail(){
        var url=window.location.href;
        url=encodeURI($("#projectContext").val()+"/session/get_detail?"+url.substring(url.indexOf("?")+1));
        $.ajax({
            url: url,
            cache: false,
            dataType: "json",
            type: "get",
            async: false,
            error: function (xmlHttpRequest, error) {
                alert(error);
            },
            success: function (result) {
                if (result.ret >= 0 && result.data) {
                    preUri=URLdecode(result.attrs.preUrl);
//                    var template=document.getElementById('sessionDetailTemp').innerHTML;
//                    var dot=doT.template(template);
//                    document.getElementById('sessionDetailBody').innerHTML= dot( result.data );
                    var html = template('sessionDetailTemp', result.data);
                    document.getElementById('sessionDetailBody').innerHTML = html;
                }
            }
        });
    }
</script>
<script id="sessionDetailTemp" type="text/html">
   <%@include file="session_detail.template"%>
</script>
</body>
</html>
