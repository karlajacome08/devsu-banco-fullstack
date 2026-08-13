package com.devsu.api_banco.exception;

public class SaldoNoDisponibleExeption extends RuntimeException {
    public SaldoNoDisponibleExeption(String message) {
        super(message);
    }

}
