package com.api.bank.domain.usercase;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.DeleteAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeleteAccountTest {

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    private DeleteAccount deleteAccount;

    @Test
    public void deleteAccountSuccessfully() throws Exception {
        // Arrange
        Long accountId = 1231L;
        Account existingAccount = new Account(TypeAccount.CC, "Viny", "12312445212");
        when(accountGateway.findById(accountId)).thenReturn(Optional.of(existingAccount));

        // Act
        deleteAccount.delete(accountId);

        // Assert
        verify(accountGateway).delete(existingAccount.getId());
    }

    @Test
    public void deleteAccountNotFound() {
        // Arrange
        Long nonExistingAccountId = 456L;

        when(accountGateway.findById(nonExistingAccountId)).thenReturn(Optional.empty());

        // Act & Assert
        Throwable throwable = assertThrows(NullPointerException.class, () -> deleteAccount.delete(nonExistingAccountId));
        Assertions.assertEquals("Account not found", throwable.getMessage());
    }
}
