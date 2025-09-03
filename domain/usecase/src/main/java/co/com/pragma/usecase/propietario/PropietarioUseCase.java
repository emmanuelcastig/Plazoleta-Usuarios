package co.com.pragma.usecase.propietario;

import co.com.pragma.model.propietario.Propietario;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PropietarioRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class PropietarioUseCase {
    private final PropietarioRepository propietarioRepository;

    public void crearPropietario(Propietario propietario) {
        if (validarEdadPropietario(propietario)){
            propietario.setRol(Roles.PROPIETARIO);
            propietarioRepository.crearPropietario(propietario);
        }
    }

    private boolean validarEdadPropietario(Propietario propietario) {
        if (propietario.getFechaNacimiento().isAfter(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("El propietario debe ser mayor de edad");
        }
        return true;
    }

    public boolean propietarioExiste(Long id) {
        Optional<Propietario> propietario = propietarioRepository.propietarioExiste(id);
        if (propietario.isPresent()) {
            if (propietario.get().getRol().equals(Roles.PROPIETARIO)) {
                return true;
            }
        }
        return false;
    }
}
