<h1 align="center" style="font-weight: bold;">Task Manager API 💻</h1>

<p align="center">
 <a href="#technologies">Technologies</a> • 
 <a href="#Features">Features</a> • 
 <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a> •
</p>

<p align="center">
    <b>Task management API developed in **Spring Boot**.</b>
</p>

<h2 id="technologies">💻 Technologies</h2>

- Java 21 
- Spring Boot 
- Spring Data JPA 
- Swagger/OpenAPI 
- H2 Database (for testing)

<h2 id="Features">🚀 Features</h2>

- Create tasks (always start with `Concluida = false`)
- List all tasks
- Update description
- Complete task (marks as completed and records `dataConclusao`)
- Delete task

<h3>Prerequisites</h3>

- Java21+

<h2 id="started">🚀 Getting started</h2>

how to run your project locally 

<h3>Cloning</h3>

```bash
git clone your-project-url-in-github
```

<h3>▶ How to start your project </h3>

```bash
cd project-name
mvn spring-boot:run
```

Access Swagger at:

http://localhost:8080/swagger-ui/index.html

<h2 id="routes">📍 API Endpoints</h2>

Here are listed the main endpoints.
​
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
  "concluida": false,
  "dataCriacao": "2026-01-19T14:45:00",
  "dataConclusao": null
}
```

<h3 id="patch-task-detail">PATCH /tarefas/1/concluir</h3>

**RESPONSE**
```json
{
  "id": 1,
  "descricao": "Estudar Spring Boot",
  "concluida": true,
  "dataCriacao": "2026-01-19T14:45:00",
  "dataConclusao": "2026-01-19T15:00:00"
}
```
