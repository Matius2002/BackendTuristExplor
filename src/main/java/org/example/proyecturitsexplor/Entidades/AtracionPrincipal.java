package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;

@Entity
@Table(name = "atracionesPrincipales")
public class AtracionPrincipal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "horario_inicio")
    private String horarioFuncionamiento;

    @Column(name = "horario_fin")
    private String horarioFin;

    // Constructores (Vacio y Cargado)
    public AtracionPrincipal() {
    }

    public AtracionPrincipal(String nombre, String descripcion, String horarioFuncionamiento, String horarioFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horarioFuncionamiento = horarioFuncionamiento;
        this.horarioFin = horarioFin;
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

    public String getHorarioFuncionamiento() {
        return horarioFuncionamiento;
    }

    public void setHorarioFuncionamiento(String horarioFuncionamiento) {
        this.horarioFuncionamiento = horarioFuncionamiento;
    }

    // Getter
    public String getHorarioFin() {
        return horarioFin;
    }

    // Setter
    public void setHorarioFin(String horarioFin) {
        this.horarioFin = horarioFin;
    }

    // Método toString()
    @Override
    public String toString() {
        return "AtraccionPrincipalServicio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", horarioFuncionamiento='" + horarioFuncionamiento + '\'' +
                ", horarioFin='" + horarioFin + '\'' +
                '}';
    }
}
