package com.api.bank.domain.usercase;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.ListAllAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ListAllAccountTest {

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    private ListAllAccount listAllAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testExecuteShouldReturnListOfAccounts() {
        // Arrange
        List<Account> expectedAccounts = Arrays.asList(
                new Account(TypeAccount.CC, "John Doe", "12345678901"),
                new Account(TypeAccount.CP, "Jane Doe", "98765432109")
        );

        // Define o comportamento do mock
        when(accountGateway.getAll()).thenReturn(expectedAccounts);

        // Act
        List<Account> actualAccounts = listAllAccount.execute();

        // Assert
        assertEquals(expectedAccounts, actualAccounts);
    }

    @Test
    public void testExecuteShouldReturnEmptyListWhenNoAccounts() {
        // Arrange
        List<Account> expectedAccounts = Arrays.asList();

        // Define o comportamento do mock
        when(accountGateway.getAll()).thenReturn(expectedAccounts);

        // Act
        List<Account> actualAccounts = listAllAccount.execute();

        // Assert
        assertEquals(expectedAccounts, actualAccounts);
    }
    }