package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destino_id", nullable = false)
    private Destinos destino;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "costo_entrada")
    private Double costoEntrada;

    @ManyToMany
    @JoinTable(
            name = "evento_images",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private Set<Images> images = new HashSet<>();

    // Constructores
    public Evento() {}

    public Evento(Destinos destino, String nombre, String descripcion, Date fechaInicio, Date fechaFin, String ubicacion, Double costoEntrada, Date fechaCreacion, Date fechaActualizacion) {
        this.destino = destino;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.ubicacion = ubicacion;
        this.costoEntrada = costoEntrada;

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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getCostoEntrada() {
        return costoEntrada;
    }

    public void setCostoEntrada(Double costoEntrada) {
        this.costoEntrada = costoEntrada;
    }


    public Set<Images> getImages() {
        return images;
    }

    public void setImages(Set<Images> images) {
        this.images = images;
    }
    // Método toString()
    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", destino=" + destino +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", ubicacion='" + ubicacion + '\'' +
                ", costoEntrada=" + costoEntrada +
                ", images=" + images +
                '}';
    }
}

