package com.devsu.api_banco.service;

import com.devsu.api_banco.model.Cliente;
import com.devsu.api_banco.model.Cuenta;
import com.devsu.api_banco.repository.CuentaRepository;
import com.devsu.api_banco.service.implementation.CuentaServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CuentaServiceImplTest {
    @Mock
    private CuentaRepository cuentaRepository;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    private Cuenta cuenta;

    @BeforeEach
    public void setUp() {
        cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipoCuenta("Ahorros");
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado(true);

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cuenta.setCliente(cliente);
    }

    @Test
    public void obtenerCuentaPorNumeroCuenta_retornaCuenta_siExiste() {
        when(cuentaRepository.findById("1234567890")).thenReturn(Optional.of(cuenta));

        Cuenta result = cuentaService.obtenerCuentaPorId("1234567890");

        assertNotNull(result);
        assertEquals("1234567890", result.getNumeroCuenta());
        verify(cuentaRepository, times(1)).findById("1234567890");
    }

    @Test
    public void obtenerCuentaPorNumeroCuenta_retornaException_siNoExiste() {
        when(cuentaRepository.findById("0987654321")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cuentaService.obtenerCuentaPorId("0987654321");
        });

        assertEquals("Cuenta no encontrada", exception.getMessage());
        verify(cuentaRepository, times(1)).findById("0987654321");
    }

    @Test
    public void eliminarCuenta_retornaTrue_siCuentaExiste_esEliminada() {
        when(cuentaRepository.findById("1234567890")).thenReturn(Optional.of(cuenta));

        Boolean result = cuentaService.eliminarCuenta("1234567890");

        assertTrue(result);
        verify(cuentaRepository, times(1)).deleteById("1234567890");
    }

}
