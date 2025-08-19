package com.eprcuen.notificationservice.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Configuration class for setting up JavaMailSender with Gmail SMTP settings.
 * It reads the email and password from application properties.
 * This class is used to send emails using Gmail's SMTP server.
 * It requires the application to have the necessary properties set for email and password.
 *
 * @author caito
 *
 */
@Configuration
public class MailConfig {
    @Value("${application.email}")
    private String email;
    @Value("${spring.mail.password}")
    private String password;

    /**
     * Creates a JavaMailSender bean configured with Gmail SMTP settings.
     *
     * @return JavaMailSender instance configured for Gmail.
     */
    @Bean
    JavaMailSender javamailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(email);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }
}
