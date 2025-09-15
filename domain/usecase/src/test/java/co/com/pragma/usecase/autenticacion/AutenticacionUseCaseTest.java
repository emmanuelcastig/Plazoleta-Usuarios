
package co.com.pragma.usecase.autenticacion;

import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import co.com.pragma.model.usuarioLogin.UsuarioLogin;
import co.com.pragma.usecase.exception.CredencialesInvalidasException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AutenticacionUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private PasswordService passwordService;
    private AutenticacionUseCase autenticacionUseCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        passwordService = mock(PasswordService.class);
        autenticacionUseCase = new AutenticacionUseCase(usuarioRepository, passwordService);
    }

    @Test
    void login_debeAutenticarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCorreo("test@correo.com");
        usuario.setClave("hashed");
        usuario.setRol(Roles.PROPIETARIO);

        when(usuarioRepository.buscarUsuarioPorCorreo("test@correo.com"))
                .thenReturn(Optional.of(usuario));
        when(passwordService.matches("1234", "hashed")).thenReturn(true);

        UsuarioLogin usuarioLogin = autenticacionUseCase.login("test@correo.com", "1234");

        assertThat(usuarioLogin.getId()).isEqualTo(1L);
        assertThat(usuarioLogin.getRol()).isEqualTo(Roles.PROPIETARIO);
    }

    @Test
    void login_debeFallarCuandoCredencialesInvalidas() {
        when(usuarioRepository.buscarUsuarioPorCorreo("fail@correo.com"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> autenticacionUseCase.login("fail@correo.com", "wrong"))
                .isInstanceOf(CredencialesInvalidasException.class)
                .hasMessage("Credenciales inválidas");
    }
}
