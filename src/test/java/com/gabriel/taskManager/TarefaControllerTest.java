package com.gabriel.taskManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.gabriel.taskManager.Controller.TarefaController;
import com.gabriel.taskManager.Model.TarefaRequestDTO;
import com.gabriel.taskManager.Model.TarefaResponseDTO;
import com.gabriel.taskManager.Service.TarefaService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(TarefaController.class)
public class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public TarefaService tarefaService;

    @Test
    void deveCriarTarefaViaPost() throws Exception {
        TarefaResponseDTO response = new TarefaResponseDTO();
        response.setId(1L);
        response.setDescricao("Nova tarefa");
        response.setConcluida(false);
        when(tarefaService.criar(any(TarefaRequestDTO.class))).thenReturn(response);
        mockMvc.perform(post("/tarefas")
                .contentType(APPLICATION_JSON)
                .content("{\"descricao\":\"Nova tarefa\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Nova tarefa"))
                .andExpect(jsonPath("$.concluida").value(false));
    }

}
