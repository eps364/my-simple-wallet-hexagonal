# My Simple Wallet - Hexagonal

Um projeto de carteira digital desenvolvido com Spring Boot e Arquitetura Hexagonal (Ports and Adapters) para fins de estudo.

## Estrutura de Diretórios

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

## Objetivo da Arquitetura

O objetivo desta arquitetura é isolar a lógica central da aplicação (as regras de negócio) de dependências externas, como banco de dados, interfaces de usuário e outros frameworks.

Isso torna o sistema mais fácil de testar, manter e adaptar a novas tecnologias no futuro, pois o núcleo da aplicação não depende de detalhes de implementação específicos.

## Vantagens da Arquitetura Hexagonal

- **Crescimento Sustentável**
    A separação clara entre a lógica de negócio (o *core* da aplicação) e as dependências externas (banco de dados, interfaces de usuário, APIs de terceiros) permite que o sistema evolua sem a necessidade de grandes refatorações. Novas funcionalidades podem ser adicionadas de forma isolada, reduzindo o risco de introduzir bugs em outras partes do sistema.

- **Troca de Framework Facilitada**
    Como a lógica de negócio não depende de nenhuma tecnologia específica, é possível trocar um framework ou um serviço externo com impacto mínimo. Por exemplo, migrar de um banco de dados MySQL para PostgreSQL exigiria apenas a criação de um novo "adaptador" de persistência, sem alterar as regras de negócio.

- **Baixo Acoplamento**
    Os componentes do sistema são independentes e se comunicam através de contratos bem definidos (as "portas"). A lógica de negócio não conhece os detalhes da implementação do banco de dados ou da interface web. Isso reduz o efeito cascata de mudanças: uma alteração na interface do usuário, por exemplo, não quebrará a lógica de negócio.

- **Testabilidade Aprimorada do Core da Aplicação**
    O *core* da aplicação, por ser totalmente isolado, pode ser testado com testes de unidade puros, sem a necessidade de mocks complexos para banco de dados ou serviços web. Isso torna os testes mais rápidos, confiáveis e fáceis de escrever, garantindo que a lógica de negócio funcione como esperado.


## Desvantagens da Arquitetura Hexagonal

- **Demora Inicial**
    A configuração inicial exige mais planejamento e a criação de mais código *boilerplate* (repetitivo). É preciso definir as interfaces (portas) para cada tipo de interação e implementar os adaptadores iniciais, o que pode tornar o início do desenvolvimento mais lento em comparação com uma abordagem monolítica tradicional.

- **Aparente Complexidade Inicial**
    Para desenvolvedores que não conhecem o padrão, a quantidade de camadas, arquivos e diretórios pode ser intimidadora. O fluxo de controle, que sempre passa de um adaptador para uma porta até chegar ao *core*, pode parecer indireto e mais complexo no início, aumentando a curva de aprendizado.

- **Potencial para Over-engineering em Projetos Simples**
    Em aplicações pequenas ou em um simples CRUD (Criar, Ler, Atualizar, Deletar), a estrutura rigorosa da arquitetura hexagonal pode ser um exagero. A complexidade adicionada pode não trazer benefícios que justifiquem o esforço, tornando o projeto desnecessariamente complicado.

## Referências

- **[Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)** - Artigo Original de Alistair Cockburn.
- **[Ports and Adapters Architecture](https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/)** - Artigo por Herberto Graca.
- **[Hexagonal Architecture in Spring Boot](https://github.com/sonnesen/fiap-product-api-with-ports-and-adapters)** - Github do Prof. Winston Spencer Sonnesen