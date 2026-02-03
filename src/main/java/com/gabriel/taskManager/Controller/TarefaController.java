package com.gabriel.taskManager.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.taskManager.DTOs.TarefaRequestDTO;
import com.gabriel.taskManager.DTOs.TarefaResponseDTO;
import com.gabriel.taskManager.Service.TarefaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> listarTodas(JwtAuthenticationToken token) {
        return ResponseEntity.ok(tarefaService.getAll(token));
    }

    @GetMapping("/{tarefaId}")
    public ResponseEntity<TarefaResponseDTO> buscarPorId(@PathVariable Long tarefaId, JwtAuthenticationToken token) {
        return ResponseEntity.ok(tarefaService.getById(tarefaId, token));
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> criar(@Valid @RequestBody TarefaRequestDTO dto, JwtAuthenticationToken token) {
        return ResponseEntity.ok(tarefaService.create(dto, token));
    }

    
    @PutMapping("/{tarefaId}")
    public ResponseEntity<TarefaResponseDTO> atualizar(@PathVariable Long tarefaId,
            @Valid @RequestBody TarefaRequestDTO dto, JwtAuthenticationToken token) {
        return ResponseEntity.ok(tarefaService.update(tarefaId, dto, token));
    }

    @PatchMapping("/{tarefaId}/concluir")
    public ResponseEntity<TarefaResponseDTO> concluir(@PathVariable Long tarefaId, JwtAuthenticationToken token) {
        return ResponseEntity.ok(tarefaService.concluir(tarefaId, token));
    }

    @DeleteMapping("/{tarefaId}")
    public ResponseEntity<Void> deletar(@PathVariable Long tarefaId, JwtAuthenticationToken token) {
        tarefaService.delete(tarefaId, token);
        return ResponseEntity.noContent().build();
    }

}
