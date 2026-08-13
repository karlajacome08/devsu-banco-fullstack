package com.devsu.api_banco.service.implementation;

import com.devsu.api_banco.model.Movimiento;
import com.devsu.api_banco.model.Cuenta;
import com.devsu.api_banco.service.MovimientoService;
import com.devsu.api_banco.repository.MovimientoRepository;
import com.devsu.api_banco.repository.CuentaRepository;
import com.devsu.api_banco.exception.CupoDiarioExcedidoException;
import com.devsu.api_banco.exception.SaldoNoDisponibleExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class MovimientoServiceImpl implements MovimientoService {
    private static final double LIMITE_DIARIO_RETIRO = 1000.0;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public List<Movimiento> obtenerTodos() {
        return movimientoRepository.findAll();
    }

    @Override
    public Movimiento obtenerMovimientoPorId(long id) {
        return movimientoRepository.findById(id).orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    @Override
    public Movimiento registrarMovimiento(String numeroCuenta, double monto, String tipoMovimiento) {
        Cuenta cuenta = cuentaRepository.findById(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        double montoAbs = Math.abs(monto);
        boolean esRetiro = "Retiro".equalsIgnoreCase(tipoMovimiento);
        double montoFirmado = esRetiro ? -montoAbs : montoAbs;

        Double saldoInicial = cuenta.getSaldoInicial();

        // Reglas
        // 1. Si el saldo es cero, y va a realizar una transacción débito, debe
        // desplegarmensaje "Saldo no disponible".
        if (saldoInicial == 0 && esRetiro) {
            throw new SaldoNoDisponibleExeption("Saldo no disponible");
        }

        // 2. Si el saldo es menor al monto a retirar, debe desplegar mensaje "Saldo
        // insuficiente".
        if (esRetiro && montoAbs > saldoInicial) {
            throw new RuntimeException("Saldo insuficiente");
        }

        // 3.Limite diario de retiro = valor tope 1000
        // Si el cupo disponible ya se cumplió no debe permitir realizar un débito y
        // debedesplegar el mensaje "Cupo diario Excedido"
        if (esRetiro) {
            double totalRetirosHoy = calcularTotalRetirosHoy(numeroCuenta);
            Double montoRetiro = montoAbs;
            if (totalRetirosHoy + montoRetiro > LIMITE_DIARIO_RETIRO) {
                throw new CupoDiarioExcedidoException("Cupo diario excedido");
            }
        }

        // Calcular el nuevo saldo de la cuenta
        double nuevoSaldo = saldoInicial + montoFirmado;
        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        // Crear el movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setCuenta(cuenta);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento(tipoMovimiento);
        movimiento.setValor(montoAbs);
        movimiento.setSaldo(nuevoSaldo);

        return movimientoRepository.save(movimiento);

    }

    private Double calcularTotalRetirosHoy(String numeroCuenta) {
        LocalDate today = LocalDate.now();
        return movimientoRepository.findAll().stream()
                .filter(movimiento -> movimiento.getCuenta().getNumeroCuenta().equals(numeroCuenta))
                .filter(movimiento -> movimiento.getFecha().toLocalDate().isEqual(today)
                        && "Retiro".equalsIgnoreCase(movimiento.getTipoMovimiento()))
                .mapToDouble(Movimiento::getValor)
                .sum();
    }

    @Override
    public Boolean eliminarMovimiento(long id) {
        if (movimientoRepository.existsById(id)) {
            movimientoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
