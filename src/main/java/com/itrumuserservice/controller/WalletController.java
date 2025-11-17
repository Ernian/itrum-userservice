package com.itrumuserservice.controller;

import com.itrumuserservice.dto.TransactionRequest;
import com.itrumuserservice.dto.TransactionResponse;
import com.itrumuserservice.dto.WalletDto;
import com.itrumuserservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/{walletId}")
    public WalletDto getWalletById(@PathVariable("walletId") String walletId) {
        return walletService.getWalletById(walletId);
    }

    @PostMapping
    public TransactionResponse handleTransaction(@RequestBody TransactionRequest request) {
        return walletService.handleTransactionRequest(request);
    }
}