package com.akira.kioku.utils;

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

    /**
     *  发送者的邮箱
     */
    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public MailUtil(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);

        mailSender.send(msg);
    }
}
