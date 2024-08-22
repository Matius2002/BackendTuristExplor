package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
@Entity
@Table(name = "Tourist_Site")
public class TouristSite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String content;
    private String tourismType;
    private double latitude;
    private double longitude;

}
