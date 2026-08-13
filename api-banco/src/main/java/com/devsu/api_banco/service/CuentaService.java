package com.devsu.api_banco.service;

import com.devsu.api_banco.model.Cuenta;
import java.util.List;

public interface CuentaService {
    List<Cuenta> obtenerTodas();

    Cuenta obtenerCuentaPorId(String id);

    Cuenta crearCuenta(Cuenta cuenta);

    Cuenta actualizarCuenta(String id, Cuenta cuenta);

    Boolean eliminarCuenta(String id);
}
