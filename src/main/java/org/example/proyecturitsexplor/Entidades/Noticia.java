package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "noticia")
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "titulo", nullable = false)
    private String titulo;
    @Column(name = "contenido", nullable = false)
    private String contenido;
    @Column(name = "fecha_publicacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublicacion;
    @Column(name = "fuente", nullable = false)
    private String fuente;

    // Relación con TipoTurismo
    @ManyToOne
    @JoinColumn(name = "id_tipo")
    private TipoTurismo tipoTurismo;

    // Relación con imágenes
    @ManyToMany
    @JoinTable(
            name = "noticia_images",
            joinColumns = @JoinColumn(name = "noticia_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private Set<Images> images = new HashSet<>();

    // Constructores
    public Noticia() {}

    public Noticia(String titulo, String contenido, Date fechaPublicacion, String fuente) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaPublicacion = fechaPublicacion;
        this.fuente = fuente;
        this.images = new HashSet<>();

    }

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getContenido() {
        return contenido;
    }
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    public String getFuente() {
        return fuente;
    }
    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
    public Set<Images> getImages() {
        return images;
    }
    public void setImages(Set<Images> images) {
        this.images = images;
    }
    public TipoTurismo getTipoTurismo() {
        return tipoTurismo;
    }
    public void setTipoTurismo(TipoTurismo tipoTurismo) {
        this.tipoTurismo = tipoTurismo;
    }
    // Método toString()
    @Override
    public String toString() {
        return "Noticia{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", fechaPublicacion=" + fechaPublicacion +
                ", fuente='" + fuente + '\'' +
                ", images=" + images +
                //", tipoTurismo=" + tipoTurismo +
                '}';
    }
}
