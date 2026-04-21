package ru.yandex.practicum.kafka;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "collector.kafka")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CollectorKafkaProperties {
    String bootstrapServers;
    String clientIdConfig;
    String producerKeySerializer;
    String producerValueSerializer;
    String userActionTopic;
}