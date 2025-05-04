package com.uma.wanchinchun.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "productos")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String gtin;
    @Column(unique = true, nullable = false)
    private String sku;
    @Column(nullable = false)
    private String nombre;
    private String textoCorto;
    @CreationTimestamp
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private OffsetDateTime creado;
    //@Temporal(TemporalType.TIMESTAMP)
    private OffsetDateTime modificado;
    private String miniatura;
    @Column(nullable = false)
    private Long idCuenta;
    @ElementCollection
    @CollectionTable(name = "atributos_producto", joinColumns = @JoinColumn(name = "id_producto"))
    private List<Attribute> attributes;
    @ManyToMany
    @JoinTable(name = "producto_categoria",
            joinColumns = @JoinColumn(name = "producto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private Set<Category> categorias;
    @OneToMany(mappedBy = "idProductoOrigen")
    private List<ProductRelationship> relacionesOrigen;
    @OneToMany(mappedBy = "idProductoDestino")
    private List<ProductRelationship> relacionesDestino;

    public Product() {}

    public Product(Long id, String gtin, String sku, String nombre, String textoCorto,
                   OffsetDateTime creado, OffsetDateTime modificado, String miniatura,
                   Long idCuenta, List<Attribute> attributes, Set<Category> categorias,
                   List<ProductRelationship> relacionesOrigen,
                   List<ProductRelationship> relacionesDestino) {
        this.id = id;
        this.gtin = gtin;
        this.sku = sku;
        this.nombre = nombre;
        this.textoCorto = textoCorto;
        this.creado = creado;
        this.modificado = modificado;
        this.miniatura = miniatura;
        this.idCuenta = idCuenta;
        this.attributes = attributes;
        this.categorias = categorias;
        this.relacionesOrigen = relacionesOrigen;
        this.relacionesDestino = relacionesDestino;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTextoCorto() {
        return textoCorto;
    }

    public void setTextoCorto(String textoCorto) {
        this.textoCorto = textoCorto;
    }

    public OffsetDateTime getCreado() {
        return creado;
    }

    public void setCreado(OffsetDateTime creado) {
        this.creado = creado;
    }

    public OffsetDateTime getModificado() {
        return modificado;
    }

    public void setModificado(OffsetDateTime modificado) {
        this.modificado = modificado;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Set<Category> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Category> categorias) {
        this.categorias = categorias;
    }

    public List<ProductRelationship> getRelacionesOrigen() {
        return relacionesOrigen;
    }

    public void setRelacionesOrigen(List<ProductRelationship> relacionesOrigen) {
        this.relacionesOrigen = relacionesOrigen;
    }

    public List<ProductRelationship> getRelacionesDestino() {
        return relacionesDestino;
    }

    public void setRelacionesDestino(List<ProductRelationship> relacionesDestino) {
        this.relacionesDestino = relacionesDestino;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(gtin, product.gtin) && Objects.equals(sku, product.sku) && Objects.equals(nombre, product.nombre) && Objects.equals(textoCorto, product.textoCorto) && Objects.equals(creado, product.creado) && Objects.equals(modificado, product.modificado) && Objects.equals(miniatura, product.miniatura) && Objects.equals(idCuenta, product.idCuenta) && Objects.equals(attributes, product.attributes) && Objects.equals(categorias, product.categorias) && Objects.equals(relacionesOrigen, product.relacionesOrigen) && Objects.equals(relacionesDestino, product.relacionesDestino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gtin, sku, nombre, textoCorto, creado, modificado, miniatura, idCuenta, attributes, categorias, relacionesOrigen, relacionesDestino);
    }
}
