package com.api.bank.domain.usercase;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.gateway.interfaces.TransactionGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.Deposit;
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
public class DepositTest {
    @Mock
    AccountGateway accountGateway;
    @Mock
    TransactionGateway transactionGateway;
    @InjectMocks
    Deposit deposit;

    @Test
    public void throwUnregisteredUserExceptionTest() {
        Account account = new Account(TypeAccount.CC, "Viny", "12312445212");
        BigDecimal amountDeposit = new BigDecimal(1000);

        when(accountGateway.searchByCpf(account.getCpf())).thenReturn(null);

        Throwable throwable;
        throwable = assertThrows(Exception.class, () -> {
            deposit.execute(account, amountDeposit);
        });

        assertEquals("Account not find in our database", throwable.getMessage());

    }

    @Test
    public void successfulDeposit() throws Exception {
        Account account = new Account(TypeAccount.CC, "Viny", "12312445212");
        account.setBalance(new BigDecimal(1000));
        BigDecimal amountWithdrawal = new BigDecimal("1000");
        when(accountGateway.searchByCpf(account.getCpf())).thenReturn(account);

        deposit.execute(account, amountWithdrawal);
        BigDecimal expectedBalance = new BigDecimal("2000.00");
        assertEquals(expectedBalance, account.getBalance());
    }
}
