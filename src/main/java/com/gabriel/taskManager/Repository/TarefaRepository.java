package com.gabriel.taskManager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.taskManager.Model.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {}
