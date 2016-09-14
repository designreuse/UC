<!-- css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/3rdLibrary/bootstrap-table/css/bootstrap-table.css" />
<!-- js -->
<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootstrap-table/js/bootstrap-table-yealink.js"></script>

<%
    // 国际化选择
    String languge = request.getLocale().getLanguage();

    // 先写死
    languge = "zh-CN";

    String curLanguageJsFile = "";
    if (languge != null) {
        languge = languge.replaceAll("_","-");
        curLanguageJsFile = request.getContextPath()+"/3rdLibrary/bootstrap-table/js/bootstrap-table-yealink-"+languge+".js";
    }
%>
<% if (!curLanguageJsFile.equals("") && !languge.equals("en")) { %>
<script type = "text/javascript" src="<%=curLanguageJsFile%>"></script >
<%} %>

<script type="text/javascript" src="${pageContext.request.contextPath}/3rdLibrary/bootbox/js/bootbox-yealink.js"></script>
