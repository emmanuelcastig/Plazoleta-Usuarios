package co.com.pragma.usecase.cliente;

import co.com.pragma.model.cliente.Cliente;
import co.com.pragma.model.cliente.gateways.ClienteRepository;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ClienteUseCaseTest {

    private PasswordService passwordService;
    private ClienteRepository clienteRepository;
    private ClienteUseCase clienteUseCase;

    @BeforeEach
    void setUp() {
        passwordService = mock(PasswordService.class);
        clienteRepository = mock(ClienteRepository.class);
        clienteUseCase = new ClienteUseCase(passwordService, clienteRepository);
    }

    @Test
    void crearCliente_debeEncriptarClave_asignarRolYGuardar() {

        Cliente cliente = new Cliente();
        cliente.setNombre("Juan");
        cliente.setClave("1234");

        when(passwordService.encode("1234")).thenReturn("hashed_1234");

        clienteUseCase.crearCliente(cliente);

        ArgumentCaptor<Cliente> captor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository).crearCliente(captor.capture());

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

        verify(clienteRepository, never()).crearCliente(any());
    }
}
