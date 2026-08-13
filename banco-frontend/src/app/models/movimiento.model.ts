export interface Movimiento {
  movimientoId?: number;
  fecha?: string;
  tipoMovimiento: string;
  valor: number;
  saldo?: number;
  numeroCuenta?: string;
  cuenta?: {
    numeroCuenta: string;
    tipoCuenta: string;
    saldoInicial: number;
    estado: boolean;
    cliente?: any;
  };
}
