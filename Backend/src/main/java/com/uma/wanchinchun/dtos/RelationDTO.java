package com.uma.wanchinchun.dtos;

import lombok.*;

import java.io.Serializable;
@Builder
public class RelationDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    // Constructor vacio
    public RelationDTO() {
    }

    public RelationDTO(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "RelationDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
    /*public RelationDTO builder() {
        return new RelationDTO(id, nombre, descripcion);
    }*/
    /*public static class Builder {
        private Long id;
        private String nombre;
        private String description;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public RelationDTO Builder() {
            return new RelationDTO(id, nombre, description);
        }
    }

    public static Builder builder() {
        return new Builder();
    }*/
}