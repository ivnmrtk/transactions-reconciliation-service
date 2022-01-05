package com.github.ivnmrtk.transactionsreconciliationservice.service;

import com.github.ivnmrtk.transactionsreconciliationservice.enumerations.ReconciliationState;
import com.github.ivnmrtk.transactionsreconciliationservice.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql("/transactions-test-data.sql")
class NotificationDtoReconciliationServiceTest extends AbstractKafkaIntegrationTest {

    @Autowired
    private NotificationDtoReconciliationService notificationDtoReconciliationService;

    @Test
    void correctValidationTest() {
        var amountToCheck = BigDecimal.valueOf(150.7);
        var resultDto = notificationDtoReconciliationService.validateAndCreateNotificationDto(201, amountToCheck);
        assertNotNull(resultDto);
        assertAll(
                () -> assertEquals(201, resultDto.getTransactionId()),
                () -> assertEquals(amountToCheck, resultDto.getIncomingAmount()),
                () -> assertEquals(BigDecimal.valueOf(150.7).setScale(2), resultDto.getSavedAmount()),
                () -> assertEquals(ReconciliationState.CORRECT, resultDto.getReconciliationState())
        );
    }

    @Test
    void incorrectValidationTest() {
        var amountToCheck = BigDecimal.valueOf(152.7);
        var resultDto = notificationDtoReconciliationService.validateAndCreateNotificationDto(201, amountToCheck);
        assertNotNull(resultDto);
        assertAll(
                () -> assertEquals(201, resultDto.getTransactionId()),
                () -> assertEquals(amountToCheck, resultDto.getIncomingAmount()),
                () -> assertEquals(BigDecimal.valueOf(150.7).setScale(2), resultDto.getSavedAmount()),
                () -> assertEquals(ReconciliationState.INCORRECT, resultDto.getReconciliationState())
        );
    }

    @Test
    void notFoundValidationTest() {
        var incomingAmount = BigDecimal.valueOf(150.7);
        var resultDto = notificationDtoReconciliationService.validateAndCreateNotificationDto(301, incomingAmount);
        assertNotNull(resultDto);
        assertAll(
                () -> assertEquals(301, resultDto.getTransactionId()),
                () -> assertEquals(incomingAmount, resultDto.getIncomingAmount()),
                () -> assertNull(resultDto.getSavedAmount()),
                () -> assertEquals(ReconciliationState.NOT_FOUND, resultDto.getReconciliationState())
        );

    }

    @Test
    void exceptionOnNullFieldsTest() {
        assertThrows(ValidationException.class, () -> notificationDtoReconciliationService.validateAndCreateNotificationDto(201, null));
    }
}
