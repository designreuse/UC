package com.yealink.ofweb.modules.mail.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *  类描述：邮件异常
 * 
 *  @author:  pengzhiyuan
 *  @version  $Id: Exp$ 
 *
 *  History:  2014-9-6 下午12:44:58   pengzhiyuan   Created.
 *           
 */
public class MailException extends Exception {
	
	private String message = null;

	public MailException() {
		super();
	}
	
	public MailException(String message) {
		super(message);
		this.message = message;
	}
	
	public MailException(Throwable t) {
		super(t);
	}
	
	public MailException(Throwable t, String message) {
		this.message = message;
		String errorInfo = getStackTraceStr(t);
		System.out.println("Send mail error:" + message + ";cause:"+errorInfo);
	}
	
	public String getMessage() {
		return this.message + super.getMessage();
	}
	
	public String getStackTraceStr() {
		return getStackTraceStr(this);
	}
	
	public String getStackTraceStr(Throwable t) {  
	    StringWriter sw = new StringWriter();  
	    PrintWriter pw = new PrintWriter(sw);  
	    try {
	        t.printStackTrace(pw);  
	        return sw.toString();  
	    } finally {
	        pw.close();  
	    }
	}
	    
}
