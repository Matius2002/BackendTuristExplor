package org.example.proyecturitsexplor.Excepciones;

public class EventoNotFoundException extends RuntimeException{
    private Long id;

    public EventoNotFoundException(Long id) {
        super("Evento no encontrado: " + id);
        this.id = id;

    }
    public Long getId() {
        return id;
    }
}
