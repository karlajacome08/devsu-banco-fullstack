package com.devsu.api_banco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SaldoNoDisponibleExeption.class)
    public ResponseEntity<Map<String, Object>> handleSaldoNoDisponibleException(SaldoNoDisponibleExeption ex) {

        return construirRespuesta(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CupoDiarioExcedidoException.class)
    public ResponseEntity<Map<String, Object>> handleCupoDiarioExcedidoException(CupoDiarioExcedidoException ex) {
        return construirRespuesta(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        return construirRespuesta(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, Object>> construirRespuesta(String mensaje, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", mensaje);

        return new ResponseEntity<>(response, status);
    }
}
