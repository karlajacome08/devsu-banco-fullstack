import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Cliente } from '../../models/cliente.model';
import { ReporteService } from '../../services/reporte';
import { ClienteService } from '../../services/cliente';
import { Reporte as ReporteModel } from '../../models/reporte.model';

@Component({
  selector: 'app-reporte',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reporte.html',
  styleUrl: './reporte.css',
})
export class Reporte implements OnInit {
  clientes: Cliente[] = [];
  clienteIdSeleccionado: number | null = null;
  fechaInicio: string = '';
  fechaFin: string = '';

  reporte: ReporteModel | null = null;
  mensajeError: string = '';
  cargando: boolean = false;

  constructor(
    private reporteService: ReporteService,
    private clienteService: ClienteService,
  ) {}

  ngOnInit(): void {
    this.clienteService.obtenerClientes().subscribe({
      next: (data) => (this.clientes = data),
      error: (err) => console.error(err),
    });
  }

  generarReporte(): void {
    if (!this.clienteIdSeleccionado || !this.fechaInicio || !this.fechaFin) {
      this.mensajeError = 'Debe seleccionar un cliente y un rango de fechas.';
      return;
    }

    this.mensajeError = '';
    this.cargando = true;
    this.reporte = null;

    this.reporteService
      .obtenerReporte(this.clienteIdSeleccionado, this.fechaInicio, this.fechaFin)
      .subscribe({
        next: (data) => {
          this.reporte = data;
          this.cargando = false;
        },
        error: (err) => {
          this.mensajeError = err.error?.mensaje || 'Error al generar el reporte.';
          this.cargando = false;
          console.error(err);
        },
      });
  }

  descargarPdf(): void {
    if (!this.clienteIdSeleccionado || !this.fechaInicio || !this.fechaFin) {
      this.mensajeError = 'Debe seleccionar un cliente y un rango de fechas.';
      return;
    }

    this.reporteService
      .obtenerReportePdf(this.clienteIdSeleccionado, this.fechaInicio, this.fechaFin)
      .subscribe({
        next: (data) => {
          this.descargarArchivo(data.pdfBase64);
        },
        error: (err) => {
          this.mensajeError = 'Error al descargar el PDF.';
          console.error(err);
        },
      });
  }

  private descargarArchivo(base64: string): void {
    const byteCharacters = atob(base64);
    const byteNumbers = new Array(byteCharacters.length);
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
    const byteArray = new Uint8Array(byteNumbers);
    const blob = new Blob([byteArray], { type: 'application/pdf' });

    const link = document.createElement('a');
    link.href = URL.createObjectURL(blob);
    link.download = `estado-cuenta-${this.fechaInicio}-a-${this.fechaFin}.pdf`;
    link.click();
    URL.revokeObjectURL(link.href);
  }
}
