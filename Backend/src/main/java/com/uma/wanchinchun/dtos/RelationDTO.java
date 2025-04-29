package com.uma.wanchinchun.dtos;

import java.io.Serializable;

public class RelationDTO {
    private Long id;
    private String nombre;

    // Constructor
    public RelationDTO() {
    }

    public RelationDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
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

    @Override
    public String toString() {
        return "RelationDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}