package com.devsu.api_banco.service.implementation;

import com.devsu.api_banco.model.Cuenta;
import com.devsu.api_banco.repository.CuentaRepository;
import com.devsu.api_banco.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public List<Cuenta> obtenerTodas() {
        return cuentaRepository.findAll();
    }

    @Override
    public Cuenta obtenerCuentaPorId(String id) {
        return cuentaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    @Override
    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    public Cuenta actualizarCuenta(String id, Cuenta cuenta) {
        Cuenta existingCuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        existingCuenta.setTipoCuenta(cuenta.getTipoCuenta());
        existingCuenta.setSaldoInicial(cuenta.getSaldoInicial());
        existingCuenta.setEstado(cuenta.getEstado());
        existingCuenta.setCliente(cuenta.getCliente());
        return cuentaRepository.save(existingCuenta);
    }

    @Override
    public Boolean eliminarCuenta(String id) {
        Cuenta cuenta = obtenerCuentaPorId(id);
        if (cuenta != null) {
            cuentaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
