# My Simple Wallet - Hexagonal

Um projeto simples de carteira digital desenvolvido com Spring Boot e Arquitetura Hexagonal (Ports and Adapters) para fins de estudo.

## Estrutura de pastas para arquitetura hexagonal

```
srv
└── main
    ├── java
    │   └── br
    │       └── dev
    │           └── mission
    │               └── simplewallet
    │                   └── api
    │                       ├── application
    │                       │   ├── domain
    │                       │   │   ├── exception
    │                       │   │   └── model
    │                       │   │       ├── account
    │                       │   │       │   └── Account.java
    │                       │   │       ├── category
    │                       │   │       │   └── Category.java
    │                       │   │       ├── transaction
    │                       │   │       │   ├── Transaction.java
    │                       │   │       │   └── TransactionType.java
    │                       │   │       └── user
    │                       │   │           └── User.java
    │                       │   ├── ports
    │                       │   │   ├── in
    │                       │   │   │   └── web
    │                       │   │   │       ├── controller
    │                       │   │   │       └── dto
    │                       │   │   └── out
    │                       │   │       └── persistence
    │                       │   │           ├── entity
    │                       │   │           ├── mapper
    │                       │   │           └── repository
    │                       │   └── usecase
    │                       └── infrastructure
    │                           ├── adapters
    │                           │   ├── in
    │                           │   └── out
    │                           └── config
    ├── resources
    └── test
```