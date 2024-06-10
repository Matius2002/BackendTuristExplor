package org.example.proyecturitsexplor.Excepciones;

public class TipoAlojamientoNotFoundException extends RuntimeException{
    private Long id;
    public TipoAlojamientoNotFoundException(Long id) {
        super("tipo de alojamiento no encontrado: " + id);
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}