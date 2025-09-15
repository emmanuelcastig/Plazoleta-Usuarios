package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.ClienteRequest;
import co.com.pragma.api.dto.EmpleadoRequest;
import co.com.pragma.model.cliente.Cliente;
import co.com.pragma.model.empleado.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "idRestaurante", ignore = true)
    @Mapping(target = "fechaNacimiento", ignore = true)
    Cliente toDomain(ClienteRequest request);
}