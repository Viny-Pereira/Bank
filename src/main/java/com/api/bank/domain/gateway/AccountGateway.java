package com.api.bank.domain.gateway;

import com.api.bank.domain.model.Account;

public interface AccountGateway {
    Account searchByCpf(String cpf);
    Account findById(Long id);
    Account save(Account account);
    void updateAccount(Account account);
    void delete(Long id);
}

