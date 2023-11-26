package com.api.bank.infra.controller;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.model.Account;
import com.api.bank.domain.model.enuns.TypeAccount;
import com.api.bank.domain.usecase.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final CreateNewAccount createNewAccount;
    private final Transfer transfer;
    private final Deposit deposit;
    private final Withdrawal withdrawal;
    private final ListAllAccount listAllAccount;
    private final GetAccountById getAccountById;

    public AccountController(CreateNewAccount createNewAccount, Transfer transfer, Deposit deposit, Withdrawal withdrawal, ListAllAccount listAllAccount, GetAccountById getAccountById) {
        this.createNewAccount = createNewAccount;
        this.transfer = transfer;
        this.deposit = deposit;
        this.withdrawal = withdrawal;
        this.listAllAccount = listAllAccount;
        this.getAccountById = getAccountById;
    }

    @GetMapping
    public List<Account> getAllAccount(){
        return listAllAccount.execute();
    }
    @GetMapping("/{id}")
    public Optional<Account> getAccountById(@PathVariable Long id){
        return getAccountById.execute(id);
    }


    @PostMapping
    public ResponseEntity<String> CreatAccount (@RequestParam TypeAccount typeAccount,
                                                  @RequestParam String name,
                                                  @RequestParam String cpf
    ) {
        try{
            createNewAccount.execute(new Account(typeAccount, name, cpf));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
    }



    @PostMapping("/transfer")
    @Transactional  // Adicione a anotação @Transactional aqui
    public ResponseEntity<String> transferAmount(@RequestParam Long sourceAccountId,
                                     @RequestParam Long targetAccountId,
                                     @RequestParam BigDecimal amount) {
        try{
            transfer.execute(sourceAccountId, targetAccountId, amount);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Transfer successful");

    }

    @PostMapping("/deposit")
    public ResponseEntity<String> depositAmount(@RequestParam Long accountId, @RequestParam BigDecimal amount) {
        try {
            Account account = getAccountById.execute(accountId).orElse(null);
            if (account == null) {
                return ResponseEntity.badRequest().body("Account not found");
            }

            deposit.execute(account, amount);

            return ResponseEntity.status(HttpStatus.OK).body("Deposit successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> withdrawalAmount(@RequestParam Long accountId, @RequestParam BigDecimal amount) {
        try {
            Account account = getAccountById.execute(accountId).orElse(null);
            if (account == null) {
                return ResponseEntity.badRequest().body("Account not found");
            }

            withdrawal.execute(account, amount);

            return ResponseEntity.status(HttpStatus.OK).body("Withdrawal successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
