package org.example.proyecturitsexplor.Excepciones;

public class NoticiasNotFoundException extends RuntimeException{
    private Long id;

    public NoticiasNotFoundException(Long id) {
        super("Noticias no encontrado: " + id);
        this.id = id;

    }
    public Long getId() {
        return id;
    }
}
