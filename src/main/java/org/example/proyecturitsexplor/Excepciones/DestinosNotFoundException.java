package org.example.proyecturitsexplor.Excepciones;

public class DestinosNotFoundException extends RuntimeException {
    private Long id;

    public DestinosNotFoundException(Long id) {
        super("Destino no encontrado: " + id);
        this.id = id;

    }
    public Long getId() {
        return id;
    }
}
