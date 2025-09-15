package co.com.pragma.usecase.propietario;

import co.com.pragma.model.propietario.Propietario;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class PropietarioUseCase {
    private final UsuarioRepository usuarioRepository;
    private final PasswordService passwordService;

    public void crearPropietario(Propietario propietario) {
        propietario.setClave(passwordService.encode(propietario.getClave()));
        if (validarEdadPropietario(propietario)){
            propietario.setRol(Roles.PROPIETARIO);
            usuarioRepository.crearUsuario(propietario);
        }
    }

    private boolean validarEdadPropietario(Propietario propietario) {
        if (propietario.getFechaNacimiento().isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("El propietario debe ser mayor de edad");
        }
        return true;
    }

    public boolean propietarioExiste(Long id) {
        Optional<Usuario> propietario = usuarioRepository.buscarUsuarioPorId(id);
        if (propietario.isPresent()) {
            if (propietario.get().getRol().equals(Roles.PROPIETARIO)) {
                return true;
            }
        }
        return false;
    }
}
