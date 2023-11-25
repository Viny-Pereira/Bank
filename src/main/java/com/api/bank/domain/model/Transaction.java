package com.api.bank.domain.model;

import com.api.bank.domain.model.enuns.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaction;
    private Long idAccount;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public Transaction(Long idAccount, TransactionType type, BigDecimal amount, LocalDateTime timestamp) {
        this.idAccount = idAccount;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
