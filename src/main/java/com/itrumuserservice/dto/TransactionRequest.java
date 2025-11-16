package com.itrumuserservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransactionRequest(
        @NotNull(message = "${message.empty-wallet-id}")
        @NotBlank(message = "${message.empty-wallet-id}")
        String walletId,

        @NotNull(message = "${message.empty-operation-type}")
        @NotBlank(message = "${message.empty-operation-type}")
        String type,

        @Min(message = "${message.amount-min-value}", value = 1)
        BigDecimal amount
) {
}