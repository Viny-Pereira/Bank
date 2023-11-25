package com.api.bank.infra.configuration;

import com.api.bank.domain.gateway.interfaces.AccountGateway;
import com.api.bank.domain.usecase.CreateNewAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfig {
    @Bean
    public CreateNewAccount createNewAccount (AccountGateway accountGateway){
        return new CreateNewAccount(accountGateway);
    }
}
