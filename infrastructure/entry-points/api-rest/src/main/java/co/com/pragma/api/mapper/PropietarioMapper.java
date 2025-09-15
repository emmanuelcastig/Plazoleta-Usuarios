package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.PropietarioRequest;
import co.com.pragma.model.propietario.Propietario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropietarioMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "idRestaurante", ignore = true)
    Propietario toDomain(PropietarioRequest request);
}