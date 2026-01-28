package com.gabriel.taskManager.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.gabriel.taskManager.Model.Tarefa;
import com.gabriel.taskManager.Model.TarefaRequestDTO;
import com.gabriel.taskManager.Model.TarefaResponseDTO;
import com.gabriel.taskManager.Repository.TarefaRepository;

@Service
public class TarefaService {

    public TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    // Conversão Entidade -> ResponseDTO
    private TarefaResponseDTO convertToResponseDTO(Tarefa tarefa) {
        TarefaResponseDTO dto = new TarefaResponseDTO();
        dto.setId(tarefa.getTarefa_id());
        dto.setDescricao(tarefa.getDescricao());
        dto.setStatus(tarefa.isStatus());
        dto.setDataCriacao(tarefa.getDataCriacao());
        dto.setDataConclusao(tarefa.getDataConclusao());
        return dto;
    }

    // GET ALL
    public List<TarefaResponseDTO> getAllTarefas() {
        return tarefaRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // GET BY ID
    public TarefaResponseDTO getTarefaById(Long id) {
        return tarefaRepository.findById(id)
                .map(this::convertToResponseDTO)
                .orElse(null);
    }

    // CREATE
    public TarefaResponseDTO criar(TarefaRequestDTO dto) {
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setStatus(false);
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefaRepository.save(tarefa);
        return convertToResponseDTO(tarefa);
    }

    // UPDATE
    public TarefaResponseDTO updateTarefa(Long id, TarefaRequestDTO dto) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setDescricao(dto.getDescricao());
            tarefaRepository.save(tarefa);
            return convertToResponseDTO(tarefa);
        }).orElse(null);
    }

    public TarefaResponseDTO updateTarefaConcluida(Long id) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setStatus(true);
            tarefa.setDataConclusao(LocalDateTime.now());
            tarefaRepository.save(tarefa);
            return convertToResponseDTO(tarefa);
        }).orElse(null);
    }

    // DELETE
    public boolean deleteTarefa(Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TarefaResponseDTO concluirTarefa(Long id) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setStatus(true);
            tarefa.setDataConclusao(LocalDateTime.now());
            tarefaRepository.save(tarefa);
            return convertToResponseDTO(tarefa);
        }).orElse(null);
    }

}
