package com.johara.user.kafka;

import com.johara.user.model.UserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducerService {
    private static final String TOPIC_NAME = "users";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProducerService.class);

    private final KafkaTemplate<Object, String> kafkaTemplate;

    @Autowired
    public UserProducerService(KafkaTemplate<Object, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserMessage(UserMessage userMessage) {
        kafkaTemplate.send(TOPIC_NAME, userMessage.toString());
        LOGGER.info("User message sent to Kafka topic: {}", TOPIC_NAME);
    }
}
