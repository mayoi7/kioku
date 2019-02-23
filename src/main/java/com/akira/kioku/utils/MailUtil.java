package com.akira.kioku.utils;

import com.akira.kioku.constant.EmailConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 邮件发送相关的工具类
 * @author Kripath
 * @date Created in 12:03 2019/2/21
 */
@Service
@Slf4j
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
    private void sendMail(String to, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);

        mailSender.send(msg);
    }

    /**
     * 发送一封html格式的邮件
     * @param to 接收方邮箱
     * @param subject 主题
     * @param content 内容
     */
    private void sendHtmlMail(String to, String subject, String content) {
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            Multipart multipart = new MimeMultipart();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true,"utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);

            // 第二个参数为true表示该邮件是HTML格式
            helper.setText(content, true);
            mailSender.send(msg);
        } catch (MessagingException e) {
            log.error("[邮件] html邮件发送异常");
        }

    }


    /**
     * 发送一封重置密码邮件
     * @param to 接收方邮箱
     * @param url 重置密码的链接（无主机ip）
     */
    public void sendResetPasswordMail(String to, String url) {
        String resetPasswordContent = "<div>您好<br/>点击下方链接即可重置密码，有效时间为30分钟<br/>";
        String resetPasswordTitle = "Kioku记事簿-重置密码";

        String resetHref = "<a href=\"https://" + host + url + "\">进行密码重置</a></div>";

        String content = resetPasswordContent + resetHref;
        sendHtmlMail(to, resetPasswordTitle, content);
    }
}
