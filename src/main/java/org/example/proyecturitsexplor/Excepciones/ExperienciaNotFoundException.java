package org.example.proyecturitsexplor.Excepciones;

public class ExperienciaNotFoundException extends RuntimeException{
    private Long id;

    public ExperienciaNotFoundException(Long id) {
        super("Experiencia no encontrado: " + id);
        this.id = id;

    }
    public Long getId() {
        return id;
    }
}
