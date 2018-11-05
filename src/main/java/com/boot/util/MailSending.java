package com.boot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailSending {

    public String send(MailConfig mailConfig,JavaMailSender jms,String mail,String title,String content){
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者
        mainMessage.setFrom(mailConfig.getUsername());
        //接收者
        mainMessage.setTo(mail);
        //发送的标题
        mainMessage.setSubject(title);
        //发送的内容
        mainMessage.setText(content);

        jms.send(mainMessage);
        return "1";
    }

}
