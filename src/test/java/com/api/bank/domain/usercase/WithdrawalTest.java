package com.api.bank.domain.usercase;


import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.gateway.interfaces.TransactionGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.Withdrawal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WithdrawalTest {
    @Mock
    private AccountGateway accountGateway;
    @Mock
    TransactionGateway transactionGateway;
    @InjectMocks
    private Withdrawal withdrawal;


    @Test
    public void throwUnregisteredUserExceptionTest(){
        Account account = new Account(TypeAccount.CC, "Viny", "12312445212");
        BigDecimal amountWithdrawal = new BigDecimal(1000);

        when(accountGateway.searchByCpf(account.getCpf())).thenReturn(null);

        Throwable throwable;
        throwable = assertThrows(IllegalArgumentException.class, () -> {
            withdrawal.execute(account, amountWithdrawal);
        });

        assertEquals("Account not find in our database", throwable.getMessage());

    }

    @Test
    public void throwInsufficientBalanceExceptionTest(){
        Account account = new Account(TypeAccount.CC, "Viny", "12312445212");
        account.setBalance(new BigDecimal(1000));
        BigDecimal amountWithdrawal = new BigDecimal(100000);

        when(accountGateway.searchByCpf(account.getCpf())).thenReturn(account);

        Throwable throwable;
        throwable = assertThrows(IllegalArgumentException.class, () -> {
            withdrawal.execute(account, amountWithdrawal);
        });

        assertEquals("The balance is lower than the amount you wish to withdraw", throwable.getMessage());
    }
    @Test
    public void successfulWithdrawalTest(){
        Account account = new Account(TypeAccount.CC, "Viny", "12312445212");
        account.setBalance(new BigDecimal("1000"));
        BigDecimal amountWithdrawal = new BigDecimal("1000");
        when(accountGateway.searchByCpf(account.getCpf())).thenReturn(account);

        withdrawal.execute(account, amountWithdrawal);
        BigDecimal expectedBalance = new BigDecimal("0");

        assertEquals(expectedBalance, account.getBalance());
    }
}
