package co.com.pragma.api;

import co.com.pragma.api.dto.ClienteRequest;
import co.com.pragma.api.mapper.ClienteMapper;
import co.com.pragma.usecase.cliente.ClienteUseCase;
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
@Tag(name = "Clientes", description = "Operaciones relacionadas con la gestión de clientes")
public class ClienteRest {

    private final ClienteUseCase clienteUseCase;
    private final ClienteMapper clienteMapper;

    @Operation(
            summary = "Crear un cliente",
            description = "Permite registrar un nuevo cliente en el sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
            }
    )
    @PostMapping(path = "/clientes")
    public ResponseEntity<Void> crearCliente(
            @RequestBody(
                    description = "Datos del nuevo cliente",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ClienteRequest.class))
            )
            @org.springframework.web.bind.annotation.RequestBody @Valid ClienteRequest clienteRequest
    ) {
        log.info("Iniciando creacion de cliente");
        clienteUseCase.crearCliente(clienteMapper.toDomain(clienteRequest));
        log.info("Cliente creado con exito");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
