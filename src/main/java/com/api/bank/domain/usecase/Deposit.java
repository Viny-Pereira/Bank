package com.api.bank.domain.usecase;

import com.api.bank.domain.gateway.AccountGateway;
import com.api.bank.domain.model.Account;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Deposit {

    private final AccountGateway accountGateway;

    public Deposit(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }
    public BigDecimal execute(Account account, BigDecimal amount) throws Exception {
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        Account existingAccount = accountGateway.searchByCpf(account.getCpf());
        if (existingAccount==null){
            throw new Exception("Account not find in our database");
        }
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountGateway.save(account);
        return account.getBalance();
    }
}
