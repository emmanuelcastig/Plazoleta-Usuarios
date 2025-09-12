package co.com.pragma.api;

import co.com.pragma.api.dto.EmpleadoRequest;
import co.com.pragma.api.mapper.EmpleadoMapper;
import co.com.pragma.model.empleado.Empleado;
import co.com.pragma.usecase.empleado.EmpleadoUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@Tag(name = "Empleados", description = "Operaciones relacionadas con la gestión de empleados")
public class EmpleadoRest {

    private final EmpleadoUseCase empleadoUseCase;
    private final EmpleadoMapper empleadoMapper;

    @Operation(
            summary = "Crear un empleado",
            description = "Permite registrar un nuevo empleado en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
            }
    )
    @PostMapping(path = "/empleados")
    public ResponseEntity<Void> crearEmpleado(
            @RequestBody(
                    description = "Datos del nuevo empleado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmpleadoRequest.class))
            )
            @org.springframework.web.bind.annotation.RequestBody @Valid EmpleadoRequest empleadoRequest
    ) {
        log.info("Iniciando creacion de empleado");
        empleadoUseCase.crearEmpleado(empleadoMapper.toDomain(empleadoRequest));
        log.info("Empleado creado con exito");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Obtener restaurante de un empleado",
            description = "Dado el ID de un empleado, devuelve el ID del restaurante al que pertenece. " +
                    "Si el empleado no existe o no tiene restaurante asociado, retorna 404.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ID del restaurante encontrado",
                            content = @Content(
                                    schema = @Schema(implementation = Long.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Empleado no encontrado o sin restaurante asociado",
                            content = @Content
                    )
            }
    )
    @GetMapping("/empleados/{id}/restaurante")
    public ResponseEntity<Long> obtenerRestauranteDeEmpleado(@PathVariable("id") Long id) {
        log.info("Iniciando busqueda de empleado");
        Empleado empleado = empleadoUseCase.buscarEmpleadoPorId(id);
        if (empleado == null || empleado.getIdRestaurante() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empleado.getIdRestaurante());
    }
}
