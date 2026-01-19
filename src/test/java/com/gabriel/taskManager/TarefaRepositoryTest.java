package com.gabriel.taskManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.gabriel.taskManager.Model.Tarefa;
import com.gabriel.taskManager.Repository.TarefaRepository;


@DataJpaTest
public class TarefaRepositoryTest {

    @Autowired
    public TarefaRepository tarefaRepository;

    @Test
    void deveSalvarETrazerTarefa() {
        Tarefa tarefa = new Tarefa();
        tarefa.setDescricao("Testar JPA");
        tarefa.setConcluida(false);
        Tarefa salva = tarefaRepository.save(tarefa);
        java.util.Optional<Tarefa> encontrada = tarefaRepository.findById(salva.getId());
        assertTrue(encontrada.isPresent());
        assertEquals("Testar JPA", encontrada.get().getDescricao());
    }

}
