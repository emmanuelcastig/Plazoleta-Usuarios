package co.com.pragma.jpa;
import co.com.pragma.jpa.entity.EmpleadoEntity;
import co.com.pragma.jpa.helper.AdapterOperations;
import co.com.pragma.model.empleado.Empleado;
import co.com.pragma.model.empleado.gateways.EmpleadoRepository;
import co.com.pragma.model.propietario.gateways.PasswordService;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EmpleadoJPARepositoryAdapter extends AdapterOperations<Empleado, EmpleadoEntity, Long, EmpleadoJPARepository>
implements EmpleadoRepository
{

    public EmpleadoJPARepositoryAdapter(EmpleadoJPARepository repository, ObjectMapper mapper,
                                        PasswordEncoder passwordEncoder, PasswordService passwordService) {
        super(repository, mapper, d -> mapper.map(d, Empleado.class));
    }


    @Override
    public void crearEmpleado(Empleado empleado) {
        repository.save(toData(empleado));
    }

    @Override
    public Optional<Empleado> buscarEmpleadoPorCorreo(String correo) {
        return repository.findByCorreo(correo).map(this::toEntity);
    }

    @Override
    public Optional<Empleado> buscarEmpleadoPorId(Long id) {
        return repository.findById(id).map(this::toEntity);
    }
}
