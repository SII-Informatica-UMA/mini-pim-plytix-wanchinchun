package com.uma.wanchinchun.models;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ProductRelationshipID {
    private Long idProductoOrigen;
    private Long idProductoDestino;

    public ProductRelationshipID() {}

    public ProductRelationshipID(Long idProductoOrigen, Long idProductoDestino) {
        this.idProductoOrigen = idProductoOrigen;
        this.idProductoDestino = idProductoDestino;
    }

    public Long getIdProductoOrigen() {
        return idProductoOrigen;
    }

    public void setIdProductoOrigen(Long idProductoOrigen) {
        this.idProductoOrigen = idProductoOrigen;
    }

    public Long getIdProductoDestino() {
        return idProductoDestino;
    }

    public void setIdProductoDestino(Long idProductoDestino) {
        this.idProductoDestino = idProductoDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRelationshipID that = (ProductRelationshipID) o;
        return idProductoOrigen.equals(that.idProductoOrigen) && idProductoDestino.equals(that.idProductoDestino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductoOrigen, idProductoDestino);
    }
}
