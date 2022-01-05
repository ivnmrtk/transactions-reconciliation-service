package com.github.ivnmrtk.transactionsreconciliationservice.service;

import com.github.ivnmrtk.transactionsreconciliationservice.dto.ExternalTransactionDto;

public interface ManualSendTransactionService {
    void sendTransaction(ExternalTransactionDto externalTransactionDto);
}
