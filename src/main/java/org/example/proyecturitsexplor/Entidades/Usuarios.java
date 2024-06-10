package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fecha_registro", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date fechaRegistro;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Rol> roles;

    // Constructores
    public Usuarios() {}

    public Usuarios(String nombreUsuario, String email, String password, Date fechaRegistro) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.password = password;
        this.fechaRegistro = fechaRegistro;
        this.roles = new HashSet<>();  // Inicialice permission para evitar NullPointerException
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Set<Rol> getRoles() {
        return roles != null ? roles : new HashSet<>();
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    // Método toString()
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                //", rol='" + roles + '\'' +
                '}';
    }
}
