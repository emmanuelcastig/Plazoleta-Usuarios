package co.com.pragma.model.usuario;
import co.com.pragma.model.propietario.enums.Roles;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Usuario {
    private Long id;
    private String correo;
    private String clave;
    private Roles rol;
}
