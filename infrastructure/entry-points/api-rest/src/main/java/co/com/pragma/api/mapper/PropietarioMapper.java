package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.PropietarioRequest;
import co.com.pragma.model.propietario.Propietario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropietarioMapper {
    Propietario toDomain(PropietarioRequest request);
}
