export interface ReporteCuenta {
  numeroCuenta: string;
  tipoCuenta: string;
  saldoActual: number;
}

export interface Reporte {
  cliente: string;
  cuentas: ReporteCuenta[];
  totalCreditos: number;
  totalDebitos: number;
}
