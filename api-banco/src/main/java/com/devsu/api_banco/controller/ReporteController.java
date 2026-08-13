package com.devsu.api_banco.controller;

import com.devsu.api_banco.controller.DTO.ReporteResponseDto;
import com.devsu.api_banco.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<?> generarReporte(
            @RequestParam Long clientId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(defaultValue = "json") String formato) {
        if (formato.equalsIgnoreCase("pdf")) {
            String pdfBase64 = reporteService.generarReportePDF(clientId, fechaInicio, fechaFin);
            Map<String, String> response = new HashMap<>();
            response.put("pdfBase64", pdfBase64);
            return ResponseEntity.ok(response); // Retorna null ya que el PDF se devuelve en base64
        } else {
            ReporteResponseDto reporte = reporteService.generarReporte(clientId, fechaInicio, fechaFin);
            return ResponseEntity.ok(reporte);
        }

    }

}
