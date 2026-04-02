package com.gabriel.taskManager.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.gabriel.taskManager.DTOs.TarefaRequestDTO;
import com.gabriel.taskManager.DTOs.TarefaResponseDTO;
import com.gabriel.taskManager.Model.Tarefa;
import com.gabriel.taskManager.Repository.TarefaRepository;
import com.gabriel.taskManager.Repository.UserRepository;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UserRepository userRepository;

    public TarefaService(TarefaRepository tarefaRepository, UserRepository userRepository) {
        this.tarefaRepository = tarefaRepository;
        this.userRepository = userRepository;
    }

    private TarefaResponseDTO toResponseDTO(Tarefa tarefa) {
        return new TarefaResponseDTO(
                tarefa.getTarefaId(),
                tarefa.getDescricao(),
                tarefa.isStatus(),
                tarefa.getDataCriacao(),
                tarefa.getDataConclusao());
    }

    public List<TarefaResponseDTO> getAll(JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        return tarefaRepository.findByUser_UserId(userId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TarefaResponseDTO getById(Long tarefaId, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        return tarefaRepository.findByTarefaIdAndUser_UserId(tarefaId, userId)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public TarefaResponseDTO update(Long tarefaId, TarefaRequestDTO dto, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        Tarefa tarefa = tarefaRepository.findByTarefaIdAndUser_UserId(tarefaId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        tarefa.setDescricao(dto.descricao());
        tarefaRepository.save(tarefa);
        return toResponseDTO(tarefa);
    }

    public TarefaResponseDTO concluir(Long tarefaId, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        Tarefa tarefa = tarefaRepository.findByTarefaIdAndUser_UserId(tarefaId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        tarefa.setStatus(true);
        tarefa.setDataConclusao(LocalDateTime.now());
        tarefaRepository.save(tarefa);
        return toResponseDTO(tarefa);
    }

    public TarefaResponseDTO create(TarefaRequestDTO dto, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        Tarefa tarefa = new Tarefa();
        tarefa.setUser(user);
        tarefa.setDescricao(dto.descricao());
        tarefa.setStatus(false);
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefaRepository.save(tarefa);
        return toResponseDTO(tarefa);
    }

    public void delete(Long tarefaId, JwtAuthenticationToken token) {
        UUID userId = UUID.fromString(token.getName());
        var tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                     "Tarefa não encontrada"));

        if (!tarefa.getUser().getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                 "Você não tem permissão para excluir esta tarefa");
        }
        tarefaRepository.delete(tarefa);

    }
}
