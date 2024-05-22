package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "experiencia")
public class Experiencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
