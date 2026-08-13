import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { Cuenta } from '../../models/cuenta.model';
import { Cliente } from '../../models/cliente.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-cuenta-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cuenta-form.html',
  styleUrl: './cuenta-form.css',
})
export class CuentaForm implements OnChanges {
  @Input() cuenta: Cuenta = this.cuentaVacia();
  @Input() esEdicion: boolean = false;
  @Input() mensajeError: string = '';
  @Input() clientes: Cliente[] = [];

  @Output() guardar = new EventEmitter<Cuenta>();
  @Output() cancelar = new EventEmitter<void>();

  cuentaLocal: Cuenta = this.cuentaVacia();
  clienteIdSeleccionado: number | null = null;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['cuenta']) {
      this.cuentaLocal = { ...this.cuenta };
      const cliente = this.cuenta.cliente as any;
      this.clienteIdSeleccionado = cliente?.id ?? null;
    }
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

  onSubmit(): void {
    this.cuentaLocal.cliente = { id: Number(this.clienteIdSeleccionado) };
    this.guardar.emit(this.cuentaLocal);
  }

  onCancelar(): void {
    this.cancelar.emit();
  }
}
