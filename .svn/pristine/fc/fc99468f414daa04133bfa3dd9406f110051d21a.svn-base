package com.dome.sdkserver.util;


import org.apache.commons.lang3.StringUtils;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * MailUtil
 *
 * @author Zhang ShanMin
 * @date 2016/3/30
 * @time 9:47
 */
public class MailUtil {


    /**
     * 发送纯文本邮件
     * @param senderAddr 发件人邮件地址
     * @param senderAddrPw 发件人邮件密码
     * @param recipientsAddr 收件人地址
     * @param mailSubject 邮件主题
     * @param mailContext 邮件内容
     * @throws Exception
     */
    public static void sendPureTextMail(String senderAddr, String senderAddrPw, List<String> recipientsAddr, String mailSubject,
                                        String mailContext) throws Exception {
        if (StringUtils.isBlank(senderAddr) || StringUtils.isBlank(senderAddrPw) || StringUtils.isBlank(mailSubject)
                || StringUtils.isBlank(mailContext) || recipientsAddr == null || recipientsAddr.size() <= 0)
            throw new IllegalArgumentException("sendPureTextMail请求参数异常");
        //1.获取session
        Session session = getMailSession();
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器
        ts.connect("imap.exmail.qq.com", senderAddr, senderAddrPw);
        //4、创建邮件
        Message message = createSimpleMail(session, senderAddr, recipientsAddr, mailSubject, mailContext);
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }

    /**
     * @param session
     * @return
     * @throws Exception
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    private static MimeMessage createSimpleMail(Session session, String senderAddr, List<String> recipientsAddr, String mailSubject,
                                                String mailContext)
            throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(senderAddr));

        Address[] recipients = new Address[recipientsAddr.size()];
        for (int i = 0;i < recipientsAddr.size();i++){
            recipients[i] = new InternetAddress(recipientsAddr.get(i));
        }
        message.setRecipients(Message.RecipientType.TO, recipients);
        //邮件的标题
        message.setSubject(mailSubject);
        //邮件的文本内容
        message.setContent(mailContext, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }

    public static Session getMailSession() {
        Properties prop = new Properties();
        prop.setProperty("mail.host", "imap.exmail.qq.com"); //设置smtp主机
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");  //使用smtp身份验证
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        //session.setDebug(true);
        return session;
    }
}
