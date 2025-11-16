package com.itrumuserservice.service;

import com.itrumuserservice.dto.WalletDto;
import com.itrumuserservice.entity.Wallet;
import com.itrumuserservice.mapper.WalletMapper;
import com.itrumuserservice.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletMapper walletMapper;
    private final WalletRepository walletRepository;

    public WalletDto getWalletById(String id) {
        UUID walletId = UUID.fromString(id);
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));

        return walletMapper.toWalletDto(wallet);
    }
}