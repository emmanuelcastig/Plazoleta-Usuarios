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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {

    private final PropietarioUseCase propietarioUseCase;
    private final PropietarioMapper propietarioMapper;


    @PostMapping(path = "/propietarios")
    public ResponseEntity crearPropietario(@RequestBody @Valid PropietarioRequest propietarioRequest) {
        log.info("Iniciando creacion de propietario: {}", propietarioRequest.getNombre());
        propietarioUseCase.crearPropietario(propietarioMapper.toDomain(propietarioRequest));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
