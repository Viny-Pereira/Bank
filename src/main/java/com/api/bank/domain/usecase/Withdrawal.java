package com.api.bank.domain.usecase;

import com.api.bank.domain.gateway.AccountGateway;
import com.api.bank.domain.model.Account;

import java.math.BigDecimal;

public class Withdrawal {
    private final AccountGateway accountGateway;

    public Withdrawal(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public void execute(Account account, BigDecimal value) {
        // Verificar se a conta existe, , etc.
        Account existingAccount = accountGateway.searchByCpf(account.getCpf());
        if(existingAccount!=null) {
            // se o saldo é suficiente
            if (account.getBalance().compareTo(value) >= 0){
                BigDecimal newBalance = account.getBalance().subtract(value);
                account.setBalance(newBalance);

                //accountGateway.updateAccount(account);
            } else {
                throw new IllegalArgumentException("The balance is lower than the amount you wish to withdraw");
            }
        }else{
            throw new IllegalArgumentException("Account not find in our database");
        }
        // Atualizar o saldo da conta após o saque
    }

}
