package com.github.ivnmrtk.transactionsreconciliationservice.service;

import com.github.ivnmrtk.transactionsreconciliationservice.config.properties.KafkaTopicsProperties;
import com.github.ivnmrtk.transactionsreconciliationservice.dto.NotificationDto;
import com.github.ivnmrtk.transactionsreconciliationservice.exception.KafkaSenderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Implementation
 */
@Service
@Slf4j
@ConditionalOnProperty(prefix = "notification-channels", name = "kafka-enabled", havingValue = "true")
public class KafkaSenderServiceImpl implements SenderService {

    private final KafkaTemplate<Integer, NotificationDto> notificationDtoKafkaTemplate;

    private final String notificationTopicName;

    public KafkaSenderServiceImpl(KafkaTemplate<Integer, NotificationDto> notificationDtoKafkaTemplate,
                                  KafkaTopicsProperties kafkaTopicsConfiguration) {
        this.notificationDtoKafkaTemplate = notificationDtoKafkaTemplate;
        this.notificationTopicName = kafkaTopicsConfiguration.getNotifications().getName();
    }

    @Override
    public void send(NotificationDto notificationDto) {
        try {
            notificationDtoKafkaTemplate.send(notificationTopicName, notificationDto.getTransactionId(), notificationDto).get();
            log.info("Message with notification: {} was successfully sent to topic: {}", notificationDto, notificationTopicName);
        } catch (Exception e) {
            log.error("Error while sending notification: {}", notificationDto, e);
            throw new KafkaSenderException(e);
        }
    }
}
