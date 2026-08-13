package com.devsu.api_banco.controller.DTO;

import lombok.Data;

@Data
public class MovimientoRequestDto {
    private String numeroCuenta;
    private String tipoMovimiento;
    private Double valor;

}
