package com.api.bank.domain.usecase;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.model.Account;

import java.util.List;

public class ListAllAccount {
    private final AccountGateway accountGateway;

    public ListAllAccount(AccountGateway accountGateway) {

        this.accountGateway = accountGateway;
    }

    public List<Account> execute(){
        return accountGateway.getAll();
    }

}
