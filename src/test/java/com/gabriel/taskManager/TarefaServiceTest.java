package com.gabriel.taskManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gabriel.taskManager.Model.Tarefa;
import com.gabriel.taskManager.Model.TarefaRequestDTO;
import com.gabriel.taskManager.Model.TarefaResponseDTO;
import com.gabriel.taskManager.Repository.TarefaRepository;
import com.gabriel.taskManager.Service.TarefaService;

@SpringBootTest
public class TarefaServiceTest {

    @Mock
    public TarefaRepository tarefaRepository;

    @Autowired
    public TarefaService tarefaService;

    @Test
    void deveCriarTarefaComDescricao() {
        Tarefa tarefa = new Tarefa();
        tarefa.setTarefa_id(1L);
        tarefa.setDescricao("Estudar Spring Boot");
        tarefa.setStatus(false);
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);
        TarefaRequestDTO request = new TarefaRequestDTO();
        request.setDescricao("Estudar Spring Boot");
        TarefaResponseDTO response = tarefaService.criar(request);
        assertEquals("Estudar Spring Boot", response.getDescricao());
        assertFalse(response.isStatus());
    }
}
