package co.com.pragma.usecase.cliente;

import co.com.pragma.model.cliente.Cliente;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.usuario.gateways.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ClienteUseCaseTest {

    private PasswordService passwordService;
    private UsuarioRepository usuarioRepository;
    private ClienteUseCase clienteUseCase;

    @BeforeEach
    void setUp() {
        passwordService = mock(PasswordService.class);
        usuarioRepository = mock(UsuarioRepository.class);
        clienteUseCase = new ClienteUseCase(passwordService, usuarioRepository);
    }

    @Test
    void crearCliente_debeEncriptarClave_asignarRolYGuardar() {

        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setClave("1234");

        when(passwordService.encode("1234")).thenReturn("hashed_1234");

        clienteUseCase.crearCliente(cliente);

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(usuarioRepository).crearUsuario(captor.capture());

        Cliente clienteGuardado = captor.getValue();
        assertThat(clienteGuardado.getClave()).isEqualTo("hashed_1234");
        assertThat(clienteGuardado.getRol()).isEqualTo(Roles.CLIENTE);

        verify(passwordService).encode("1234");
    }

    @Test
    void crearCliente_debeFallarCuandoPasswordNoSePuedeEncriptar() {

        Cliente cliente = new Cliente();
        cliente.setNombre("Ana");
        cliente.setClave("claveInvalida");

        when(passwordService.encode("claveInvalida"))
                .thenThrow(new RuntimeException("Error al encriptar clave"));

        assertThatThrownBy(() -> clienteUseCase.crearCliente(cliente))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Error al encriptar clave");

        verify(usuarioRepository, never()).crearUsuario(any());
    }
}
