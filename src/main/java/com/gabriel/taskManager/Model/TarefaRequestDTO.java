package com.gabriel.taskManager.Model;

import jakarta.validation.constraints.NotBlank;

public class TarefaRequestDTO {

    @NotBlank(message = "A descrição não pode estar vazia")
    private String descricao;

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
