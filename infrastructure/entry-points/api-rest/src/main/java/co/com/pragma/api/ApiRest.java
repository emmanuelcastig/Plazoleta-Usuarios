package co.com.pragma.api;

import co.com.pragma.api.dto.PropietarioRequest;
import co.com.pragma.api.mapper.PropietarioMapper;
import co.com.pragma.usecase.propietario.PropietarioUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Tag(name = "Propietarios", description = "Operaciones relacionadas con la gestión de propietarios de restaurantes")
public class ApiRest {

    private final PropietarioUseCase propietarioUseCase;
    private final PropietarioMapper propietarioMapper;

    @Operation(
            summary = "Crear un propietario",
            description = "Permite registrar un nuevo propietario en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
            }
    )
    @PostMapping(path = "/propietarios")
    public ResponseEntity<Void> crearPropietario(
            @RequestBody(
                    description = "Datos del nuevo propietario",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PropietarioRequest.class))
            )
            @org.springframework.web.bind.annotation.RequestBody @Valid PropietarioRequest propietarioRequest
    ) {
        log.info("Iniciando creacion de propietario: {}", propietarioRequest.getNombre());
        propietarioUseCase.crearPropietario(propietarioMapper.toDomain(propietarioRequest));
        log.info("Propietario creado con exito: {}", propietarioRequest.getNombre());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Verificar existencia de propietario",
            description = "Verifica si un propietario existe en el sistema por su ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta exitosa (true = existe, false = no existe)",
                            content = @Content(schema = @Schema(implementation = Boolean.class))),
                    @ApiResponse(responseCode = "400", description = "Parámetros inválidos", content = @Content)
            }
    )
    @GetMapping(path = "/propietarios/{id}")
    public ResponseEntity<Boolean> obtenerPropietario(
            @Parameter(description = "ID del propietario a verificar", example = "5", required = true)
            @PathVariable("id") Long id
    ) {
        log.info("Verificando existencia de propietario con id: {}", id);
        boolean existe = propietarioUseCase.propietarioExiste(id);
        log.info("Existencia de propietario: {}", existe);
        return ResponseEntity.ok(existe);
    }
}
