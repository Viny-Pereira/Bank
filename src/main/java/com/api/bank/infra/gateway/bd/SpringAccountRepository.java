package com.api.bank.infra.gateway.bd;

import com.api.bank.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringAccountRepository extends JpaRepository<Account, Long> {
    Account findByCpf(String cpf);
}
