package com.api.bank.domain.usercase;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.UpdateAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateAccountTest {

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    private UpdateAccount updateAccount;

    @Test
    public void throwsExceptionIfUserNotFound() {
        Account account = new Account(TypeAccount.CC, "Viny", "12312445212");

        when(accountGateway.searchByCpf(account.getCpf())).thenReturn(null);
        Throwable throwable = assertThrows(Exception.class, () -> updateAccount.execute(account));
        assertEquals("User not found", throwable.getMessage());

    }
    @Test
    public void updateSuccessful() throws Exception {
        Account account = new Account(TypeAccount.CC, "Viny", "12312445212");

        when(accountGateway.searchByCpf(account.getCpf())).thenReturn(account);

        Account updatedAccount = new Account(TypeAccount.CC, "Pedro", "12312445212");
        updateAccount.execute(updatedAccount);

        verify(accountGateway).save(updatedAccount);

    }
}
