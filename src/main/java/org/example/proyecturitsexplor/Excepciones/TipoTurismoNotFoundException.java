package org.example.proyecturitsexplor.Excepciones;

public class TipoTurismoNotFoundException extends RuntimeException{
    private Long id;

    public TipoTurismoNotFoundException(Long id){
        super("TipoTurismo no encontrado: " + id);
        this.id = id;
    }
    public Long getId(){return id;}
}