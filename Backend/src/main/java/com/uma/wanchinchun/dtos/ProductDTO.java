package com.uma.wanchinchun.dtos;

import com.uma.wanchinchun.models.Attribute;
import com.uma.wanchinchun.models.Category;
import com.uma.wanchinchun.models.ProductRelationship;

import java.time.OffsetDateTime;
import java.util.List;

public class ProductDTO {
    private Long id;
    private String gtin;
    private String sku;
    private String nombre;
    private String textoCorto;
    private OffsetDateTime creado;
    private OffsetDateTime modificado;
    private String miniatura;
    private List<Attribute> atributos;
    private List<Category> categorias;
    private List<ProductRelationship> relaciones;

    public ProductDTO() {}

    public ProductDTO(Long id, String gtin, String sku, String nombre, String textoCorto, OffsetDateTime creado, OffsetDateTime modificado, String miniatura, List<Attribute> atributos, List<Category> categorias, List<ProductRelationship> relaciones) {
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
        this.relaciones = relaciones;
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

    public List<Attribute> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<Attribute> atributos) {
        this.atributos = atributos;
    }

    public List<Category> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Category> categorias) {
        this.categorias = categorias;
    }

    public List<ProductRelationship> getRelaciones() {
        return relaciones;
    }

    public void setRelaciones(List<ProductRelationship> relaciones) {
        this.relaciones = relaciones;
    }
}
