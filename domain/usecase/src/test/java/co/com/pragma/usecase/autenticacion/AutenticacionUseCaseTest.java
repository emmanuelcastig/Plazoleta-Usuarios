package co.com.pragma.usecase.autenticacion;

import co.com.pragma.model.cliente.Cliente;
import co.com.pragma.model.cliente.gateways.ClienteRepository;
import co.com.pragma.model.empleado.Empleado;
import co.com.pragma.model.empleado.gateways.EmpleadoRepository;
import co.com.pragma.model.propietario.Propietario;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.propietario.gateways.PropietarioRepository;
import co.com.pragma.model.usuario.Usuario;
import co.com.pragma.usecase.exception.CredencialesInvalidasException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AutenticacionUseCaseTest {

    private PropietarioRepository propietarioRepository;
    private ClienteRepository clienteRepository;
    private EmpleadoRepository empleadoRepository;
    private PasswordService passwordService;
    private AutenticacionUseCase autenticacionUseCase;

    @BeforeEach
    void setUp() {
        propietarioRepository = mock(PropietarioRepository.class);
        clienteRepository = mock(ClienteRepository.class);
        empleadoRepository = mock(EmpleadoRepository.class);
        passwordService = mock(PasswordService.class);
        autenticacionUseCase = new AutenticacionUseCase(propietarioRepository, clienteRepository, empleadoRepository, passwordService);
    }

    @Test
    void login_debeAutenticarPropietario() {
        Propietario propietario = new Propietario();
        propietario.setId(1L);
        propietario.setCorreo("test@correo.com");
        propietario.setClave("hashed");
        propietario.setRol(Roles.PROPIETARIO);

        when(propietarioRepository.findByCorreo("test@correo.com")).thenReturn(Optional.of(propietario));
        when(passwordService.matches("1234", "hashed")).thenReturn(true);

        Usuario usuario = autenticacionUseCase.login("test@correo.com", "1234");

        assertThat(usuario.getId()).isEqualTo(1L);
        assertThat(usuario.getRol()).isEqualTo(Roles.PROPIETARIO);
    }

    @Test
    void login_debeAutenticarCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(2L);
        cliente.setCorreo("cliente@correo.com");
        cliente.setClave("hashed");
        cliente.setRol(Roles.CLIENTE);

        when(propietarioRepository.findByCorreo("cliente@correo.com")).thenReturn(Optional.empty());
        when(clienteRepository.buscarClientePorCorreo("cliente@correo.com")).thenReturn(Optional.of(cliente));
        when(passwordService.matches("1234", "hashed")).thenReturn(true);

        Usuario usuario = autenticacionUseCase.login("cliente@correo.com", "1234");

        assertThat(usuario.getId()).isEqualTo(2L);
        assertThat(usuario.getRol()).isEqualTo(Roles.CLIENTE);
    }

    @Test
    void login_debeAutenticarEmpleado() {
        Empleado empleado = new Empleado();
        empleado.setId(3L);
        empleado.setCorreo("empleado@correo.com");
        empleado.setClave("hashed");
        empleado.setRol(Roles.EMPLEADO);

        when(propietarioRepository.findByCorreo("empleado@correo.com")).thenReturn(Optional.empty());
        when(clienteRepository.buscarClientePorCorreo("empleado@correo.com")).thenReturn(Optional.empty());
        when(empleadoRepository.buscarEmpleadoPorCorreo("empleado@correo.com")).thenReturn(Optional.of(empleado));
        when(passwordService.matches("1234", "hashed")).thenReturn(true);

        Usuario usuario = autenticacionUseCase.login("empleado@correo.com", "1234");

        assertThat(usuario.getId()).isEqualTo(3L);
        assertThat(usuario.getRol()).isEqualTo(Roles.EMPLEADO);
    }

    @Test
    void login_debeFallarCuandoCredencialesInvalidas() {
        when(propietarioRepository.findByCorreo("fail@correo.com")).thenReturn(Optional.empty());
        when(clienteRepository.buscarClientePorCorreo("fail@correo.com")).thenReturn(Optional.empty());
        when(empleadoRepository.buscarEmpleadoPorCorreo("fail@correo.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> autenticacionUseCase.login("fail@correo.com", "wrong"))
                .isInstanceOf(CredencialesInvalidasException.class)
                .hasMessage("Credenciales inválidas");
    }
}
