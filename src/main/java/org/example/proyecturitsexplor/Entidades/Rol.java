package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "rol_name", nullable = false)
    private String rolName;

    @Column(name = "rol_descripc")
    private String rolDescripc;

    @Column(name = "rol_fecha_creac", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rolFechaCreac;

    @Column(name = "rol_fecha_modic", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rolFechaModic;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roles_permisos",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    private Set<Permiso> permisos;

    // Constructores
    public Rol() {}

    public Rol(String rolName, String rolDescripc, Date rolFechaCreac, Date rolFechaModic) {
        this.rolName = rolName;
        this.rolDescripc = rolDescripc;
        this.rolFechaCreac = rolFechaCreac;
        this.rolFechaModic = rolFechaModic;
        this.permisos = new HashSet<>();  // Inicialice permission para evitar NullPointerException
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    public String getRolDescripc() {
        return rolDescripc;
    }

    public void setRolDescripc(String rolDescripc) {
        this.rolDescripc = rolDescripc;
    }

    public Date getRolFechaCreac() {
        return rolFechaCreac;
    }

    public void setRolFechaCreac(Date rolFechaCreac) {
        this.rolFechaCreac = rolFechaCreac;
    }

    public Date getRolFechaModic() {
        return rolFechaModic;
    }

    public void setRolFechaModic(Date rolFechaModic) {
        this.rolFechaModic = rolFechaModic;
    }

    // Método toString()
    @Override
    public String toString() {
        return "Rol{" +
                "id=" + id +
                ", rolName='" + rolName + '\'' +
                ", rolDescripc='" + rolDescripc + '\'' +
                ", rolFechaCreac=" + rolFechaCreac +
                ", rolFechaModic=" + rolFechaModic +
                '}';
    }

}
