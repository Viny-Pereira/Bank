package com.api.bank.domain.usecase;

import com.api.bank.domain.model.Account;
import com.api.bank.domain.gateway.AccountGateway;

public class CreateNewAccount {
    private final AccountGateway accountGateway;

    public CreateNewAccount(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public Account execute(Account account) throws Exception {
        Account existingAccount = accountGateway.searchByCpf(account.getCpf());

        // validar se o usuario ja possui uma conta
        if(existingAccount!=null) {
            // - se possuir vamos lancar uma exception
            throw new Exception("User already has a account ");
        }

        // criar uma nova conta
        Account newAccount = accountGateway.save(account);

        //return Conta;
        return newAccount;
    }
}
