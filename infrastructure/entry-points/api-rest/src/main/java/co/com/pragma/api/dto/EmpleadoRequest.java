package co.com.pragma.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmpleadoRequest {
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

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @NotBlank(message = "La clave no puede estar vacía")
    private String clave;

    @NotNull(message = "el idRestaurante no puede estar vacío")
    private Long idRestaurante;
}
