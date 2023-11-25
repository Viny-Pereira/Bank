package com.api.bank.domain.usecase;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.model.Account;

public class UpdateAccount {
    private final AccountGateway accountGateway;

    public UpdateAccount(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public void execute(Account updateAccount) throws Exception {
        Account existingAccount = accountGateway.searchByCpf(updateAccount.getCpf());
        // validar se o usuario ja possui uma conta
        if(existingAccount==null) {
            // - se possuir vamos lancar uma exception
            throw new Exception("User not found");
        }
        accountGateway.save(updateAccount);

    }
}
