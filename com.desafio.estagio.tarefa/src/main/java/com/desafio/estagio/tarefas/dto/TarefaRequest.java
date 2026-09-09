package com.desafio.estagio.tarefas.dto;

import java.time.LocalDate;

public record TarefaRequest(
        String titulo,
        String descricao,
        String responsavel,
        LocalDate dataEntrega
) {
}
