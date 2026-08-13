package com.devsu.api_banco.service;

import com.devsu.api_banco.controller.DTO.ReporteResponseDto;
import java.time.LocalDate;

public interface ReporteService {
    ReporteResponseDto generarReporte(Long clientId, LocalDate fechaInicio, LocalDate fechaFin);

    String generarReportePDF(Long clientId, LocalDate fechaInicio, LocalDate fechaFin);

}
