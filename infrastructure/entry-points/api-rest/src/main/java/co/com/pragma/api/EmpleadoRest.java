package co.com.pragma.api;

import co.com.pragma.api.dto.EmpleadoRequest;
import co.com.pragma.api.mapper.EmpleadoMapper;
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
public class EmpleadoRest {
    private final EmpleadoUseCase empleadoUseCase;
    private final EmpleadoMapper empleadoMapper;

    @PostMapping(path = "/empleados")
    public ResponseEntity<Void> crearEmpleado(@RequestBody @Valid EmpleadoRequest empleadoRequest) {
        log.info("Iniciando creacion de empleado");
        empleadoUseCase.crearEmpleado(empleadoMapper.toDomain(empleadoRequest));
        log.info("Empleado creado con exito");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
