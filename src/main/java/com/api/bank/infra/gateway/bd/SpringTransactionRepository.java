package com.api.bank.infra.gateway.bd;

import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringTransactionRepository extends JpaRepository<Transaction, Long> {
}
