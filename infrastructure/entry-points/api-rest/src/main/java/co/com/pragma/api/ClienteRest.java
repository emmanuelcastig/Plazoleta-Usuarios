package co.com.pragma.api;

import co.com.pragma.api.dto.ClienteRequest;
import co.com.pragma.api.dto.EmpleadoRequest;
import co.com.pragma.api.mapper.ClienteMapper;
import co.com.pragma.api.mapper.EmpleadoMapper;
import co.com.pragma.usecase.cliente.ClienteUseCase;
import co.com.pragma.usecase.empleado.EmpleadoUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ClienteRest {
    private final ClienteUseCase clienteUseCase;
    private final ClienteMapper clienteMapper;

    @PostMapping(path = "/clientes")
    public ResponseEntity<Void> crearCliente(@RequestBody @Valid ClienteRequest clienteRequest) {
        log.info("Iniciando creacion de cliente");
        clienteUseCase.crearCliente(clienteMapper.toDomain(clienteRequest));
        log.info("Cliente creado con exito");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
