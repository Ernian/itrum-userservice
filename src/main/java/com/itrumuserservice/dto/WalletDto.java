package com.itrumuserservice.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record WalletDto(
        String id,
        String userId,
        BigDecimal balance,
        String currency
) {
}