package co.com.pragma.usecase.cliente;

import co.com.pragma.model.cliente.Cliente;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class ClienteUseCase {

    private final PasswordService passwordService;
    private final UsuarioRepository usuarioRepository;

    public void crearCliente(Cliente cliente) {
        cliente.setClave(passwordService.encode(cliente.getClave()));
        cliente.setRol(Roles.CLIENTE);
        usuarioRepository.crearUsuario(cliente);
    }
}
