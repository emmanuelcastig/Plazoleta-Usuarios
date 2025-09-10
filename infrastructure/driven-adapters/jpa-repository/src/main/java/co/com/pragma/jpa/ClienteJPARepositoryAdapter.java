package co.com.pragma.jpa;
import co.com.pragma.jpa.entity.ClienteEntity;
import co.com.pragma.jpa.helper.AdapterOperations;
import co.com.pragma.model.cliente.Cliente;
import co.com.pragma.model.cliente.gateways.ClienteRepository;
import co.com.pragma.model.propietario.gateways.PasswordService;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ClienteJPARepositoryAdapter extends AdapterOperations<Cliente, ClienteEntity, Long, ClienteJPARepository>
implements ClienteRepository
{

    public ClienteJPARepositoryAdapter(ClienteJPARepository repository, ObjectMapper mapper,
                                       PasswordEncoder passwordEncoder, PasswordService passwordService) {
        super(repository, mapper, d -> mapper.map(d, Cliente.class));
    }

    @Override
    public void crearCliente(Cliente cliente) {
        repository.save(toData(cliente));
    }

    @Override
    public Optional<Cliente> buscarClientePorCorreo(String correo) {
        return repository.findByCorreo(correo).map(this::toEntity);
    }
}
