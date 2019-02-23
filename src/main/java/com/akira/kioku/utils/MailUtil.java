package com.akira.kioku.utils;

import com.akira.kioku.constant.EmailConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 邮件发送相关的工具类
 * @author Kripath
 * @date Created in 12:03 2019/2/21
 */
@Service
public class MailUtil {

    private final JavaMailSender mailSender;

    /** 发送者的邮箱 */
    @Value("${spring.mail.username}")
    private String from;

    /** 主机地址 */
    @Value("${website.host.name}")
    private String host;

    @Autowired
    public MailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 发送一封邮件
     * @param to 接收方邮箱
     * @param subject 主题
     * @param content 内容
     */
    public void sendMail(String to, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);

        mailSender.send(msg);
    }

    /**
     * 发送一封重置密码邮件
     * @param to 接收方邮箱
     * @param url 重置密码的链接（无主机ip）
     */
    public void sendResetPasswordMail(String to, String url) {
        String resetPasswordContent = "您好，点击下方链接可重置密码，有效时间为30分钟\n";
        String resetPasswordTitle = "Kioku记事簿-重置密码";

        String resetHref = "</a href='" + host + url + "'>点击这里进行密码重置</a>";

        String content = resetPasswordContent + resetHref;
        sendMail(to, resetPasswordTitle, content);
    }
}
