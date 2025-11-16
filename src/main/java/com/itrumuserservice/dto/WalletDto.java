package com.itrumuserservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletDto(
        UUID id,
        BigDecimal amount,
        String currency
) {
}