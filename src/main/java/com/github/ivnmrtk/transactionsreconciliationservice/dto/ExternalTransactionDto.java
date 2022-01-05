package com.github.ivnmrtk.transactionsreconciliationservice.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO class representing incoming transaction's data from external channels
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalTransactionDto {
    /**
     * External transaction identifier
     */
    @JsonProperty("PID")
    private Integer pId;
    /**
     * Transaction amount
     */
    @JsonProperty("PAMOUNT")
    private BigDecimal pAmount;
    /**
     * Transaction payload data
     */
    @JsonProperty("PDATA")
    private Long pData;
}
