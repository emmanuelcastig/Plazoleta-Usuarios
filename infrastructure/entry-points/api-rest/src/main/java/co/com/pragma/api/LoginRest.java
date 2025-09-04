package co.com.pragma.api;

import co.com.pragma.api.dto.LoginRequest;
import co.com.pragma.api.dto.LoginResponse;
import co.com.pragma.api.security.JwtProvider;
import co.com.pragma.model.propietario.Propietario;
import co.com.pragma.usecase.propietario.autenticacion.AutenticacionUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class LoginRest {

    private final AutenticacionUseCase autenticacionUseCase;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            Propietario propietario = autenticacionUseCase.login(loginRequest.getCorreo(), loginRequest.getClave());

            String token = jwtProvider.generateToken(propietario.getId(), propietario.getRol().name());

            LoginResponse response = new LoginResponse(token, propietario.getId(), propietario.getRol());
            log.info("Login exitoso con el id: {} y el rol: {}", propietario.getId(), response.getRol());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error en login: {}", e.getMessage());
            return ResponseEntity.status(401).build();
        }
    }
}
