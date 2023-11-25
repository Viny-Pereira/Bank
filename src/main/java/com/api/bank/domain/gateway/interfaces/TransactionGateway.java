package com.api.bank.domain.gateway.interfaces;

import com.api.bank.domain.model.Transaction;

public interface TransactionGateway {
    void saveTransaction(Transaction transaction);
}
