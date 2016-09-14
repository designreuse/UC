
<%
	String contextPath = request.getContextPath();
%>
<%
	String currentLanguage = "en";
	String defaultPath = "/3rdLibrary/bootstrap-datetimepicker/js/locales/";
	String acceptLanguage = (String)session.getAttribute("language");

	String i18nFile = "en";
	String absoluteFilePath = "";
	if (acceptLanguage != null && !acceptLanguage.equals("en")) {
		String[] languages = acceptLanguage.split(",");
		String suffix = "en";
		if (languages.length > 0) {
			suffix = languages[0];
		}
		suffix = suffix.replaceAll("_", "-");

		absoluteFilePath = getServletContext().getRealPath("/")
				+ defaultPath + "bootstrap-datetimepicker." + suffix
				+ ".js";
		if (new File(absoluteFilePath).exists()) {
			i18nFile = contextPath + defaultPath
					+ "bootstrap-datetimepicker." + suffix + ".js"; 
		}
	}
%>

<%@page import="java.io.File"%>
<%
if(!i18nFile.equals("en")){
%>
	<script type="text/javascript" src="<%=i18nFile%>" charset="UTF-8"></script>
<%
}
%>