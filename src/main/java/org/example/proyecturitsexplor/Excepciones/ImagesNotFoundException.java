package org.example.proyecturitsexplor.Excepciones;

public class ImagesNotFoundException  extends RuntimeException{
    private Long id;

    public ImagesNotFoundException(Long id) {
        super("Images no encontrado: " + id);
        this.id = id;

    }
    public Long getId() {
        return id;
    }
}
