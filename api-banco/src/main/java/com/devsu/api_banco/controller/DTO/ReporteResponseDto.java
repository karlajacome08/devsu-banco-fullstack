package com.devsu.api_banco.controller.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteResponseDto {
    private String cliente;
    private List<ReporteCuenta> cuentas;
    private Double totalCreditos;
    private Double totalDebitos;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReporteCuenta {
        private String numeroCuenta;
        private String tipoCuenta;
        private Double saldoActual;
    }

}
