package com.api.bank.infra.controller;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.CreateNewAccount;
import com.api.bank.domain.usecase.ListAllAccount;
import com.api.bank.domain.usecase.Transfer;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountGateway accountRepository;
    private final CreateNewAccount createNewAccount;
    private final Transfer transfer;
    private final ListAllAccount listAllAccount;

    public AccountController(AccountGateway accountRepository, CreateNewAccount createNewAccount, Transfer transfer, ListAllAccount listAllAccount) {
        this.accountRepository = accountRepository;
        this.createNewAccount = createNewAccount;
        this.transfer = transfer;
        this.listAllAccount = listAllAccount;
    }

    @GetMapping("{id}")
    public Account getAccount(@PathVariable Long id){
        return accountRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @GetMapping
    public List<Account> getAllAccount(){
        return listAllAccount.execute();
    }


    @PostMapping
    public ResponseEntity<String> ResponseEntity (@RequestParam TypeAccount typeAccount,
                                                  @RequestParam String name,
                                                  @RequestParam String cpf
    ) {
        try{
            createNewAccount.execute(new Account(typeAccount, name, cpf));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(accountRepository.toString());
    }



    @PostMapping("/transfer")
    @Transactional  // Adicione a anotação @Transactional aqui
    public void transferAmount(@RequestParam Long sourceAccountId,
                                     @RequestParam Long targetAccountId,
                                     @RequestParam BigDecimal amount) throws Exception {
        transfer.execute(sourceAccountId, targetAccountId, amount);
    }
}
