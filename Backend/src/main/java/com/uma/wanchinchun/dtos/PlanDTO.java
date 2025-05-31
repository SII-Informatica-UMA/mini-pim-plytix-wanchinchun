package com.uma.wanchinchun.dtos;

import java.util.Objects;

public class PlanDTO {
    private Long id;
    private String nombre;
    private Integer maxProductos;
    private Integer maxActivos;
    private Integer maxAlmacenamiento;
    private Integer maxCategoriasProductos;
    private Integer maxCategoriasActivos;
    private Integer maxRelaciones;
    private Integer precio;

    public PlanDTO() {}

    public PlanDTO(Long id, String nombre, Integer maxProductos, Integer maxActivos, Integer maxAlmacenamiento, Integer maxCategoriasProductos, Integer maxCategoriasActivos, Integer maxRelaciones, Integer precio) {
        this.id = id;
        this.nombre = nombre;
        this.maxProductos = maxProductos;
        this.maxActivos = maxActivos;
        this.maxAlmacenamiento = maxAlmacenamiento;
        this.maxCategoriasProductos = maxCategoriasProductos;
        this.maxCategoriasActivos = maxCategoriasActivos;
        this.maxRelaciones = maxRelaciones;
        this.precio = precio;
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

    public Integer getMaxProductos() {
        return maxProductos;
    }

    public void setMaxProductos(Integer maxProductos) {
        this.maxProductos = maxProductos;
    }

    public Integer getMaxActivos() {
        return maxActivos;
    }

    public void setMaxActivos(Integer maxActivos) {
        this.maxActivos = maxActivos;
    }

    public Integer getMaxAlmacenamiento() {
        return maxAlmacenamiento;
    }

    public void setMaxAlmacenamiento(Integer maxAlmacenamiento) {
        this.maxAlmacenamiento = maxAlmacenamiento;
    }

    public Integer getMaxCategoriasProductos() {
        return maxCategoriasProductos;
    }

    public void setMaxCategoriasProductos(Integer maxCategoriasProductos) {
        this.maxCategoriasProductos = maxCategoriasProductos;
    }

    public Integer getMaxCategoriasActivos() {
        return maxCategoriasActivos;
    }

    public void setMaxCategoriasActivos(Integer maxCategoriasActivos) {
        this.maxCategoriasActivos = maxCategoriasActivos;
    }

    public Integer getMaxRelaciones() {
        return maxRelaciones;
    }

    public void setMaxRelaciones(Integer maxRelaciones) {
        this.maxRelaciones = maxRelaciones;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PlanDTO planDTO = (PlanDTO) o;
        return Objects.equals(id, planDTO.id) && Objects.equals(nombre, planDTO.nombre) && Objects.equals(maxProductos, planDTO.maxProductos) && Objects.equals(maxActivos, planDTO.maxActivos) && Objects.equals(maxAlmacenamiento, planDTO.maxAlmacenamiento) && Objects.equals(maxCategoriasProductos, planDTO.maxCategoriasProductos) && Objects.equals(maxCategoriasActivos, planDTO.maxCategoriasActivos) && Objects.equals(maxRelaciones, planDTO.maxRelaciones) && Objects.equals(precio, planDTO.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, maxProductos, maxActivos, maxAlmacenamiento, maxCategoriasProductos, maxCategoriasActivos, maxRelaciones, precio);
    }
}
