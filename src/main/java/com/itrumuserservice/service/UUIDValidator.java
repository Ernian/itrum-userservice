package com.itrumuserservice.service;

import com.itrumuserservice.exception.InvalidUUIDException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDValidator {
    @Value("${message.invalid-uuid}")
    String invalidUUIDMessage;

    @Value("${message.empty-wallet-id}")
    String emptyUUIDMessage;

    public UUID getValidUUID(String uuid) {
        checkUUIDNotNullAndNotBlank(uuid);
        return getUUIDFromString(uuid);
    }

    public void checkUUIDNotNullAndNotBlank(String uuid) {
        if (uuid == null || uuid.isBlank()) {
            throw new InvalidUUIDException(emptyUUIDMessage);
        }
    }

    public UUID getUUIDFromString(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidUUIDException(invalidUUIDMessage);
        }
    }
}