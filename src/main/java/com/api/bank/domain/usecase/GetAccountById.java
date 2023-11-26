package com.api.bank.domain.usecase;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.model.Account;

import java.util.Optional;

public class GetAccountById {
    private final AccountGateway accountGateway;

    public GetAccountById(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public Optional<Account> execute(Long id){
        return accountGateway.findById(id);
    }
}
