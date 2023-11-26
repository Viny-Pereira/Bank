# Spring Bank API

Spring Bank API é uma simples RESTful API construída usando Spring Boot usando como base de dados o H2, que permite aos usuários realizar várias operações bancárias, como criar uma conta, fazer um depósito, fazer um saque e transferir dinheiro entre contas.

## Features

- Criar uma conta com nome, CPF e tipo de conta
- Listar todas as contas
- Obter uma conta por ID
- Transferir dinheiro entre contas
- Depositar dinheiro em uma conta
- Sacar dinheiro de uma conta

## Prerequisites

- Java 17
- Maven 

## API Documentation

### Create an account

POST /account

```curl -X POST "http://localhost:8080/account?typeAccount=CC&name=Viny&cpf=12345678900"```

### List all accounts

GET /account

```curl -X GET "http://localhost:8080/account"```

### Get an account by ID

GET /account/{id}

```curl -X GET "http://localhost:8080/account/1"```

### Transfer money between accounts

POST /account/transfer

```curl -X POST "http://localhost:8080/account/transfer?sourceAccountId=1&targetAccountId=2&amount=100.00"```

### Deposit money into an account

POST /account/deposit

```curl -X POST "http://localhost:8080/account/deposit?accountId=1&amount=100.00"```

### Withdraw money from an account

POST /account/withdrawal

```curl -X POST "http://localhost:8080/account/withdrawal?accountId=1&amount=100.00"```
