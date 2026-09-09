package com.desafio.estagio.tarefas.dto;

import com.desafio.estagio.tarefas.domain.entity.Tarefa;

import java.time.LocalDate;

public record TarefaResponse(
        String titulo,
        String descricao,
        String responsavel,
        LocalDate dataEntrega,
        Boolean concluido
) {
    public static TarefaResponse toDto(Tarefa tarefa) {
        return new TarefaResponse(
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getResponsavel(),
                tarefa.getDataEntrega(),
                tarefa.getConcluido()
        );
    }

}
