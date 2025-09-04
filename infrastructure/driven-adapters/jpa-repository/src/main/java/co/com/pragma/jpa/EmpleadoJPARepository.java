package co.com.pragma.jpa;
import co.com.pragma.jpa.entity.EmpleadoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface EmpleadoJPARepository extends CrudRepository<EmpleadoEntity, Long>,
        QueryByExampleExecutor<EmpleadoEntity> {
    Optional<EmpleadoEntity> findByCorreo(String correo);
}
