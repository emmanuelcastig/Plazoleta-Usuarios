package co.com.pragma.jpa;

import co.com.pragma.jpa.entity.PropietarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface JPARepository extends CrudRepository<PropietarioEntity, Long>,
        QueryByExampleExecutor<PropietarioEntity> {
    Optional<PropietarioEntity> findByCorreo(String correo);
}
