package com.github.ivnmrtk.transactionsreconciliationservice.service;

import com.github.ivnmrtk.transactionsreconciliationservice.dto.NotificationDto;
import com.github.ivnmrtk.transactionsreconciliationservice.enumerations.ReconciliationState;
import com.github.ivnmrtk.transactionsreconciliationservice.exception.ValidationException;
import com.github.ivnmrtk.transactionsreconciliationservice.repository.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationDtoReconciliationService {

    private final TransactionsRepository transactionsRepository;

    @Transactional(transactionManager = "transactionManager", readOnly = true)
    public NotificationDto validateAndCreateNotificationDto(final Integer txId, final BigDecimal txAmount) {
        if (txId == null || txAmount == null) {
            log.warn("Some of passed fields are null, txId: {}, txAmount: {}", txId, txAmount);
            throw new ValidationException("Transaction's payload is incorrect!");
        }
        final var optionalTransaction = transactionsRepository.findById(txId);
        final var notificationDto = new NotificationDto();
        notificationDto.setTransactionId(txId);
        notificationDto.setIncomingAmount(txAmount);
        if (optionalTransaction.isEmpty()) {
            //If transaction wasn't found in DB set appropriate state
            notificationDto.setReconciliationState(ReconciliationState.NOT_FOUND);
        } else {
            final var savedTransaction = optionalTransaction.get();
            notificationDto.setSavedAmount(savedTransaction.getAmount());
            //If amount of incoming transaction coincide with amount stored in DB assign set CORRECT state and INCORRECT otherwise
            if (txAmount.compareTo(savedTransaction.getAmount()) == 0) {
                notificationDto.setReconciliationState(ReconciliationState.CORRECT);
            } else {
                notificationDto.setReconciliationState(ReconciliationState.INCORRECT);
            }
        }
        return notificationDto;
    }

}
