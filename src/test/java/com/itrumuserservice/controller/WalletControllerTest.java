package com.itrumuserservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itrumuserservice.dto.TransactionRequest;
import com.itrumuserservice.dto.TransactionResponse;
import com.itrumuserservice.dto.WalletDto;
import com.itrumuserservice.exception.InvalidAmountException;
import com.itrumuserservice.exception.InvalidOperationTypeException;
import com.itrumuserservice.exception.InvalidUUIDException;
import com.itrumuserservice.exception.WalletNotFoundException;
import com.itrumuserservice.service.WalletService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@DisplayName("Тестирование класса WalletController")
public class WalletControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    WalletService walletService;

    private final static String BASE_URL = "/wallet";
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    @DisplayName("Тестирование метода getWalletById, позитивный случай")
    public void test1() throws Exception {
        String walletId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        String currency = "RUB";
        BigDecimal amount = new BigDecimal(100);

        WalletDto walletDto = WalletDto.builder()
                .id(walletId)
                .userId(userId)
                .currency(currency)
                .amount(amount)
                .build();

        when(walletService.getWalletById(walletId)).thenReturn(walletDto);

        mockMvc.perform(get(BASE_URL + "/" + walletId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(walletId))
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.currency").value(currency))
                .andExpect(jsonPath("$.amount").value(amount));
    }

    @Test
    @DisplayName("Тестирование метода getWalletById, невалидный id кошелька")
    public void test2() throws Exception {
        String walletId = UUID.randomUUID().toString();

        when(walletService.getWalletById(walletId)).thenThrow(InvalidUUIDException.class);

        mockMvc.perform(get(BASE_URL + "/" + walletId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Тестирование метода handleTransaction, позитивный случай")
    public void test3() throws Exception {
        TransactionRequest request = TransactionRequest.builder().build();
        String message = "message";
        TransactionResponse response = TransactionResponse.builder()
                .message(message)
                .build();

        when(walletService.handleTransactionRequest(request)).thenReturn(response);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    @DisplayName("Тестирование метода handleTransaction, невалидный id кошелька")
    public void test4() throws Exception {
        TransactionRequest request = TransactionRequest.builder().build();

        when(walletService.handleTransactionRequest(request)).thenThrow(InvalidUUIDException.class);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(request))
                ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Тестирование метода handleTransaction, кошелек не найден")
    public void test5() throws Exception {
        TransactionRequest request = TransactionRequest.builder().build();

        when(walletService.handleTransactionRequest(request)).thenThrow(WalletNotFoundException.class);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Тестирование метода handleTransaction, невалидный тип операции")
    public void test6() throws Exception {
        TransactionRequest request = TransactionRequest.builder().build();

        when(walletService.handleTransactionRequest(request)).thenThrow(InvalidOperationTypeException.class);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Тестирование метода handleTransaction, ограничение минимальной суммы операции")
    public void test7() throws Exception {
        TransactionRequest request = TransactionRequest.builder().build();

        when(walletService.handleTransactionRequest(request)).thenThrow(InvalidAmountException.class);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(OBJECT_MAPPER.writeValueAsString(request))
        ).andExpect(status().isBadRequest());
    }
}