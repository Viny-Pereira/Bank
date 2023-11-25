package com.api.bank.infra.gateway.bd;

import com.api.bank.domain.gateway.interfaces.TransactionGateway;
import com.api.bank.domain.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class TransactionGatewayImpl implements TransactionGateway {
    private final SpringTransactionRepository springTransactionRepository;

    public TransactionGatewayImpl(SpringTransactionRepository springTransactionRepository) {
        this.springTransactionRepository = springTransactionRepository;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        springTransactionRepository.save(transaction);
    }
}
