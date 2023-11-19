package com.api.bank.infra.controller;

import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.TypeAccount;
import com.api.bank.infra.gateway.bd.AccountRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    AccountRepository accountRepository;

    @PostMapping
    public Account createAccount(@RequestParam TypeAccount typeAccount,
                                 @RequestParam String name,
                                 @RequestParam String cpf
    ) {
        return accountRepository.save(new Account(typeAccount, name, cpf));
    }
}
