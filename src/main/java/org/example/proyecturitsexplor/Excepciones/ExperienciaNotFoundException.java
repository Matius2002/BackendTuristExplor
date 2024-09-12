package org.example.proyecturitsexplor.Excepciones;

public class ExperienciaNotFoundException extends RuntimeException{
    private Long id;

    public ExperienciaNotFoundException(Long id) {
        super("No se pudo encontrar la experiencia con el ID: " + id);
        this.id = id;

    }
    public Long getId() {
        return id;
    }
}
