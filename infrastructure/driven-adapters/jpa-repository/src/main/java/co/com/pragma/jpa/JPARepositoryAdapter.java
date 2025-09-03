package co.com.pragma.jpa;

import co.com.pragma.jpa.entity.PropietarioEntity;
import co.com.pragma.jpa.helper.AdapterOperations;
import co.com.pragma.model.propietario.Propietario;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PropietarioRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JPARepositoryAdapter extends AdapterOperations<Propietario, PropietarioEntity, Long, JPARepository>
implements PropietarioRepository
{

    private final PasswordEncoder passwordEncoder;

    public JPARepositoryAdapter(JPARepository repository, ObjectMapper mapper, PasswordEncoder passwordEncoder) {
        super(repository, mapper, d -> mapper.map(d, Propietario.class));
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void crearPropietario(Propietario propietario) {
        propietario.setClave(passwordEncoder.encode(propietario.getClave()));
        repository.save(toData(propietario));
    }

    @Override
    public Optional<Propietario> propietarioExiste(Long id) {
        return repository.findById(id).map(this::toEntity);
    }
}
