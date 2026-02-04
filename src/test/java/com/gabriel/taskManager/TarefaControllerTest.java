package com.gabriel.taskManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.taskManager.DTOs.TarefaRequestDTO;
import com.gabriel.taskManager.DTOs.TarefaResponseDTO;
import com.gabriel.taskManager.Service.TarefaService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TarefaService tarefaService;

    @Autowired
    private ObjectMapper objectMapper;

    private JwtAuthenticationToken buildToken(UUID userId) {
        Jwt jwt = new Jwt(
                "tokenValue",
                null,
                null,
                Map.of("alg", "none"),
                Map.of("sub", userId.toString()));
        return new JwtAuthenticationToken(jwt);
    }

    @Nested
    class getAll {
        @Test
        @DisplayName("List all success")
        void listAll_success() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            var tarefaResponse = new TarefaResponseDTO(1L, "Teste", false, null, null);

            Mockito.when(tarefaService.getAll(any(JwtAuthenticationToken.class)))
                    .thenReturn(List.of(tarefaResponse));

            mockMvc.perform(get("/tarefas").principal(token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].descricao").value("Teste"));
        }

        @Test
        @DisplayName("List all empty")
        void listAll_empty() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            Mockito.when(tarefaService.getAll(any(JwtAuthenticationToken.class)))
                    .thenReturn(List.of());

            mockMvc.perform(get("/tarefas").principal(token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isEmpty());
        }
    }

    @Nested
    class getById {
        @Test
        @DisplayName("Find by id success")
        void findById_success() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            var tarefaResponse = new TarefaResponseDTO(1L, "Teste", false, null, null);

            Mockito.when(tarefaService.getById(eq(1L), any(JwtAuthenticationToken.class)))
                    .thenReturn(tarefaResponse);

            mockMvc.perform(get("/tarefas/1").principal(token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.descricao").value("Teste"));
        }

        @Test
        @DisplayName("Find by id not found")
        void findById_notFound() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            Mockito.when(tarefaService.getById(eq(1L), any(JwtAuthenticationToken.class)))
                    .thenThrow(new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND));

            mockMvc.perform(get("/tarefas/1").principal(token))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class create {
        @Test
        @DisplayName("Create success")
        void create_success() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            var dto = new TarefaRequestDTO("Nova tarefa");
            var tarefaResponse = new TarefaResponseDTO(1L, "Nova tarefa", false, null, null);

            Mockito.when(tarefaService.create(eq(dto), any(JwtAuthenticationToken.class)))
                    .thenReturn(tarefaResponse);

            mockMvc.perform(post("/tarefas")
                    .principal(token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.descricao").value("Nova tarefa"));
        }

        @Test
        void criar_userNotFound() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            var dto = new TarefaRequestDTO("Nova tarefa");

            Mockito.when(tarefaService.create(eq(dto), any(JwtAuthenticationToken.class)))
                    .thenThrow(new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND));

            mockMvc.perform(post("/tarefas")
                    .principal(token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class update {
        @Test
        @DisplayName("Update success")
        void update_success() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            var dto = new TarefaRequestDTO("Atualizada");
            var tarefaResponse = new TarefaResponseDTO(1L, "Atualizada", false, null, null);

            Mockito.when(tarefaService.update(eq(1L), eq(dto), any(JwtAuthenticationToken.class)))
                    .thenReturn(tarefaResponse);

            mockMvc.perform(put("/tarefas/1")
                    .principal(token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.descricao").value("Atualizada"));
        }

        @Test
        @DisplayName("Update not found")
        void update_notFound() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            var dto = new TarefaRequestDTO("Atualizada");

            Mockito.when(tarefaService.update(eq(1L), eq(dto), any(JwtAuthenticationToken.class)))
                    .thenThrow(new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND));

            mockMvc.perform(put("/tarefas/1")
                    .principal(token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class concluir {
        @Test
        @DisplayName("Completed success")
        void conpleted_success() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            var tarefaResponse = new TarefaResponseDTO(1L, "Teste", true, null, null);

            Mockito.when(tarefaService.concluir(eq(1L), any(JwtAuthenticationToken.class)))
                    .thenReturn(tarefaResponse);

            mockMvc.perform(patch("/tarefas/1/concluir").principal(token))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("Concluído"));
        }

        @Test
        @DisplayName("Completed not found")
        void completed_notFound() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            Mockito.when(tarefaService.concluir(eq(1L), any(JwtAuthenticationToken.class)))
                    .thenThrow(new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.NOT_FOUND));

            mockMvc.perform(patch("/tarefas/1/concluir").principal(token))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class delete {
        @Test
        @DisplayName("Delete success")
        void delete_success() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            mockMvc.perform(delete("/tarefas/1").principal(token))
                    .andExpect(status().isNoContent());

            Mockito.verify(tarefaService).delete(eq(1L), any(JwtAuthenticationToken.class));
        }

        @Test
        @DisplayName("Delete not found")
        void delete_notFound() throws Exception {
            UUID userId = UUID.randomUUID();
            var token = buildToken(userId);

            Mockito.doThrow(new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND))
                    .when(tarefaService).delete(eq(1L), any(JwtAuthenticationToken.class));

            mockMvc.perform(delete("/tarefas/1").principal(token))
                    .andExpect(status().isNotFound());
        }
    }
}
