package com.api.bank.controller.infra.controller;

import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.GetAccountById;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetAccountByIdTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetAccountById getAccountById;

    @Test
    public void testGetAccountById() throws Exception {
        // Arrange
        Long idAccount = 1L;
        Account account1 = new Account(TypeAccount.CC, "Viny", "10451001200");
        account1.setId(idAccount);
        when(getAccountById.execute(idAccount)).thenReturn(Optional.of(account1));

        // Act & Assert
        mockMvc.perform(get("/account/{id}", idAccount)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("owner").value("Viny"))
                .andExpect(jsonPath("cpf").value("10451001200"))
                .andExpect(jsonPath("balance").value(1000));
    }
}
