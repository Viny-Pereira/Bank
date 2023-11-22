package com.api.bank.domain.gateway;

import com.api.bank.domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountGateway {
    Account searchByCpf(String cpf);
    Optional<Account> findById(Long id);
    Account save(Account account);
    void delete(Long id);
    List<Account> getAll();
}

