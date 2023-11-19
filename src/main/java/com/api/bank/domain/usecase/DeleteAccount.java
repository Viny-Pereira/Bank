package com.api.bank.domain.usecase;

import com.api.bank.domain.gateway.AccountGateway;
import com.api.bank.domain.model.Account;

public class DeleteAccount {
    private final AccountGateway accountGateway;

    public DeleteAccount(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public void delete(Long accountId) throws Exception {
        Account existingAccount = accountGateway.findById(accountId);

        if (existingAccount != null) {
            accountGateway.delete(existingAccount.getId());
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }
}
