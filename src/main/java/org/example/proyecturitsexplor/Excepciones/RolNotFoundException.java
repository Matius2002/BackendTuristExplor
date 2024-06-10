package org.example.proyecturitsexplor.Excepciones;

public class RolNotFoundException extends RuntimeException {
    private Long id;

    public RolNotFoundException(Long id) {
        super("Rol no encontrado: " + id);
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
