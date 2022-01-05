package com.github.ivnmrtk.transactionsreconciliationservice.exception;

public class KafkaConsumingException extends RuntimeException{

    public KafkaConsumingException() {
    }

    public KafkaConsumingException(String message) {
        super(message);
    }

    public KafkaConsumingException(String message, Throwable cause) {
        super(message, cause);
    }

    public KafkaConsumingException(Throwable cause) {
        super(cause);
    }

}
