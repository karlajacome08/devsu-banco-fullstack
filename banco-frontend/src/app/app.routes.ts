import { Routes } from '@angular/router';
import { ClienteList } from './clientes/cliente-list/cliente-list';
import { CuentaList } from './cuentas/cuenta-list/cuenta-list';
import { MovimientoList } from './movimientos/movimiento-list/movimiento-list';
import { Reporte } from './reportes/reporte/reporte';

export const routes: Routes = [
  { path: '', redirectTo: 'clientes', pathMatch: 'full' },
  { path: 'clientes', component: ClienteList },
  { path: 'cuentas', component: CuentaList },
  { path: 'movimientos', component: MovimientoList },
  { path: 'reportes', component: Reporte },
];
