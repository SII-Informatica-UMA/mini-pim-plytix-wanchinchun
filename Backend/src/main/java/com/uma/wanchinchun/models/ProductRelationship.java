package com.uma.wanchinchun.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@IdClass(ProductRelationshipID.class)
@Table(name = "productos_relacionados")
public class ProductRelationship {
    @Id
    private Long idProductoOrigen;
    @Id
    private Long idProductoDestino;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProductoOrigen")
    private Product productoOrigen;
    @ManyToOne
    @JoinColumn(name = "idProductoDestino")
    private Product productoDestino;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "relacion_fk")
    private Relationship relacion;

    public ProductRelationship() {}

    public ProductRelationship(Long idProductoOrigen, Long idProductoDestino, Product productoOrigen, Product productoDestino, Relationship relacion) {
        this.idProductoOrigen = idProductoOrigen;
        this.idProductoDestino = idProductoDestino;
        this.productoOrigen = productoOrigen;
        this.productoDestino = productoDestino;
        this.relacion = relacion;
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

    public Product getProductoOrigen() {
        return productoOrigen;
    }

    public void setProductoOrigen(Product productoOrigen) {
        this.productoOrigen = productoOrigen;
    }

    public Product getProductoDestino() {
        return productoDestino;
    }

    public void setProductoDestino(Product productoDestino) {
        this.productoDestino = productoDestino;
    }

    public Relationship getRelacion() {
        return relacion;
    }

    public void setRelacion(Relationship relacion) {
        this.relacion = relacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRelationship that = (ProductRelationship) o;
        return idProductoOrigen.equals(that.idProductoOrigen) &&
                idProductoDestino.equals(that.idProductoDestino) &&
                productoOrigen.equals(that.productoOrigen) &&
                productoDestino.equals(that.productoDestino) &&
                relacion.equals(that.relacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProductoOrigen, idProductoDestino, productoOrigen, productoDestino, relacion);
    }
}
