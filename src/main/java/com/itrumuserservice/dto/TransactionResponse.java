package com.itrumuserservice.dto;

import lombok.Builder;

@Builder
public record TransactionResponse(
        String message,
        String transactionId
) {
}