package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "permiso")

public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "permisos", fetch = FetchType.LAZY)
    private Set<Rol> roles;

    // Constructores
    public Permiso() {}

    public Permiso(String name) {
        this.name = name;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    // Método toString()
    @Override
    public String toString() {
        return "Permiso{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", roles=" + roles +
                '}';
    }
}
