package com.itrumuserservice.exception.handler;

import com.itrumuserservice.exception.InvalidAmountException;
import com.itrumuserservice.exception.InvalidOperationTypeException;
import com.itrumuserservice.exception.InvalidUUIDException;
import com.itrumuserservice.dto.ErrorResponse;
import com.itrumuserservice.exception.WalletNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Value("${message.wallet-not-found}")
    private String walletNotFoundMessage;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUUIDException.class)
    public ErrorResponse handleInvalidUUIDException(InvalidUUIDException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WalletNotFoundException.class)
    public ErrorResponse handleWalletNotFoundException() {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(walletNotFoundMessage)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidOperationTypeException.class)
    public ErrorResponse handleInvalidOperationTypeException(InvalidOperationTypeException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAmountException.class)
    public ErrorResponse handleInvalidAmountException(InvalidAmountException e) {
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }
}