package org.example.proyecturitsexplor.Entidades;

import jakarta.persistence.*;
@Entity
@Table(name = "Tourist_Site")
public class TouristSite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @SuppressWarnings("unused")
    private String title;
    @SuppressWarnings("unused")
    private String content;
    @SuppressWarnings("unused")
    private String tourismType;
    @SuppressWarnings("unused")
    private double latitude;
    @SuppressWarnings("unused")
    private double longitude;

}
