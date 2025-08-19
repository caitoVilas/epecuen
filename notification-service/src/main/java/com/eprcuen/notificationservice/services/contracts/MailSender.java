package com.eprcuen.notificationservice.services.contracts;


import java.io.File;
import java.util.Map;

/**
 * Interface for sending emails.
 * Provides methods to send simple emails, emails with attachments, and templated emails.
 * This interface can be implemented by various email service providers.
 *
 * @author caito
 *
 */
public interface MailSender {
    void sendEmail(String[] to, String subject, String body);
    void sendEmailWithAttachment(String[] to, String subject, String body, File file);
    void sendEmailWithTemplate(String[] to, String subject, String templateName, Map<String, String> data);
}
