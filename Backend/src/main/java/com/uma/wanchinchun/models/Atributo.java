package com.uma.wanchinchun.models;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Atributo {
    private String nombre;
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
        Atributo atributo = (Atributo) o;
        return nombre.equals(atributo.nombre) && valor.equals(atributo.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, valor);
    }
}
