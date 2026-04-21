package ru.yandex.practicum.kafka;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Getter
@Setter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties(prefix = "aggregator.kafka")
public class AggregatorKafkaProperties {
    Properties producerProps;
    Properties consumerProps;
    String userActionsTopic;
    String eventsSimilarityTopic;
}