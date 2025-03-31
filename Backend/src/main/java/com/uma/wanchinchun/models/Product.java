package com.uma.wanchinchun.models;

import jakarta.persistence.*;

import java.util.*;

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
    @Lob
    private byte[] miniatura;
    @ElementCollection
    @CollectionTable(name = "atributos_producto", joinColumns = @JoinColumn(name = "id_producto"))
    private List<Atributo> atributos;
    @ManyToMany
    @JoinTable(name = "producto_categoria",
            joinColumns = @JoinColumn(name = "producto_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private Set<Category> categorias;
    @OneToMany(mappedBy = "idProductoOrigen")
    private List<ProductRelationship> relacionesOrigen;
    @OneToMany(mappedBy = "idProductoDestino")
    private List<ProductRelationship> relacionesDestino;

    public Product() {}

    public Product(Long id, String gtin, String sku, String nombre, String textoCorto, Date creado, Date modificado, byte[] miniatura, List<Atributo> atributos, Set<Category> categorias, List<ProductRelationship> relacionesOrigen, List<ProductRelationship> relacionesDestino) {
        this.id = id;
        this.gtin = gtin;
        this.sku = sku;
        this.nombre = nombre;
        this.textoCorto = textoCorto;
        this.creado = creado;
        this.modificado = modificado;
        this.miniatura = miniatura;
        this.atributos = atributos;
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

    public Date getCreado() {
        return creado;
    }

    public void setCreado(Date creado) {
        this.creado = creado;
    }

    public Date getModificado() {
        return modificado;
    }

    public void setModificado(Date modificado) {
        this.modificado = modificado;
    }

    public byte[] getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(byte[] miniatura) {
        this.miniatura = miniatura;
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Atributo> atributos) {
        this.atributos = atributos;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) && gtin.equals(product.gtin) && sku.equals(product.sku) && nombre.equals(product.nombre) && textoCorto.equals(product.textoCorto) && creado.equals(product.creado) && modificado.equals(product.modificado) && Arrays.equals(miniatura, product.miniatura) && atributos.equals(product.atributos) && categorias.equals(product.categorias) && relacionesOrigen.equals(product.relacionesOrigen) && relacionesDestino.equals(product.relacionesDestino);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, gtin, sku, nombre, textoCorto, creado, modificado, atributos, categorias, relacionesOrigen, relacionesDestino);
        result = 31 * result + Arrays.hashCode(miniatura);
        return result;
    }
}
