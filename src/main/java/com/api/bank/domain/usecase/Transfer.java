package com.api.bank.domain.usecase;

import com.api.bank.domain.gateway.AccountGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.infra.gateway.bd.AccountRepository;

import java.math.BigDecimal;

public class Transfer {
    private final AccountGateway repository;
    private final Withdrawal withdrawal;
    private final Deposit deposit;

    public Transfer(AccountGateway repository, Withdrawal withdrawal, Deposit deposit) {
        this.repository = repository;
        this.withdrawal = withdrawal;
        this.deposit = deposit;
    }

    public BigDecimal execute(long idSourceAccount, long idTargetAccount, BigDecimal among) throws Exception {
        Account source = repository.findById(idSourceAccount).orElseThrow(RuntimeException::new);
        Account target = repository.findById(idTargetAccount).orElseThrow(RuntimeException::new);

        if (among.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Operation was not carried out because the transaction value is negative.");
        }

        withdrawal.execute(source, among);
        deposit.execute(target, among);
        return source.getBalance();
    }

}
