package com.uma.wanchinchun.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "productos")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String gtin;
    private String sku;
    private String nombre;
    private String textoCorto;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creado;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificado;
    private byte[] miniatura;
    @ElementCollection
    @CollectionTable(name = "atributos_producto", joinColumns = @JoinColumn(name = "id_producto"))
    private List<Atributo> atributos;
    @ManyToMany
    @JoinTable(name = "producto_categoria", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "producto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<Category> categorias;
    // private List<> relaciones;

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", nombre='" + nombre + "'}";
    }

    public Product() {
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getGtin() {
        return gtin;
    }

    public String getSku() {
        return sku;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTextoCorto() {
        return textoCorto;
    }

    public Date getCreado() {
        return creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public Set<Category> getCategorias() {
        return categorias;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTextoCorto(String textoCorto) {
        this.textoCorto = textoCorto;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public void setCategorias(Set<Category> categorias) {
        this.categorias = categorias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(gtin, product.gtin) &&
                Objects.equals(sku, product.sku) &&
                Objects.equals(nombre, product.nombre) &&
                Objects.equals(textoCorto, product.textoCorto) &&
                Objects.equals(creado, product.creado) &&
                Objects.equals(modificado, product.modificado) &&
                Objects.equals(miniatura, product.miniatura) &&
                Objects.equals(categorias, product.categorias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gtin, sku, nombre, textoCorto, creado, modificado, miniatura, categorias);
    }
}
