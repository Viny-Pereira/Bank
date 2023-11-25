package com.api.bank.domain.usercase;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.gateway.interfaces.TransactionGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
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
    TransactionGateway transactionGateway;
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

        Account sourceAccount = new Account(TypeAccount.CC, "Viny", "12312445212");
        sourceAccount.setBalance(new BigDecimal(1000));
        sourceAccount.setId(sourceAccountId);
        Account targetAccount = new Account(TypeAccount.CC, "Maria", "12312446322");
        targetAccount.setBalance(new BigDecimal(1000));

        targetAccount.setId(targetAccountId);
        when(accountGateway.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountGateway.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));

        // Act
        when(transfer.execute(sourceAccountId, targetAccountId, amount)).thenReturn(amount);

        // Adicione outras asserções conforme necessário
        assertEquals(sourceAccount.getBalance(), amount);
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
