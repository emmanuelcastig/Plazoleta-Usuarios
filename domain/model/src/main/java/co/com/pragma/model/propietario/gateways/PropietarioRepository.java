package co.com.pragma.model.propietario.gateways;

import co.com.pragma.model.propietario.Propietario;

import java.util.Optional;


public interface PropietarioRepository {
    void crearPropietario(Propietario propietario);
    Optional<Propietario> propietarioExiste(Long id);
}
