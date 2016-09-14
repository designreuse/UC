package com.yealink.uc.platform.web.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.yealink.uc.platform.utils.StringUtil;

/**
 * 用于页面 jstl时间格式化
 */
public class DateTag extends TagSupport {

    private static final long serialVersionUID = 6464168398214506236L;
    private String value;
    private String pattern;
   
    @Override
    public int doStartTag() throws JspException {
        if(pattern == null || pattern.equalsIgnoreCase("")){
            pattern = "yyyy-MM-dd";
        }
        String outValue = "";
        if(!StringUtil.isStrEmpty(value)) {
            long time = Long.valueOf(value);
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            outValue = df.format(c.getTime());
        }
        try {
            pageContext.getOut().write(outValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super .doStartTag();
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}