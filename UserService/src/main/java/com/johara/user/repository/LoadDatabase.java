package com.johara.user.repository;

import com.johara.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    /*
     * @Bean
     * CommandLineRunner initDatabase(OrderRepository repository) {
     * //TODO: Populate from a file
     * return args -> {
     * log.info("Preloading " + repository.save(new Order("Order 1", "Order 1 desc",
     * 1234)));
     * log.info("Preloading " + repository.save(new Order("Order 2", "Order 2 desc",
     * 234)));
     * };
     * }
     */
}
