package co.com.pragma.usecase.empleado;

import co.com.pragma.model.empleado.Empleado;
import co.com.pragma.model.empleado.gateways.EmpleadoRepository;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class EmpleadoUseCaseTest {

    private PasswordService passwordService;
    private EmpleadoRepository empleadoRepository;
    private EmpleadoUseCase empleadoUseCase;

    @BeforeEach
    void setUp() {
        passwordService = mock(PasswordService.class);
        empleadoRepository = mock(EmpleadoRepository.class);
        empleadoUseCase = new EmpleadoUseCase(passwordService, empleadoRepository);
    }

    @Test
    void crearEmpleado_debeEncriptarClaveYAsignarRol() {
        Empleado empleado = new Empleado();
        empleado.setNombre("Carlos");
        empleado.setClave("1234");

        when(passwordService.encode("1234")).thenReturn("encriptada123");

        empleadoUseCase.crearEmpleado(empleado);

        ArgumentCaptor<Empleado> captor = ArgumentCaptor.forClass(Empleado.class);
        verify(empleadoRepository).crearEmpleado(captor.capture());

        Empleado empleadoGuardado = captor.getValue();
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

        verify(empleadoRepository, never()).crearEmpleado(any());
    }
}
