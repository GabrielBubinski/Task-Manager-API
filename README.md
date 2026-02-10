<h1 align="center" style="font-weight: bold;">Task Manager API 💻</h1>

<p align="center">
 <a href="#technologies">Technologies</a> • 
 <a href="#features">Features</a> • 
 <a href="#started">Getting Started</a> • 
 <a href="#auth">Authentication</a> •
 <a href="#routes">API Endpoints</a> •
</p>

<p align="center">
    <b>Task management API developed in Spring Boot with JWT authentication.</b>
</p>

---

<h2 id="technologies">💻 Technologies</h2>

- Java 21 
- Spring Boot 
- Spring Data JPA 
- Spring Security + JWT 
- Swagger/OpenAPI (with JWT Authorize button) 
- Docker & Docker Compose (to run MySQL)

---

<h2 id="features">🚀 Features</h2>

- User registration and login with JWT authentication
- Secure endpoints with Spring Security
- Create tasks (always start with `concluida = false`)
- List all tasks
- Update description
- Complete task (marks as completed and records `dataConclusao`)
- Delete task
- Interactive API documentation with Swagger UI

---

<h3>Prerequisites</h3>

- Java 21+
- Maven
- Docker

---

<h2 id="started">🚀 Getting started</h2>

<h3>Cloning</h3>

```bash
git clone your-project-url-in-github
```

<h3>▶ How to start your project </h3>

```bash
cd project-name

docker-compose up 

mvn spring-boot:run
```

Access Swagger at:

👉 http://localhost:8080/swagger-ui/index.html

<h2 id="auth">🔐 Authentication</h2>

This API uses JWT (JSON Web Token) for authentication.

**Register a new user:**
- EndPoint:
`POST /users`
- Register Request Exemple:
```bash
{
  "username": "gabriel",
  "password": "123456"
}
```

**Login to get a JWT token:**
- EndPoint:
`POST /login`

- Login Request Example:

```bash
{
  "username": "gabriel",
  "password": "123456"
}
```
- Login Response Example:

```bash
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
}
```

**After login, copy the token and click Authorize in Swagger UI.**

Insert the token in the format:
```bash
Bearer <your_token>
```
Now you can access protected endpoints.

<h2 id="routes">📍 API Endpoints</h2>

*<h3 id="public-points">Public Endpoints</h3>*

| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /users</kbd>     | register a new user 
| <kbd>POST /login</kbd>     | login and receive JWT token

*<h3 id="routes">Protected Endpoints (require JWT)</h3>*
| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /tarefas</kbd>     | creates a new task [request details](#post-task-detail)
| <kbd>PATCH /tarefas/{id}/concluir</kbd>     |  marks as completed [response details](#patch-task-detail)
| <kbd>GET /tarefas</kbd>     | lists all tasks 
| <kbd>GET /tarefas/{id}</kbd>     |  fetches task by ID 
| <kbd>PUT /tarefas/{id}</kbd>     | updates description 
| <kbd>DELETE /tarefas/{id}</kbd>     | deletes task 




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
