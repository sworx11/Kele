package me.wemeet.kele.common.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MailHelper {
    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    private String from(String lang) {
        String fromZh = "渴樂团队" + '<' + from + '>';
        String fromEn = "KELE Team" + '<' + from + '>';
        return "zh".equalsIgnoreCase(lang) ? fromZh : fromEn;
    }

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from("zh"));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendMail(String to, String subject, String text, String lang) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from(lang));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
