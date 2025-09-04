package co.com.pragma.usecase.propietario.autenticacion;

import co.com.pragma.model.propietario.Propietario;
import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.propietario.gateways.PropietarioRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AutenticacionUseCase {

    private final PropietarioRepository propietarioRepository;
    private final PasswordService passwordService;

    public Propietario login(String correo, String clave) {
        return propietarioRepository.findByCorreo(correo)
                .filter(propietario -> passwordService.matches(clave, propietario.getClave()))
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }
}
