import { Component, OnInit } from '@angular/core';
import { CuentaService } from '../../services/cuenta';
import { ClienteService } from '../../services/cliente';
import { Cuenta } from '../../models/cuenta.model';
import { Cliente } from '../../models/cliente.model';
import { CuentaForm } from '../cuenta-form/cuenta-form';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cuenta-list',
  imports: [CuentaForm, CommonModule, FormsModule],
  templateUrl: './cuenta-list.html',
  styleUrl: './cuenta-list.css',
})
export class CuentaList implements OnInit {
  cuentas: Cuenta[] = [];
  cuentasFiltradas: Cuenta[] = [];
  clientes: Cliente[] = [];
  busqueda: string = '';
  mensajeError: string = '';

  mostrarModal: boolean = false;
  esEdicion: boolean = false;
  cuentaSeleccionada: Cuenta = this.cuentaVacia();

  constructor(
    private cuentaService: CuentaService,
    private clienteService: ClienteService,
  ) {}

  ngOnInit(): void {
    this.cargarCuentas();
    this.cargarClientes();
  }

  cuentaVacia(): Cuenta {
    return {
      numeroCuenta: '',
      tipoCuenta: '',
      saldoInicial: 0,
      estado: true,
      cliente: { id: 0 },
    };
  }

  cargarCuentas(): void {
    this.cuentaService.obtenerCuentas().subscribe({
      next: (data) => {
        this.cuentas = data;
        this.cuentasFiltradas = data;
      },
      error: (err) => {
        this.mensajeError = 'Error al cargar las cuentas.';
        console.error(err);
      },
    });
  }

  cargarClientes(): void {
    this.clienteService.obtenerClientes().subscribe({
      next: (data) => (this.clientes = data),
      error: (err) => console.error(err),
    });
  }

  buscar(): void {
    const termino = this.busqueda.toLowerCase();
    this.cuentasFiltradas = this.cuentas.filter(
      (c) =>
        c.numeroCuenta.toLowerCase().includes(termino) ||
        c.tipoCuenta.toLowerCase().includes(termino),
    );
  }

  nombreCliente(cuenta: Cuenta): string {
    const cliente = cuenta.cliente as any;
    return cliente?.nombre ?? 'N/A';
  }

  abrirModalNuevo(): void {
    this.esEdicion = false;
    this.cuentaSeleccionada = this.cuentaVacia();
    this.mensajeError = '';
    this.mostrarModal = true;
  }

  abrirModalEditar(cuenta: Cuenta): void {
    this.esEdicion = true;
    this.cuentaSeleccionada = { ...cuenta };
    this.mensajeError = '';
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
  }

  onGuardar(cuenta: Cuenta): void {
    if (this.esEdicion) {
      this.cuentaService.actualizarCuenta(cuenta.numeroCuenta, cuenta).subscribe({
        next: () => {
          this.cargarCuentas();
          this.cerrarModal();
        },
        error: (err) => {
          this.mensajeError = 'Error al actualizar la cuenta.';
          console.error(err);
        },
      });
    } else {
      this.cuentaService.crearCuenta(cuenta).subscribe({
        next: () => {
          this.cargarCuentas();
          this.cerrarModal();
        },
        error: (err) => {
          this.mensajeError = 'Error al crear la cuenta.';
          console.error(err);
        },
      });
    }
  }

  eliminar(numeroCuenta: string): void {
    if (confirm('¿Está seguro que desea eliminar esta cuenta?')) {
      this.cuentaService.eliminarCuenta(numeroCuenta).subscribe({
        next: () => this.cargarCuentas(),
        error: (err) => {
          this.mensajeError = 'Error al eliminar la cuenta.';
          console.error(err);
        },
      });
    }
  }
}
