package com.api.bank.domain.usercase;


import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.CreateNewAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateNewAccountTest {

    @Mock
    private AccountGateway accountGateway;

    @InjectMocks
    private CreateNewAccount createNewAccount;

    @Test
    public void throwsExceptionIfUserAlreadyHasAccount() {
        Account account = new Account(TypeAccount.CC, "Viny", "12312445212");

        when(accountGateway.searchByCpf(account.getCpf())).thenReturn(account);
        assertThrows(Exception.class, () -> createNewAccount.execute(account));

        Mockito.verify(accountGateway, Mockito.times(1)).searchByCpf(account.getCpf());
        Mockito.verify(accountGateway, Mockito.times(0)).save(account);
    }


    @Test
    public void creatNewAccount() throws Exception {
        Account account = new Account(TypeAccount.CC, "Viny", "12312445212");

        when(accountGateway.searchByCpf(account.getCpf())).thenReturn(null);
        when(accountGateway.save(account)).thenReturn(account);

        Account newAccount = createNewAccount.execute(account);

        Assertions.assertAll(
                () -> Assertions.assertEquals("Viny", newAccount.getOwner()),
                () -> Assertions.assertEquals("12312445212", newAccount.getCpf()));

        Mockito.verify(accountGateway, Mockito.times(1)).searchByCpf(account.getCpf());
        Mockito.verify(accountGateway, Mockito.times(1)).save(account);

    }
}
