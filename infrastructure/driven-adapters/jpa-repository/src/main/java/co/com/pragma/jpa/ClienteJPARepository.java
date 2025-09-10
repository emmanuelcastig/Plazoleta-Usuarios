package co.com.pragma.jpa;
import co.com.pragma.jpa.entity.ClienteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface ClienteJPARepository extends CrudRepository<ClienteEntity, Long>,
        QueryByExampleExecutor<ClienteEntity> {
    Optional<ClienteEntity> findByCorreo(String correo);
}
