# ProblemDetail (RFC 7807) - Guia de Implementação

Este projeto implementa o padrão **ProblemDetail** conforme especificado na RFC 7807 para padronizar as respostas de erro da API REST.

## 🎯 O que é ProblemDetail?

O ProblemDetail é um formato padronizado para representar problemas em APIs REST, definido na RFC 7807. Ele fornece:

- **Estrutura consistente** para erros HTTP
- **Informações detalhadas** sobre o problema
- **Machine-readable** e **human-readable**
- **Extensibilidade** para informações específicas da aplicação

## 🏗️ Implementação

### 1. Configuração no `application.yaml`

```yaml
spring:
  mvc:
    problemdetails:
      enabled: true
```

### 2. GlobalExceptionHandler

O `GlobalExceptionHandler` captura exceções e as converte em `ProblemDetail`:

- **NotFoundExceptionCore** → HTTP 404
- **InvalidExceptionCore** → HTTP 400
- **MethodArgumentNotValidException** → HTTP 400 (Bean Validation)
- **MethodArgumentTypeMismatchException** → HTTP 400 (Type mismatch)
- **Exception** → HTTP 500 (Erro genérico)

### 3. Estrutura de Resposta

```json
{
  "type": "/problems/not-found",
  "title": "Resource Not Found",
  "status": 404,
  "detail": "Account not found with id: 999",
  "timestamp": "2025-09-07T15:30:00Z",
  "path": "/api/accounts/999"
}
```

## 🧪 Testando a Implementação

### 1. Endpoints de Demonstração

Para testar diferentes tipos de erro, use os endpoints de demonstração:

#### Error 404 - Not Found
```bash
curl -X GET http://localhost:8080/api/demo/errors/not-found
```

**Resposta:**
```json
{
  "type": "/problems/not-found",
  "title": "Resource Not Found",
  "status": 404,
  "detail": "This is a demo of NotFound exception using ProblemDetail",
  "timestamp": "2025-09-07T15:30:00Z",
  "path": "/api/demo/errors/not-found"
}
```

#### Error 400 - Invalid Data
```bash
curl -X GET http://localhost:8080/api/demo/errors/invalid-data
```

**Resposta:**
```json
{
  "type": "/problems/invalid-data",
  "title": "Invalid Data",
  "status": 400,
  "detail": "This is a demo of Invalid data exception using ProblemDetail",
  "timestamp": "2025-09-07T15:30:00Z",
  "path": "/api/demo/errors/invalid-data"
}
```

#### Error 400 - Type Mismatch
```bash
curl -X GET http://localhost:8080/api/demo/errors/type-mismatch/abc
```

**Resposta:**
```json
{
  "type": "/problems/type-mismatch",
  "title": "Type Mismatch",
  "status": 400,
  "detail": "Invalid value 'abc' for parameter 'id'. Expected type: Long",
  "timestamp": "2025-09-07T15:30:00Z",
  "path": "/api/demo/errors/type-mismatch/abc",
  "parameter": "id",
  "expectedType": "Long"
}
```

#### Error 500 - Internal Server Error
```bash
curl -X GET http://localhost:8080/api/demo/errors/server-error
```

**Resposta:**
```json
{
  "type": "/problems/internal-error",
  "title": "Internal Server Error",
  "status": 500,
  "detail": "An unexpected error occurred. Please try again later.",
  "timestamp": "2025-09-07T15:30:00Z",
  "path": "/api/demo/errors/server-error"
}
```

### 2. Testando Validação Bean Validation

#### Criar Account com dados inválidos
```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "description": "",
    "balance": -100,
    "credit": -50,
    "dueDate": 35,
    "userId": null
  }'
```

**Resposta:**
```json
{
  "type": "/problems/validation-error",
  "title": "Validation Error",
  "status": 400,
  "detail": "Validation failed for one or more fields",
  "timestamp": "2025-09-07T15:30:00Z",
  "path": "/api/accounts",
  "fieldErrors": [
    {
      "field": "description",
      "message": "Account description is required",
      "rejectedValue": ""
    },
    {
      "field": "balance",
      "message": "Balance must be positive or zero",
      "rejectedValue": -100
    },
    {
      "field": "credit",
      "message": "Credit must be positive or zero",
      "rejectedValue": -50
    },
    {
      "field": "dueDate",
      "message": "Due date must be between 1 and 31",
      "rejectedValue": 35
    },
    {
      "field": "userId",
      "message": "User ID is required",
      "rejectedValue": null
    }
  ]
}
```

### 3. Testando Endpoints Reais

#### Account não encontrada
```bash
curl -X GET http://localhost:8080/api/accounts/999
```

#### Criar Account válida
```bash
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "description": "Minha Conta Corrente",
    "balance": 1000.00,
    "credit": 500.00,
    "dueDate": 15,
    "userId": "550e8400-e29b-41d4-a716-446655440000"
  }'
```

## 🔧 Personalizações

### 1. Adicionando Campos Customizados

Você pode adicionar propriedades específicas ao ProblemDetail:

```java
problemDetail.setProperty("errorCode", "ACC_001");
problemDetail.setProperty("timestamp", Instant.now());
problemDetail.setProperty("supportContact", "support@example.com");
```

### 2. Diferentes Types de Erro

Cada tipo de erro pode ter um URI específico:

- `/problems/not-found` - Recurso não encontrado
- `/problems/invalid-data` - Dados inválidos
- `/problems/validation-error` - Erro de validação
- `/problems/type-mismatch` - Tipo incorreto
- `/problems/internal-error` - Erro interno

### 3. Internacionalização

As mensagens podem ser internacionalizadas usando o `MessageSource`:

```properties
# messages.properties
account.notfound=Account not found with id: {0}
error.internal=An unexpected error occurred. Please try again later.
```

## 🎯 Benefícios da Implementação

1. **Consistência**: Todas as respostas de erro seguem o mesmo padrão
2. **Detalhamento**: Informações específicas sobre cada tipo de erro
3. **Machine-readable**: Clientes podem processar erros programaticamente
4. **Padrão RFC**: Seguindo padrões internacionais
5. **Extensibilidade**: Fácil adicionar novos tipos de erro
6. **Debugging**: Informações úteis para desenvolvimento e produção

## 🚀 Próximos Passos

1. **Remover ErrorDemoController** em produção
2. **Adicionar logging** estruturado de erros
3. **Implementar rate limiting** com ProblemDetail
4. **Adicionar métricas** de erros
5. **Configurar alertas** para erros 500

## 📚 Referencias

- [RFC 7807 - Problem Details for HTTP APIs](https://tools.ietf.org/html/rfc7807)
- [Spring Boot ProblemDetail Support](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-ann-rest-exceptions)
- [Spring Boot Error Handling](https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc)
