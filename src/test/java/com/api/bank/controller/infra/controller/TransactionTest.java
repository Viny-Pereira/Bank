package com.api.bank.controller.infra.controller;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.gateway.interfaces.TransactionGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.Transaction;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.Transfer;
import com.api.bank.infra.controller.AccountController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class TransactionTest {
    @InjectMocks
    private AccountController accountController;

    @Mock
    private Transfer transferService;

    @Mock
    private AccountGateway repository;

    @Mock
    private TransactionGateway transactionGateway;

    @InjectMocks
    private Transfer transfer;

    @Test
    public void testTransfer_Success() throws Exception {
        long sourceAccountId = 1;
        long targetAccountId = 2;
        BigDecimal amount = BigDecimal.valueOf(100);
        Account sourceAccount = new Account(TypeAccount.CC, "Viny", "12312445212");
        sourceAccount.setBalance(new BigDecimal(1000));

        Account targetAccount = new Account(TypeAccount.CC, "Maria", "12312446322");
        sourceAccount.setId(sourceAccountId);
        targetAccount.setId(targetAccountId);
        targetAccount.setBalance(new BigDecimal(1000));


        doReturn(Optional.of(sourceAccount)).when(repository).findById(sourceAccountId);
        doReturn(Optional.of(targetAccount)).when(repository).findById(targetAccountId);

        var result = transfer.execute(sourceAccountId, targetAccountId, amount);
        ResponseEntity<String> response = accountController.transferAmount(sourceAccountId, targetAccountId, amount);
        assertEquals(amount, result);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(new BigDecimal(900), sourceAccount.getBalance());
        assertEquals(new BigDecimal(1100), targetAccount.getBalance());

        ArgumentCaptor<Transaction> sourceTransactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);
        ArgumentCaptor<Transaction> targetTransactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);

        verify(transactionGateway, times(2)).saveTransaction(sourceTransactionArgumentCaptor.capture());
        verify(transactionGateway, times(2)).saveTransaction(targetTransactionArgumentCaptor.capture());

        List<Transaction> sourceTransactions = sourceTransactionArgumentCaptor.getAllValues();
        List<Transaction> targetTransactions = targetTransactionArgumentCaptor.getAllValues();

        assertEquals(1, sourceTransactions.get(0).getIdAccount());
        assertEquals(2, targetTransactions.get(1).getIdAccount());
    }

    @Test
    public void testTransferAmount_Failed() throws Exception {
        Long sourceAccountId = 1L;
        Long targetAccountId = 2L;
        BigDecimal amount = BigDecimal.valueOf(100);

        when(transferService.execute(sourceAccountId, targetAccountId, amount)).thenReturn(null);

        ResponseEntity<String> response = accountController.transferAmount(3L, targetAccountId, amount);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }
}