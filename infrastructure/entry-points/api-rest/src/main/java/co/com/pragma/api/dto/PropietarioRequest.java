package co.com.pragma.api.dto;

import co.com.pragma.model.propietario.enums.Roles;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PropietarioRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @NotBlank(message = "El documento de identidad no puede estar vacío")
    @Pattern(regexp = "^[0-9]+$", message = "El documento de identidad debe contener solo números")
    private String documentoIdentidad;

    @NotBlank(message = "El número de celular no puede estar vacío")
    @Pattern(
            regexp = "^\\+?[0-9]{1,13}$",
            message = "El celular debe tener máximo 13 dígitos y puede comenzar con +. Ejemplo: +573005698325"
    )
    private String celular;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "La clave no puede estar vacía")
    private String clave;

}
