package com.uma.wanchinchun.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "relaciones")
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    private String description;

    public Relationship() {}

    public Relationship(Long id, String nombre, String description) {
        this.id = id;
        this.nombre = nombre;
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return id.equals(that.id) && nombre.equals(that.nombre) && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, description);
    }
}
