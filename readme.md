# FinConnect – Enterprise Internet Banking Platform

FinConnect is a simplified Internet Banking platform built with a high-performance microservices architecture.

The project was designed to simulate the ecosystem used by large financial institutions.

The primary goal of this repository is not only functionality, but also the demonstration of concepts such as resilience, scalability, security, and ACID data integrity.

---

# 🏗️ System Architecture

## Technical Decisions & Enterprise Justifications

| Technology | Role | Enterprise Justification |
| :--- | :--- | :--- |
| **Java 17 / Spring Boot 3** | Core Backend | Industry standard in banking systems due to robustness, strong typing, and support for complex transactions. |
| **Angular 17+** | Frontend SPA | Opinionated and modular structure preferred by large enterprises for complex systems. |
| **PostgreSQL** | Relational Database | Guarantees ACID properties, essential for balance and transaction consistency. |
| **MongoDB** | NoSQL Database | Flexible and fast storage for notification logs and audit trails. |
| **Kafka** | Messaging Broker | Asynchronous communication ensures external failures (e.g., email delivery) do not affect banking transactions. |
| **Docker Compose** | Infrastructure | Standardized environment setup and simplified CI/CD deployment pipelines. |

---

# 📦 Microservices Ecosystem

The platform is composed of the following services, communicating through REST (synchronous) and Messaging (asynchronous):

- **API Gateway (Spring Cloud Gateway):** Single entry point responsible for centralized authentication and request routing.

- **Discovery Service (Netflix Eureka):** Enables dynamic service discovery.

- **Auth Service:** Handles identity and authentication using Spring Security, JWT, and BCrypt password encryption.

- **Account Service:** Manages customer profiles and account balances using **Pessimistic Locking** to avoid race conditions during concurrent transactions.

- **Transaction Service:** The financial core responsible for orchestrating transfers (PIX/TED).

- **Notification Service:** Asynchronous worker that consumes Kafka topics and persists notification history in MongoDB.

---

# 🛡️ Engineering Pillars

## 1. Transaction Integrity (ACID)

In banking systems, a transfer cannot fail halfway.

The platform uses Spring's `@Transactional` annotation to guarantee that debiting the source account and crediting the destination account happen within the same transaction.

If any step fails, the system automatically performs a **rollback**.

---

## 2. Security & Authentication

- **JWT (JSON Web Token):** Token-based authentication with a 60-minute expiration time.

- **Encryption:** Passwords are never stored in plain text. All passwords are encrypted using **BCrypt with salt**.

- **Route Protection:** The Angular frontend uses **Auth Guards** and HTTP interceptors to automatically inject tokens into authenticated requests.

---

## 3. Resilience & Observability

- **Correlation ID:** Every request receives a unique identifier at the Gateway level, enabling end-to-end tracing across all microservices.

---

# 🚀 Running the Project

The entire ecosystem can be started with a single command, simplifying technical evaluation and local development.

```bash
# Clone the repository
git clone https://github.com/matheus-aroxa/finconnect.git

# Start infrastructure and services
docker-compose up -d
```

---

# 🧪 Code Quality & Engineering Standards

To meet the requirements of mission-critical financial systems, FinConnect follows strict engineering and testing standards.

## 1. Testing Strategy

The project implements a testing pyramid focused on ensuring that business logic — especially money transfers — remains reliable and fault tolerant.

### Unit Tests
- Minimum 80% coverage in the `Service` layer
- Built with **JUnit 5** and **Mockito**
- Full component isolation

### Integration Tests
- Validates complete flows between layers:
  `Controller → Service → Repository`
- Uses `@SpringBootTest`

### Testcontainers
- Real **PostgreSQL** containers are used during integration tests
- Guarantees behavior consistency between test and production environments

### RestAssured
- Used to validate API contracts and JSON responses for authentication and transaction endpoints

---

## 2. Standardization & Best Practices

- **SOLID & Clean Code:** Single Responsibility Principle and dependency injection applied throughout the ecosystem.

- **DTO Pattern (Data Transfer Object):** Complete decoupling between persistence entities and presentation layers using **MapStruct** for high-performance mapping.

- **Global Exception Handler:** Centralized exception handling with `@ControllerAdvice`, ensuring standardized error responses and semantic HTTP status codes (e.g., `409 Conflict` for duplicated users).

- **Bean Validation:** Annotations such as `@CPF` and `@Email` validate data before it reaches the business layer.

---

## 3. Project Structure (Maven/Gradle Layout)

The project follows the standard structure separating production and test code, improving maintainability and CI/CD organization.

```plaintext
src/
├── main/java/          # Production code (Hexagonal Architecture)
└── test/java/          # Test suites
    ├── unit/           # Fast unit tests without Spring Context
    ├── integration/    # Integration tests using @SpringBootTest
    └── containers/     # Testcontainers infrastructure configuration
```

---

# 🔌 Portas dos Serviços & Redirecionamento

Para facilitar o desenvolvimento e a integração, a infraestrutura e os microsserviços expõem as seguintes portas no ambiente local:

| Serviço / Interface | Porta Local | Descrição |
| :--- | :--- | :--- |
| **API Gateway** | `8080` | Ponto de entrada único para a API pública do ecossistema. |
| **Netflix Eureka** | `8761` | Painel de controle para visualização dos serviços registrados. |
| **Kafka UI** | `8000` | Interface web para monitoramento de tópicos, partições e mensagens. |
| **Swagger UI** | `8080/swagger-ui.html` | Documentação centralizada e interativa das APIs do sistema. |
| **Frontend** | `4200` | Aplicação frontend |

> 💡 **Nota de Arquitetura:** Graças à centralização do **Spring Cloud Gateway**, você não precisa acessar as portas individuais de cada microsserviço (como Auth, Account ou Transaction) para testar os endpoints ou ver a documentação. Tudo é roteado de forma transparente através da porta `8080`.

# 📌 Key Engineering Highlights

- Microservices Architecture
- ACID Transactions
- JWT Authentication
- Kafka Event-Driven Communication
- PostgreSQL + MongoDB Polyglot Persistence
- Dockerized Infrastructure
- Correlation ID Distributed Tracing
- Testcontainers Integration Testing
- Clean Code & SOLID Principles

---

# 📄 License

This project is intended for educational and portfolio purposes.

Feel free to fork, study, and adapt it for learning purposes.
