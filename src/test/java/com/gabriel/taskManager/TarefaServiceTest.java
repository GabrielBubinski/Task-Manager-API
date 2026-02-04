package com.gabriel.taskManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.server.ResponseStatusException;

import com.gabriel.taskManager.DTOs.TarefaRequestDTO;
import com.gabriel.taskManager.Model.Tarefa;
import com.gabriel.taskManager.Model.User;
import com.gabriel.taskManager.Repository.TarefaRepository;
import com.gabriel.taskManager.Repository.UserRepository;
import com.gabriel.taskManager.Service.TarefaService;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TarefaService tarefaService;

    // variaveis auxiliares
    private JwtAuthenticationToken token;
    private UUID userId;
    private User user;
    private Tarefa tarefa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();

        // Criando um Jwt fake
        Jwt jwt = new Jwt(
                "tokenValue", // valor do token
                null, // issuedAt
                null, // expiresAt
                Map.of("alg", "none"), // headers
                Map.of("sub", userId.toString()) // claims (sub = subject)
        );

        token = new JwtAuthenticationToken(jwt, Collections.emptyList());

        user = new User();
        user.setUserId(userId);

        tarefa = new Tarefa();
        tarefa.setTarefaId(1L);
        tarefa.setUser(user);
        tarefa.setDescricao("Teste");
        tarefa.setStatus(false);
        tarefa.setDataCriacao(LocalDateTime.now());

    }

    @Nested
    class getAll {
        @Test
        @DisplayName("Get all success")
        void getAll_success() {
            when(tarefaRepository.findByUser_UserId(userId)).thenReturn(List.of(tarefa));

            var result = tarefaService.getAll(token);

            assertEquals(1, result.size());
            assertEquals("Teste", result.get(0).getDescricao());
        }

        @Test
        @DisplayName("Get all empty list")
        void getAll_emptyList() {
            when(tarefaRepository.findByUser_UserId(userId)).thenReturn(Collections.emptyList());

            var result = tarefaService.getAll(token);

            assertTrue(result.isEmpty());
        }

    }

    @Nested
    class getById {
        @Test
        @DisplayName("Get by id success")
        void getById_success() {
            when(tarefaRepository.findByTarefaIdAndUser_UserId(1L, userId)).thenReturn(Optional.of(tarefa));

            var result = tarefaService.getById(1L, token);

            assertEquals("Teste", result.getDescricao());
        }

        @Test
        @DisplayName("Get by id not found")
        void getById_notFound() {
            when(tarefaRepository.findByTarefaIdAndUser_UserId(1L, userId)).thenReturn(Optional.empty());

            assertThrows(ResponseStatusException.class, () -> tarefaService.getById(1L, token));
        }

    }

    @Nested
    class update {
        @Test
        @DisplayName("Update success")
        void update_success() {
            TarefaRequestDTO dto = new TarefaRequestDTO("Nova descrição");
            when(tarefaRepository.findByTarefaIdAndUser_UserId(1L, userId)).thenReturn(Optional.of(tarefa));

            var result = tarefaService.update(1L, dto, token);

            assertEquals("Nova descrição", result.getDescricao());
            verify(tarefaRepository).save(tarefa);
        }

        @Test
        @DisplayName("Update not found")
        void update_notFound() {
            TarefaRequestDTO dto = new TarefaRequestDTO("Nova descrição");
            when(tarefaRepository.findByTarefaIdAndUser_UserId(1L, userId)).thenReturn(Optional.empty());

            assertThrows(ResponseStatusException.class, () -> tarefaService.update(1L, dto, token));
        }

    }

    @Nested
    class concluir {
        @Test
        void concluir_success() {
            when(tarefaRepository.findByTarefaIdAndUser_UserId(1L, userId)).thenReturn(Optional.of(tarefa));

            var result = tarefaService.concluir(1L, token);

            assertTrue(result.isStatus());
            assertNotNull(result.getDataConclusao());
            verify(tarefaRepository).save(tarefa);
        }

        @Test
        void concluir_notFound() {
            when(tarefaRepository.findByTarefaIdAndUser_UserId(1L, userId)).thenReturn(Optional.empty());

            assertThrows(ResponseStatusException.class, () -> tarefaService.concluir(1L, token));
        }

    }

    @Nested
    class create {
        @Test
        void create_success() {
            TarefaRequestDTO dto = new TarefaRequestDTO("Nova tarefa");
            when(userRepository.findById(userId)).thenReturn(Optional.of(user));

            var result = tarefaService.create(dto, token);

            assertEquals("Nova tarefa", result.getDescricao());
            assertFalse(result.isStatus());
            verify(tarefaRepository).save(any(Tarefa.class));
        }

        @Test
        void create_userNotFound() {
            TarefaRequestDTO dto = new TarefaRequestDTO("Nova tarefa");
            when(userRepository.findById(userId)).thenReturn(Optional.empty());

            assertThrows(ResponseStatusException.class, () -> tarefaService.create(dto, token));
        }

    }

    @Nested
    class delete {
        @Test
        @DisplayName("Delete success")
        void delete_success() {
            when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

            tarefaService.delete(1L, token);

            verify(tarefaRepository).delete(tarefa);
        }

        @Test
        @DisplayName("Delete not owner")
        void delete_notOwner() {
            User otherUser = new User();
            otherUser.setUserId(UUID.randomUUID());
            tarefa.setUser(otherUser);

            when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

            assertThrows(ResponseStatusException.class, () -> tarefaService.delete(1L, token));
        }

    }

}
