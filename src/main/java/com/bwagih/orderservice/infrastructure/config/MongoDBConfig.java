package com.bwagih.orderservice.infrastructure.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@RequiredArgsConstructor
public class MongoDBConfig {
    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void createCollections() {
        if (!mongoTemplate.collectionExists("orders")) {
            mongoTemplate.createCollection("orders");
        }

    }
}
