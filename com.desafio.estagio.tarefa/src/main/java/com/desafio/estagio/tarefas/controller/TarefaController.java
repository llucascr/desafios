package com.desafio.estagio.tarefas.controller;

import com.desafio.estagio.tarefas.domain.entity.Tarefa;
import com.desafio.estagio.tarefas.dto.TarefaRequest;
import com.desafio.estagio.tarefas.dto.TarefaResponse;
import com.desafio.estagio.tarefas.repository.TarefaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {

    private final TarefaRepository tarefaRepository;

    public TarefaController(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarefaResponse cadastrar(@RequestBody TarefaRequest request) {
        Tarefa tarefaSalva = tarefaRepository.save(new Tarefa(
                request.titulo(),
                request.descricao(),
                request.responsavel(),
                request.dataEntrega()
        ));
        return TarefaResponse.toDto(tarefaSalva);
    }

    @GetMapping
    public List<TarefaResponse> buscar(
            @RequestParam(required = false) String responsavel,
            @RequestParam Map<String, String> parametros
    ) {

        if (!parametros.isEmpty() && !parametros.containsKey("responsavel")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Parâmetro inválido. O parâmetro permitido é: responsavel"
            );
        }

        if (responsavel == null || responsavel.isEmpty()) {
            return tarefaRepository.findAll()
                    .stream()
                    .map(TarefaResponse::toDto)
                    .toList();
        }

        return tarefaRepository.findByResponsavelContainingIgnoreCase(responsavel)
                .stream()
                .map(TarefaResponse::toDto)
                .toList();
    }

    @GetMapping("/data")
    public List<TarefaResponse> buscarPorDataEntrega(@RequestParam LocalDate dataEntrega) {
        return tarefaRepository.findByDataEntrega(dataEntrega)
                .stream()
                .map(TarefaResponse::toDto)
                .toList();
    }

    @GetMapping("/pendentes")
    public List<TarefaResponse> buscarPorPendentes(
            @RequestParam(required = false) String responsavel,
            @RequestParam Map<String, String> parametros
    ) {

        if (!parametros.isEmpty() && !parametros.containsKey("responsavel")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Parâmetro inválido. O parâmetro permitido é: responsavel"
            );
        }

        if (responsavel == null || responsavel.isEmpty()) {
            return tarefaRepository.findByConcluidoFalse()
                    .stream()
                    .map(TarefaResponse::toDto)
                    .toList();
        }

        return tarefaRepository.findByConcluidoFalseAndResponsavelContainingIgnoreCase(responsavel)
                .stream()
                .map(TarefaResponse::toDto)
                .toList();
    }

}
