package com.api.bank.controller.infra.controller;

import com.api.bank.domain.gateway.AccountGateway;
import com.api.bank.domain.model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.AbstractCachingConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateNewAccountTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountGateway accountGateway;
    @Test
    public void testAccountCreation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/account")
                        .param("typeAccount", "CC")
                        .param("name", "John Doe")
                        .param("cpf", "12345678901"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Account account = accountGateway.searchByCpf("12345678901");
        assertNotNull(account);
    }
}
