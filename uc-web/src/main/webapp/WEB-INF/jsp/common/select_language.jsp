
<%
	String contextPath = request.getContextPath();
%>
<%
	String defaultPath = "/js/i18n/";
	String defaultI18nFile = defaultPath + "message-en.js";
	String acceptLanguage = (String)session.getAttribute("language");
	
	String i18nFile = contextPath + defaultI18nFile;
	if (acceptLanguage != null) {
		String[] languages = acceptLanguage.split(",");
		String suffix = "en";
		if (languages.length > 0) {
			suffix = languages[0];
		}
		
		suffix = suffix.replaceAll("_", "-");
		String absoluteFilePath = getServletContext().getRealPath("/")
				+ defaultPath + "message-" + suffix + ".js";
		if (new File(absoluteFilePath).exists()) {
			i18nFile = contextPath + defaultPath + "message-" + suffix + ".js";
		}
	}
%>

<%@page import="java.io.File"%>
<script type="text/javascript" src="<%=i18nFile%>"></script>