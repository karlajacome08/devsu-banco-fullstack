package com.devsu.api_banco.service;

import com.devsu.api_banco.model.Cuenta;
import com.devsu.api_banco.model.Movimiento;
import com.devsu.api_banco.controller.DTO.*;
import com.devsu.api_banco.repository.MovimientoRepository;
import com.devsu.api_banco.repository.CuentaRepository;
import com.devsu.api_banco.service.implementation.MovimientoServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovimientoServiceImplTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(2000.0);
        cuenta.setEstado(true);
    }

    @Test
    void registrarMovimiento_retornaMovimiento_siRetiroExitoso() {
        MovimientoRequestDto movimiento = new MovimientoRequestDto();
        movimiento.setNumeroCuenta("1234567890");
        movimiento.setTipoMovimiento("Retiro");
        movimiento.setValor(500.0);

        when(cuentaRepository.findById("1234567890")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Movimiento result = movimientoService.registrarMovimiento(
                movimiento.getNumeroCuenta(),
                movimiento.getValor(),
                movimiento.getTipoMovimiento());

        assertNotNull(result);
        assertEquals("Retiro", result.getTipoMovimiento());
        assertEquals(500.0, result.getValor());
        verify(cuentaRepository, times(1)).findById("1234567890");
        verify(movimientoRepository, times(1)).save(any(Movimiento.class));
    }

    @Test
    public void registrarMovimiento_retornaException_siCuentaNoExiste() {
        MovimientoRequestDto movimiento = new MovimientoRequestDto();
        movimiento.setNumeroCuenta("0987654321");
        movimiento.setTipoMovimiento("Retiro");
        movimiento.setValor(500.0);

        when(cuentaRepository.findById("0987654321")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimientoService.registrarMovimiento(
                    movimiento.getNumeroCuenta(),
                    movimiento.getValor(),
                    movimiento.getTipoMovimiento());
        });

        assertEquals("Cuenta no encontrada", exception.getMessage());
        verify(cuentaRepository, times(1)).findById("0987654321");
        verify(movimientoRepository, never()).save(any(Movimiento.class));
    }

    @Test
    public void registrarMovimiento_retornaException_siSaldoInsuficiente() {
        MovimientoRequestDto movimiento = new MovimientoRequestDto();
        movimiento.setNumeroCuenta("1234567890");
        movimiento.setTipoMovimiento("Retiro");
        movimiento.setValor(3000.0);

        when(cuentaRepository.findById("1234567890")).thenReturn(Optional.of(cuenta));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            movimientoService.registrarMovimiento(
                    movimiento.getNumeroCuenta(),
                    movimiento.getValor(),
                    movimiento.getTipoMovimiento());
        });

        assertEquals("Saldo insuficiente", exception.getMessage());
        verify(cuentaRepository, times(1)).findById("1234567890");
        verify(movimientoRepository, never()).save(any(Movimiento.class));
    }

    @Test
    public void registrarMovimiento_retornaMovimiento_siDepositoExitoso() {
        MovimientoRequestDto movimiento = new MovimientoRequestDto();
        movimiento.setNumeroCuenta("1234567890");
        movimiento.setTipoMovimiento("Deposito");
        movimiento.setValor(1000.0);

        when(cuentaRepository.findById("1234567890")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Movimiento result = movimientoService.registrarMovimiento(
                movimiento.getNumeroCuenta(),
                movimiento.getValor(),
                movimiento.getTipoMovimiento());

        assertNotNull(result);
        assertEquals("Deposito", result.getTipoMovimiento());
        assertEquals(1000.0, result.getValor());
        verify(cuentaRepository, times(1)).findById("1234567890");
        verify(movimientoRepository, times(1)).save(any(Movimiento.class));
    }

}
