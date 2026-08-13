import { ClienteService } from './../../services/cliente';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { Cliente } from '../../models/cliente.model';
import { ClienteForm } from '../cliente-form/cliente-form';

@Component({
  selector: 'app-cliente-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, ClienteForm],
  templateUrl: './cliente-list.html',
  styleUrl: './cliente-list.css',
})
export class ClienteList implements OnInit {
  clientes: Cliente[] = [];
  clientesFiltrados: Cliente[] = [];
  terminoBusqueda: string = '';
  mensajeError: string = '';

  mostrarModal: boolean = false;
  esEdicion: boolean = false;
  clienteSeleccionado: Cliente = this.clienteVacio();

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.cargarClientes();
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

  cargarClientes(): void {
    this.clienteService.obtenerClientes().subscribe({
      next: (data) => {
        this.clientes = data;
        this.clientesFiltrados = data;
      },
      error: (err) => {
        this.mensajeError = 'Error al cargar los clientes.';
        console.error(err);
      },
    });
  }

  buscar(): void {
    const termino = this.terminoBusqueda.toLowerCase();
    this.clientesFiltrados = this.clientes.filter(
      (c) =>
        c.nombre.toLowerCase().includes(termino) ||
        c.identificacion.toLowerCase().includes(termino),
    );
  }

  abrirModalNuevo(): void {
    this.esEdicion = false;
    this.clienteSeleccionado = this.clienteVacio();
    this.mensajeError = '';
    this.mostrarModal = true;
  }

  abrirModalEditar(cliente: Cliente): void {
    this.esEdicion = true;
    this.clienteSeleccionado = { ...cliente };
    this.mensajeError = '';
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
  }

  onGuardar(cliente: Cliente): void {
    if (this.esEdicion && cliente.id) {
      this.clienteService.actualizarCliente(cliente.id, cliente).subscribe({
        next: () => {
          this.cargarClientes();
          this.cerrarModal();
        },
        error: (err) => {
          this.mensajeError = 'Error al actualizar el cliente.';
          console.error(err);
        },
      });
    } else {
      this.clienteService.crearCliente(cliente).subscribe({
        next: () => {
          this.cargarClientes();
          this.cerrarModal();
        },
        error: (err) => {
          this.mensajeError = 'Error al crear el cliente.';
          console.error(err);
        },
      });
    }
  }

  eliminar(id: number | undefined): void {
    if (!id) return;
    if (confirm('¿Está seguro que desea eliminar este cliente?')) {
      this.clienteService.eliminarCliente(id).subscribe({
        next: () => this.cargarClientes(),
        error: (err) => {
          this.mensajeError = 'Error al eliminar el cliente.';
          console.error(err);
        },
      });
    }
  }
}
