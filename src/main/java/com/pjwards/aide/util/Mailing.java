package com.pjwards.aide.util;

import com.pjwards.aide.domain.User;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.HashMap;
import java.util.Map;

@Component
@PropertySource("classpath:application.properties")
@PropertySource("classpath:mail.properties")
public class Mailing {
    private static final Logger LOGGER = LoggerFactory.getLogger(Mailing.class);

    private final String VELOCITY_ENCODING = "UTF-8";

    @Autowired
    private VelocityEngine velocityEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.application.address}")
    private String applicationAddress;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.filePath.root}")
    private String filePathRoot;

    @Value("${mail.filePath.en}")
    private String filePathEn;

    @Value("${mail.filePath.kr}")
    private String filePathKr;

    @Value("${mail.forgotPassword.endpoint}")
    private String forgotPasswordEndpoint;

    @Value("${mail.forgotPassword.subject}")
    private String forgotPasswordSubject;

    @Value("${mail.forgotPassword.filename}")
    private String forgotPasswordFilename;

    public SimpleMailMessage sendForgotPasswordMail(User user, String keyHash){
        LOGGER.debug("Send mail forget password, email={}", user.getEmail());
        String passwordRestoreUrl = forgotPasswordEndpoint + "?k=" + keyHash;
        LOGGER.debug("Forget password page url={}", passwordRestoreUrl);

        return sendMailByFile(user, applicationName + ' ' + forgotPasswordSubject, forgotPasswordFilename, applicationAddress + passwordRestoreUrl);
    }

    /*
    Send mail by file. It uses velocity engine
     */
    private SimpleMailMessage sendMailByFile(User user, String mailSubject, String msgTextFileName, String urlInMessage) {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("username", user.getName());
        model.put("url", urlInMessage);
        String mailBody = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, filePathRoot + filePathKr + msgTextFileName, VELOCITY_ENCODING, model);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(mailSubject);
        simpleMailMessage.setText(mailBody);
        javaMailSender.send(simpleMailMessage);

        LOGGER.debug("Send mail by file, To={}, From={}, Subject={}, Body={}", user.getEmail(), from, mailSubject, mailBody);
        return simpleMailMessage;
    }
}
