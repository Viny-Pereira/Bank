package com.api.bank.domain.model;

import com.api.bank.domain.model.enuns.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
public class Transaction {
    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
}
