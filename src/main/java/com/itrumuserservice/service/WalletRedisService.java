package com.itrumuserservice.service;

import com.itrumuserservice.dto.WalletDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class WalletRedisService {
    private final RedisTemplate<String, WalletDto> redisTemplate;

    @Value("${redis-params.wallet-ttl-minutes}")
    private int walletTtlMinutes;

    @Value("${redis-params.wallet-key-prefix}")
    private String walletKeyPrefix;

    public WalletDto findById(String walletId) {
        String key = walletKeyPrefix + walletId;
        return redisTemplate.opsForValue().get(key);
    }

    public void cache(WalletDto walletDto) {
        String key = walletKeyPrefix + walletDto.id();
        redisTemplate.opsForValue().set(key, walletDto, Duration.ofMinutes(walletTtlMinutes));
    }
}