package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "destino")
public class Destinos {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "destinoName")
    private String destinoName;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "tipoTurismo")
    private String tipoTurismo;

    @Column(name = "atraccionesPrincipales")
    private String atraccionesPrincipales;

    @Column(name = "epocaVisitar")
    private String epocaVisitar;

    @Column(name = "imagenes")
    private String imagenes;

    @Column(name = "fechaCreacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date fechaCreacion;

    @Column(name = "horaCreacion")
    private String horaCreacion;

    @Column(name = "fechaActualizacion" , columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date fechaActualizacion;

    @Column(name = "horaActualizacion")
    private String horaActualizacion;

    // Constructores
    public Destinos() {}

    public Destinos(String destinoName, String descripcion, String ubicacion, String tipoTurismo,
                    String atraccionesPrincipales, String epocaVisitar, String imagenes,
                    Date fechaCreacion, String horaCreacion, Date fechaActualizacion,
                    String horaActualizacion) {
        this.destinoName = destinoName;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.tipoTurismo = tipoTurismo;
        this.atraccionesPrincipales = atraccionesPrincipales;
        this.epocaVisitar = epocaVisitar;
        this.imagenes = imagenes;
        this.fechaCreacion = fechaCreacion;
        this.horaCreacion = horaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.horaActualizacion = horaActualizacion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestinoName() {
        return destinoName;
    }

    public void setDestinoName(String destinoName) {
        this.destinoName = destinoName;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTipoTurismo() {
        return tipoTurismo;
    }

    public void setTipoTurismo(String tipoTurismo) {
        this.tipoTurismo = tipoTurismo;
    }

    public String getAtraccionesPrincipales() {
        return atraccionesPrincipales;
    }

    public void setAtraccionesPrincipales(String atraccionesPrincipales) {
        this.atraccionesPrincipales = atraccionesPrincipales;
    }

    public String getEpocaVisitar() {
        return epocaVisitar;
    }

    public void setEpocaVisitar(String epocaVisitar) {
        this.epocaVisitar = epocaVisitar;
    }

    public String getImagenes() {
        return imagenes;
    }

    public void setImagenes(String imagenes) {
        this.imagenes = imagenes;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(String horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getHoraActualizacion() {
        return horaActualizacion;
    }

    public void setHoraActualizacion(String horaActualizacion) {
        this.horaActualizacion = horaActualizacion;
    }

    // Método toString()
    @Override
    public String toString() {
        return "Destinos{" +
                "id=" + id +
                ", destinoName='" + destinoName + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", tipoTurismo='" + tipoTurismo + '\'' +
                ", atraccionesPrincipales='" + atraccionesPrincipales + '\'' +
                ", epocaVisitar='" + epocaVisitar + '\'' +
                ", imagenes='" + imagenes + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", horaCreacion=" + horaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                ", horaActualizacion=" + horaActualizacion +
                '}';
    }
}
