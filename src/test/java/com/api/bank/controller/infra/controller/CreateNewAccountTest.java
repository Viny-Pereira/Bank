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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateNewAccountTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountGateway accountGateway;
    @Test
    public void succeedCreateAccount() throws Exception {
        mockMvc.perform(post("/account")
                        .param("typeAccount", "CC")
                        .param("name", "John Doe")
                        .param("cpf", "1251481351310"))
                .andExpect(status().isCreated());

        Account account = accountGateway.searchByCpf("1251481351310");
        assertNotNull(account);
    }
    @Test
    public void unsucceedCreateAccountAlreadyExist() throws Exception {
        mockMvc.perform(post("/account")
                        .param("typeAccount", "CC")
                        .param("name", "John Doe")
                        .param("cpf", "12345678901"));

        mockMvc.perform(post("/account")
                        .param("typeAccount", "CC")
                        .param("name", "John Doe")
                        .param("cpf", "12345678901"))
                .andExpect(status().isBadRequest());
        Account account = accountGateway.searchByCpf("12345678901");
        assertNotNull(account);
    }



}
