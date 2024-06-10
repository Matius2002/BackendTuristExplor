package org.example.proyecturitsexplor.Excepciones;

public class EpocaVisitarNotFoundException extends RuntimeException{
    private Long id;

    public EpocaVisitarNotFoundException(Long id) {
        super("Epoca Visitar no encontrado: " + id);
        this.id = id;

    }
    public Long getId() {
        return id;
    }
}
