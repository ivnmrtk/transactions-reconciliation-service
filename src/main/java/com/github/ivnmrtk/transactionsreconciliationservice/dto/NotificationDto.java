package com.github.ivnmrtk.transactionsreconciliationservice.dto;

import com.github.ivnmrtk.transactionsreconciliationservice.enumerations.ReconciliationState;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO class representing reconciliation result data for sending notifications
 */
@Data
public class NotificationDto {
    /**
     * Transaction identifier
     **/
    private Integer transactionId;
    /**
     * External transaction sum
     */
    private BigDecimal incomingAmount;
    /**
     * Saved amount in service DB
     */
    private BigDecimal savedAmount;
    /**
     * Reconciliation status
     */
    private ReconciliationState reconciliationState;
}

