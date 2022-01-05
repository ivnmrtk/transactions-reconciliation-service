package com.github.ivnmrtk.transactionsreconciliationservice.service;

import com.github.ivnmrtk.transactionsreconciliationservice.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultDispatcherService {

    private final NotificationDtoReconciliationService notificationDtoReconciliationService;

    private final List<SenderService> senderServices;

    public void validateAndDispatch(final Integer txId, final BigDecimal txAmount) {
        try {
            var notificationDto = notificationDtoReconciliationService.validateAndCreateNotificationDto(txId, txAmount);
            //Send notification to all the enabled channels
            senderServices.forEach(it -> it.send(notificationDto));
        } catch (ValidationException e) {
            log.warn("Stop notification sending due to invalid data of received transaction!");
        }
    }

}
