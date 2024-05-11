package org.example.tasklist.services.impl;

import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.tasklist.domain.MailType;
import org.example.tasklist.domain.user.User;
import org.example.tasklist.services.MailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final Configuration configuration;

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(User user, MailType type, Properties params){
        switch (type){
            case REGISTRATION -> sendRegistrationEmail(user, params);
            default -> {}
        }
    }

    @SneakyThrows
    private void sendRegistrationEmail(User user, Properties params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        mimeMessageHelper.setSubject("Спасибо за регистрацию, " + user.getName());
        mimeMessageHelper.setTo(user.getEmail());
        String emailContent = getRegistrationEmailContent(user, params);
        mimeMessageHelper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getRegistrationEmailContent(User user, Properties params) {
        StringWriter stringWriter = new StringWriter();
        Map<String, Object> module = new HashMap<>();
        module.put("name", user.getName());
        configuration.getTemplate("register.ftlh").process(module, stringWriter);
        return stringWriter.getBuffer().toString();
    }
}
