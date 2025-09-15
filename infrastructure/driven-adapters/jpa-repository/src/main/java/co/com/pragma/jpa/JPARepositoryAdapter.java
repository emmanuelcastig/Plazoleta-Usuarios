package co.com.pragma.jpa;

import co.com.pragma.jpa.entity.UsuarioEntity;
import co.com.pragma.jpa.helper.AdapterOperations;
import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JPARepositoryAdapter extends AdapterOperations<Usuario, UsuarioEntity, Long, JPARepository>
implements UsuarioRepository
{

    public JPARepositoryAdapter(JPARepository repository, ObjectMapper mapper, PasswordEncoder passwordEncoder, PasswordService passwordService) {
        super(repository, mapper, d -> mapper.map(d, Usuario.class));
    }

    @Override
    public void crearUsuario(Usuario usuario) {
        repository.save(toData(usuario));
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorCorreo(String correo) {
        return repository.findByCorreo(correo).map(this::toEntity);
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return repository.findById(id).map(this::toEntity);
    }

}
