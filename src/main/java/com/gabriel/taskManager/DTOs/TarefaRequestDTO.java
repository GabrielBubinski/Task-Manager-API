package com.gabriel.taskManager.DTOs;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public record TarefaRequestDTO(String descricao, JwtAuthenticationToken token) {
}
