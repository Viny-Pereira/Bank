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
public class Transfer {
    private final AccountGateway repository;
    private final Withdrawal withdrawal;
    private final Deposit deposit;
    private final TransactionGateway transactionGateway;

    public Transfer(AccountGateway repository, Withdrawal withdrawal, Deposit deposit, TransactionGateway transactionGateway) {
        this.repository = repository;
        this.withdrawal = withdrawal;
        this.deposit = deposit;
        this.transactionGateway = transactionGateway;
    }

    public BigDecimal execute(long idSourceAccount, long idTargetAccount, BigDecimal among) throws Exception {
        if (among.compareTo(BigDecimal.ZERO) >= 0) {
            Account sourceAccount = repository.findById(idSourceAccount).orElseThrow(RuntimeException::new);
            Account targetAccount = repository.findById(idTargetAccount).orElseThrow(RuntimeException::new);
            if(sourceAccount != null && targetAccount != null) {
                // se o saldo é suficiente
                if (sourceAccount.getBalance().compareTo(among) >= 0){
                    BigDecimal newSourceBalance = sourceAccount.getBalance().subtract(among);
                    BigDecimal newTargetBalance = targetAccount.getBalance().add(among);
                    sourceAccount.setBalance(newSourceBalance);
                    targetAccount.setBalance(newTargetBalance);
                    repository.save(sourceAccount);
                    repository.save(targetAccount);

                    // Salvar a transação após a transferência
                    Transaction sourceTransaction = new Transaction(sourceAccount.getId(), TransactionType.TRANSFER, among, LocalDateTime.now());
                    Transaction targetTransaction = new Transaction(targetAccount.getId(), TransactionType.TRANSFER, among, LocalDateTime.now());
                    transactionGateway.saveTransaction(sourceTransaction);
                    transactionGateway.saveTransaction(targetTransaction);
                    return among;

                } else {
                    throw new IllegalArgumentException("The balance is lower than the amount you wish to transfer");
                }
            } else {
                throw new IllegalArgumentException("Account not found in our database");
            }
        }else{
            throw new Exception("Operation was not carried out because the transaction value is negative.");
        }



    }
}
