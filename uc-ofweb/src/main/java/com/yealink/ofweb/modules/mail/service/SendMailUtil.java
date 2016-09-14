package com.yealink.ofweb.modules.mail.service;

import com.yealink.ofweb.modules.fileshare.util.PropertiesUtils;
import com.yealink.ofweb.modules.mail.dao.MailSettingDao;
import com.yealink.ofweb.modules.mail.entity.ByteDataSource;
import com.yealink.ofweb.modules.mail.entity.MailSendInfo;
import com.yealink.ofweb.modules.mail.exception.MailException;
import com.yealink.ofweb.modules.mail.util.MailAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * 类描述：发送邮件
 *
 * @author: pengzhiyuan
 * @version $Id: Exp$
 *
 *          History: 2014-9-6 上午11:22:44 pengzhiyuan Created.
 *
 */
@Service
public class SendMailUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SendMailUtil.class);

    @Autowired
    private MailSettingDao mailSettingDao;
    @Autowired
    private MessageSource messageSource;

    /**
     * 保存邮箱设置
     * @param mailSendInfo
     */
    public void saveMailSetting(MailSendInfo mailSendInfo) {
        // 先删除再保存
        if (mailSendInfo.getId() != null) {
            mailSettingDao.deleteMailSettingById(mailSendInfo.getId());
        }
        mailSettingDao.saveMailSetting(mailSendInfo);
    }

    /**
     * 测试邮箱设置
     * @param mailSendInfo
     */
    public void testMailSend(MailSendInfo mailSendInfo) throws MailException {
        // 邮箱设置测试邮件
        mailSendInfo.setSubject(messageSource.getMessage("mail.msg.test.content",null, Locale.getDefault()));
        mailSendInfo.setContent(messageSource.getMessage("mail.msg.test.content",null, Locale.getDefault()));
        mailSendInfo.setDebug(true);
        mailSendInfo.setValidate(true);

        if (mailSendInfo.isSSlSend()) {
            mailSendInfo.setMailProtocol("smtps");
        } else {
            mailSendInfo.setMailProtocol("smtp");
        }
        sendMail(mailSendInfo);
        LOG.debug("邮件发送成功!");
    }

    /**
     * 查询邮箱设置信息
     * @return
     */
    public Map<String,Object> queryMailSetting() {
        return mailSettingDao.queryMailSetting();
    }

    /**
     *
     * 方法说明：邮件发送
     *
     * Author：        pengzhiyuan
     * Create Date：   2014-9-6 下午1:32:44
     * History:  2014-9-6 下午1:32:44   pengzhiyuan   Created.
     *
     * @param mailSendInfo
     * @throws MailException
     *
     */
    public void sendMail(MailSendInfo mailSendInfo) throws MailException {
        // 邮件属性
        Properties properties = getProperties(mailSendInfo);
        // 邮件session
        Session mailSession = createSession(properties, mailSendInfo);
        // 发送
        sendMail(mailSession, mailSendInfo);
    }

    /**
     *
     * 方法说明：回复邮件
     *
     * Author：        pengzhiyuan
     * Create Date：   2014-9-13 下午9:45:44
     * History:  2014-9-13 下午9:45:44   pengzhiyuan   Created.
     *
     * @param messageId --需要回复的邮件ID
     * @param mailSendInfo -- 回复信息
     * @param isReplyAll -- 是否回复所有人
     * @throws MailException
     *
     */
//	public void replyMail(String messageId, MailSendInfo mailSendInfo, boolean isReplyAll) throws MailException {
//		ReceiveMailUtil receiveMailUtil = new ReceiveMailUtil();
//
//    	Session session = createSession(mailSendInfo);
//    	Message message = receiveMailUtil.getRecMailByMessageId(messageId, session, false);
//    	mailSendInfo.setMessage(message);
//    	mailSendInfo.setReply(true);
//    	mailSendInfo.setAllReply(isReplyAll);
//    	// 发送
//    	sendMail(session, mailSendInfo);
//
//    	receiveMailUtil.close();
//	}

    /**
     *
     * 方法说明：邮件发送
     *
     * Author：        pengzhiyuan
     * Create Date：   2014-9-6 下午1:32:44
     * History:  2014-9-6 下午1:32:44   pengzhiyuan   Created.
     *
     * @param mailSendInfo
     * @throws MailException
     *
     */
    public void sendMail(Session mailSession, MailSendInfo mailSendInfo) throws MailException{
        // 邮件mimemessage
        MimeMessage mimessage = createMimeMessage(mailSession, mailSendInfo);

        Transport transport = null;
        try {
            // 邮件协议
            String mailProctocol = PropertiesUtils.getString(mailSendInfo.getMailProtocol());
            // 发送服务器端口
            String mailServerPort = PropertiesUtils.getString(mailSendInfo.getMailServerPort());
            // 发送服务器主机地址
            String mailSmtpHost = PropertiesUtils.getString(mailSendInfo.getMailServerHost());
            // 用户名
            String mailUsername = PropertiesUtils.getString(mailSendInfo.getUserName());
            // 密码
            String mailPassword = PropertiesUtils.getString(mailSendInfo.getPassWord());

            transport = mailSession.getTransport(mailProctocol);
            if (mailServerPort.equals("")) {
                transport.connect(mailSmtpHost, mailUsername, mailPassword);
            } else {
                int port = Integer.parseInt(mailServerPort);
                transport.connect(mailSmtpHost, port, mailUsername,mailPassword);
            }
            transport.sendMessage(mimessage, mimessage.getAllRecipients());
        } catch (Exception e) {
            throw new MailException(e, "send mail failed!");
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * 方法说明：获取邮件属性信息
     *
     * Author：        pengzhiyuan
     * Create Date：   2014-9-6 上午11:40:39
     * History:  2014-9-6 上午11:40:39   pengzhiyuan   Created.
     *
     * @param mailSendInfo
     * @return
     *
     */
    public Properties getProperties(MailSendInfo mailSendInfo) {
        Properties properties = new Properties();

        // 是否使用代理
        if(mailSendInfo.isMailHttpProxySet()){
            properties.put("http.proxySet", "true");
            properties.put("http.proxyHost", mailSendInfo.getMailHttpProxyHost());
            properties.put("http.proxyPort", mailSendInfo.getMailHttpProxyPort());
        }

        properties.put("mail.transport.protocol", mailSendInfo.getMailProtocol());
        properties.put("mail.smtp.host",mailSendInfo.getMailServerHost());
        properties.put("mail.smtp.auth",String.valueOf(mailSendInfo.isValidate()));
        properties.put("mail.smtp.port",mailSendInfo.getMailServerPort());


        // 采用 ssl
        if (mailSendInfo.isSSlSend()) {
            properties.put("mail.smtp.ssl.trust", mailSendInfo.getMailServerHost());
            properties.put("mail.smtp.socketFactory.fallback","false");
            properties.put("mail.smtp.socketFactory.port",mailSendInfo.getMailServerPort());
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }
        // starttls
        if (mailSendInfo.isTlsSend()) {
            properties.put("mail.smtp.starttls.enable","true");
        }
        return properties;
    }

    /**
     *
     * 方法说明：地址转换 多个地址
     *
     * Author：        pengzhiyuan
     * Create Date：   2014-9-6 上午11:50:02
     * History:  2014-9-6 上午11:50:02   pengzhiyuan   Created.
     *
     * @param addressstring
     * @return
     *
     */
    public InternetAddress[] parseInternetAddress(String addressstring) {
        if(addressstring==null || addressstring.equals("")){
            return null;
        }
        String[] addressArr = addressstring.split(";");
        if (addressArr != null && addressArr.length > 0) {
            int length = addressArr.length;
            InternetAddress[] address = new InternetAddress[length];
            for (int i=0; i<length; i++) {
                String theMailTo=addressArr[i];
                try{
                    address[i]=new InternetAddress(theMailTo);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return address;
        }
        return null;
    }

    /**
     *
     * 方法说明：创建邮件session 提供javamail provider配置信息和属性信息的容器
     *
     * Author：        pengzhiyuan                
     * Create Date：   2014-9-6 下午1:01:43
     * History:  2014-9-6 下午1:01:43   pengzhiyuan   Created.
     *
     * @param proteries
     * @param mailInfo
     * @return
     * @throws MailException
     *
     */
    public Session createSession(Properties proteries, MailSendInfo mailInfo) throws MailException {
        MailAuthenticator authenticator = null;
        try {
            if (mailInfo.isValidate()) {
                // 如果需要身份认证，则创建一个密码验证器
                authenticator = new MailAuthenticator(mailInfo.getUserName(), mailInfo.getPassWord());
            }
            // 根据邮件会话属性和密码验证器构造一个发送邮件的session
            Session sendMailSession = Session.getInstance(proteries, authenticator);
            sendMailSession.setDebug(mailInfo.isDebug());
            return sendMailSession;
        } catch (Exception e) {
            throw new MailException(e, "create mail session failed!");
        }
    }
    /**
     *
     * 方法说明：创建邮件session 提供javamail provider配置信息和属性信息的容器
     *
     * Author：        pengzhiyuan                
     * Create Date：   2014-9-13 下午1:30:10
     * History:  2014-9-13 下午1:30:10   pengzhiyuan   Created.
     *
     * @param mailInfo
     * @return
     * @throws MailException
     *
     */
    public Session createSession(MailSendInfo mailInfo) throws MailException {
        Properties properties = getProperties(mailInfo);
        return createSession(properties, mailInfo);
    }

    /**
     *
     * 方法说明：创建mime message邮件消息
     *
     * Author：        pengzhiyuan                
     * Create Date：   2014-9-6 下午12:25:37
     * History:  2014-9-6 下午12:25:37   pengzhiyuan   Created.
     *
     * @param mailInfo
     * @return
     * @throws Exception
     *
     */
    public MimeMessage createMimeMessage(Session sendMailSession, MailSendInfo mailInfo) throws MailException {
        try {
            MimeMessage mimeMessage = null;
            //是否是回复邮件
            if (mailInfo.isReply()) {
                mimeMessage = (MimeMessage)mailInfo.getMessage().reply(mailInfo.isAllReply());
            } else {
                mimeMessage = new MimeMessage(sendMailSession);
            }

            mimeMessage.setHeader("X-Mailer", "MailServerEmail");
            if (!mailInfo.isReply() || !PropertiesUtils.getString(mailInfo.getSubject()).equals("")) {
                mimeMessage.setSubject(mailInfo.getSubject(),"UTF-8"); //主题
            }
            mimeMessage.setFrom(new InternetAddress(mailInfo.getFromAddress())); //发信人

            // 设置收信人地址
            InternetAddress[] addressTo=parseInternetAddress(mailInfo.getToAddress());
            if(addressTo!=null){
                mimeMessage.setRecipients(Message.RecipientType.TO,addressTo);
            }
            InternetAddress[] addressToCc=parseInternetAddress(mailInfo.getToCCAddress());
            if(addressToCc!=null){
                mimeMessage.setRecipients(Message.RecipientType.CC,addressToCc);
            }
            InternetAddress[] addressToBcc=parseInternetAddress(mailInfo.getToBCCAddress());
            if(addressToBcc!=null){
                mimeMessage.setRecipients(Message.RecipientType.BCC,addressToBcc);
            }

            // 邮件发送时间
            Date sendDate = mailInfo.getSendDate();
            if (sendDate == null) {
                sendDate = new Date();
            }
            mimeMessage.setSentDate(sendDate);

            // html格式
            if (mailInfo.isHtmlFormat()) {
                //正文有图片
                if (mailInfo.isHasImage()
                        && mailInfo.getImageNames() != null
                        && mailInfo.getImageNames().length > 0) {

                    Multipart multipart = new MimeMultipart();
                    // 创建正文图片
                    MimeBodyPart bodypart = createImageContent(mailInfo.getContent(),
                            mailInfo.getImageNames(), mailInfo.getImageContentIds(),
                            mailInfo.getImgByteList());
                    multipart.addBodyPart(bodypart);
                    mimeMessage.setContent(multipart);

                } else {
                    Multipart multipart = new MimeMultipart();
                    setMultipartMailHtmlContent(mailInfo.getContent(), multipart);
                    mimeMessage.setContent(multipart);
                }
            } else {
                mimeMessage.setText(mailInfo.getContent(),"UTF-8");
            }

            // 带有附件
            if (mailInfo.isHasAttach()
                    && mailInfo.getAttachFileNames() != null
                    && mailInfo.getAttachFileNames().length > 0) {
                MimeMultipart multipart = (MimeMultipart)mimeMessage.getContent();
                if (multipart == null) {
                    multipart = new MimeMultipart();
                }
                BodyPart msgAttachBodyPart = null;
                if (mailInfo.getByteList() != null && mailInfo.getByteList().size() > 0) {
                    // 传入字节数组
                    for (int i=0; i<mailInfo.getByteList().size(); i++) {
                        msgAttachBodyPart = createByteAttachment(mailInfo.getAttachFileNames()[i],
                                mailInfo.getByteList().get(i));
                        multipart.addBodyPart(msgAttachBodyPart);
                    }
                } else {
                    // 传入本地文件路径
                    for (String attachFileName : mailInfo.getAttachFileNames()) {
                        msgAttachBodyPart = createFileAttachment(attachFileName);
                        multipart.addBodyPart(msgAttachBodyPart);
                    }
                }

                mimeMessage.setContent(multipart);
            }

            mimeMessage.saveChanges();

            return mimeMessage;
        } catch (Exception e) {
            throw new MailException(e, "create mail mimemessage failed!");
        }
    }

    /**
     *
     * 方法说明：html格式内容邮件
     *
     * Author：        pengzhiyuan                
     * Create Date：   2014-9-6 下午1:15:30
     * History:  2014-9-6 下午1:15:30   pengzhiyuan   Created.
     *
     * @param content
     * @param mimeMultipart
     *
     */
    public void setMultipartMailHtmlContent(String content, Multipart mimeMultipart){
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        try{
            mimeBodyPart.setContent(
                    "<html><meta http-equiv=Content-Type content=text/html; charset=utf-8>"
                            + content
                            + "</html>", "text/html;charset=utf-8"
            );

            mimeMultipart.addBodyPart(mimeBodyPart);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * 方法说明：带附件 本地文件路径
     *
     * Author：        pengzhiyuan                
     * Create Date：   2014-9-6 下午4:00:10
     * History:  2014-9-6 下午4:00:10   pengzhiyuan   Created.
     *
     * @param fileName
     * @return
     * @throws Exception
     *
     */
    public MimeBodyPart createFileAttachment(String fileName) throws Exception {
        MimeBodyPart attachmentPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(fileName);
        attachmentPart.setDataHandler(new DataHandler(fds));
        attachmentPart.setFileName(fds.getName());
        return attachmentPart;
    }

    /**
     *
     * 方法说明：带附件 传字节数组
     *
     * Author：        pengzhiyuan                
     * Create Date：   2014-9-7 上午9:29:40
     * History:  2014-9-7 上午9:29:40   pengzhiyuan   Created.
     *
     * @param fileName
     * @param filebytes
     * @return
     * @throws Exception
     *
     */
    public MimeBodyPart createByteAttachment(String fileName, byte[] filebytes) throws Exception {
        MimeBodyPart attachmentPart = new MimeBodyPart();
        ByteDataSource bds = new ByteDataSource(filebytes, fileName);
        attachmentPart.setDataHandler(new DataHandler(bds));
        attachmentPart.setFileName(fileName);
        return attachmentPart;
    }

    /**
     *
     * 方法说明：邮件正文图片
     *
     * Author：        pengzhiyuan                
     * Create Date：   2014-9-6 下午4:42:54
     * History:  2014-9-6 下午4:42:54   pengzhiyuan   Created.
     *
     * @param imageFileName
     * @return
     * @throws Exception
     *
     */
    public MimeBodyPart createImageContent(String content, String[] imageFileName, String[] imageContentIds,
                                           List<byte[]> imgByteList) throws Exception {
        // 正文  
        MimeBodyPart contentBody = new MimeBodyPart();
        // 用于组合文本和图片，"related"型的MimeMultipart对象 
        MimeMultipart contentMulti = new MimeMultipart("related");

        // 正文的文本部分  
        String body = content;
        MimeBodyPart textBody = new MimeBodyPart();
        for (String contentId : imageContentIds) {
            body = body + "<img src=\"cid:"+contentId+"\"/><br>";
        }
        textBody.setContent(body, "text/html;charset=utf-8");
        contentMulti.addBodyPart(textBody);

        // 正文的图片部分  
        MimeBodyPart imageBody = new MimeBodyPart();
        if (imgByteList != null && imgByteList.size() > 0) {
            for (int i=0; i<imageFileName.length; i++) {
                ByteDataSource bds = new ByteDataSource(imgByteList.get(i), imageFileName[i]);
                imageBody.setDataHandler(new DataHandler(bds));
                imageBody.setContentID(imageContentIds[i]);
                contentMulti.addBodyPart(imageBody);
            }
        } else {
            for (int i=0; i<imageFileName.length; i++) {
                FileDataSource fds = new FileDataSource(imageFileName[i]);
                imageBody.setDataHandler(new DataHandler(fds));
                imageBody.setContentID(imageContentIds[i]);
                contentMulti.addBodyPart(imageBody);
            }
        }

        // 将上面"related"型的 MimeMultipart 对象作为邮件的正文  
        contentBody.setContent(contentMulti);
        return contentBody;
    }

    public static void main(String[] args) throws MailException {
    	SendMailUtil sendMail = new SendMailUtil();
    	MailSendInfo mailSendInfo = new MailSendInfo();

    	mailSendInfo.setSubject("测试-"+ new Random().nextInt(100));
    	mailSendInfo.setContent("<h2><font color=red>测试邮件</font></h2><br><p>正文内容...........</p>");
    	mailSendInfo.setFromAddress("pzysoft@163.com");
    	mailSendInfo.setToAddress("pengzy@yealink.com");
		mailSendInfo.setMailProtocol("smtp");
		mailSendInfo.setMailServerPort("25");
		mailSendInfo.setMailServerHost("smtp.163.com");
		mailSendInfo.setDebug(true);
		mailSendInfo.setValidate(true);
		mailSendInfo.setUserName("pzysoft@163.com");
		mailSendInfo.setPassWord("");
        mailSendInfo.setHtmlFormat(true);
        mailSendInfo.setTlsSend(false);
        mailSendInfo.setSSlSend(false);

		try {
			sendMail.sendMail(mailSendInfo);
			System.out.println("邮件发送成功!");
		} catch (MailException e) {
			e.printStackTrace();
		}
    }

}
