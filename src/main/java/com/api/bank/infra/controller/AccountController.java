package com.api.bank.infra.controller;

import com.api.bank.domain.gateway.AccountGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.Transfer;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountGateway accountRepository;
    private final Transfer transfer;

    public AccountController(AccountGateway accountRepository, Transfer transfer) {
        this.accountRepository = accountRepository;
        this.transfer = transfer;
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



    @PostMapping("/transfer")
    @Transactional  // Adicione a anotação @Transactional aqui
    public BigDecimal transferAmount(@RequestParam Long sourceAccountId,
                                     @RequestParam Long targetAccountId,
                                     @RequestParam BigDecimal amount) throws Exception {
        return transfer.execute(sourceAccountId, targetAccountId, amount);
    }
}
