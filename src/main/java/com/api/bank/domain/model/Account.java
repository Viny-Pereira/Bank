package com.api.bank.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name="Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long id;

    @Column(name = "AGENCY")
    private Long agency;

    @Column(name = "DIGIT")
    private Long digit;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private TypeAccount typeAccount;

    // Usuario
    @Column(name="OWNER")
    private String owner;

    @Column(name = "CPF", unique = true)
    private String cpf;

    public Account(Long id, Long agency, Long digit, BigDecimal balance, String owner, String cpf) {
        this.id = id;
        this.agency = agency;
        this.digit = digit;
        this.balance = balance;
        this.owner = owner;
        this.cpf = cpf;
        this.typeAccount = TypeAccount.CC;
    }

    public Account(TypeAccount typeAccount, String owner, String cpf) {
        this();
        this.typeAccount = typeAccount;
        this.owner = owner;
        this.cpf = cpf;
    }

    public Account() {
        this.agency = 001L;
        this.digit = 005L;
        balance = BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", agency=" + agency +
                ", digit=" + digit +
                ", balance=" + balance +
                ", typeAccount=" + typeAccount +
                ", owner='" + owner + '\'' +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
