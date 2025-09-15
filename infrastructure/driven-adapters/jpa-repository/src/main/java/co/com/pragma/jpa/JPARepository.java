package co.com.pragma.jpa;
import co.com.pragma.jpa.entity.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface JPARepository extends CrudRepository<UsuarioEntity, Long>,
        QueryByExampleExecutor<UsuarioEntity> {
    Optional<UsuarioEntity> findByCorreo(String correo);
}
