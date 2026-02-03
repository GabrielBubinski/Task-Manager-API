package com.gabriel.taskManager.Model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_task")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long tarefaId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotBlank(message = "A descrição não pode estar vazia")
    public String descricao;
    public Boolean status;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    public LocalDateTime dataCriacao;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    public LocalDateTime dataConclusao;

    


    public Tarefa() {
    }

    public Tarefa(long tarefaId,User user, String descricao, Boolean status, LocalDateTime dataCriacao,
            LocalDateTime dataConclusao) {
        this.tarefaId = tarefaId;
        this.user = user;
        this.descricao = descricao;
        this.status = status = false;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
    }

    public Long getTarefaId() {
        return tarefaId;
    }

    public void setTarefaId(Long tarefaId) {
        this.tarefaId = tarefaId;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
