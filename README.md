<h1 align="center" style="font-weight: bold;">Task Manager API 💻</h1>

<p align="center">
<a href="#technologies">Tecnologias</a> •
<a href="#features">Funcionalidades</a> •
<a href="#started">Como começar</a> •
<a href="#auth">Autenticação</a> •
<a href="#routes">Endpoints da API</a> •
</p>

<p align="center">
<b>API de gerenciamento de tarefas desenvolvida em Spring Boot com autenticação JWT.</b>
</p>

---

<h2 id="technologies">💻 Tecnologias</h2>

- Java 21 
- Spring Boot 
- Spring Data JPA 
- Spring Security + JWT 
- Swagger/OpenAPI (com botão de autorização JWT)
- Docker & Docker Compose (para rodar aplicação e banco MySQL)

---

<h2 id="features">🚀 Funcionalidades</h2>

- Cadastro e login de usuários com autenticação JWT
- Endpoints seguros com Spring Security
- Criar tarefas
- Listar todas as tarefas
- Atualizar descrição
- Concluir tarefa
- Excluir tarefa
- Documentação interativa da API com Swagger UI

---

<h3>Pré-requisitos</h3>

- Docker
- Docker Compose

---

<h2 id="started">🚀 Como começar</h2>

<h3>Clonando</h3>

```bash
git clone url-do-projeto-no-github

cd nome-do-projeto
```

<h3>▶ Como iniciar o projeto </h3>

```bash
docker-compose up 
```

Acesse o Swagger em:

👉 http://localhost:8080/swagger-ui/index.html

<h2 id="auth">🔐 Autenticação</h2>

Esta API utiliza JWT (JSON Web Token) para autenticação.

**Registrar novo usuário:**
- EndPoint:
`POST /users`
- Exemplo de requisição:
```bash
{
  "username": "gabriel",
  "password": "123456"
}
```

**Login para obter o token JWT:**
- EndPoint:
`POST /login`

- Exemplo de requisição:

```bash
{
  "username": "gabriel",
  "password": "123456"
}
```
- Exemplo de resposta:

```bash
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
}
```

**Após o login, copie o token e clique em Authorize no Swagger UI ou**

Insira o token no formato:
```bash
Bearer <your_token>
```
Agora você pode acessar os endpoints protegidos.

<h2 id="routes">📍 Endpoints da API</h2>

*<h3 id="public-points">Endpoints Públicos</h3>*

| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /users</kbd>     | cadastrar novo usuário
| <kbd>POST /login</kbd>     | login e receber token JWT

*<h3 id="routes">Endpoints Protegidos (requer JWT)</h3>*
| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /tarefas</kbd>     | Criar novas tarefas [detalhes](#post-task-detail)
| <kbd>PATCH /tarefas/{id}/concluir</kbd>     |  Marcar como concluída [detalhes](#patch-task-detail)
| <kbd>GET /tarefas</kbd>     | Listar todas as tarefas
| <kbd>GET /tarefas/{id}</kbd>     |  Buscar tarefa por ID 
| <kbd>PUT /tarefas/{id}</kbd>     | Atualizar descrição 
| <kbd>DELETE /tarefas/{id}</kbd>     | Excluir tarefa 




<h3 id="post-task-detail">POST /tarefas</h3>

**REQUEST**
```json
{
  "descricao": "Estudar Spring Boot"
}
```

**RESPONSE**
```json
{
  "id": 1,
  "descricao": "Estudar Spring Boot",
  "status": "Pendente",
  "dataCriacao": "2026-01-19T14:45:00",
  "dataConclusao": "Ainda não concluída"
}
```

<h3 id="patch-task-detail">PATCH /tarefas/1/concluir</h3>

**RESPONSE**
```json
{
  "id": 1,
  "descricao": "Estudar Spring Boot",
  "status": "Concluído",
  "dataCriacao": "2026-01-19T14:45:00",
  "dataConclusao": "2026-01-19T15:00:00"
}
```
