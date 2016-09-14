package com.yealink.uc.platform.web.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author ChNan
 * //todo change to js
 */
public class MenuTag extends TagSupport {
    Logger logger = LoggerFactory.getLogger(MenuTag.class);

    @Override
    public int doStartTag() throws JspException {
        String menus = (String) pageContext.getSession().getAttribute("menus");
        try {
            pageContext.getOut().write(menus);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return SKIP_BODY;
    }
}
