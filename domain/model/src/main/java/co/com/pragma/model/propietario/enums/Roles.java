package co.com.pragma.model.propietario.enums;

public enum Roles {
    ADMINISTRADOR("ADMINISTRADOR"),
    PROPIETARIO("PROPIETARIO"),
    EMPLEADO("EMPLEADO");

    private final String nombre;

    Roles(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

}
