package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Visita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoTurismo;
    private LocalDateTime fechaHoraVisita;
    private String usuario;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoTurismo() {
        return tipoTurismo;
    }

    public void setTipoTurismo(String rutaVisitada) {
        this.tipoTurismo = rutaVisitada;
    }

    public LocalDateTime getFechaHoraVisita() {
        return fechaHoraVisita;
    }

    public void setFechaHoraVisita(LocalDateTime fechaHoraVisita) {
        this.fechaHoraVisita = fechaHoraVisita;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}

