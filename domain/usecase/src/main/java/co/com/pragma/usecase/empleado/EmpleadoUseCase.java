package co.com.pragma.usecase.empleado;

import co.com.pragma.model.empleado.Empleado;
import co.com.pragma.model.empleado.gateways.EmpleadoRepository;
import co.com.pragma.model.propietario.enums.Roles;
import co.com.pragma.model.propietario.gateways.PasswordService;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class EmpleadoUseCase {
    private final PasswordService passwordService;
    private final EmpleadoRepository empleadoRepository;

    public void crearEmpleado(Empleado empleado) {
        empleado.setClave(passwordService.encode(empleado.getClave()));
        empleado.setRol(Roles.EMPLEADO);
        empleadoRepository.crearEmpleado(empleado);
    }
}
