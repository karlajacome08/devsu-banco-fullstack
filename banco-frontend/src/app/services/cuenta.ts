import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cuenta } from '../models/cuenta.model';

@Injectable({
  providedIn: 'root',
})
export class CuentaService {
  private apiUrl = 'http://localhost:8080/cuentas';

  constructor(private http: HttpClient) {}

  obtenerCuentas(): Observable<Cuenta[]> {
    return this.http.get<Cuenta[]>(this.apiUrl);
  }

  obtenerCuentaPorId(numeroCuenta: string): Observable<Cuenta> {
    return this.http.get<Cuenta>(`${this.apiUrl}/ ${numeroCuenta}`);
  }

  crearCuenta(cuenta: Cuenta): Observable<Cuenta> {
    return this.http.post<Cuenta>(this.apiUrl, cuenta);
  }

  actualizarCuenta(numeroCuenta: string, cuenta: Cuenta): Observable<Cuenta> {
    const url = `${this.apiUrl}/${numeroCuenta}`;
    return this.http.put<Cuenta>(url, cuenta);
  }

  eliminarCuenta(numeroCuenta: string): Observable<boolean> {
    const url = `${this.apiUrl}/${numeroCuenta}`;
    return this.http.delete<boolean>(url);
  }
}
