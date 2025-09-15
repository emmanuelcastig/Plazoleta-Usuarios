package co.com.pragma.usecase.empleado;

import co.com.pragma.model.empleado.Empleado;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class EmpleadoUseCaseTest {

    private PasswordService passwordService;
    private UsuarioRepository usuarioRepository;
    private EmpleadoUseCase empleadoUseCase;

    @BeforeEach
    void setUp() {
        passwordService = mock(PasswordService.class);
        usuarioRepository = mock(UsuarioRepository.class);
        empleadoUseCase = new EmpleadoUseCase(passwordService, usuarioRepository);
    }

    @Test
    void crearEmpleado_debeEncriptarClaveYAsignarRol() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Carlos");
        empleado.setClave("1234");

        when(passwordService.encode("1234")).thenReturn("encriptada123");

        empleadoUseCase.crearEmpleado(empleado);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).crearUsuario(captor.capture());

        Usuario empleadoGuardado = captor.getValue();
        assertThat(empleadoGuardado.getClave()).isEqualTo("encriptada123");
        assertThat(empleadoGuardado.getRol()).isEqualTo(Roles.EMPLEADO);
    }

    @Test
    void crearEmpleado_debeFallarCuandoPasswordNoSePuedeEncriptar() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Laura");
        empleado.setClave("claveInvalida");

        when(passwordService.encode("claveInvalida"))
                .thenThrow(new RuntimeException("Error al encriptar clave"));

        assertThatThrownBy(() -> empleadoUseCase.crearEmpleado(empleado))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al encriptar clave");

        verify(usuarioRepository, never()).crearUsuario(any());
    }

    @Test
    void buscarEmpleadoPorId_debeRetornarEmpleadoCuandoExiste() {
        Empleado empleado = new Empleado();
        empleado.setId(1L);
        empleado.setNombre("Ana");

        when(usuarioRepository.buscarUsuarioPorId(1L))
                .thenReturn(Optional.of(empleado));

        Usuario resultado = empleadoUseCase.buscarEmpleadoPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Ana");
        verify(usuarioRepository).buscarUsuarioPorId(1L);
    }

    @Test
    void buscarEmpleadoPorId_debeRetornarNullCuandoNoExiste() {
        when(usuarioRepository.buscarUsuarioPorId(99L))
                .thenReturn(Optional.empty());

        Usuario resultado = empleadoUseCase.buscarEmpleadoPorId(99L);

        assertThat(resultado).isNull();
        verify(usuarioRepository).buscarUsuarioPorId(99L);
    }
}
