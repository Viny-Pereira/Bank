package com.api.bank.controller.infra.controller;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.GetAccountById;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetAccountByIdTest {
    @Mock
    private AccountGateway accountGateway;

    private GetAccountById getAccountById;

    @BeforeEach
    public void init() {
        getAccountById = new GetAccountById(accountGateway);
    }

    @Test
    public void shouldReturnAccountWhenFound() {
        // Arrange
        Long id = 1L;
        Account expectedAccount =new Account(TypeAccount.CC,"Viny", "10451001200");
        when(accountGateway.findById(id)).thenReturn(Optional.of(expectedAccount));

        // Act
        Optional<Account> result = getAccountById.execute(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedAccount, result.get());
        verify(accountGateway, times(1)).findById(id);
    }
}
