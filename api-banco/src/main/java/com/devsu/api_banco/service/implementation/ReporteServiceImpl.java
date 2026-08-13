package com.devsu.api_banco.service.implementation;

import com.devsu.api_banco.service.ReporteService;
import com.devsu.api_banco.controller.DTO.ReporteResponseDto;
import com.devsu.api_banco.model.Cliente;
import com.devsu.api_banco.model.Cuenta;
import com.devsu.api_banco.model.Movimiento;
import com.devsu.api_banco.repository.ClienteRepository;
import com.devsu.api_banco.repository.CuentaRepository;
import com.devsu.api_banco.repository.MovimientoRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Base64;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Override
    public ReporteResponseDto generarReporte(Long clientId, LocalDate fechaInicio, LocalDate fechaFin) {
        Cliente cliente = clienteRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        List<Cuenta> cuentas = cuentaRepository.findAll().stream()
                .filter(cuenta -> cuenta.getCliente().getId().equals(clientId))
                .collect(Collectors.toList());

        List<ReporteResponseDto.ReporteCuenta> reporteCuentas = cuentas.stream()
                .map(cuenta -> new ReporteResponseDto.ReporteCuenta(
                        cuenta.getNumeroCuenta(),
                        cuenta.getTipoCuenta(),
                        cuenta.getSaldoInicial()))
                .collect(Collectors.toList());

        List<Movimiento> movimientos = movimientoRepository.findAll().stream()
                .filter(movimiento -> cuentas.stream()
                        .anyMatch(cuenta -> cuenta.getNumeroCuenta().equals(movimiento.getCuenta().getNumeroCuenta())))
                .filter(movimiento -> !movimiento.getFecha().toLocalDate().isBefore(fechaInicio)
                        && !movimiento.getFecha().toLocalDate().isAfter(fechaFin))
                .collect(Collectors.toList());

        Double totalCreditos = movimientos.stream()
                .filter(movimiento -> movimiento.getValor() > 0)
                .mapToDouble(Movimiento::getValor)
                .sum();

        Double totalDebitos = movimientos.stream()
                .filter(movimiento -> movimiento.getValor() < 0)
                .mapToDouble(movimiento -> Math.abs(movimiento.getValor()))
                .sum();

        return new ReporteResponseDto(cliente.getNombre(), reporteCuentas, totalCreditos, totalDebitos);
    }

    @Override
    public String generarReportePDF(Long clientId, LocalDate fechaInicio, LocalDate fechaFin) {
        ReporteResponseDto reporte = generarReporte(clientId, fechaInicio, fechaFin);

        try {
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font fontContent = FontFactory.getFont(FontFactory.HELVETICA, 12);

            document.add(new Paragraph("Estado de cuenta", fontTitle));
            document.add(new Paragraph("Cliente: " + reporte.getCliente(), fontContent));
            document.add(new Paragraph("Fecha de inicio: " + fechaInicio, fontContent));
            document.add(new Paragraph("Fecha de fin: " + fechaFin, fontContent));
            document.add(new Paragraph(""));

            for (ReporteResponseDto.ReporteCuenta cuenta : reporte.getCuentas()) {
                document.add(new Paragraph("Número de cuenta: " + cuenta.getNumeroCuenta(), fontContent));
                document.add(new Paragraph("Tipo de cuenta: " + cuenta.getTipoCuenta(), fontContent));
                document.add(new Paragraph("Saldo actual: " + cuenta.getSaldoActual(), fontContent));
                document.add(new Paragraph(""));
            }

            document.add(new Paragraph(""));
            document.add(new Paragraph("Total Créditos: " + reporte.getTotalCreditos(), fontContent));
            document.add(new Paragraph("Total Débitos: " + reporte.getTotalDebitos(), fontContent));

            document.close();

            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte PDF", e);
        }
    }
}
