package com.desafio.redis.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildResponse(
            HttpStatus status,
            String message,
            Exception ex
    ) {
        Map<String, Object> response = new HashMap<>();

        response.put("status", status.value());
        response.put("timestamp", LocalDateTime.now());
        response.put("message", message);
        response.put("error", ex.getMessage());

        return ResponseEntity
                .status(status)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno da API",
                ex
        );
    }

    @ExceptionHandler(ErrorApiWeather.class)
    public ResponseEntity<?> handleException(ErrorApiWeather ex) {
        return buildResponse(
                HttpStatus.BAD_GATEWAY,
                "Erro ao consultar API de clima",
                ex
        );
    }

}
