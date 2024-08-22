package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "fechaCreacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date fechaCreacion;

    @Column(name = "fechaActualizacion" , columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date fechaActualizacion;

    // Relación con tipos de turismo
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "destino_tipos_turismo",
            joinColumns = @JoinColumn(name = "destino_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_turismo_id")
    )
    private Set<TipoTurismo> tipoTurismo = new HashSet<>();

    // Relación con atracciones principales
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "destino_atraciones_principales",
            joinColumns = @JoinColumn(name = "destino_id"),
            inverseJoinColumns = @JoinColumn(name = "atracion_principal_id")
    )
    private Set<AtracionPrincipal> atracionesPrincipales = new HashSet<>();

    // Relación con épocas para visitar
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "destino_epocas_visitar",
            joinColumns = @JoinColumn(name = "destino_id"),
            inverseJoinColumns = @JoinColumn(name = "epoca_visitar_id")
    )
    private Set<EpocaVisitar> epocasVisitar = new HashSet<>();

    // Relación con imágenes
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "destino_imagenes",
            joinColumns = @JoinColumn(name = "destino_id"),
            inverseJoinColumns = @JoinColumn(name = "imagen_id")
    )
    private Set<Images> imagenes = new HashSet<>();

    @ManyToMany(mappedBy = "destinos")
    private Set<Evento> eventos = new HashSet<>();

    // Constructores
    public Destinos() {}

    public Destinos(String destinoName, String descripcion, String ubicacion,
                    Date fechaCreacion, String horaCreacion, Date fechaActualizacion,
                    String horaActualizacion) {
        this.destinoName = destinoName;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;

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

    public Set<TipoTurismo> getTipoTurismo() {
        return tipoTurismo;
    }

    public void setTipoTurismo(Set<TipoTurismo> tipoTurismo) {
        this.tipoTurismo = tipoTurismo;
    }

    public Set<AtracionPrincipal> getAtracionesPrincipales() {
        return atracionesPrincipales;
    }

    public void setAtracionesPrincipales(Set<AtracionPrincipal> atracionesPrincipales) {
        this.atracionesPrincipales = atracionesPrincipales;
    }

    public Set<EpocaVisitar> getEpocasVisitar() {
        return epocasVisitar;
    }

    public void setEpocasVisitar(Set<EpocaVisitar> epocasVisitar) {
        this.epocasVisitar = epocasVisitar;
    }

    public Set<Images> getImagenes() {
        return imagenes;
    }

    public void setImagenes(Set<Images> imagenes) {
        this.imagenes = imagenes;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
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
                ", atracionesPrincipales='" + atracionesPrincipales + '\'' +
                ", epocasVisitar='" + epocasVisitar + '\'' +
                ", imagenes='" + imagenes + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
