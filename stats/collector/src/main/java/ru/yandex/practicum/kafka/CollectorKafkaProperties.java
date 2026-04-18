package ru.yandex.practicum.kafka;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "collector.kafka")
public class CollectorKafkaProperties {
    String bootstrapServers;
    String clientIdConfig;
    String producerKeySerializer;
    String producerValueSerializer;
    String userActionTopic;
}