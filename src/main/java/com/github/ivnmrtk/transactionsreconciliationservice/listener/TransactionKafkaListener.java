package com.github.ivnmrtk.transactionsreconciliationservice.listener;

import com.github.ivnmrtk.transactionsreconciliationservice.dto.ExternalTransactionDto;
import com.github.ivnmrtk.transactionsreconciliationservice.exception.KafkaConsumingException;
import com.github.ivnmrtk.transactionsreconciliationservice.service.DefaultDispatcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionKafkaListener {

    private final DefaultDispatcherService defaultDispatcherService;

    @KafkaListener(topics = "${topics.transactions.name}")
    public void listenTransactions(final ExternalTransactionDto externalTransactionDto) {
        try {
            if (externalTransactionDto == null) {
                log.warn("Received transactionKafkaDto is null");
                return;
            }
            log.info("Received transaction: {}", externalTransactionDto);
            defaultDispatcherService.validateAndDispatch(
                    externalTransactionDto.getPId(),
                    externalTransactionDto.getPAmount());
        } catch (Exception e) {
            log.error("Can't handle transaction: {}", externalTransactionDto, e);
            throw new KafkaConsumingException(e);
        }
    }
}
