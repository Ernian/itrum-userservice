package com.itrumuserservice.mapper;

import com.itrumuserservice.dto.WalletDto;
import com.itrumuserservice.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface WalletMapper {
    public WalletDto toWalletDto(Wallet wallet);
}