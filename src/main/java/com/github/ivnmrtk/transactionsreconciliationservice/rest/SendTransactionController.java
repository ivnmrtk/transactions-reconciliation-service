package com.github.ivnmrtk.transactionsreconciliationservice.rest;

import com.github.ivnmrtk.transactionsreconciliationservice.dto.ExternalTransactionDto;
import com.github.ivnmrtk.transactionsreconciliationservice.service.ManualSendTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class SendTransactionController {

    private final ManualSendTransactionService manualSendTransactionService;

    @PostMapping("/send")
    public void sendTxToKafkaAndCheck(@RequestBody ExternalTransactionDto externalTransactionDto) {
        manualSendTransactionService.sendTransaction(externalTransactionDto);
    }
}
