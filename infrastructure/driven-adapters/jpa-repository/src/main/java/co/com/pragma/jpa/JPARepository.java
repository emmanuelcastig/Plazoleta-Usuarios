package co.com.pragma.jpa;

import co.com.pragma.jpa.entity.PropietarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface JPARepository extends CrudRepository<PropietarioEntity, Long>,
        QueryByExampleExecutor<PropietarioEntity> {

}
