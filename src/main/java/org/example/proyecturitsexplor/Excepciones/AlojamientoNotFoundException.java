package org.example.proyecturitsexplor.Excepciones;

public class AlojamientoNotFoundException extends RuntimeException{
    private Long id;

    public AlojamientoNotFoundException(Long id) {
        super("Alojamiento no encontrado: " + id);
        this.id = id;

    }
    public Long getId() {
        return id;
    }
}
