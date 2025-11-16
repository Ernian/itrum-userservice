package com.itrumuserservice.service;

import com.itrumuserservice.dto.WalletDto;
import com.itrumuserservice.entity.Wallet;
import com.itrumuserservice.exception.WalletNotFoundException;
import com.itrumuserservice.mapper.WalletMapper;
import com.itrumuserservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletMapper walletMapper;
    private final UUIDValidator uuidValidator;
    private final WalletRepository walletRepository;


    public WalletDto getWalletById(String id) {
        UUID walletId = uuidValidator.getValidUUID(id);
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(WalletNotFoundException::new);

        return walletMapper.toWalletDto(wallet);
    }
}