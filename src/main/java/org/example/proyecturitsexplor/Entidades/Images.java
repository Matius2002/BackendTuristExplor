package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;
@Entity
@Table(name = "images")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ruta", nullable = false)
    private String ruta;

    @Column(name = "activa", nullable = false)
    private boolean activa;



    // Constructores (Vacio y Cargado)
    public Images() {
    }

    public Images(String nombre, String ruta, boolean activa) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.activa = activa;
    }


    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }


    // Método toString()
    @Override
    public String toString() {
        return "Images{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ruta='" + ruta + '\'' +
                ", activa=" + activa +
                '}';
    }
}
