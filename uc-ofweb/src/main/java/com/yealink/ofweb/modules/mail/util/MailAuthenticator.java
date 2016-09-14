package com.yealink.ofweb.modules.mail.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 *  类描述：发送邮件认证
 * 
 *  @author:  pengzhiyuan
 *  @version  $Id: Exp$ 
 *
 *  History:  2014-9-6 上午11:21:23   pengzhiyuan   Created.
 *           
 */
public class MailAuthenticator extends Authenticator {

	//邮件验证用户名
    private String userName = null;
    //邮件验证用户密码
    private String password = null;

    /**
     * 设置邮件验证用户名
     * @param userName
     */
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    /**
     * 获取邮件验证密码
     * @return String
     */
    public String getUserName(){
    	return userName;
    }

    /**
     * 设置邮件验证密码
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
    }
    
    /**
     * 获取邮件验证密码
     * @return String
     */
    public String getPassword(){
    	return password;
    }

    public MailAuthenticator(){
        super();
    }

    /**
     * 验证用户
     * @param userName
     * @param password
     */
    public MailAuthenticator(String userName, String password){
        super();
        this.userName=userName;
        this.password=password;
    }
    
    /**
     * 在需要身份验证时自动被调用的
     */
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(userName, password);
    }
}
