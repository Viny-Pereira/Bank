package com.api.bank.infra.controller;

import com.api.bank.domain.gateway.AccountGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.TypeAccount;
import com.api.bank.infra.gateway.bd.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountGateway accountRepository;

    public AccountController(AccountGateway accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping("{id}")
    public Account getAccount(@PathVariable Long id){
        return accountRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @GetMapping
    public List<Account> getAccount(){
        return accountRepository.getAll();
    }
    @PostMapping
    public Account createAccount(@RequestParam TypeAccount typeAccount,
                                 @RequestParam String name,
                                 @RequestParam String cpf
    ) {
        return accountRepository.save(new Account(typeAccount, name, cpf));
    }
}
