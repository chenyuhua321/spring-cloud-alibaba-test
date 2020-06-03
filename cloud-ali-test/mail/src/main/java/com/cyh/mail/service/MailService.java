package com.cyh.mail.service;

import com.cyh.dubbo.service.IMailService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


/**
 * @author Chenyuhua
 * @date 2020/5/20 22:43
 */
@Service
public class MailService implements IMailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);
    @Autowired
    private JavaMailSender javaMailSender;

    private static final String SENDER = "chenyuhuahi@163.com";
    private static final String SUBJECT = "登录验证码";

    /**
     * 发送普通邮件
     *
     * @param to   收件人
     * @param code 内容
     */
    @Override
    public void sendMail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER);
        message.setTo(to);
        message.setSubject(SUBJECT);
        message.setText(code);
        javaMailSender.send(message);
    }
}
