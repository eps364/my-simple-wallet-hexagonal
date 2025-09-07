# My Simple Wallet - Arquitetura Hexagonal

Uma aplicação de carteira digital simples implementada usando **Arquitetura Hexagonal (Ports and Adapters)** com Spring Boot.

## 🏗️ Arquitetura

Este projeto segue os princípios da Arquitetura Hexagonal, separando as responsabilidades em camadas bem definidas:

- **Core (Domain)**: Contém a lógica de negócio e as regras de domínio
- **Ports**: Interfaces que definem contratos entre as camadas
- **Adapters**: Implementações que conectam o core com tecnologias externas

## 📁 Estrutura do Projeto

```
src/main/java/br/dev/mission/simplewallet/
├── core/                                    # Núcleo da aplicação (Domain)
│   ├── model/
│   │   └── account/
│   │       └── AccountCore.java             # Entidade de domínio
│   ├── ports/
│   │   ├── inbound/                         # Portas de entrada
│   │   │   └── account/
│   │   │       └── AccountPort.java         # ← interface (Casos de uso)
│   │   └── outbound/                        # Portas de saída
│   │       └── account/
│   │           └── AccountRepositoryPort.java # ← interface (Persistência)
│   ├── service/                             # Serviços de aplicação
│   │   └── account/
│   │       └── AccountService.java          # Implements AccountPort
│   └── exceptions/                          # Exceções de domínio
│       ├── InvalidException.java
│       └── NotFoundException.java
│
├── infrastructure/                          # Infraestrutura (Adapters)
│   ├── adapters/
│   │   ├── inbound/                         # Adaptadores de entrada
│   │   │   └── web/
│   │   │       └── account/
│   │   │           └── AccountController.java # REST Controller
│   │   └── outbound/                        # Adaptadores de saída
│   │       └── account/
│   │           └── AccountJpaAdapter.java   # Implements AccountRepositoryPort
│   ├── entities/                            # Entidades JPA
│   │   └── account/
│   │       └── AccountEntity.java           # Entidade de persistência
│   ├── repositories/                        # Repositórios Spring Data
│   │   └── AccountJpaRepository.java        # ← interface (extends JpaRepository)
│   ├── mappers/                             # Mapeadores Entity ↔ Core
│   │   └── AccountMapper.java               # Mapper de conversão
│   └── config/                              # Configurações
│       └── DatabaseConfig.java
└── 
```

### Legenda:
- **← interface**: Indica que a classe é uma interface
- **Implements {Interface}**: Indica que a classe implementa uma interface específica
- **extends {Class}**: Indica herança de classe ou interface

## 🔄 Fluxo da Arquitetura

```
[REST Controller] → [AccountPort] → [AccountService] → [AccountRepositoryPort] → [AccountJpaAdapter] → [Database]
```

1. **Controller** (inbound adapter) chama **AccountPort** (inbound port)
2. **AccountService** (implementa AccountPort) executa lógica de negócio
3. **AccountService** chama **AccountRepositoryPort** (outbound port)
4. **AccountJpaAdapter** (implementa AccountRepositoryPort) acessa o banco de dados

## 🎯 Principais Interfaces e Implementações

| Interface | Implementação | Descrição |
|-----------|---------------|-----------|
| `AccountPort` | `AccountService` | Define casos de uso de conta |
| `AccountRepositoryPort` | `AccountJpaAdapter` | Define operações de persistência |
| `AccountJpaRepository` | Spring Data JPA | Repositório de dados |

## 🚀 Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **PostgreSQL** (produção) / **H2 Database** (desenvolvimento)
- **Maven**
- **Jakarta Persistence API**
- **Docker** & **Docker Compose**

## 📋 Funcionalidades

- ✅ Criar conta
- ✅ Buscar conta por ID
- ✅ Listar todas as contas
- ✅ Atualizar conta
- ✅ Deletar conta
- ✅ Validações de negócio
- ✅ Tratamento de exceções

## 🛠️ Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+
- Docker & Docker Compose (opcional)

### Passos

1. **Clone o repositório**
   ```bash
   git clone https://github.com/eps364/my-simple-wallet-hexagonal.git
   cd my-simple-wallet-hexagonal
   ```

2. **Execute com Docker (Recomendado)**
   ```bash
   # Inicia PostgreSQL e a aplicação
   docker compose up --build
   ```

3. **Execute manualmente**
   ```bash
   # Inicia apenas o PostgreSQL
   docker compose up -d postgres
   
   # Compila e executa a aplicação
   ./mvnw spring-boot:run
   ```

4. **Execute com Hot Reload (Desenvolvimento)**
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.devtools.restart.enabled=true
   ```

5. **Acesse a aplicação**
   - URL: `http://localhost:8080`
   - H2 Console (dev): `http://localhost:8080/h2-console`

## 📡 API Endpoints

### Contas (Accounts)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/accounts` | Criar nova conta |
| `GET` | `/api/accounts/{id}` | Buscar conta por ID |
| `GET` | `/api/accounts` | Listar todas as contas |
| `PUT` | `/api/accounts/{id}` | Atualizar conta |
| `DELETE` | `/api/accounts/{id}` | Deletar conta |

### Exemplo de Payload

```json
{
  "description": "Minha Conta Corrente",
  "balance": 1000.00,
  "credit": 500.00,
  "dueDate": 15,
  "userId": "550e8400-e29b-41d4-a716-446655440000"
}
```

## 🧪 Executando Testes

```bash
# Testes unitários
./mvnw test

# Testes de integração
./mvnw verify

# Coverage report
./mvnw jacoco:report
```

## 📦 Build e Deploy

```bash
# Gerar JAR
./mvnw clean package

# Executar JAR
java -jar target/simple-wallet-*.jar

# Build Docker
docker build -t simple-wallet .
```

## 🐳 Docker

### Comandos disponíveis via tasks.json:

- **Start Docker Services**: `docker compose up -d postgres`
- **Stop Docker Services**: `docker compose down`
- **Full Docker Setup**: `docker compose up --build`

### Ambiente de desenvolvimento:
```bash
# PostgreSQL para desenvolvimento
docker compose up -d postgres

# Aplicação com hot reload
./mvnw spring-boot:run -Dspring-boot.devtools.restart.enabled=true
```


## 💡 Objetivo da Arquitetura

O objetivo desta arquitetura é isolar a lógica central da aplicação (as regras de negócio) de dependências externas, como banco de dados, interfaces de usuário e outros frameworks.

Isso torna o sistema mais fácil de testar, manter e adaptar a novas tecnologias no futuro, pois o núcleo da aplicação não depende de detalhes de implementação específicos.

## ✅ Vantagens da Arquitetura Hexagonal

- **Crescimento Sustentável**
    A separação clara entre a lógica de negócio (o *core* da aplicação) e as dependências externas (banco de dados, interfaces de usuário, APIs de terceiros) permite que o sistema evolua sem a necessidade de grandes refatorações. Novas funcionalidades podem ser adicionadas de forma isolada, reduzindo o risco de introduzir bugs em outras partes do sistema.

- **Troca de Framework Facilitada**
    Como a lógica de negócio não depende de nenhuma tecnologia específica, é possível trocar um framework ou um serviço externo com impacto mínimo. Por exemplo, migrar de um banco de dados MySQL para PostgreSQL exigiria apenas a criação de um novo "adaptador" de persistência, sem alterar as regras de negócio.

- **Baixo Acoplamento**
    Os componentes do sistema são independentes e se comunicam através de contratos bem definidos (as "portas"). A lógica de negócio não conhece os detalhes da implementação do banco de dados ou da interface web. Isso reduz o efeito cascata de mudanças: uma alteração na interface do usuário, por exemplo, não quebrará a lógica de negócio.

- **Testabilidade Aprimorada do Core da Aplicação**
    O *core* da aplicação, por ser totalmente isolado, pode ser testado com testes de unidade puros, sem a necessidade de mocks complexos para banco de dados ou serviços web. Isso torna os testes mais rápidos, confiáveis e fáceis de escrever, garantindo que a lógica de negócio funcione como esperado.

## ⚠️ Desvantagens da Arquitetura Hexagonal

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
- **[Arquitetura Hexagonal | #AluraMais](https://www.youtube.com/watch?v=X_EPcBNI5xU)** - Vinícius Dias, Instrutor na Alura
- **[Arquitetura Hexagonal na Prática | Arquitetura com Java e Spring Boot](https://youtu.be/UKSj5VJEzps?si=WV9dvtiY0pPyMq5M)** - Fernanda Kipper
- **[Arquitetura Hexagonal (Explicação de Ports & Adapters Simplificada) // Dicionário do Programador](https://youtu.be/7SaA3HCOc4c?si=vGryZRb_o7W_3vJS)** - Código Fonte TV
- **[Quando utilizar a Arquitetura Hexagonal - Palestra no TDC](https://youtu.be/opH8tomzw60?si=yh2tW0-rfq21pKSk)** - Nataniel Paiva
  - **[Git Hub com projeto - Springboot](https://github.com/natanielpaiva/arquitetura-hexagonal-spring)** - Nataniel Paiva
  - **[Git Hub com projeto - Quarkus](https://github.com/natanielpaiva/arquitetura-hexagonal)** - Nataniel Paiva
- **[Playlist - Arquitetura Hexagonal com Kotlin e Spring Boot](https://youtube.com/playlist?list=PLRHt7FXZbVCQmSscfVQVKT_gegPHurnHs&si=H-ITZcdZmqMgG0Js)** - DevEduardoAlbuquerque

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.

## 👨‍💻 Autor

**Emerson Pereira da Silva** - [GitHub](https://github.com/eps364)

---

⭐ Se este projeto te ajudou, considere dar uma estrela!