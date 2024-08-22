package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Visita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rutaVisitada;
    private LocalDateTime fechaHoraVisita;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRutaVisitada() {
        return rutaVisitada;
    }

    public void setRutaVisitada(String rutaVisitada) {
        this.rutaVisitada = rutaVisitada;
    }

    public LocalDateTime getFechaHoraVisita() {
        return fechaHoraVisita;
    }

    public void setFechaHoraVisita(LocalDateTime fechaHoraVisita) {
        this.fechaHoraVisita = fechaHoraVisita;
    }
}

