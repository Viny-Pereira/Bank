package com.api.bank.infra.controller;

import com.api.bank.infra.gateway.bd.AccountRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    AccountRepository accountRepository;

}
