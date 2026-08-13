package com.devsu.api_banco.service;

import com.devsu.api_banco.model.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> obtenerTodos();

    Cliente obtenerClientePorId(long id);

    Cliente crearCliente(Cliente cliente);

    Cliente actualizarCliente(long id, Cliente cliente);

    boolean eliminarCliente(long id);

}
