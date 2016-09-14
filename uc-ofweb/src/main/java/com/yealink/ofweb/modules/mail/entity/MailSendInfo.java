package com.yealink.ofweb.modules.mail.entity;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *  类描述：邮件发送属性信息
 *
 *  @author:  pengzhiyuan
 *  @version  $Id: Exp$
 *
 */
public class MailSendInfo {

    private String id;

    private boolean isDebug; //是否开启调试

    private String userName; //登陆用户名
    private String passWord; //登陆密码
    private boolean validate=false; //是否需要验证
    private boolean isSSlSend; //是否采用ssl加密传输
    private boolean isTlsSend; //是否采用tls加密传输

    private String mailProtocol; //发送邮件协议    默认smtp
    private String mailServerPort; //端口 	默认25
    private String mailServerHost; //发送邮件服务器地址

    private boolean mailHttpProxySet=false; //是否使用代理服务器
    private String mailHttpProxyHost; //代理服务器地址
    private String mailHttpProxyPort;  //代理服务器端口

    private Date sendDate = null;
    private String fromAddress; //发送人地址
    private String toAddress;  //收件人地址
    private String toCCAddress; //抄送人地址
    private String toBCCAddress; //密送人地址
    private boolean isReply = false; //是否是回复邮件
    private boolean isAllReply = false;  //是否回复所有人
    private Message message = null; //回复邮件

    private String subject; //邮件主题
    private String content; //邮件正文
    private boolean isHtmlFormat=false; //邮件内容是否是html格式

    private boolean hasAttach=false;  //是否有附件
    private String[] attachFileNames; //附件文件路径名字 或者   当byteList不为空的时候 传文件名字
    private List<byte[]> byteList = new ArrayList<byte[]>(); //附件字节数组

    private boolean hasImage=false;  //是否正文有图片
    private String[] imageNames; //图片文件路径名字 或者   当imgByteList不为空的时候 传文件名字
    private String[] imageContentIds; //正文图片contentIds 要和 数组imageNames 一一对应
    private List<byte[]> imgByteList = new ArrayList<byte[]>(); //图片字节数组

    public MailSendInfo() {
    }

    public boolean isAllReply() {
        return isAllReply;
    }
    public void setAllReply(boolean isAllReply) {
        this.isAllReply = isAllReply;
    }
    public Message getMessage() {
        return message;
    }
    public void setMessage(Message message) {
        this.message = message;
    }
    public Date getSendDate() {
        return sendDate;
    }
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
    public boolean isReply() {
        return isReply;
    }
    public void setReply(boolean isReply) {
        this.isReply = isReply;
    }
    public void setDebug(boolean debug) {
        isDebug = debug;
    }
    public boolean isDebug() {
        return isDebug;
    }
    public List<byte[]> getImgByteList() {
        return imgByteList;
    }
    public void setImgByteList(List<byte[]> imgByteList) {
        this.imgByteList = imgByteList;
    }
    public List<byte[]> getByteList() {
        return byteList;
    }
    public void setByteList(List<byte[]> byteList) {
        this.byteList = byteList;
    }
    public String[] getImageContentIds() {
        return imageContentIds;
    }
    public void setImageContentIds(String[] imageContentIds) {
        this.imageContentIds = imageContentIds;
    }
    public boolean isHasImage() {
        return hasImage;
    }
    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }
    public String[] getImageNames() {
        return imageNames;
    }
    public void setImageNames(String[] imageNames) {
        this.imageNames = imageNames;
    }
    public boolean isHtmlFormat() {
        return isHtmlFormat;
    }
    public void setHtmlFormat(boolean isHtmlFormat) {
        this.isHtmlFormat = isHtmlFormat;
    }
    public boolean isHasAttach() {
        return hasAttach;
    }
    public void setHasAttach(boolean hasAttach) {
        this.hasAttach = hasAttach;
    }
    public String getToBCCAddress() {
        return toBCCAddress;
    }
    public void setToBCCAddress(String btoCCAddress) {
        this.toBCCAddress = btoCCAddress;
    }
    public String getMailProtocol() {
        return mailProtocol;
    }
    public void setMailProtocol(String mailProtocol) {
        this.mailProtocol = mailProtocol;
    }
    public boolean isMailHttpProxySet() {
        return mailHttpProxySet;
    }
    public void setMailHttpProxySet(boolean mailHttpProxySet) {
        this.mailHttpProxySet = mailHttpProxySet;
    }
    public String getMailHttpProxyHost() {
        return mailHttpProxyHost;
    }
    public void setMailHttpProxyHost(String mailHttpProxyHost) {
        this.mailHttpProxyHost = mailHttpProxyHost;
    }
    public String getMailHttpProxyPort() {
        return mailHttpProxyPort;
    }
    public void setMailHttpProxyPort(String mailHttpProxyPort) {
        this.mailHttpProxyPort = mailHttpProxyPort;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getMailServerHost() {
        return mailServerHost;
    }
    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }
    public String getMailServerPort() {
        return mailServerPort;
    }
    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }
    public String getFromAddress() {
        return fromAddress;
    }
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    public String getToAddress() {
        return toAddress;
    }
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }
    public String getToCCAddress() {
        return toCCAddress;
    }
    public void setToCCAddress(String toCCAddress) {
        this.toCCAddress = toCCAddress;
    }
    public boolean isValidate() {
        return validate;
    }
    public void setValidate(boolean validate) {
        this.validate = validate;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String[] getAttachFileNames() {
        return attachFileNames;
    }
    public void setAttachFileNames(String[] attachFileNames) {
        this.attachFileNames = attachFileNames;
    }

    public boolean isTlsSend() {
        return isTlsSend;
    }

    public void setTlsSend(boolean tlsSend) {
        isTlsSend = tlsSend;
    }

    public boolean isSSlSend() {
        return isSSlSend;
    }

    public void setSSlSend(boolean SSlSend) {
        isSSlSend = SSlSend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
