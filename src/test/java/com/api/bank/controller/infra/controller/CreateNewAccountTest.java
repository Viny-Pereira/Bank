package com.api.bank.controller.infra.controller;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    public void succeedCreateAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .param("typeAccount", "CC")
                        .param("name", "John Doe")
                        .param("cpf", "12345678901"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Account account = accountGateway.searchByCpf("12345678901");
        assertNotNull(account);
    }
    @Test
    public void unsucceedCreateAccountAlreadyExist() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .param("typeAccount", "CC")
                        .param("name", "John Doe")
                        .param("cpf", "12345678901"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .param("typeAccount", "CC")
                        .param("name", "John Doe")
                        .param("cpf", "12345678901"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Account account = accountGateway.searchByCpf("12345678901");
        assertNotNull(account);
    }
}
