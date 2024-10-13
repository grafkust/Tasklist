package com.example.tasklist.service;

import com.example.tasklist.domain.mail.MailType;
import com.example.tasklist.domain.model.user.User;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@Service
@RequiredArgsConstructor
public class MailService {

    private final Configuration configuration;
    private final JavaMailSender mailSender;

    public void sendEmail(User user, MailType type, Properties params) {

        switch (type) {
            case REGISTRATION -> sendRegistrationEmail(user);
            case REMINDER -> sendReminderEmail(user, params);
            default -> {
            }
        }
    }

    @SneakyThrows
    private void sendRegistrationEmail(User user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setSubject("Thank you for your registration, " + user.getName());
        helper.setTo(user.getUsername());
        String emailContent = getRegistrationEmailContent(user);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private void sendReminderEmail(User user, Properties params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
        helper.setSubject(user.getName() + ", you have task to do in 1 hour.");
        helper.setTo(user.getUsername());
        String emailContent = getReminderEmailContent(user, params);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getRegistrationEmailContent(User user) {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName());
        configuration.getTemplate("register.ftlh").process(model, writer);
        return writer.getBuffer().toString();
    }

    @SneakyThrows
    private String getReminderEmailContent(User user, Properties properties) {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getName());
        model.put("title", properties.getProperty("task.title"));
        model.put("description", properties.getProperty("task.description"));
        configuration.getTemplate("reminder.ftlh").process(model, writer);
        return writer.getBuffer().toString();
    }
}
