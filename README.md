# 🔐 REST API — Spring Boot + JWT

API RESTful com autenticação completa usando Spring Security e JWT, desenvolvida do zero como projeto de aprendizado.

---

## 🚀 Tecnologias

- **Java 25** + **Spring Boot 4.0.6**
- **Spring Security** — autenticação e autorização
- **JWT** (Auth0 java-jwt) — geração e validação de tokens
- **Spring Data JPA** + **Hibernate** — persistência de dados
- **H2 Database** — banco em memória (desenvolvimento)
- **Lombok** — redução de boilerplate
- **Bean Validation** — validação de dados de entrada

---

## 📁 Estrutura do Projeto

```
src/main/java/com/exemplo/rest/
├── controller/
│   ├── AuthenticationController.java   # Register e Login
│   └── UserController.java             # CRUD de usuários
├── dto/
│   ├── AuthenticationDTO.java          # Entrada do login
│   ├── RegisterDTO.java                # Entrada do cadastro
│   ├── LoginResponseDTO.java           # Resposta com token
│   ├── UserDTO.java                    # Entrada de usuário
│   └── UserResponse.java              # Saída de usuário
├── exception/
│   ├── GlobalExceptionHandler.java     # Tratamento global de erros
│   └── UserNotFoundException.java      # Exceção customizada
├── infra/security/
│   ├── SecurityConfiguration.java      # Configuração do Spring Security
│   ├── SecurityFilter.java             # Filtro JWT por requisição
│   └── TokenService.java              # Geração e validação de tokens
├── model/
│   ├── UserModel.java                  # Entidade de usuário
│   └── UserRole.java                  # Enum de roles (ADMIN, USER)
├── repository/
│   └── UserRepository.java            # Acesso ao banco
└── service/
    ├── AuthorizationService.java       # UserDetailsService
    └── UserService.java               # Regras de negócio de usuário
```

---

## 🔑 Endpoints

### Auth
| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| POST | `/auth/register` | Cadastro de usuário | ❌ |
| POST | `/auth/login` | Login e geração de token | ❌ |

### Usuários
| Método | Rota | Descrição | Auth |
|--------|------|-----------|------|
| GET | `/user/list` | Lista todos os usuários | ✅ |
| GET | `/user/list/{id}` | Busca usuário por ID | ✅ |
| POST | `/user` | Cria usuário | ✅ |

---

## 📬 Exemplos de Requisição

### Register
```http
POST /auth/register
Content-Type: application/json

{
    "name": "Thiago",
    "email": "thiago@email.com",
    "password": "123456",
    "role": "ADMIN"
}
```

### Login
```http
POST /auth/login
Content-Type: application/json

{
    "email": "thiago@email.com",
    "password": "123456"
}
```

**Resposta:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Requisição autenticada
```http
GET /user/list
Authorization: Bearer {token}
```

---

## ⚙️ Como rodar

**Pré-requisitos:** Java 17+, Maven

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/seu-repositorio.git

# Entre na pasta
cd rest

# Configure o secret no application.properties
# api.security.token.secret=sua-chave-secreta

# Rode o projeto
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

O console do H2 estará em `http://localhost:8080/h2-console`

---

## 🛡️ Segurança

- Senhas armazenadas com **BCrypt**
- Autenticação stateless via **JWT** com expiração de 2 horas
- Roles: `ADMIN` e `USER`
- Endpoints protegidos exigem token no header `Authorization: Bearer {token}`
- Tratamento global de erros com **RFC 9457 ProblemDetail**

---

## 📌 Próximos passos

- [ ] Refresh Token
- [ ] Testes unitários com JUnit e Mockito
- [ ] Documentação com Swagger (SpringDoc OpenAPI)
- [ ] Deploy no Railway ou Render

---

## 👨‍💻 Autor

Feito por **Thiago** — projeto de aprendizado de Spring Boot e Spring Security.
