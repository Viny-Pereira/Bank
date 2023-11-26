package com.api.bank.domain.model;

import com.api.bank.domain.model.enuns.TypeAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "account")
@NoArgsConstructor
@Getter
@Setter
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeAccount typeAccount;
    private String owner;
    @Column(unique = true, nullable = false)
    private String cpf;
    private BigDecimal balance;

    public Account(TypeAccount typeAccount, String owner, String cpf) {
        this.balance = new BigDecimal(0);
        this.typeAccount = typeAccount;
        this.owner = owner;
        this.cpf = cpf;
    }


}
