package com.api.bank.controller.infra.controller;
import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.gateway.interfaces.TransactionGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.Deposit;
import com.api.bank.domain.usecase.GetAccountById;
import com.api.bank.domain.usecase.Withdrawal;
import com.api.bank.infra.controller.AccountController;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class DepositTest {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AccountGateway accountGateway;
    @Mock
    private GetAccountById getAccountById;

    @Mock
    private Deposit deposit;

    @InjectMocks
    private AccountController accountController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testDepositAmount() throws Exception {
        // Arrange - Crie uma conta para testar
        mockMvc.perform(post("/account")
                        .param("typeAccount", TypeAccount.CC.name())
                        .param("name", "John Doe")
                        .param("cpf", "12345678900")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // Simule o retorno da conta criada
        Account account = new Account(TypeAccount.CC, "John Doe", "12345678900");
        account.setId(1L);
        when(getAccountById.execute(1L)).thenReturn(java.util.Optional.of(account));

        // Faça um depósito na conta recém-criada
        mockMvc.perform(post("/account/deposit")
                        .param("accountId", "1")
                        .param("amount", "500.00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }


    @Test
    void testDepositExecuteAccountNotFound() throws Exception {
        // Arrange
        Account nonExistingAccount = new Account();
        nonExistingAccount.setId(2L);

        BigDecimal withdrawalAmount = BigDecimal.valueOf(500);

        // Mocking
        when(accountGateway.findById(nonExistingAccount.getId())).thenReturn(null);

        // Act
        deposit.execute(nonExistingAccount, withdrawalAmount);
        ResponseEntity<String> response = accountController.depositAmount(nonExistingAccount.getId(), withdrawalAmount);


        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
