package org.example.proyecturitsexplor.Excepciones;

public class AtracionPrincipalNotFoundException extends RuntimeException {
    private Long id;

    public AtracionPrincipalNotFoundException(Long id) {
        super("Atración Principal no encontrado: " + id);
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
