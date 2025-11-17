package com.itrumuserservice.service;

import com.itrumuserservice.dto.TransactionRequest;
import com.itrumuserservice.dto.TransactionResponse;
import com.itrumuserservice.dto.WalletDto;
import com.itrumuserservice.entity.Wallet;
import com.itrumuserservice.exception.WalletNotFoundException;
import com.itrumuserservice.mapper.WalletMapper;
import com.itrumuserservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletMapper walletMapper;
    private final UUIDValidator uuidValidator;
    private final WalletRepository walletRepository;
    private final WalletRedisService walletRedisService;
    private final TransactionRequestValidator transactionRequestValidator;
    private final KafkaTemplate<String, TransactionRequest> kafkaTemplate;

    @Value("${message.transaction-response}")
    private String transactionResponseMessage;

    @Value("${spring.kafka.topic.transaction-request.name}")
    private String transactionRequestTopic;

    public WalletDto getWalletById(String id) {
        WalletDto walletDto = walletRedisService.findById(id);

        if (walletDto != null) {
            log.debug("The wallet was found in the cache with the ID {}", id);
            return walletDto;
        }

        UUID walletId = uuidValidator.getValidUUID(id);
        return findByIdAndCache(walletId);
    }

    private Wallet findById(UUID id) {
        return walletRepository.findById(id)
                .orElseThrow(WalletNotFoundException::new);
    }

    private WalletDto findByIdAndCache(UUID id) {
        Wallet wallet = findById(id);
        WalletDto walletDto = walletMapper.toWalletDto(wallet);
        walletRedisService.cache(walletDto);
        log.debug("The wallet is cached with the Id: {}", id);
        return walletDto;
    }

    public TransactionResponse handleTransactionRequest(TransactionRequest request) {
        UUID uuid = uuidValidator.getValidUUID(request.walletId());
        transactionRequestValidator.validateRequest(request);

        WalletDto walletDto = walletRedisService.findById(request.walletId());

        if (walletDto == null) {
            walletDto = findByIdAndCache(uuid);
        }

        String transactionId = generateTransactionId();
        String userId = walletDto.userId();
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .transactionId(transactionId)
                .walletId(request.walletId())
                .userId(userId)
                .amount(request.amount())
                .type(request.type())
                .build();


        var future = kafkaTemplate.send(transactionRequestTopic, userId, transactionRequest);

        future.whenComplete((result, exception) -> {
            if (exception != null) {
                handleSendingError(exception);
            }
        });

        return TransactionResponse.builder()
                .message(transactionResponseMessage)
                .transactionId(transactionId)
                .build();
    }

    private String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

    private void handleSendingError(Throwable exception) {
        log.error("Error while sending transaction request", exception);
    }
}