import { Injectable } from '@angular/core';
import { Reporte } from '../models/reporte.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ReporteService {
  private apiUrl = 'http://localhost:8080/reportes';

  constructor(private http: HttpClient) {}

  obtenerReporte(clienteId: number, fechaInicio: string, fechaFin: string): Observable<Reporte> {
    const params = `clientId=${clienteId}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}&formato=json`;
    return this.http.get<Reporte>(`${this.apiUrl}?${params}`);
  }

  obtenerReportePdf(
    clienteId: number,
    fechaInicio: string,
    fechaFin: string,
  ): Observable<{ pdfBase64: string }> {
    const params = `clientId=${clienteId}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}&formato=pdf`;
    return this.http.get<{ pdfBase64: string }>(`${this.apiUrl}?${params}`);
  }
}
