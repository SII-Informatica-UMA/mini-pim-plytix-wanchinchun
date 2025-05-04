package com.uma.wanchinchun.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Attribute {
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String valor;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return nombre.equals(attribute.nombre) && valor.equals(attribute.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, valor);
    }
}
