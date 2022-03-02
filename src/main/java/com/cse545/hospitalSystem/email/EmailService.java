package com.cse545.hospitalSystem.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Properties;


@Service
public class EmailService {
    
    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);
    

    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    String fromMail;
    
    @Value("${spring.mail.password}")
    String userPassword;
    
    public EmailService(JavaMailSender javaMailSender, MessageSource messageSource) {
        this.mailSender = javaMailSender;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        logger.info("inside emailservice send email");
        JavaMailSender javaMailsender = getJavaMailSender();
        MimeMessage mimeMessage = javaMailsender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
                    isMultipart,
                    StandardCharsets.UTF_8.name());
            //message.setTo(to);
            message.setTo("glendsouza1702@gmail.com");// need to change to message.setTo
            message.setFrom(fromMail);
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailsender.send(mimeMessage);
            } catch (MailException | MessagingException e) {
                logger.error("Email could not be sent to user '{}'"+ to+ e);
                throw new IllegalStateException("Email could not be sent");
            }
        
    }
    
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(fromMail);
        mailSender.setPassword(userPassword);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        return mailSender;
    }
    
    public void sendOtpEmail(String email, String otp) {
        String subject = "Your OTP for Secure Hospital System";
        String body = "<html><body>" + otp + "</body></html>";
        sendEmail(email, subject, body, false, true);
    }

}
