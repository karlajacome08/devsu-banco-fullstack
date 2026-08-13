import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ClienteService } from '../../services/cliente';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente } from '../../models/cliente.model';

@Component({
  selector: 'app-cliente-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cliente-form.html',
  styleUrl: './cliente-form.css',
})
export class ClienteForm implements OnChanges {
  @Input() cliente: Cliente = this.clienteVacio();
  @Input() esEdicion: boolean = false;
  @Input() mensajeError: string = '';

  @Output() guardar = new EventEmitter<Cliente>();
  @Output() cancelar = new EventEmitter<void>();

  clienteLocal: Cliente = this.clienteVacio();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['cliente']) {
      this.clienteLocal = { ...this.cliente };
    }
  }

  clienteVacio(): Cliente {
    return {
      identificacion: '',
      nombre: '',
      genero: '',
      edad: 0,
      direccion: '',
      telefono: '',
      contrasena: '',
      estado: true,
    };
  }

  onSubmit(): void {
    this.guardar.emit(this.clienteLocal);
  }

  onCancelar(): void {
    this.cancelar.emit();
  }
}
