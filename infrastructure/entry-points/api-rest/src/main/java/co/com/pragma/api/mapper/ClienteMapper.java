package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.ClienteRequest;
import co.com.pragma.api.dto.EmpleadoRequest;
import co.com.pragma.model.cliente.Cliente;
import co.com.pragma.model.empleado.Empleado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente toDomain(ClienteRequest request);
}
