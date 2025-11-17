package com.itrumuserservice.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrumuserservice.dto.WalletDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {
    @Bean
    public RedisTemplate<String, WalletDto> redisWalletDtoTemplate(
            RedisConnectionFactory redisConnectionFactory,
            ObjectMapper objectMapper
    ) {
        RedisTemplate<String, WalletDto> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        var valueSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, WalletDto.class);
        template.setValueSerializer(valueSerializer);
        template.afterPropertiesSet();

        return template;
    }
}