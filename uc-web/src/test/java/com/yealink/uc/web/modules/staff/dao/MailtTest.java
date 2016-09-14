package com.yealink.uc.web.modules.staff.dao;

import java.util.Calendar;
import java.util.Locale;

/**
 * @author ChNan
 */
public class MailtTest {
    private static final String HTML_HEAD_STYLE = "<head><style>table.infotable {width: 485px;border-collapse:collapse;background-color: #FFFFFF;}"
        + "table.infotable th, table.infotable td{width: 25%;border: 1px solid #aaa;text-align: center;}</style></head>";
    private static final String HTML_INFO_TABLE_START = "<div style=\"padding:8px 0;\"><table class=\"infotable\" "
        + "style=\"width: 485px;border-collapse:collapse;background-color: #FFFFFF;\">"
        + "<tbody><tr style=\"height: 35px;\">";
    private static final String HTML_INFO_TABLE_TH_START = "<td width=\"25%\" style=\"width: 25%;border: 1px solid #aaa;text-align: center;\">";
    private static final String HTML_INFO_TABLE_TH_END = "</td>";
    private static final String HTML_TR_END = "</tr>";
    private static final String HTML_INFO_CONTENT = "<tr style=\"height: 35px;\"><td style=\"width: 121.25px;border: 1px solid #aaa;text-align: center;\">%s</td>"
        + "<td style=\"width: 121.25px;border: 1px solid #aaa;text-align: center;\">%s</td>"
        + "<td style=\"width: 121.25px;border: 1px solid #aaa;text-align: center;\">%s</td>"
        + "<td style=\"width: 121.25px;border: 1px solid #aaa;text-align: center;\">%s</td></tr></tbody></table></div>";


    private static final String HTML_B = "<b>";
    private static final String HTML_END_B = "</b>";
    protected static final String HTML_BR = "<br/>";
    protected static final String HTML_END_BODY = "</body></html>";
    protected static final String HTML_TAB = "&nbsp;&nbsp;&nbsp;&nbsp;";
    protected static final String HTML_END_P = "</p>";
    protected static final String HTML_START_P = "<p>";
    protected static final String HTML_START_BODY = "<html><body>";
    protected static final String HTML_DIV_END = "</div>";
    protected static final String HTML_BODY_END = "</body>";
    protected static final String HTML_START = "<html>";
    protected static final String HTML_END = "</html>";
    protected static final String HTML_END_DIV = "</div>";
    private static final String HTML_TEAM_P = "<p style=\"line-height: 30px;\">";
    private static final String HTML_YEALINK_A = "<a href=\"http://www.yealink.com\" target=\"view_window\" "
        + "style=\"text-decoration:none;color: #444444\">www.yealink.com</a>";
    private static final String HTML_YEALINK_A_CN = "<a href=\"http://www.yealink.com.cn\" target=\"view_window\" "
        + "style=\"text-decoration:none;color: #444444\">www.yealink.com.cn</a>";
    protected static final String HTML_BODY_START = "<body style=\"line-height: 25px;font-size: 14px;color: #444444;\">";
    protected static final String HTML_CONTENT_DIV_START = "<div align=\"center\" style=\"padding-top: 30px;margin-left:auto;margin-right:auto;\">";
    protected static final String HTML_CONTENT_TABLE_START = "<table style=\"width:560px;border: 1px solid #E3E3E3;background-color:#FBFBFB;\" cellspacing=\"0\">"
        + "<tbody>";
    protected static final String HTML_CONTENT_HEAD_LOGO = "<tr><td style=\"padding:15px 40px 0 40px;\" align=\"left\">"
        + "<a href=\"http://www.yealinkvc.com\" target=\"view_window\" style=\"text-decoration:none;\">"
        + "<img src=\"https://www.yealinkvc.com/images/mail_logo.png\"></a></td></tr>"
        + "<tr><td style=\"padding:0 40px;\"><p style=\"mso-line-height-rule:exactly;line-height:4px;height:4px;"
        + "border:none;border-bottom: 4px solid #40A382;color:#40A382;\">&nbsp;</p></td></tr>";
    protected static final String HTML_CONTENT_TR_START = "<tr><td style=\"padding:18px 40px 36px 40px;font-family: Microsoft YaHei !important;\">";
    protected static final String HTML_CONTENT_TR_EDN = "</td></tr>";
    protected static final String HTL_CONTENT_TABLE_END = "</tbody></table>";
    protected static final String HTML_COPYRIGHT_DIV_START = "<div style=\"font-size: 12px;color: #444444;padding-top:10px;\">";
    protected static final String HTML_MESSAGE_P = "<p>";
    protected static final String HTML_A = "<a style=\"text-decoration:none;color: #25A77B\" href=";
    protected static final String HTML_MID_A = ">";
    protected static final String HTML_END_A = "</a>";

    /*
     * (non-Javadoc)
     *
     * @see com.yealink.vcCloud.modules.mail.entity.MailTemplate#getContent()
     */
    public static String getContent(Locale locale) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(HTML_MESSAGE_P);
        buffer.append(HTML_BR);
        buffer.append(HTML_END_P);
        buffer.append(HTML_INFO_TABLE_START);
        buffer.append(HTML_INFO_TABLE_TH_START);
        buffer.append(HTML_INFO_TABLE_TH_END);
        buffer.append(HTML_INFO_TABLE_TH_START);
        buffer.append(HTML_INFO_TABLE_TH_END);
        buffer.append(HTML_INFO_TABLE_TH_START);
        buffer.append(HTML_INFO_TABLE_TH_END);
        buffer.append(HTML_INFO_TABLE_TH_START);
        buffer.append(HTML_INFO_TABLE_TH_END);
        buffer.append(HTML_TR_END);
        buffer.append(HTML_START_P);
        buffer.append(HTML_END_P);

        return  getContentByFrame(HTML_HEAD_STYLE, buffer.toString(), locale);
    }
    public static void main(String[] args) {
        String s = getContent(null);
        System.out.println(s);
    }
    protected static String getContentByFrame(String headStyle, String mailContent,
                                        Locale locale) {
        StringBuffer sb = new StringBuffer();
        sb.append(HTML_START);
        sb.append(HTML_BODY_START);
        sb.append(HTML_CONTENT_DIV_START);
        sb.append(HTML_CONTENT_TABLE_START);
        sb.append(HTML_CONTENT_HEAD_LOGO);
        sb.append(HTML_CONTENT_TR_START);
        sb.append(HTML_MESSAGE_P);
        sb.append(HTML_END_P);
        sb.append(mailContent);
        sb.append(HTML_CONTENT_TR_EDN);
        sb.append(HTL_CONTENT_TABLE_END);
        sb.append(HTML_COPYRIGHT_DIV_START);
        Calendar cal = Calendar.getInstance();
        String year = Integer.toString(cal.get(Calendar.YEAR));
        sb.append(HTML_DIV_END);
        sb.append(HTML_DIV_END);
        sb.append(HTML_BODY_END);
        sb.append(HTML_END);
        return sb.toString();
    }

    /**
     * 加粗信息
     *
     * @param message
     * @return message两边加上B标签
     */
    protected String boldMessage(Object message) {
        return HTML_B + message.toString() + HTML_END_B;
    }

}
