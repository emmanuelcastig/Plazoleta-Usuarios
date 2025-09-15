package co.com.pragma.api.mapper;

import co.com.pragma.model.usuario.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    void updateUsuarioFromDomain(Usuario source, @MappingTarget Usuario target);
}