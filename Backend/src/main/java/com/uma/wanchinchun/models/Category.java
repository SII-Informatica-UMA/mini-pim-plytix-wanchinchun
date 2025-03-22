package com.uma.wanchinchun.models;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product producto;
}
