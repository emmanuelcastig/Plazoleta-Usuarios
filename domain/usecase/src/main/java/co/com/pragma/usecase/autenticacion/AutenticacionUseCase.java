package co.com.pragma.usecase.autenticacion;

import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import co.com.pragma.model.usuarioLogin.UsuarioLogin;
import co.com.pragma.usecase.exception.CredencialesInvalidasException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AutenticacionUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordService passwordService;

    public UsuarioLogin login(String correo, String clave) {
        return usuarioRepository.buscarUsuarioPorCorreo(correo)
                .map(p -> new UsuarioLogin(p.getId(), p.getCorreo(), p.getClave(), p.getRol()))
                .filter(u -> passwordService.matches(clave, u.getClave()))
                .orElseThrow(() -> new CredencialesInvalidasException("Credenciales inválidas"));
    }
}
