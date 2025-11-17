package com.itrumuserservice.dto;

import java.math.BigDecimal;

public record WalletDto(
        String id,
        String userId,
        BigDecimal amount,
        String currency
) {
}