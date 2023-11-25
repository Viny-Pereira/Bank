package com.api.bank.domain.usecase;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.gateway.interfaces.TransactionGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.Transaction;
import com.api.bank.domain.model.enuns.TransactionType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Component
public class Withdrawal {
    private final AccountGateway accountGateway;
    private final TransactionGateway transactionGateway;


    public Withdrawal(AccountGateway accountGateway, TransactionGateway transactionGateway) {
        this.accountGateway = accountGateway;
        this.transactionGateway = transactionGateway;
    }

    public void execute(Account account, BigDecimal among) {
        // Verificar se a conta existe, , etc.
        Account existingAccount = accountGateway.searchByCpf(account.getCpf());
        if(existingAccount!=null) {
            // se o saldo é suficiente
            if (account.getBalance().compareTo(among) >= 0){
                BigDecimal newBalance = account.getBalance().subtract(among);
                account.setBalance(newBalance);
                accountGateway.save(account);
                Transaction transaction = new Transaction(account.getId(), TransactionType.WITHDRAWAL, among, LocalDateTime.now());
                transactionGateway.saveTransaction(transaction);

            } else {
                throw new IllegalArgumentException("The balance is lower than the amount you wish to withdraw");
            }
        }else{
            throw new IllegalArgumentException("Account not find in our database");
        }
        // Atualizar o saldo da conta após o saque
    }

}
