package com.github.ivnmrtk.transactionsreconciliationservice.service;

import com.github.ivnmrtk.transactionsreconciliationservice.dto.NotificationDto;

/**
 * Service for sending notifications
 */
public interface SenderService {
    /**
     *
     * @param notificationDto
     */
    void send(NotificationDto notificationDto);
}
