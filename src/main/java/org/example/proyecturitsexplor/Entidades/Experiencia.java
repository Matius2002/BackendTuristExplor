package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "experiencia")
public class Experiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Relación con Destinos
    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false)
    private Destinos destino;

    // Relación con Usuarios
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuarios usuario;

    @Column(name = "calificacion", nullable = false)
    private String calificacion;

    @Column(name = "comentario")
    private String comentario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") // Especifica el formato de fecha
    @Column(name = "fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    // Constructores
    public Experiencia() {}

    public Experiencia(Destinos destino, Usuarios usuario, String calificacion, String comentario, Date fecha) {
        this.destino = destino;
        this.usuario = usuario;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Destinos getDestino() {
        return destino;
    }

    public void setDestino(Destinos destino) {
        this.destino = destino;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    // Método toString()
    @Override
    public String toString() {
        return "Experiencia{" +
                "id=" + id +
                ", destino=" + destino +
                ", usuario=" + usuario +
                ", calificacion=" + calificacion +
                ", comentario='" + comentario + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
