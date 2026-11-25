# Task Manager API

![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-6DB33F?logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

API REST para gerenciamento de tarefas individuais. Ela permite cadastrar usuários, autenticar com JWT e criar, consultar, atualizar, concluir e excluir tarefas, mantendo cada usuário limitado às próprias tarefas.

## Funcionalidades principais

- Cadastro de usuários com senha criptografada usando BCrypt.
- Autenticação stateless com JWT válido por 1 hora.
- Controle de acesso por usuário para todas as operações de tarefas.
- CRUD de tarefas com descrição, status, data de criação e data de conclusão.
- Inicialização automática das funções `admin` e `user`.
- Usuário administrador inicial criado na primeira execução.
- Documentação interativa com Swagger/OpenAPI.
- Execução com MySQL por Docker Compose.

## Tecnologias utilizadas

- **Java 21**
- **Spring Boot 3.5.9**
- Spring Web
- Spring Data JPA / Hibernate
- Spring Security e OAuth2 Resource Server
- JWT
- Bean Validation
- MySQL
- SpringDoc OpenAPI / Swagger UI
- Maven
- Docker e Docker Compose
- Lombok

## Pré-requisitos

### Para executar com Docker

- Docker
- Docker Compose

### Para executar localmente

- JDK 21
- Maven 3.9+ ou o Maven Wrapper incluído no projeto
- Servidor MySQL

## Instalação e configuração

### 1. Clonar o projeto

```bash
git clone https://github.com/GabrielBubinski/Task-Manager-API.git
cd Task-Manager-API
```

### 2. Executar com Docker Compose

Na raiz do repositório, crie o arquivo local `.env` a partir do exemplo (se ainda não existir) e ajuste as credenciais:

```bash
cp .env.example .env
```

No PowerShell:

```powershell
Copy-Item .env.example .env
```

Como o Compose está em `docker/docker-compose.yml` e o `.env` fica na raiz, informe o arquivo explicitamente com `--env-file`:

```bash
docker compose --env-file .env -f docker/docker-compose.yml up --build
```

A configuração atual disponibiliza:

| Serviço | Endereço |
|---|---|
| API | `http://localhost:8080` |
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |
| MySQL | `localhost:3307` |

Para encerrar os containers:

```bash
docker compose --env-file .env -f docker/docker-compose.yml down
```

O arquivo `.env.example` lista as variáveis disponíveis para configurar as portas, credenciais e nome do banco. O Compose usa as mesmas credenciais e o mesmo nome de banco no MySQL e na configuração do backend.

```properties
API_PORT=8080
MYSQL_HOST_PORT=3307
MYSQL_ROOT_PASSWORD=sua_senha_root_aqui
MYSQL_USER=admin
MYSQL_PASSWORD=sua_senha_aqui
MYSQL_DATABASE=mydb
```

### 3. Executar localmente

Crie um banco MySQL e configure as variáveis de ambiente antes de iniciar a API.

No Linux ou macOS:

```bash
export SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/mydb"
export SPRING_DATASOURCE_USERNAME="admin"
export SPRING_DATASOURCE_PASSWORD="123"
./mvnw spring-boot:run
```

No Windows PowerShell:

```powershell
$env:SPRING_DATASOURCE_URL="jdbc:mysql://localhost:3306/mydb"
$env:SPRING_DATASOURCE_USERNAME="admin"
$env:SPRING_DATASOURCE_PASSWORD="123"
.\mvnw.cmd spring-boot:run
```

As chaves RSA usadas para assinar e validar o JWT já estão incluídas em `src/main/resources/app.key` e `src/main/resources/app.pub`.

## Como usar

### 1. Criar um usuário

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"username":"gabriel","password":"senha123"}'
```

O endpoint retorna `200 OK` sem corpo.

### 2. Autenticar

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"gabriel","password":"senha123"}'
```

Resposta:

```json
{
  "accessToken": "<jwt>",
  "expiresIn": 3600
}
```

Envie o valor de `accessToken` no cabeçalho `Authorization` no formato `Bearer <jwt>`.

### 3. Criar uma tarefa

```bash
curl -X POST http://localhost:8080/tarefas \
  -H "Authorization: Bearer <jwt>" \
  -H "Content-Type: application/json" \
  -d '{"descricao":"Estudar Spring Boot"}'
```

### 4. Consultar todas as tarefas

```bash
curl http://localhost:8080/tarefas \
  -H "Authorization: Bearer <jwt>"
```

## Endpoints

### Públicos

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/users` | Cadastra um usuário comum |
| `POST` | `/login` | Autentica e retorna um JWT |

### Protegidos por JWT

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/tarefas` | Lista as tarefas do usuário autenticado |
| `GET` | `/tarefas/{id}` | Busca uma tarefa do usuário autenticado |
| `POST` | `/tarefas` | Cria uma tarefa |
| `PUT` | `/tarefas/{id}` | Atualiza a descrição da tarefa |
| `PATCH` | `/tarefas/{id}/concluir` | Marca a tarefa como concluída |
| `DELETE` | `/tarefas/{id}` | Exclui a tarefa |
| `GET` | `/users` | Lista todos os usuários; exige perfil `admin` |

### Usuário administrador

Na primeira execução, a aplicação cria automaticamente:

```text
Usuário: admin
Senha: 123
```

O usuário administrador pode autenticar em `/login` e acessar `GET /users`.

## Exemplo de tarefa

```json
{
  "tarefaId": 1,
  "descricao": "Estudar Spring Boot",
  "status": "Pendente",
  "dataCriacao": "25/11/2026 19:10",
  "dataConclusao": "Ainda não concluída"
}
```

Ao concluir uma tarefa, `status` passa a representar o estado concluído e `dataConclusao` recebe a data e a hora da conclusão.

## Estrutura de pastas

```text
src/
├── main/
│   ├── java/com/gabriel/taskManager/
│   │   ├── Config/          # Segurança, JWT, Swagger e usuário admin
│   │   ├── Controller/      # Endpoints REST
│   │   ├── DTOs/            # Contratos de entrada e saída
│   │   ├── Model/           # Entidades JPA
│   │   ├── Repository/      # Repositórios Spring Data
│   │   ├── Service/         # Regras de negócio
│   │   └── TaskManagerApplication.java
│   └── resources/
│       ├── application.properties
│       ├── app.key         # Chave privada RSA
│       ├── app.pub         # Chave pública RSA
│       └── data.sql        # Inicialização das funções
└── test/
    └── java/com/gabriel/taskManager/
docker/
└── docker-compose.yml
Dockerfile
pom.xml
```

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
