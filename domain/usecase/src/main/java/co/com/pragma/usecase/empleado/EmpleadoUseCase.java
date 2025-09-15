package co.com.pragma.usecase.empleado;

import co.com.pragma.model.empleado.Empleado;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class EmpleadoUseCase {
    private final PasswordService passwordService;
    private final UsuarioRepository usuarioRepository;

    public void crearEmpleado(Empleado empleado) {
        empleado.setClave(passwordService.encode(empleado.getClave()));
        empleado.setRol(Roles.EMPLEADO);
        usuarioRepository.crearUsuario(empleado);
    }

    public Usuario buscarEmpleadoPorId(Long id) {
        return usuarioRepository.buscarUsuarioPorId(id).orElse(null);
    }
}
