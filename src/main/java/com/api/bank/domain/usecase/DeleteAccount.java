package com.api.bank.domain.usecase;

import com.api.bank.domain.gateway.AccountGateway;
import com.api.bank.domain.model.Account;

import java.util.Optional;

public class DeleteAccount {
    private final AccountGateway accountGateway;

    public DeleteAccount(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public void delete(Long accountId){
        Optional<Account> existingAccount = accountGateway.findById(accountId);

        if (existingAccount.isPresent()) {
            accountGateway.delete(existingAccount.get().getId());
        } else {
            throw new NullPointerException("Account not found");
        }
    }
}
