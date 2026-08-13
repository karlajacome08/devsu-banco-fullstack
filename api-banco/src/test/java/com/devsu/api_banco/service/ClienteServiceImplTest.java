package com.devsu.api_banco.service;

import com.devsu.api_banco.model.Cliente;
import com.devsu.api_banco.repository.ClienteRepository;
import com.devsu.api_banco.service.implementation.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan Perez");
        cliente.setGenero("Masculino");
        cliente.setIdentificacion("1234567890");
        cliente.setEdad(26);
        cliente.setDireccion("Calle Gayasamin 123");
        cliente.setTelefono("555-1234");
        cliente.setContrasena("password");
        cliente.setEstado(true);
    }

    @Test
    public void testGetClienteById_retornaCliente_siExiste() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente result = clienteService.obtenerClientePorId(1L);

        assertNotNull(result);
        assertEquals("Juan Perez", result.getNombre());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetClienteById_retornaException_siNoExiste() {
        when(clienteRepository.findById(2L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.obtenerClientePorId(2L);
        });

        assertEquals("Cliente no encontrado", exception.getMessage());
        verify(clienteRepository, times(1)).findById(2L);
    }

    @Test
    public void crearCliente_retornaClienteCreado() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente result = clienteService.crearCliente(cliente);

        assertNotNull(result);
        assertEquals("Juan Perez", result.getNombre());
        verify(clienteRepository, times(1)).save(cliente);
    }
}
