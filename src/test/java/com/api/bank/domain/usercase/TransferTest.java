package com.api.bank.domain.usercase;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.gateway.interfaces.TransactionGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.Transaction;
import com.api.bank.domain.model.enuns.TransactionType;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.Deposit;
import com.api.bank.domain.usecase.Transfer;
import com.api.bank.domain.usecase.Withdrawal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    private Account sourceAccount;
    private Account targetAccount;

    @BeforeEach
    public void init() {
        sourceAccount = new Account(TypeAccount.CC, "Viny", "12312445212");
        sourceAccount.setBalance(new BigDecimal(1000));

        targetAccount = new Account(TypeAccount.CC, "Maria", "12312446322");
        targetAccount.setBalance(new BigDecimal(1000));

    }

    @Test
    public void executeTransferSuccessfully() throws Exception {
        // Given
        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(500.00);
        sourceAccount.setId(sourceAccountId);
        targetAccount.setId(targetAccountId);
        // when
        when(accountGateway.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountGateway.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));
        Transaction sourceTransaction = new Transaction(sourceAccount.getId(), TransactionType.TRANSFER, amount, LocalDateTime.now());
        Transaction targetTransaction = new Transaction(sourceAccount.getId(), TransactionType.TRANSFER, amount, LocalDateTime.now());
        BigDecimal newAmount = BigDecimal.valueOf(500.00);

        targetTransaction.setAmount(newAmount);
        transactionGateway.saveTransaction(sourceTransaction);
        transactionGateway.saveTransaction(targetTransaction);
        // then
        transfer.execute(sourceAccount.getId(), targetAccount.getId(), amount);
        assertEquals(sourceAccount.getBalance(), amount);
    }

    @Test
    public void throwExceptionNegativeInsufficientBalance() throws Exception {
        // Given
        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(10000.00);
        sourceAccount.setId(sourceAccountId);
        targetAccount.setId(targetAccountId);
        // when
        when(accountGateway.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountGateway.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));

        // then
        Throwable throwable = assertThrows(Exception.class, () -> transfer.execute(sourceAccountId, targetAccountId, amount));
        assertEquals("The balance is lower than the amount you wish to transfer", throwable.getMessage());

    }


    @Test
    public void throwExceptionNegativeAmount() throws Exception {
        // Arrange
        long sourceAccountId = 1L;
        long targetAccountId = 2L;
        BigDecimal negativeAmount = BigDecimal.valueOf(-500.00);

        Throwable throwable = assertThrows(Exception.class, () -> transfer.execute(sourceAccountId, targetAccountId, negativeAmount));
        assertEquals("Operation was not carried out because the transaction value is negative.", throwable.getMessage());
        // Verifica que os métodos de withdrawal e deposit não foram chamados
        verify(withdrawal, never()).execute(any(), any());
        verify(deposit, never()).execute(any(), any());
    }

    @Test
    public void throwExceptionAccountNotFound() throws Exception {
        // Arrange
        long nonExistingAccountId = 999L;
        long targetAccountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(500.00);

        // when
        when(accountGateway.findById(nonExistingAccountId)).thenReturn(Optional.empty());

        // then
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> transfer.execute(nonExistingAccountId, targetAccountId, amount));
        assertEquals("Account not found in our database", throwable.getMessage());

        // Verifica que os métodos de withdrawal e deposit não foram chamados
        verify(withdrawal, never()).execute(any(), any());
        verify(deposit, never()).execute(any(), any());
    }
}
