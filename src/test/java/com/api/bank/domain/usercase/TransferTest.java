package com.api.bank.domain.usercase;

import com.api.bank.domain.gateway.AccountGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.usecase.Deposit;
import com.api.bank.domain.usecase.Transfer;
import com.api.bank.domain.usecase.Withdrawal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferTest {
    @Mock
    private AccountGateway accountGateway;

    @Mock
    private Withdrawal withdrawal;

    @Mock
    private Deposit deposit;

    @InjectMocks
    private Transfer transfer;
    @Test
    public void executeTransferSuccessfully() throws Exception {
        // Arrange
        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(500.00);

        Account sourceAccount = new Account(sourceAccountId, 123L, 456L, BigDecimal.valueOf(1000.00), "John Doe", "12345678901");
        Account targetAccount = new Account(targetAccountId, 789L, 987L, BigDecimal.valueOf(2000.00), "Jane Doe", "98765432109");

        when(accountGateway.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountGateway.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));

        // Act
        BigDecimal sourceAccountBalance = transfer.execute(sourceAccountId, targetAccountId, amount);

        // Assert
        verify(withdrawal).execute(sourceAccount, amount);
        verify(deposit).execute(targetAccount, amount);

        // Adicione outras asserções conforme necessário
        assertEquals(sourceAccount.getBalance(), sourceAccountBalance);
    }


    @Test
    public void executeTransferNegativeAmount() throws Exception {
        // Arrange
        long sourceAccountId = 1L;
        long targetAccountId = 2L;
        BigDecimal negativeAmount = BigDecimal.valueOf(-500.00);

        // Act & Assert
        // Verifica se a exceção IllegalArgumentException é lançada para valores negativos
        assertThrows(RuntimeException.class, () -> transfer.execute(sourceAccountId, targetAccountId, negativeAmount));

        // Verifica que os métodos de withdrawal e deposit não foram chamados
        verify(withdrawal, never()).execute(any(), any());
        verify(deposit, never()).execute(any(), any());
    }
}
