package com.itrumuserservice.service;

import com.itrumuserservice.dto.TransactionRequest;
import com.itrumuserservice.entity.OperationType;
import com.itrumuserservice.exception.InvalidAmountException;
import com.itrumuserservice.exception.InvalidOperationTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionRequestValidator {
    @Value("${message.invalid-operation-type}")
    private String invalidOperationTypeMessage;

    @Value("${message.amount-min-value}")
    private String amountMinValueMessage;

    @Value("${app.amount-min-value:1}")
    private BigDecimal minAmount;

    public void validateRequest(TransactionRequest request) {
        validateOperationType(request.type());
        validateAmount(request.amount());
    }

    private void validateOperationType(String type) {
        try {
            OperationType.valueOf(type.toUpperCase());
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new InvalidOperationTypeException(invalidOperationTypeMessage);
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(minAmount) < 0) {
            throw new InvalidAmountException(amountMinValueMessage);
        }
    }
}