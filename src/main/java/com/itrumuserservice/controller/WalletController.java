package com.itrumuserservice.controller;

import com.itrumuserservice.dto.WalletDto;
import com.itrumuserservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    @GetMapping("/{walletId}")
    public WalletDto getWalletById(@NotNull @NotBlank @PathVariable("walletId") String walletId) {
        return walletService.getWalletById(walletId);
    }
}