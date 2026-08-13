import { Cliente } from './cliente.model';

export interface Cuenta {
  numeroCuenta: string;
  tipoCuenta: string;
  saldoInicial: number;
  estado: boolean;
  cliente: Cliente | { id: number };
}
