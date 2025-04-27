package com.uma.wanchinchun.dtos;

import java.util.Set;

public class CategoryDTO {
    private Long id;
    private String nombre;
    private Set<Long> productosIds; // Solo guardamos los IDs de los productos

    // Constructores
    public CategoryDTO() {}

    public CategoryDTO(Long id, String nombre, Set<Long> productosIds) {
        this.id = id;
        this.nombre = nombre;
        this.productosIds = productosIds;
    }

    // Getters y setters
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

    public Set<Long> getProductosIds() {
        return productosIds;
    }

    public void setProductosIds(Set<Long> productosIds) {
        this.productosIds = productosIds;
    }
}