package com.github.ivnmrtk.transactionsreconciliationservice.service;

import com.github.ivnmrtk.transactionsreconciliationservice.config.properties.KafkaTopicsProperties;
import com.github.ivnmrtk.transactionsreconciliationservice.dto.ExternalTransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class ManualSendTransactionServiceImpl implements ManualSendTransactionService {

    private final KafkaTemplate<Integer, ExternalTransactionDto> kafkaTemplate;

    private final String transactionsTopic;

    public ManualSendTransactionServiceImpl(KafkaTemplate<Integer, ExternalTransactionDto> kafkaTemplate, KafkaTopicsProperties topicsProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.transactionsTopic = topicsProperties.getTransactions().getName();
    }

    @Override
    @Transactional(transactionManager = "kafkaTransactionManager")
    public void sendTransaction(ExternalTransactionDto externalTransactionDto) {
        try {
            kafkaTemplate.send(transactionsTopic, externalTransactionDto.getPId(), externalTransactionDto).get();
        } catch (Exception e) {
            log.error("Can't send to kafka transaction {}!", externalTransactionDto, e);
        }
    }

}
