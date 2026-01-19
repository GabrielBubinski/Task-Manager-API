package com.gabriel.taskManager;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;

import org.junit.jupiter.api.Test;

import com.gabriel.taskManager.Model.TarefaRequestDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class TarefaRequestDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void deveValidarDescricaoObrigatoria() {
        TarefaRequestDTO dto = new TarefaRequestDTO();
        dto.setDescricao("");
        Set<ConstraintViolation<TarefaRequestDTO>> violacoes = validator.validate(dto);
        assertFalse(violacoes.isEmpty());
    }
}
