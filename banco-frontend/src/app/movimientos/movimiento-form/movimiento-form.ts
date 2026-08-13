import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Cuenta } from '../../models/cuenta.model';
import { Movimiento } from '../../models/movimiento.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-movimiento-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './movimiento-form.html',
  styleUrl: './movimiento-form.css',
})
export class MovimientoForm {
  @Input() mensajeError: string = '';
  @Input() cuentas: Cuenta[] = [];

  @Output() guardar = new EventEmitter<Movimiento>();
  @Output() cancelar = new EventEmitter<void>();

  numeroCuentaSeleccionada: string = '';
  tipoMovimiento: string = 'Deposito';
  monto: number = 0;

  onSubmit(): void {
    const valor = this.tipoMovimiento === 'Retiro' ? -Math.abs(this.monto) : Math.abs(this.monto);

    const movimiento: Movimiento = {
      numeroCuenta: this.numeroCuentaSeleccionada,
      tipoMovimiento: this.tipoMovimiento,
      valor: valor,
    };

    this.guardar.emit(movimiento);
  }

  onCancelar(): void {
    this.cancelar.emit();
  }
}
