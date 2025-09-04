package co.com.pragma.model.empleado.gateways;

import co.com.pragma.model.empleado.Empleado;

import java.util.Optional;

public interface EmpleadoRepository {
    void crearEmpleado(Empleado empleado);
    Optional<Empleado> buscarEmpleadoPorCorreo(String correo);
}
