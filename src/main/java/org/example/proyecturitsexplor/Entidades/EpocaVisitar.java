package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;

@Entity
@Table(name = "epocaVisitar")
public class EpocaVisitar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "clima", nullable = false)
    private String clima;

    // falta la realcion con destinos

    // Constructores (Vacio y Cargado)
    public EpocaVisitar() {
    }

    public EpocaVisitar(String nombre, String descripcion, String clima) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.clima = clima;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    // Método toString()
    @Override
    public String toString() {
        return "EpocaVisitar{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", clima='" + clima + '\'' +
                '}';
    }
}
