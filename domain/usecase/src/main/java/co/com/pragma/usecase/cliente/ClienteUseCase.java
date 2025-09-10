package co.com.pragma.usecase.cliente;

import co.com.pragma.model.cliente.Cliente;
import co.com.pragma.model.cliente.gateways.ClienteRepository;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class ClienteUseCase {

    private final PasswordService passwordService;
    private final ClienteRepository clienteRepository;

    public void crearCliente(Cliente cliente) {
        cliente.setClave(passwordService.encode(cliente.getClave()));
        cliente.setRol(Roles.CLIENTE);
        clienteRepository.crearCliente(cliente);
    }
}
