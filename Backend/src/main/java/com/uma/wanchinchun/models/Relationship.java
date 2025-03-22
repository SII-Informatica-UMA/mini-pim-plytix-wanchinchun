package com.uma.wanchinchun.models;

import jakarta.persistence.*;

@Entity
@Table(name = "relaciones")
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String description;
}
