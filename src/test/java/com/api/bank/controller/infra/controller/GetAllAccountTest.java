package com.api.bank.controller.infra.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.ListAllAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetAllAccountTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ListAllAccount listAllAccount;

    @Test
    public void testGetAllAccount() throws Exception {
        // Arrange
        Account account1 = new Account(TypeAccount.CC, "Viny", "10451001200");
        Account account2 = new Account(TypeAccount.CP, "Maria", "10451001401");
        List<Account> accountList = Arrays.asList(account1, account2);

        when(listAllAccount.execute()).thenReturn(accountList);

        // Act & Assert
        mockMvc.perform(get("/account")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].owner").value("Viny"))
                .andExpect(jsonPath("$[0].cpf").value("10451001200"))
                .andExpect(jsonPath("$[0].balance").value(0))
                .andExpect(jsonPath("$[1].owner").value("Maria"))
                .andExpect(jsonPath("$[1].cpf").value("10451001401"))
                .andExpect(jsonPath("$[1].balance").value(0));
    }
}