package com.api.bank.infra.gateway.bd;

import com.api.bank.domain.gateway.AccountGateway;
import com.api.bank.domain.model.Account;

import java.util.Optional;

public class AccountRepository implements AccountGateway {
    private final SpringAccountRepository springAccountRepository;

    public AccountRepository(SpringAccountRepository springAccountRepository) {
        this.springAccountRepository = springAccountRepository;
    }

    @Override
    public Account searchByCpf(String cpf) {
        return springAccountRepository.findByCpf(cpf);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return springAccountRepository.findById(id);
    }

    @Override
    public Account save(Account account) {
        if (account.getId()==null){
            springAccountRepository.save(account);
            return account;
        } else{
            springAccountRepository.save(account);
            return account;

        }
    }

    @Override
    public void delete(Long id) {
        // todo

    }
}
