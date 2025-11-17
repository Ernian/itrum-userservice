package com.itrumuserservice.service;

import com.itrumuserservice.dto.WalletDto;
import com.itrumuserservice.entity.Wallet;
import com.itrumuserservice.exception.WalletNotFoundException;
import com.itrumuserservice.mapper.WalletMapper;
import com.itrumuserservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public WalletDto getWalletById(String id) {
        WalletDto walletDto = walletRedisService.findById(id);

        if (walletDto != null) {
            log.debug("Wallet found in cache with id {}", id);
            return walletDto;
        }

        UUID walletId = uuidValidator.getValidUUID(id);
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(WalletNotFoundException::new);
        walletDto = walletMapper.toWalletDto(wallet);
        walletRedisService.cache(walletDto);
        log.debug("Set wallet in cache with id {}", walletId);

        return walletDto;
    }
}