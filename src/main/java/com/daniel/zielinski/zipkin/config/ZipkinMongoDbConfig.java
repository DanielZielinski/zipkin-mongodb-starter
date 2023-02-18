package com.daniel.zielinski.zipkin.config;

import brave.Tracing;
import brave.mongodb.MongoDBTracing;
import com.mongodb.event.CommandListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.zipkin", name = "enabled", havingValue = "true")
class ZipkinMongoDbConfig {

    private final Tracing tracing;

    @Bean
    MongoClientSettingsBuilderCustomizer customizer() {
        CommandListener listener = MongoDBTracing.create(tracing)
                .commandListener();
        log.info("Registering mongodb zipkin tracing");
        return builder -> builder.addCommandListener(listener);
    }
}