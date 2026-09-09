package com.desafio.estagio.tarefas.repository;

import com.desafio.estagio.tarefas.domain.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByResponsavelContainingIgnoreCase(String responsavel);

    List<Tarefa> findByDataEntrega(LocalDate dataEntrega);

    List<Tarefa> findByConcluidoFalse();

    List<Tarefa> findByConcluidoFalseAndResponsavelContainingIgnoreCase(String responsavel);

}
