package co.com.pragma.model.usuario.gateways;

import co.com.pragma.model.usuario.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    void crearUsuario(Usuario usuario);
    Optional<Usuario> buscarUsuarioPorCorreo(String correo);
    Optional<Usuario> buscarUsuarioPorId(Long id);
}
