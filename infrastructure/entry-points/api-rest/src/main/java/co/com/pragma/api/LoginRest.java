package co.com.pragma.api;

import co.com.pragma.api.dto.LoginRequest;
import co.com.pragma.api.dto.LoginResponse;
import co.com.pragma.api.security.JwtProvider;
import co.com.pragma.model.usuarioLogin.UsuarioLogin;
import co.com.pragma.usecase.autenticacion.AutenticacionUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Tag(name = "Autenticación", description = "Operaciones relacionadas con el inicio de sesión y generación de JWT")
public class LoginRest {

    private final AutenticacionUseCase autenticacionUseCase;
    private final JwtProvider jwtProvider;

    @Operation(
            summary = "Iniciar sesión",
            description = "Permite a un usuario autenticarse en el sistema. Si las credenciales son válidas, devuelve un token JWT junto con la información básica del usuario.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content)
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody(
                    description = "Credenciales de inicio de sesión (correo y clave)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequest.class))
            )
            @org.springframework.web.bind.annotation.RequestBody LoginRequest loginRequest
    ) {
        UsuarioLogin usuarioLogin = autenticacionUseCase.login(loginRequest.getCorreo(), loginRequest.getClave());

        String token = jwtProvider.generateToken(usuarioLogin.getId(), usuarioLogin.getRol().name());

        LoginResponse response = new LoginResponse(token, usuarioLogin.getId(), usuarioLogin.getRol());
        log.info("Login exitoso con el id: {} y el rol: {}", usuarioLogin.getId(), response.getRol());
        return ResponseEntity.ok(response);
    }
}
