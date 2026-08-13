import { Component, OnInit } from '@angular/core';
import { MovimientoService } from '../../services/movimiento';
import { CuentaService } from '../../services/cuenta';
import { Movimiento } from '../../models/movimiento.model';
import { Cuenta } from '../../models/cuenta.model';
import { MovimientoForm } from '../movimiento-form/movimiento-form';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-movimiento-list',
  imports: [MovimientoForm, CommonModule, FormsModule],
  templateUrl: './movimiento-list.html',
  styleUrl: './movimiento-list.css',
})
export class MovimientoList implements OnInit {
  movimientos: Movimiento[] = [];
  movimientosFiltrados: Movimiento[] = [];
  cuentas: Cuenta[] = [];
  busqueda: string = '';
  mensajeError: string = '';

  mostrarModal: boolean = false;

  constructor(
    private movimientoService: MovimientoService,
    private cuentaService: CuentaService,
  ) {}

  ngOnInit(): void {
    this.cargarMovimientos();
    this.cargarCuentas();
  }

  cargarMovimientos(): void {
    this.movimientoService.obtenerMovimientos().subscribe({
      next: (data) => {
        this.movimientos = data;
        this.movimientosFiltrados = data;
      },
      error: (err) => {
        this.mensajeError = 'Error al cargar los movimientos.';
        console.error(err);
      },
    });
  }

  cargarCuentas(): void {
    this.cuentaService.obtenerCuentas().subscribe({
      next: (data) => (this.cuentas = data),
      error: (err) => console.error(err),
    });
  }

  buscar(): void {
    const termino = this.busqueda.toLowerCase();
    this.movimientosFiltrados = this.movimientos.filter((m) => {
      const numeroCuenta = m.cuenta?.numeroCuenta || m.numeroCuenta || '';
      return (
        numeroCuenta.toLowerCase().includes(termino) ||
        m.tipoMovimiento.toLowerCase().includes(termino)
      );
    });
  }

  abrirModal(): void {
    this.mensajeError = '';
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
  }

  onGuardar(movimiento: Movimiento): void {
    this.movimientoService.registrarMovimiento(movimiento).subscribe({
      next: () => {
        this.cargarMovimientos();
        this.cerrarModal();
      },
      error: (err) => {
        this.mensajeError = err.error?.mensaje || 'Error al registrar el movimiento.';
        console.error(err);
      },
    });
  }

  eliminar(id: number | undefined): void {
    if (!id) return;
    if (confirm('¿Está seguro que desea eliminar este movimiento?')) {
      this.movimientoService.eliminarMovimiento(id).subscribe({
        next: () => this.cargarMovimientos(),
        error: (err) => {
          this.mensajeError = 'Error al eliminar el movimiento.';
          console.error(err);
        },
      });
    }
  }
}
