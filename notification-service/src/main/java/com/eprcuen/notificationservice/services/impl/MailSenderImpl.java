package com.eprcuen.notificationservice.services.impl;


import com.eprcuen.commons.exceptions.EmailSendingException;
import com.eprcuen.commons.exceptions.FileIOException;
import com.eprcuen.commons.logs.WriteLog;
import com.eprcuen.notificationservice.services.contracts.MailSender;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Implementation of the MailSender interface for sending emails.
 * This class uses JavaMailSender to send simple emails.
 * It handles exceptions related to email sending and logs errors.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MailSenderImpl implements MailSender {
    private final JavaMailSender mailSender;
    @Value("${application.email}")
    private String email;
    private final String ERROR_MESSAGE = "no se pudo enviar el e-mail";
    private final String ERROR_TEMPLATE = "no se pudo cargar el template";

    /**
     * Sends a simple email to the specified recipients.
     *
     * @param to      Array of recipient email addresses.
     * @param subject Subject of the email.
     * @param body    Body content of the email.
     * @throws EmailSendingException if there is an error while sending the email.
     */
    @Override
    public void sendEmail(String[] to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        }catch (MailException e){
            log.error(WriteLog.logError(ERROR_MESSAGE));
            throw new EmailSendingException(ERROR_MESSAGE);
        }
    }

    /**
     * Sends an email with an attachment to the specified recipients.
     *
     * @param to      Array of recipient email addresses.
     * @param subject Subject of the email.
     * @param body    Body content of the email.
     * @param file    File to be attached to the email.
     * @throws EmailSendingException if there is an error while sending the email.
     */
    @Override
    public void sendEmailWithAttachment(String[] to, String subject, String body, File file) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(email);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment(file.getName(), file);
            mailSender.send(message);
        } catch (Exception e) {
            log.error(WriteLog.logError(ERROR_MESSAGE));
            throw new EmailSendingException(ERROR_MESSAGE);
        }
    }

    /**
     * Sends an email using a template with dynamic data.
     *
     * @param to          Array of recipient email addresses.
     * @param subject     Subject of the email.
     * @param templateName Name of the template to be used.
     * @param data        Map containing dynamic data to be replaced in the template.
     * @throws EmailSendingException if there is an error while sending the email or loading the template.
     */
    @Override
    public void sendEmailWithTemplate(String[] to, String subject, String templateName, Map<String, String> data) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, StandardCharsets.UTF_8.name());
            helper.setFrom(email);
            helper.setTo(to);
            helper.setSubject(subject);
            String template = this.getTemplate(templateName);
            for (Map.Entry<String, String> m : data.entrySet()) {
                template = template.replace("${" + m.getKey() + "}", m.getValue());
            }
            helper.setText(template, true); // true indicates HTML content
            mailSender.send(message);
        } catch (Exception e) {
            log.error(WriteLog.logError(ERROR_TEMPLATE));
            throw new EmailSendingException(ERROR_TEMPLATE);
        }
    }

    /**
     * Loads a template from the classpath and returns its content as a String.
     *
     * @param templateName Name of the template file to be loaded.
     * @return Content of the template as a String.
     * @throws FileIOException if there is an error while reading the template file.
     */
    private String getTemplate(String templateName) {
        ClassPathResource resource = new ClassPathResource(templateName);
        try {
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("--> ERROR: ".concat(ERROR_TEMPLATE));
            throw new FileIOException(ERROR_TEMPLATE);
        }
    }
}
