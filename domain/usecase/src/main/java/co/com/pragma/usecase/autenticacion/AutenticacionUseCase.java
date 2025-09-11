package co.com.pragma.usecase.autenticacion;

import co.com.pragma.model.cliente.gateways.ClienteRepository;
import co.com.pragma.model.empleado.gateways.EmpleadoRepository;
import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.propietario.gateways.PropietarioRepository;
import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.usecase.exception.CredencialesInvalidasException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AutenticacionUseCase {

    private final PropietarioRepository propietarioRepository;
    private final ClienteRepository clienteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PasswordService passwordService;

    public Usuario login(String correo, String clave) {
        return propietarioRepository.findByCorreo(correo)
                .map(p -> new Usuario(p.getId(), p.getCorreo(), p.getClave(), p.getRol()))
                .filter(u -> passwordService.matches(clave, u.getClave()))
                .or(() -> clienteRepository.buscarClientePorCorreo(correo)
                        .map(c -> new Usuario(c.getId(), c.getCorreo(), c.getClave(), c.getRol()))
                        .filter(u -> passwordService.matches(clave, u.getClave())))
                .or(() -> empleadoRepository.buscarEmpleadoPorCorreo(correo)
                        .map(e -> new Usuario(e.getId(), e.getCorreo(), e.getClave(), e.getRol()))
                        .filter(u -> passwordService.matches(clave, u.getClave())))
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales inválidas"));
    }
}
