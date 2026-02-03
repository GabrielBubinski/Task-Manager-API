package com.gabriel.taskManager.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.taskManager.Model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    List<Tarefa> findByUser_UserId(UUID userId);
    Optional<Tarefa> findByTarefaIdAndUser_UserId(Long tarefaId, UUID userId);
}
