package org.example.proyecturitsexplor.Entidades;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
public class Auditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accion_realizada", nullable = false)
    private String accionRealizada;

    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    // Getters y Setters
}
