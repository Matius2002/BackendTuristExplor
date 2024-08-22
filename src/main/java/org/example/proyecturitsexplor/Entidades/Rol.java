package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_permisos",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id")
    )
    private List<Permiso> permisos=new ArrayList<>();

    // Constructores
    public Rol() {}

    public Rol(String rolName, String rolDescripc, Date rolFechaCreac, Date rolFechaModic) {
        this.rolName = rolName;
        this.rolDescripc = rolDescripc;
        this.rolFechaCreac = rolFechaCreac;
        this.rolFechaModic = rolFechaModic;
        // Inicialice permission para evitar NullPointerException
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

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public List<Permiso> getPermisos() {
        return permisos;
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
