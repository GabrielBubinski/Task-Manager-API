package com.gabriel.taskManager.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "task_table")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long tarefa_id;
    public String descricao;
    public Boolean status;
    public LocalDateTime dataCriacao;
    public LocalDateTime dataConclusao;

    public Tarefa() {
    }

    public Tarefa(long tarefa_id, String descricao, Boolean status, LocalDateTime dataCriacao,
            LocalDateTime dataConclusao) {
        this.tarefa_id = tarefa_id;
        this.descricao = descricao;
        this.status = status = false;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
    }

    public Long getTarefa_id() {
        return tarefa_id;
    }

    public void setTarefa_id(Long tarefa_id) {
        this.tarefa_id = tarefa_id;
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

}
