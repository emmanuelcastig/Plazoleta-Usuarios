package co.com.pragma.model.cliente.gateways;

import co.com.pragma.model.cliente.Cliente;

import java.util.Optional;

public interface ClienteRepository {
    void crearCliente(Cliente cliente);
    Optional<Cliente> buscarClientePorCorreo(String correo);
}
