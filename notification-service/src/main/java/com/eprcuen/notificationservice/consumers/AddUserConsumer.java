package com.eprcuen.notificationservice.consumers;

import com.eprcuen.commons.models.HighMsg;
import com.eprcuen.notificationservice.services.contracts.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Consumer for user registration messages.
 * This class listens to the "user-topic" Kafka topic and processes incoming HighMsg messages.
 * It sends an email to the user with an activation link.
 *
 * @author eprcuen
 *
 */
@Component
@RequiredArgsConstructor
public class AddUserConsumer {
    private final MailSender mailSender;

    /**
     * Listens to the "user-topic" Kafka topic and processes HighMsg messages.
     * When a new user is registered, it sends an email with an activation link.
     *
     * @param msg the HighMsg message received from the Kafka topic
     */
    @KafkaListener(topics = "user-topic", groupId = "add-user-group")
    public void AddUserHandler(HighMsg msg){
        //System.out.println("Received message: " + msg);
        System.out.println("Received message: " + msg.getEmail() + " " + msg.getUsername() + " "
                + msg.getValidationToken());
        Map<String, String> data = new HashMap<>();
        data.put("name", msg.getUsername());
        data.put("token", msg.getValidationToken());
        mailSender.sendEmailWithTemplate(new String[]{msg.getEmail()},
                "Account Activation - No Reply",
                "templates/activate_account.html",
                data);
    }
}
