package com.johara.user.service;

import com.johara.user.kafka.UserProducerService;
import com.johara.user.model.User;
import com.johara.user.model.UserMessage;
import com.johara.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserProducerService userProducerService;

    @Autowired
    public UserService(UserRepository userRepository, UserProducerService userProducerService) {
        this.userRepository = userRepository;
        this.userProducerService = userProducerService;
    }

    public User createUser(User user) {
        // Save the user to the database
        User savedUser = userRepository.save(user);

        // Send the user message to Kafka
        UserMessage userMessage = convertToUserMessage(savedUser);
        userProducerService.sendUserMessage(userMessage);

        return savedUser;
    }

    private UserMessage convertToUserMessage(User user) {
        // Convert User object to UserMessage object
        UserMessage userMessage = new UserMessage();
        userMessage.setId(user.getId());
        userMessage.setName(user.getName());
        userMessage.setEmail(user.getEmail());
        return userMessage;
    }
}
