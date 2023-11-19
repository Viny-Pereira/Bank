package com.api.bank.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Account {
    private Long id;
    private Long agency;
    private Long digit;
    private BigDecimal balance;

    // Usuario
    private String owner;
    private String cpf;

    public Account(Long id, Long agency, Long digit, BigDecimal balance, String owner, String cpf) {
        this.id = id;
        this.agency = agency;
        this.digit = digit;
        this.balance = balance;
        this.owner = owner;
        this.cpf = cpf;
    }

}
