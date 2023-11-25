package com.api.bank.domain.usecase;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.gateway.interfaces.TransactionGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.Transaction;
import com.api.bank.domain.model.enuns.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
@Component
public class Deposit {

    private final AccountGateway accountGateway;
    private final TransactionGateway transactionGateway;

    public Deposit(AccountGateway accountGateway, TransactionGateway transactionGateway) {
        this.accountGateway = accountGateway;
        this.transactionGateway = transactionGateway;
    }

    public void execute(Account account, BigDecimal amount) throws Exception {
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        Account existingAccount = accountGateway.searchByCpf(account.getCpf());
        if (existingAccount==null){
            throw new Exception("Account not find in our database");
        }
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountGateway.save(account);
        Transaction transaction = new Transaction(account.getId(), TransactionType.DEPOSIT, amount, LocalDateTime.now());
        transactionGateway.saveTransaction(transaction);
    }
}
