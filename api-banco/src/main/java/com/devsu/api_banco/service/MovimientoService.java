package com.devsu.api_banco.service;

import com.devsu.api_banco.model.Movimiento;
import java.util.List;

public interface MovimientoService {
    List<Movimiento> obtenerTodos();

    Movimiento obtenerMovimientoPorId(long id);

    Movimiento registrarMovimiento(String numeroCuenta, double monto, String tipoMovimiento);

    Boolean eliminarMovimiento(long id);
}
