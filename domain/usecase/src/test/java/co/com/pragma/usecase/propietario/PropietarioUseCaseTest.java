package co.com.pragma.usecase.propietario;

import co.com.pragma.model.propietario.Propietario;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import co.com.pragma.model.propietario.gateways.PropietarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropietarioUseCaseTest {

    private PropietarioRepository propietarioRepository;
    private PasswordService passwordService;
    private PropietarioUseCase propietarioUseCase;

    @BeforeEach
    void setUp() {
        propietarioRepository = mock(PropietarioRepository.class);
        passwordService = mock(PasswordService.class);
        propietarioUseCase = new PropietarioUseCase(propietarioRepository, passwordService);
    }

    @Test
    void crearPropietario_debeAsignarRolYGuardarCuandoEsMayorDeEdad() {
        Propietario propietario = new Propietario();
        propietario.setNombre("Ana");
        propietario.setClave("1234");
        propietario.setFechaNacimiento(LocalDate.now().minusYears(25));

        when(passwordService.encode("1234")).thenReturn("claveEncriptada");

        propietarioUseCase.crearPropietario(propietario);

        ArgumentCaptor<Propietario> captor = ArgumentCaptor.forClass(Propietario.class);
        verify(propietarioRepository).crearPropietario(captor.capture());

        Propietario guardado = captor.getValue();
        assertThat(guardado.getClave()).isEqualTo("claveEncriptada");
        assertThat(guardado.getRol()).isEqualTo(Roles.PROPIETARIO);
    }

    @Test
    void crearPropietario_debeFallarCuandoEsMenorDeEdad() {
        Propietario propietario = new Propietario();
        propietario.setNombre("Luis");
        propietario.setClave("abcd");
        propietario.setFechaNacimiento(LocalDate.now().minusYears(15)); 

        when(passwordService.encode("abcd")).thenReturn("claveEncriptada");

        assertThatThrownBy(() -> propietarioUseCase.crearPropietario(propietario))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El propietario debe ser mayor de edad");

        verify(propietarioRepository, never()).crearPropietario(any());
    }

    @Test
    void propietarioExiste_debeRetornarTrueCuandoExisteConRolPropietario() {
        Propietario propietario = new Propietario();
        propietario.setId(1L);
        propietario.setRol(Roles.PROPIETARIO);

        when(propietarioRepository.propietarioExiste(1L)).thenReturn(Optional.of(propietario));

        boolean existe = propietarioUseCase.propietarioExiste(1L);

        assertThat(existe).isTrue();
    }

    @Test
    void propietarioExiste_debeRetornarFalseCuandoNoExiste() {
        when(propietarioRepository.propietarioExiste(1L)).thenReturn(Optional.empty());

        boolean existe = propietarioUseCase.propietarioExiste(1L);

        assertThat(existe).isFalse();
    }
}
