package com.gabriel.taskManager.DTOs;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TarefaResponseDTO {

    public Long tarefaId;
    public String descricao;
    public boolean status;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataCriacao;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataConclusao;

    public TarefaResponseDTO(Long tarefaId, String descricao, boolean status, LocalDateTime dataCriacao, LocalDateTime dataConclusao) {
        this.tarefaId = tarefaId;
        this.descricao = descricao;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
    }


    public Long getTarefaId() {
        return tarefaId;
    }

    public void setTarefaId(Long tarefaId) {
        this.tarefaId = tarefaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    @JsonProperty("status")
    public String getStatusDescricao() {
        return status ? "Concluído" : "Pendente";
    }

    @JsonProperty("dataConclusao")
    public String getDataConclusaoFormatada() {
        return dataConclusao != null
                ? dataConclusao.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                : "Ainda não concluída";
    }
}
